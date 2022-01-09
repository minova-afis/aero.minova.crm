package aero.minova.crm.service.trac;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.function.Consumer;

import org.eclipse.core.runtime.Platform;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.MarkupText;
import aero.minova.crm.model.jpa.Ticket;
import aero.minova.crm.model.jpa.TicketAttachment;
import aero.minova.crm.model.service.MilestoneService;
import aero.minova.crm.model.service.TicketComponentService;
import aero.minova.crm.model.service.TicketCustomerPrioService;
import aero.minova.crm.model.service.TicketCustomerStateService;
import aero.minova.crm.model.service.TicketCustomerTypeService;
import aero.minova.crm.model.service.TicketPriorityService;
import aero.minova.crm.model.service.TicketResolutionService;
import aero.minova.crm.model.service.TicketService;
import aero.minova.crm.model.service.TicketStateService;
import aero.minova.crm.model.service.TicketTypeService;
import aero.minova.crm.service.trac.converter.TicketToModel;
import aero.minova.crm.service.trac.converter.TracMilestoneConverter;
import aero.minova.crm.service.trac.converter.TracTicketComponentConverter;
import aero.minova.crm.service.trac.converter.TracTicketCustomerPrioConverter;
import aero.minova.crm.service.trac.converter.TracTicketCustomerStateConverter;
import aero.minova.crm.service.trac.converter.TracTicketCustomerTypeConverter;
import aero.minova.crm.service.trac.converter.TracTicketPriorityConverter;
import aero.minova.crm.service.trac.converter.TracTicketResolutionConverter;
import aero.minova.crm.service.trac.converter.TracTicketStateConverter;
import aero.minova.crm.service.trac.converter.TracTicketTypeConverter;
import aero.minova.trac.TracService;
import aero.minova.trac.domain.TracTicket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Component(service = TicketService.class)
public class TicketServiceImpl implements TicketService {
	private EntityManager entityManager;

	@Reference
	private DatabaseService databaseService;

	@Reference
	private TracService tracService;

	@Reference
	private MilestoneService milestoneService;
	@Reference
	private TicketComponentService ticketComponentService;
	@Reference
	private TicketCustomerPrioService ticketCustomerPrioService;
	@Reference
	private TicketCustomerStateService ticketCustomerStateService;
	@Reference
	private TicketCustomerTypeService ticketCustomerTypeService;
	@Reference
	private TicketPriorityService ticketPriorityService;
	@Reference
	private TicketStateService ticketStateService;
	@Reference
	private TicketTypeService ticketTypeService;
	@Reference
	private TicketResolutionService ticketResolutionService;

	@Deactivate
	protected void deactivateComponent() {
		if (entityManager != null) {
			entityManager.close();
			entityManager = null;
		}
	}

	@Override
	public void getTickets(Consumer<List<Ticket>> ticketsConsumer) {
		checkEntityManager();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Ticket> cq = cb.createQuery(Ticket.class);
		Root<Ticket> rootTicket = cq.from(Ticket.class);
		CriteriaQuery<Ticket> allTickets = cq.select(rootTicket);
		TypedQuery<Ticket> ticketsQuery = entityManager.createQuery(allTickets);

		List<Ticket> ticketList = ticketsQuery.getResultList();
		ticketsConsumer.accept(ticketList);
	}

	private void checkEntityManager() {
		if (entityManager != null)
			return;
		entityManager = databaseService.getEntityManager();
	}

	// create or update an existing instance of Todo
	@Override
	public synchronized boolean saveTicket(Ticket newTicket) {
		checkEntityManager();
		Ticket find = entityManager.find(Ticket.class, newTicket.getId());
		if (find != null) {
			entityManager.getTransaction().begin();
			entityManager.merge(newTicket);
			entityManager.getTransaction().commit();
		} else {
			entityManager.getTransaction().begin();
			entityManager.persist(newTicket);
			entityManager.getTransaction().commit();
		}
		return true;
	}

	private synchronized boolean saveTicketAttachment(TicketAttachment newEntity) {
		checkEntityManager();
		TicketAttachment entity = null;
		if (newEntity.getId() != 0)
			entity = entityManager.find(TicketAttachment.class, newEntity.getId());
		if (entity != null) {
			entityManager.getTransaction().begin();
			entityManager.merge(newEntity);
			entityManager.getTransaction().commit();
		} else {
			entityManager.getTransaction().begin();
			entityManager.persist(newEntity);
			entityManager.getTransaction().commit();
		}
		return true;
	}

	@Override
	public Optional<Ticket> getTicket(int id) {
		checkEntityManager();
		Ticket find = entityManager.find(Ticket.class, id);
		if (find == null) {
			find = getTicketFromTrac(id);
		}
		return Optional.ofNullable(find);
	}

	private Ticket getTicketFromTrac(int id) {
		TracTicket tracTicket = tracService.getTicket(id);

		Ticket ticket = TicketToModel.getTicket(tracTicket);
		ticket.setMilestone(TracMilestoneConverter.get(tracTicket, tracService, milestoneService));
		ticket.setComponent(TracTicketComponentConverter.get(tracTicket, tracService, ticketComponentService));
		ticket.setCustomerPrio(TracTicketCustomerPrioConverter.get(tracTicket, tracService, ticketCustomerPrioService));
		ticket.setCustomerState(
				TracTicketCustomerStateConverter.get(tracTicket, tracService, ticketCustomerStateService));
		ticket.setCustomerType(TracTicketCustomerTypeConverter.get(tracTicket, tracService, ticketCustomerTypeService));
		ticket.setPriority(TracTicketPriorityConverter.get(tracTicket, tracService, ticketPriorityService));
		ticket.setState(TracTicketStateConverter.get(tracTicket, tracService, ticketStateService));
		ticket.setType(TracTicketTypeConverter.get(tracTicket, tracService, ticketTypeService));
		ticket.setResolution(TracTicketResolutionConverter.get(tracTicket, tracService, ticketResolutionService));

		MarkupText description = new MarkupText();
		description.setMarkup(tracTicket.getDescription());
		try {
			description.setHtml(tracService.wikiToHtml(tracTicket.getDescription()));
		} catch (Exception e) {
			description.setHtml(null);
		}
		ticket.setDescription(description);
		saveTicket(ticket);

		getAttachmentsFromTrac(ticket);

		return ticket;
	}

	private void getAttachmentsFromTrac(Ticket ticket) {
		Vector<?> ticketAttachments = tracService.getTicketAttachments(ticket.getId());

		for (Object tracAttachment : ticketAttachments) {
			Object b[] = (Object[]) tracAttachment;
			TicketAttachment ticketAttachment = new TicketAttachment();
			ticketAttachment.setName((String) b[0]);
			ticketAttachment.setDescription((String) b[1]);
			ticketAttachment.setSize((int) b[2]);
			Date d = (Date) b[3];
			if (d != null) {
				ticketAttachment.setLastDate(d.toInstant());
			}
			ticketAttachment.setLastUser((String) b[4]);
			ticketAttachment.setTicket(ticket);

			saveTicketAttachment(ticketAttachment);

			downloadAttachment(ticketAttachment);
		}
	}

	private void downloadAttachment(TicketAttachment ticketAttachment) {
		FileOutputStream fos = null;
		try {
			File f = new File(Platform.getInstanceLocation().getURL().toURI());
			File dir = makeDir(f, "attachement/ticket/" + ticketAttachment.getTicket().getId());
			File atFile = new File(dir, ticketAttachment.getName());
			fos = new FileOutputStream(atFile);
			fos.write(
					tracService.getTicketAttachment(ticketAttachment.getTicket().getId(), ticketAttachment.getName()));
			fos.close();
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
				}
		}
	}

	private File makeDir(File f, String string) {
		File dir = new File(f.getAbsoluteFile() + "/" + string);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	@Override
	public boolean deleteTicket(int id) {
		checkEntityManager();
		entityManager.getTransaction().begin();
		Ticket find = entityManager.find(Ticket.class, id);
		entityManager.remove(find);
		entityManager.getTransaction().commit();
		return true;
	}
}

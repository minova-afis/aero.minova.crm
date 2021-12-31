package aero.minova.crm.service.jpa;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.Ticket;
import aero.minova.crm.model.service.TicketService;
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
		if (entityManager != null) return;
		entityManager = databaseService.getEntityManager();
	}

	// create or update an existing instance of Todo
	@Override
	public synchronized boolean saveTicket(Ticket newTicket) {
		checkEntityManager();
		Optional<Ticket> ticketOptional = getTicket(newTicket.getId());
		if (ticketOptional.isPresent()) {
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

	@Override
	public Optional<Ticket> getTicket(int id) {
		checkEntityManager();
		Ticket find = entityManager.find(Ticket.class, id);
		return Optional.ofNullable(find);
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

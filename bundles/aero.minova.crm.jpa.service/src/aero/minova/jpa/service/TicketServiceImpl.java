package aero.minova.jpa.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.Ticket;
import aero.minova.crm.model.jpa.service.TicketService;
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
		// hold the Optional object as reference to determine, if the Todo is
		// newly created or not
		Optional<Ticket> ticketOptional = getTicket(newTicket.getId());

		// get the actual todo or create a new one
		Ticket ticket = ticketOptional.orElse(new Ticket());
		ticket.setId(newTicket.getId());
		ticket.setSummary(newTicket.getSummary());
		ticket.setDescription(newTicket.getDescription());

		// send out events
		if (ticketOptional.isPresent()) {
			entityManager.getTransaction().begin();
			entityManager.merge(ticket);
			entityManager.getTransaction().commit();
		} else {
			entityManager.getTransaction().begin();
			entityManager.persist(ticket);
			entityManager.getTransaction().commit();
		}
		return true;
	}

	@Override
	public Optional<Ticket> getTicket(int id) {
		checkEntityManager();
		entityManager.getTransaction().begin();
		Ticket find = entityManager.find(Ticket.class, id);
		entityManager.getTransaction().commit();

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

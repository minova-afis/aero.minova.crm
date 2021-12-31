package aero.minova.crm.service.jpa;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.TicketComponent;
import aero.minova.crm.model.service.TicketComponentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Component(service = TicketComponentService.class)
public class TicketComponentServiceImpl implements TicketComponentService {
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
	public void getTicketComponents(Consumer<List<TicketComponent>> ticketComponentsConsumer) {
		checkEntityManager();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<TicketComponent> cq = cb.createQuery(TicketComponent.class);
		Root<TicketComponent> rootTicket = cq.from(TicketComponent.class);
		CriteriaQuery<TicketComponent> allTickets = cq.select(rootTicket);
		TypedQuery<TicketComponent> ticketsQuery = entityManager.createQuery(allTickets);
		List<TicketComponent> ticketList = ticketsQuery.getResultList();
		ticketComponentsConsumer.accept(ticketList);
	}

	private void checkEntityManager() {
		if (entityManager != null) return;
		entityManager = databaseService.getEntityManager();
	}

	@Override
	public synchronized boolean saveTicketComponent(TicketComponent ticketComponent) {
		checkEntityManager();
		Optional<TicketComponent> ticketComponentOptional = getTicketComponent(ticketComponent.getId());
		if (ticketComponentOptional.isPresent()) {
			entityManager.getTransaction().begin();
			entityManager.merge(ticketComponent);
			entityManager.getTransaction().commit();
		} else {
			entityManager.getTransaction().begin();
			entityManager.persist(ticketComponent);
			entityManager.getTransaction().commit();
		}
		return true;
	}

	@Override
	public Optional<TicketComponent> getTicketComponent(int id) {
		checkEntityManager();
		TicketComponent find = entityManager.find(TicketComponent.class, id);
		return Optional.ofNullable(find);
	}

	@Override
	public boolean deleteTicketComponent(int id) {
		checkEntityManager();
		entityManager.getTransaction().begin();
		TicketComponent find = entityManager.find(TicketComponent.class, id);
		entityManager.remove(find);
		entityManager.getTransaction().commit();
		return true;
	}

	@Override
	public Optional<TicketComponent> getTicketComponent(String name) {
		TicketComponent ticketComponent = null;
		checkEntityManager();
		Query query = entityManager.createQuery("SELECT e FROM TicketComponent e WHERE e.name = :name");
		query.setParameter("name", name);
		try {
			ticketComponent = (TicketComponent) query.getSingleResult();
		} catch (NoResultException nre) {}
		return Optional.ofNullable(ticketComponent);
	}
}

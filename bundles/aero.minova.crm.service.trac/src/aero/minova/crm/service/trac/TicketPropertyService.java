package aero.minova.crm.service.trac;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.osgi.service.component.annotations.Deactivate;

import aero.minova.crm.model.jpa.TicketProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public abstract class TicketPropertyService<TP extends TicketProperty> {
	private EntityManager entityManager;

	private final Class<TP> ticketPropertyClass;

	public TicketPropertyService(Class<TP> clazz) {
		ticketPropertyClass = clazz;
	}

	protected abstract DatabaseService getDatabaseService();

	private void checkEntityManager() {
		if (entityManager != null && entityManager.isOpen())
			return;
		entityManager = getDatabaseService().getEntityManager();
	}

	@Deactivate
	protected void deactivateComponent() {
		if (entityManager != null && entityManager.isOpen()) {
			entityManager.close();
			entityManager = null;
		}
	}

	public List<TP> getAll() {
		checkEntityManager();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<TP> cq = cb.createQuery(ticketPropertyClass);
		Root<TP> rootTicket = cq.from(ticketPropertyClass);
		CriteriaQuery<TP> allTickets = cq.select(rootTicket);
		TypedQuery<TP> ticketsQuery = entityManager.createQuery(allTickets);
		return ticketsQuery.getResultList();
	}

	public void get(Consumer<List<TP>> ticketPropertiesConsumer) {
		checkEntityManager();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<TP> cq = cb.createQuery(ticketPropertyClass);
		Root<TP> rootTicket = cq.from(ticketPropertyClass);
		CriteriaQuery<TP> allTickets = cq.select(rootTicket);
		TypedQuery<TP> ticketsQuery = entityManager.createQuery(allTickets);
		List<TP> ticketList = ticketsQuery.getResultList();
		ticketPropertiesConsumer.accept(ticketList);
	}

	public synchronized boolean save(TP ticketProperty) {
		checkEntityManager();
		Optional<TP> ticketPropertyOptional = get(ticketProperty.getId());
		if (ticketPropertyOptional.isPresent()) {
			entityManager.getTransaction().begin();
			entityManager.merge(ticketProperty);
			entityManager.getTransaction().commit();
		} else {
			entityManager.getTransaction().begin();
			entityManager.persist(ticketProperty);
			entityManager.getTransaction().commit();
		}
		return true;
	}

	public Optional<TP> get(int id) {
		checkEntityManager();
		TP find = entityManager.find(ticketPropertyClass, id);
		return Optional.ofNullable(find);
	}

	public boolean delete(int id) {
		checkEntityManager();
		entityManager.getTransaction().begin();
		TP find = entityManager.find(ticketPropertyClass, id);
		entityManager.remove(find);
		entityManager.getTransaction().commit();
		return true;
	}

	public Optional<TP> get(String name) {
		TP ticketProperty = null;
		checkEntityManager();
		Query query = entityManager.createQuery("SELECT e FROM TicketProperty e WHERE e.name = :name");
		query.setParameter("name", name);
		try {
			@SuppressWarnings("unchecked")
			List<TicketProperty> resultList = query.getResultList();
			for (Object object : resultList) {
				if (ticketPropertyClass.isInstance(object)) {
					ticketProperty = ticketPropertyClass.cast(object);
					return Optional.of(ticketProperty);
				}
			}
		} catch (NoResultException nre) {
		}
		return Optional.ofNullable(ticketProperty);
	}
}

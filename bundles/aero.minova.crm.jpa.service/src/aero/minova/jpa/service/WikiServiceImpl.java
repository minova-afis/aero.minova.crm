package aero.minova.jpa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import aero.minova.crm.model.jpa.WikiPage;
import aero.minova.crm.model.jpa.service.WikiService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

@Component(service = WikiService.class)
public class WikiServiceImpl implements WikiService {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	@Activate
	@SuppressWarnings("unchecked")
	protected void activateComponent() {
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put(PersistenceUnitProperties.CLASSLOADER, getClass().getClassLoader());
		map.put(PersistenceUnitProperties.WEAVING, true);

		PersistenceProvider persistenceProvider = new PersistenceProvider();
		entityManagerFactory = persistenceProvider.createEntityManagerFactory("h2-eclipselink", map);
		entityManager = entityManagerFactory.createEntityManager();
	}

	@Deactivate
	protected void deactivateComponent() {
		entityManager.close();
		entityManagerFactory.close();
		entityManager = null;
		entityManagerFactory = null;
	}

	public WikiServiceImpl() {}

	@Override
	public boolean saveWikiPage(WikiPage newPage) {
		// hold the Optional object as reference to determine, if the Todo is
		// newly created or not
		Optional<WikiPage> pageOptional = getWikiPage(newPage.getPath());

		// get the actual todo or create a new one
		WikiPage page = pageOptional.orElse(new WikiPage());
		page.setPath(newPage.getPath());
		page.setDescription(newPage.getDescription());
		page.setComment(newPage.getComment());
		page.setLastModified(newPage.getLastModified());
		page.setLastUser(newPage.getLastUser());
		page.setVersion(newPage.getVersion());

		// send out events
		if (pageOptional.isPresent()) {
			entityManager.getTransaction().begin();
			entityManager.merge(page);
			entityManager.getTransaction().commit();
		} else {
			entityManager.getTransaction().begin();
			entityManager.persist(page);
			entityManager.getTransaction().commit();
		}
		return true;
	}

	@Override
	public Optional<WikiPage> getWikiPage(int id) {
		entityManager.getTransaction().begin();
		WikiPage find = entityManager.find(WikiPage.class, id);
		entityManager.getTransaction().commit();

		return Optional.ofNullable(find);
	}

	@Override
	public Optional<WikiPage> getWikiPage(String path) {
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("SELECT w FROM WikiPage w WHERE w.path = :path");
		query.setParameter("path", path);
		List<WikiPage> page = query.getResultList();
		entityManager.getTransaction().commit();

		if (page.size() == 0) return Optional.empty();
		return Optional.of(page.get(0));
	}

//	@Override
//	public void getTickets(Consumer<List<Ticket>> ticketsConsumer) {
//		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//		CriteriaQuery<Ticket> cq = cb.createQuery(Ticket.class);
//		Root<Ticket> rootTicket = cq.from(Ticket.class);
//		CriteriaQuery<Ticket> allTickets = cq.select(rootTicket);
//		TypedQuery<Ticket> ticketsQuery = entityManager.createQuery(allTickets);
//
//		List<Ticket> ticketList = ticketsQuery.getResultList();
//		ticketsConsumer.accept(ticketList);
//	}
//
//	// create or update an existing instance of Todo
//	@Override
//	public synchronized boolean saveTicket(Ticket newTicket) {
//		// hold the Optional object as reference to determine, if the Todo is
//		// newly created or not
//		Optional<Ticket> ticketOptional = getTicket(newTicket.getId());
//
//		// get the actual todo or create a new one
//		Ticket ticket = ticketOptional.orElse(new Ticket());
//		ticket.setId(newTicket.getId());
//		ticket.setSummary(newTicket.getSummary());
//		ticket.setDescription(newTicket.getDescription());
//
//		// send out events
//		if (ticketOptional.isPresent()) {
//			entityManager.getTransaction().begin();
//			entityManager.merge(ticket);
//			entityManager.getTransaction().commit();
//		} else {
//			entityManager.getTransaction().begin();
//			entityManager.persist(ticket);
//			entityManager.getTransaction().commit();
//		}
//		return true;
//	}
//
//	@Override
//	public Optional<Ticket> getTicket(int id) {
//		entityManager.getTransaction().begin();
//		Ticket find = entityManager.find(Ticket.class, id);
//		entityManager.getTransaction().commit();
//
//		return Optional.ofNullable(find);
//	}
//
//	@Override
//	public boolean deleteTicket(int id) {
//		entityManager.getTransaction().begin();
//		Ticket find = entityManager.find(Ticket.class, id);
//		entityManager.remove(find);
//		entityManager.getTransaction().commit();
//
//		return true;
//	}
}

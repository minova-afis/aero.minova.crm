package aero.minova.crm.service.jpa;

import java.util.List;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.WikiPage;
import aero.minova.crm.model.service.jpa.WikiService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Component(service = WikiService.class)
public class WikiServiceImpl implements WikiService {
	private EntityManager entityManager;

	@Reference
	private DatabaseService databaseService;

	@Deactivate
	protected void deactivateComponent() {
		if (entityManager == null) return;
		entityManager.close();
		entityManager = null;
	}

	private void checkEntityManager() {
		if (entityManager != null) return;
		entityManager = databaseService.getEntityManager();
	}

	@Override
	public boolean saveWikiPage(WikiPage newPage) {
		checkEntityManager();
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
		checkEntityManager();
		entityManager.getTransaction().begin();
		WikiPage find = entityManager.find(WikiPage.class, id);
		entityManager.getTransaction().commit();

		return Optional.ofNullable(find);
	}

	@Override
	public Optional<WikiPage> getWikiPage(String path) {
		checkEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("SELECT w FROM WikiPage w WHERE w.path = :path");
		query.setParameter("path", path);
		@SuppressWarnings("unchecked")
		List<WikiPage> page = query.getResultList();
		entityManager.getTransaction().commit();

		if (page.size() == 0) return Optional.empty();
		return Optional.of(page.get(0));
	}
}

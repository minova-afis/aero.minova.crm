package aero.minova.crm.service.trac;

import java.util.List;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.Wiki;
import aero.minova.crm.model.service.WikiService;
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
	public boolean saveWikiPage(Wiki page) {
		checkEntityManager();
		Optional<Wiki> pageOptional = getWikiPage(page.getPath());
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
	public Optional<Wiki> getWikiPage(int id) {
		checkEntityManager();
		Wiki find = entityManager.find(Wiki.class, id);
		return Optional.ofNullable(find);
	}

	@Override
	public Optional<Wiki> getWikiPage(String path) {
		checkEntityManager();
		Query query = entityManager.createQuery("SELECT w FROM WikiPage w WHERE w.path = :path");
		query.setParameter("path", path);
		@SuppressWarnings("unchecked")
		List<Wiki> page = query.getResultList();

		if (page.size() == 0) return Optional.empty();
		return Optional.of(page.get(0));
	}
}

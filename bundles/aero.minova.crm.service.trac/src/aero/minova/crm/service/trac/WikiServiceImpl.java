package aero.minova.crm.service.trac;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.MarkupText;
import aero.minova.crm.model.jpa.Wiki;
import aero.minova.crm.model.service.WikiService;
import aero.minova.crm.service.trac.converter.WikiToModel;
import aero.minova.trac.TracService;
import aero.minova.trac.domain.TracWikiPage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Component(service = WikiService.class)
public class WikiServiceImpl implements WikiService {
	private EntityManager entityManager;

	@Reference
	private DatabaseService databaseService;

	@Reference
	private TracService tracService;

	@Deactivate
	protected void deactivateComponent() {
		if (entityManager == null)
			return;
		entityManager.close();
		entityManager = null;
	}

	private void checkEntityManager() {
		if (entityManager != null)
			return;
		entityManager = databaseService.getEntityManager();
	}

	@Override
	public boolean saveWiki(Wiki page) {
		checkEntityManager();
		Optional<Wiki> pageOptional = getWiki(page.getPath(), false);
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
	public Optional<Wiki> getWiki(int id) {
		checkEntityManager();
		Wiki find = entityManager.find(Wiki.class, id);
		return Optional.ofNullable(find);
	}

	@Override
	public Optional<Wiki> getWiki(String path) {
		return getWiki(path, true);
	}

	private Optional<Wiki> getWiki(String path, boolean searchTrac) {
		if (path.startsWith("/")) path = path.substring(1);
		Wiki wiki;
		checkEntityManager();
		Query query = entityManager.createQuery("SELECT w FROM Wiki w WHERE w.path = :path");
		query.setParameter("path", path);
		@SuppressWarnings("unchecked")
		List<Wiki> wikis = query.getResultList();

		wiki = (wikis.size() == 0) ? null : wikis.get(0);
		if (wiki == null && searchTrac) {
			wiki = getWikiFromTrac(path);
		}
		return Optional.ofNullable(wiki);
	}

	private Wiki getWikiFromTrac(String path) {
		TracWikiPage tracWiki = tracService.getWiki(path);

		Wiki wiki = WikiToModel.getWiki(tracWiki);
		wiki.setDescription(new MarkupText());
		wiki.getDescription().setMarkup(tracWiki.getContent());
		try {
			Hashtable<String, ?> infos = tracService.getPageInfo(path);
			Date lastModified = (Date) infos.get("lastModified");
			if (lastModified != null) {
				@SuppressWarnings("deprecation")
				Instant i = lastModified.toInstant().minusSeconds(lastModified.getTimezoneOffset() * 60);
				LocalDateTime ldt = LocalDateTime.ofInstant(i, ZoneId.of("UTC"));
				wiki.setLastModified(ldt);
			}
			wiki.setLastUser((String) infos.get("author"));
			wiki.setVersion((Integer) infos.get("version"));
			wiki.setComment((String) infos.get("comment"));
		} catch (Exception e) {}
		saveWiki(wiki);

		return wiki;
	}
}

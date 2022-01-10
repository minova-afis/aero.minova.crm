package aero.minova.crm.service.trac;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import org.eclipse.core.runtime.Platform;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.MarkupText;
import aero.minova.crm.model.jpa.Ticket;
import aero.minova.crm.model.jpa.TicketAttachment;
import aero.minova.crm.model.jpa.Wiki;
import aero.minova.crm.model.jpa.WikiAttachment;
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
	
	private boolean saveWikiAttachment(WikiAttachment newEntity) {
		checkEntityManager();
		WikiAttachment entity = null;
		if (newEntity.getId() != 0)
			entity = entityManager.find(WikiAttachment.class, newEntity.getId());
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
		if (path.startsWith("/"))
			path = path.substring(1);
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
		} catch (Exception e) {
		}
		saveWiki(wiki);

		getAttachmentsFromTrac(wiki);

		return wiki;
	}

	private void getAttachmentsFromTrac(Wiki wiki) {
		Vector<?> wikiAttachments = tracService.getWikiAttachments(wiki.getPath());

		for (Object tracAttachment : wikiAttachments) {
			String name = (String) tracAttachment;
			WikiAttachment wikiAttachment = new WikiAttachment();
			wikiAttachment.setName(name);
//			wikiAttachment.setDescription((String) b[1]);
//			wikiAttachment.setSize((int) b[2]);
//			Date d = (Date) b[3];
//			if (d != null) {
//				wikiAttachment.setLastDate(d.toInstant());
//			}
//			wikiAttachment.setLastUser((String) b[4]);
			wikiAttachment.setWiki(wiki);

			saveWikiAttachment(wikiAttachment);

			downloadAttachment(wikiAttachment);
		}
	}

	private void downloadAttachment(WikiAttachment wikiAttachment) {
		FileOutputStream fos = null;
		String name = wikiAttachment.getName();
		String filename = name.substring(0, name.lastIndexOf("/"));
		name = name.substring(name.lastIndexOf("/")+1);
		try {
			File f = new File(Platform.getInstanceLocation().getURL().toURI());
			File atFile = getFile(f, "attachment/wiki/" + wikiAttachment.getName());
			fos = new FileOutputStream(atFile);
			fos.write(tracService.getWikiAttachment(filename, name)); //wikiAttachment.getName()));
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
				}
		}
	}

	private File getFile(File f, String filename) {
		String dirname = filename.substring(0, filename.lastIndexOf("/"));
		File dir = new File(f.getAbsoluteFile() + "/" + dirname);

		if (!dir.exists()) {
			dir.mkdirs();
		}
		return new File(f.getAbsoluteFile() + "/" + filename);
	}
}

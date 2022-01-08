package aero.minova.crm.main.jobs;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;

import aero.minova.crm.model.jpa.MarkupText;
import aero.minova.crm.model.jpa.Wiki;
import aero.minova.crm.model.service.WikiService;
import aero.minova.trac.TracService;

public class DownloadWikiJob extends Job {

	private TracService tracService;
	private WikiService wikiService;

	public DownloadWikiJob(TracService tracService, WikiService wikiService) {
		super("Synchronize Wiki");
		this.tracService = tracService;
		this.wikiService = wikiService;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 100);

		subMonitor.worked(5);
		List<String> pageNames = tracService.listWikiPages();
		subMonitor.setWorkRemaining(pageNames.size() + 1);
		subMonitor.worked(1);

		for (String path : pageNames) {
			checkWikiPage(path);
			subMonitor.worked(1);
		}

		return Status.OK_STATUS;
	}

	private void checkWikiPage(String pagename) {
		try {
			Optional<Wiki> pageOptional = wikiService.getWikiPage(pagename);
			if (pageOptional.isPresent() && pageOptional.get().getLastModified() != null) return;
		} catch (Exception e) {
			System.out.println(e);
			return;
		}

		MarkupText mt = new MarkupText();
		Wiki page = new Wiki();
		page.setPath(pagename);
		page.setDescription(mt);

		try {
			Hashtable<String, ?> infos = tracService.getPageInfo(pagename);
			Date lastModified = (Date) infos.get("lastModified");
			if (lastModified != null) {
				@SuppressWarnings("deprecation")
				Instant i = lastModified.toInstant().minusSeconds(lastModified.getTimezoneOffset() * 60);
				LocalDateTime ldt = LocalDateTime.ofInstant(i, ZoneId.of("UTC"));
				page.setLastModified(ldt);
			}
			page.setLastUser((String) infos.get("author"));
			page.setVersion((Integer) infos.get("version"));
			page.setComment((String) infos.get("comment"));
		} catch (Exception e) {}
		mt.setMarkup(tracService.getPage(pagename));

		// HTML-Code laden / wir versuchen es mal ohne
//		try {
//			mt.setHtml(tracService.getPageHTML(pagename));
//			if (mt.getHtml().length() > 1000000) {
//				// Kann nicht mehr sauber in der Datenabnk gespeichert werden
//				mt.setHtml(null);
//			}
//		} catch (Exception e) {
//			mt.setHtml(null);
//		}

		wikiService.saveWikiPage(page);
	}

}

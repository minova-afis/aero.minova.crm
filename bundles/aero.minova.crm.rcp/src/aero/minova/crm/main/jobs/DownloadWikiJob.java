package aero.minova.crm.main.jobs;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;

import aero.minova.crm.model.service.WikiService;
import aero.minova.trac.TracService;

public class DownloadWikiJob extends Job {

	private TracService tracService;
	private WikiService wikiService;
//	@SuppressWarnings("restriction")
//	Logger logger = Platform.getWorkbench().getService(org.eclipse.e4.core.services.log.Logger.class);

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
		System.out.println("...synchronize finished");

		return Status.OK_STATUS;
	}

	private void checkWikiPage(String pagename) {
		try {
			System.out.println("sync page " + pagename + "...");
			wikiService.getWiki(pagename);
		} catch (Exception e) {
			System.out.println(e);
			return;
		}
	}

}

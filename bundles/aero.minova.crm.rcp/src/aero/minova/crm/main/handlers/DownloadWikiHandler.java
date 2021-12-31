 
package aero.minova.crm.main.handlers;

import javax.inject.Inject;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import aero.minova.crm.main.jobs.DownloadWikiJob;
import aero.minova.crm.model.service.jpa.WikiPageService;
import aero.minova.trac.TracService;

public class DownloadWikiHandler {
	@Inject
	UISynchronize sync;

	@Inject
	TracService tracService;

	@Execute
	public void execute(WikiPageService wikiService, @Optional MPart part) {
		Job job = new DownloadWikiJob(tracService, wikiService);
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				System.out.println("ready");
			}
		});
		job.schedule();
	}

}
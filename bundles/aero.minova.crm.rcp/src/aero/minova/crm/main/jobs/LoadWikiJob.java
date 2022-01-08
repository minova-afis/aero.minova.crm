package aero.minova.crm.main.jobs;

import java.util.Optional;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import aero.minova.crm.main.parts.WikiPart;
import aero.minova.crm.model.jpa.Wiki;
import aero.minova.crm.model.service.WikiService;

public class LoadWikiJob extends Job {
	private WikiService service;
	private String path;
	private WikiPart part;

	public LoadWikiJob(WikiService service, String path, WikiPart part) {
		super("Load page ~" + path);
		this.service = service;
		this.part = part;
		this.path = path;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		Optional<Wiki> p = service.getWiki(path);

		if (p.isPresent()) {
			part.setPage(p.get());
		} else {
			part.setPage(null);
		}
		return Status.OK_STATUS;
	}
}

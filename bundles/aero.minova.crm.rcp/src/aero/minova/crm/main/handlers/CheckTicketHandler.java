
package aero.minova.crm.main.handlers;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import aero.minova.crm.main.jetty.TicketServlet;
import aero.minova.crm.main.parts.SamplePart;
import aero.minova.crm.model.jpa.MarkupText;
import aero.minova.crm.model.jpa.service.TicketService;
import aero.minova.trac.TracService;
import aero.minova.trac.domain.Ticket;

public class CheckTicketHandler {

	@Inject
	UISynchronize sync;

	@Inject
	TracService tracService;

	@Execute
	public void execute(TicketService ticketService, @Optional MPart part) {
		Job job = new Job("Load Ticket 5228") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				aero.minova.crm.model.jpa.Ticket ticket = null;

				SubMonitor subMonitor = SubMonitor.convert(monitor, 4);
				subMonitor.worked(0);

				java.util.Optional<aero.minova.crm.model.jpa.Ticket> ticketOptional = ticketService.getTicket(5228);
				subMonitor.worked(1);

				if (ticketOptional.isEmpty()) {
					Ticket tracTicket = null;
					tracTicket = tracService.getTicket(5228);
					subMonitor.worked(1);

					ticket = new aero.minova.crm.model.jpa.Ticket();
					ticket.setId(5228);
					ticket.setSummary((String) tracTicket.getSummary());
					MarkupText mt = new MarkupText();
					mt.setMarkup((String) tracTicket.getDescription());
					mt.setHtml(tracService.wikiToHtml(tracTicket.getDescription()));
					subMonitor.worked(1);
					ticket.setDescription(mt);
					ticketService.saveTicket(ticket);
					subMonitor.worked(1);
				} else {
					ticket = ticketOptional.get();
					subMonitor.worked(3);
				}

				if (part == null) return Status.OK_STATUS;
				if (!(part.getObject() instanceof SamplePart)) return Status.OK_STATUS;

				SamplePart samplePart = (SamplePart) part.getObject();

				TicketServlet.setLastTicket(ticket);
				sync.asyncExec(() -> {
					samplePart.showLastTicket();
				});

				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

}
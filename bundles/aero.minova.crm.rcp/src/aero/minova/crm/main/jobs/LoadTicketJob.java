package aero.minova.crm.main.jobs;

import java.util.Optional;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import aero.minova.crm.main.parts.TicketPart;
import aero.minova.crm.model.jpa.MarkupText;
import aero.minova.crm.model.jpa.Ticket;
import aero.minova.crm.model.jpa.service.TicketService;
import aero.minova.trac.TracService;

public class LoadTicketJob extends Job {

	private TracService tracServer;
	private TicketService ticketService;
	private int ticketId;
	private TicketPart ticketPart;

	public LoadTicketJob(TracService tracService, TicketService ticketService, int ticketId, TicketPart part) {
		super("Load ticket #" + ticketId);
		this.tracServer = tracService;
		this.ticketService = ticketService;
		this.ticketPart = part;
		this.ticketId = ticketId;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		Optional<Ticket> t = ticketService.getTicket(ticketId);

		if (t.isPresent()) {
			ticketPart.setTicket(t.get());
			return Status.OK_STATUS;
		}

		aero.minova.trac.domain.Ticket tracTicket = tracServer.getTicket(ticketId);

		if (tracTicket == null) {
			ticketPart.setTicket(null);
			return Status.OK_STATUS;
		}

		Ticket ticket = new Ticket();
		MarkupText description = new MarkupText();

		ticket.setId(ticketId);
		ticket.setSummary((String) tracTicket.getSummary());
		description.setMarkup(tracTicket.getDescription());
		try {
			description.setHtml(tracServer.wikiToHtml(tracTicket.getDescription()));
		} catch (Exception e) {
			description.setHtml(null);
		}
		ticketService.saveTicket(ticket);
		ticketPart.setTicket(ticket);

		return Status.OK_STATUS;
	}
}

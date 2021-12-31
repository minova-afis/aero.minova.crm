package aero.minova.crm.main.jobs;

import java.util.Optional;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import aero.minova.crm.main.parts.TicketPart;
import aero.minova.crm.model.jpa.Ticket;
import aero.minova.crm.model.service.TicketService;

public class LoadTicketJob extends Job {
	private TicketService ticketService;
	private int ticketId;
	private TicketPart ticketPart;

	public LoadTicketJob(TicketService ticketService, int ticketId, TicketPart part) {
		super("Load ticket #" + ticketId);
		this.ticketService = ticketService;
		this.ticketPart = part;
		this.ticketId = ticketId;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		Optional<Ticket> t = ticketService.getTicket(ticketId);

		if (t.isPresent()) {
			ticketPart.setTicket(t.get());
		} else {
			ticketPart.setTicket(null);
		}
		return Status.OK_STATUS;
	}
}

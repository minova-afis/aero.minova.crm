
package aero.minova.crm.main.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import aero.minova.crm.main.parts.SamplePart;
import aero.minova.trac.domain.Server;
import aero.minova.trac.domain.Ticket;

public class CheckTicketHandler {
	@Execute
	public void execute(@Optional MPart part) {
		Server server = Server.getInstance();

		Ticket ticket = server.getTicket(5228);

		if (part == null) return;
		if (!(part.getObject() instanceof SamplePart)) return;

		SamplePart samplePart = (SamplePart) part.getObject();
		samplePart.refresh("<p>" + ticket.getId() + ": " + ticket.getSummary().toString() + "</p>" //
				+ "<p>" + ticket.getDescription() + "</p>" //
				+ "<p>" + ticket.getWiki().getAddress() + "</p>");
	}

}
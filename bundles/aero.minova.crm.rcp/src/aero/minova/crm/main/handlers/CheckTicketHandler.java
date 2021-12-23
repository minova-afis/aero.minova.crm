
package aero.minova.crm.main.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import aero.minova.crm.main.jetty.TicketServlet;
import aero.minova.crm.main.parts.SamplePart;
import aero.minova.crm.model.jpa.MarkupText;
import aero.minova.crm.model.jpa.service.TicketService;
import aero.minova.trac.domain.Server;
import aero.minova.trac.domain.Ticket;

public class CheckTicketHandler {

	@Execute
	public void execute(TicketService ticketService, @Optional MPart part) {
		aero.minova.crm.model.jpa.Ticket ticket = null;
		Ticket tracTicket = null;

		Server server = Server.getInstance();

		java.util.Optional<aero.minova.crm.model.jpa.Ticket> ticketOptional = ticketService.getTicket(5228);

		if (ticketOptional.isEmpty()) {
			tracTicket = server.getTicket(5228);
			ticket = new aero.minova.crm.model.jpa.Ticket();
			ticket.setId(5228);
			ticket.setSummary((String) tracTicket.getSummary());
			MarkupText mt = new MarkupText();
			mt.setMarkup((String) tracTicket.getDescription());
			mt.setHtml(server.wikiTiHtml(tracTicket.getDescription()));
			ticket.setDescription(mt);
			ticketService.saveTicket(ticket);
		} else {
			ticket = ticketOptional.get();
		}

		if (part == null) return;
		if (!(part.getObject() instanceof SamplePart)) return;

		SamplePart samplePart = (SamplePart) part.getObject();
		TicketServlet.setLastTicket(ticket);
		samplePart.refresh(ticket);
	}

}
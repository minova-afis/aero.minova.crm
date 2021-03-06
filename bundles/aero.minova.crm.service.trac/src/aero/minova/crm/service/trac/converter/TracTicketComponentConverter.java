package aero.minova.crm.service.trac.converter;

import java.util.Vector;

import aero.minova.crm.model.jpa.TicketComponent;
import aero.minova.crm.model.service.TicketComponentService;
import aero.minova.trac.TracService;
import aero.minova.trac.domain.TracTicket;

public class TracTicketComponentConverter {

	public static TicketComponent get(TracTicket tracTicket, TracService tracService,
			TicketComponentService ticketComponentService) {
		String componentName = tracTicket.getComponent();
		TicketComponent result = null;
		if (componentName == null || componentName.trim().isEmpty())
			return null;

		result = ticketComponentService.get(componentName).orElse(null);
		if (result == null) {
			// Wir müssen nachladen und speichern
			Vector<String> ticketComponents = tracService.getTicketComponents();
			if (!ticketComponents.contains(componentName)) {
				// Diese Komponente steht zwar im Ticket, ist aber nicht mehr gültig
				TicketComponent tc = new TicketComponent();
				tc.setName(componentName);
				ticketComponentService.save(tc);
				result = ticketComponentService.get(tc.getId()).orElse(null);
			} else {
				for (String string : ticketComponents) {
					TicketComponent tc = new TicketComponent();
					tc.setName(string);
					ticketComponentService.save(tc);
					if (componentName.equals(tc.getName())) {
						result = ticketComponentService.get(tc.getId()).orElse(null);
					}
				}
			}
		}

		return result;
	}

}

package aero.minova.crm.service.trac.converter;

import java.util.Vector;

import aero.minova.crm.model.jpa.TicketComponent;
import aero.minova.crm.model.service.TicketComponentService;
import aero.minova.trac.TracService;
import aero.minova.trac.domain.TracTicket;

public class TracComponentConverter {

	public static TicketComponent get(TracTicket tracTicket, TracService tracService,
			TicketComponentService ticketComponentService) {
		String componentName = tracTicket.getComponent();
		TicketComponent result = null;
		if (componentName == null || componentName.trim().isEmpty())
			return null;

		TicketComponent ticketComponent = ticketComponentService.get(componentName).orElse(null);
		if (ticketComponent == null) {
			// Wir müssen nachladen und speichern
			Vector<String> ticketComponents = tracService.getTicketComponents();
			for (String string : ticketComponents) {
				TicketComponent tc = new TicketComponent();
				tc.setName(string);
				ticketComponentService.save(tc);
				if (componentName.equals(tc.getName())) {
					result = ticketComponentService.get(tc.getId()).orElse(null);
				}
			}
		}

		return result;
	}

}

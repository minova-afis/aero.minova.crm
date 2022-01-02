package aero.minova.crm.service.trac.converter;

import java.util.Vector;

import aero.minova.crm.model.jpa.TicketState;
import aero.minova.crm.model.service.TicketStateService;
import aero.minova.trac.TracService;
import aero.minova.trac.domain.TracTicket;

public class TracTicketStateConverter {

	public static TicketState get(TracTicket tracTicket, TracService tracService,
			TicketStateService ticketStateService) {
		String StateName = tracTicket.getStatus();
		TicketState result = null;
		if (StateName == null || StateName.trim().isEmpty())
			return null;

		result = ticketStateService.get(StateName).orElse(null);
		if (result == null) {
			// Wir müssen nachladen und speichern
			Vector<String> ticketStates = tracService.getTicketStates();
			for (String string : ticketStates) {
				TicketState tc = new TicketState();
				tc.setName(string);
				ticketStateService.save(tc);
				if (StateName.equals(tc.getName())) {
					result = ticketStateService.get(tc.getId()).orElse(null);
				}
			}
		}

		return result;
	}

}

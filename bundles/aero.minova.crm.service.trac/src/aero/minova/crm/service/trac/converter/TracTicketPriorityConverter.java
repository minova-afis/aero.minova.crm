package aero.minova.crm.service.trac.converter;

import java.util.Vector;

import aero.minova.crm.model.jpa.TicketPriority;
import aero.minova.crm.model.service.TicketPriorityService;
import aero.minova.trac.TracService;
import aero.minova.trac.domain.TracTicket;

public class TracTicketPriorityConverter {

	public static TicketPriority get(TracTicket tracTicket, TracService tracService,
			TicketPriorityService ticketPriorityService) {
		String priorityName = tracTicket.getPriority();
		TicketPriority result = null;
		if (priorityName == null || priorityName.trim().isEmpty())
			return null;

		TicketPriority ticketPriority = ticketPriorityService.get(priorityName).orElse(null);
		if (ticketPriority == null) {
			// Wir müssen nachladen und speichern
			Vector<String> ticketPrioritys = tracService.getTicketPriorities();
			for (String string : ticketPrioritys) {
				TicketPriority tc = new TicketPriority();
				tc.setName(string);
				ticketPriorityService.save(tc);
				if (priorityName.equals(tc.getName())) {
					result = ticketPriorityService.get(tc.getId()).orElse(null);
				}
			}
		}

		return result;
	}

}

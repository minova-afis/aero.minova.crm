package aero.minova.crm.service.trac.converter;

import java.util.Vector;

import aero.minova.crm.model.jpa.TicketType;
import aero.minova.crm.model.service.TicketTypeService;
import aero.minova.trac.TracService;
import aero.minova.trac.domain.TracTicket;

public class TracTicketTypeConverter {

	public static TicketType get(TracTicket tracTicket, TracService tracService,
			TicketTypeService ticketTypeService) {
		String TypeName = tracTicket.getStatus();
		TicketType result = null;
		if (TypeName == null || TypeName.trim().isEmpty())
			return null;

		TicketType ticketType = ticketTypeService.get(TypeName).orElse(null);
		if (ticketType == null) {
			// Wir müssen nachladen und speichern
			Vector<String> ticketTypes = tracService.getTicketTypes();
			for (String string : ticketTypes) {
				TicketType tc = new TicketType();
				tc.setName(string);
				ticketTypeService.save(tc);
				if (TypeName.equals(tc.getName())) {
					result = ticketTypeService.get(tc.getId()).orElse(null);
				}
			}
		}

		return result;
	}

}

package aero.minova.crm.service.trac.converter;

import aero.minova.crm.model.jpa.TicketCustomerPrio;
import aero.minova.crm.model.service.TicketCustomerPrioService;
import aero.minova.trac.TracService;
import aero.minova.trac.domain.TracTicket;

public class TracTicketCustomerPrioConverter {

	public static TicketCustomerPrio get(TracTicket tracTicket, TracService tracService,
			TicketCustomerPrioService ticketCustomerPrioService) {
		String customerPrioName = tracTicket.getCustomerPrio();
		TicketCustomerPrio result = null;
		if (customerPrioName == null || customerPrioName.trim().isEmpty())
			return null;

		TicketCustomerPrio ticketCustomerPrio = ticketCustomerPrioService.get(customerPrioName).orElse(null);
		if (ticketCustomerPrio == null) {
			// Wir müssen nachladen und speichern
			String ticketCustomerPrios[] = new String[] { "niedrig", "normal", "hoch" };
			for (String string : ticketCustomerPrios) {
				TicketCustomerPrio tc = new TicketCustomerPrio();
				tc.setName(string);
				ticketCustomerPrioService.save(tc);
				if (customerPrioName.equals(tc.getName())) {
					result = ticketCustomerPrioService.get(tc.getId()).orElse(null);
				}
			}
		}

		return result;
	}

}

package aero.minova.crm.service.trac.converter;

import aero.minova.crm.model.jpa.TicketCustomerState;
import aero.minova.crm.model.service.TicketCustomerStateService;
import aero.minova.trac.TracService;
import aero.minova.trac.domain.TracTicket;

public class TracTicketCustomerStateConverter {

	public static TicketCustomerState get(TracTicket tracTicket, TracService tracService,
			TicketCustomerStateService ticketCustomerStateService) {
		String customerStateName = tracTicket.getCustomerState();
		TicketCustomerState result = null;
		if (customerStateName == null || customerStateName.trim().isEmpty())
			return null;

		TicketCustomerState ticketCustomerState = ticketCustomerStateService.get(customerStateName).orElse(null);
		if (ticketCustomerState == null) {
			// Wir müssen nachladen und speichern
			String ticketCustomerStates[] = new String[] { "erfasst", "bearbeitet", "Kunde am Zug", "auslieferbereit",
					"gelöst", "ausgeliefert", "Analysieren", "Dokumentieren", "Implementieren", "Testen" };
			for (String string : ticketCustomerStates) {
				TicketCustomerState tc = new TicketCustomerState();
				tc.setName(string);
				ticketCustomerStateService.save(tc);
				if (customerStateName.equals(tc.getName())) {
					result = ticketCustomerStateService.get(tc.getId()).orElse(null);
				}
			}
		}

		return result;
	}

}

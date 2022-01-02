package aero.minova.crm.service.trac.converter;

import aero.minova.crm.model.jpa.TicketCustomerType;
import aero.minova.crm.model.service.TicketCustomerTypeService;
import aero.minova.trac.TracService;
import aero.minova.trac.domain.TracTicket;

public class TracTicketCustomerTypeConverter {

	public static TicketCustomerType get(TracTicket tracTicket, TracService tracService,
			TicketCustomerTypeService ticketCustomerTypeService) {
		String customerTypeName = tracTicket.getCustomerType();
		TicketCustomerType result = null;
		if (customerTypeName == null || customerTypeName.trim().isEmpty())
			return null;

		result = ticketCustomerTypeService.get(customerTypeName).orElse(null);
		if (result == null) {
			// Wir müssen nachladen und speichern
			String ticketCustomerTypes[] = new String[] { "-", "Programm", "Konfiguration", "Anwender", "Wunsch" };
			for (String string : ticketCustomerTypes) {
				TicketCustomerType tc = new TicketCustomerType();
				tc.setName(string);
				ticketCustomerTypeService.save(tc);
				if (customerTypeName.equals(tc.getName())) {
					result = ticketCustomerTypeService.get(tc.getId()).orElse(null);
				}
			}
		}

		return result;
	}

}

package aero.minova.crm.service.trac.converter;

import aero.minova.crm.model.jpa.TicketResolution;
import aero.minova.crm.model.service.TicketResolutionService;
import aero.minova.trac.TracService;
import aero.minova.trac.domain.TracTicket;

public class TracTicketResolutionConverter {

	public static TicketResolution get(TracTicket tracTicket, TracService tracService,
			TicketResolutionService ticketResolutionService) {
		String resolutionName = tracTicket.getResolution();
		TicketResolution result = null;
		if (resolutionName == null || resolutionName.trim().isEmpty())
			return null;

		result = ticketResolutionService.get(resolutionName).orElse(null);
		if (result == null) {
			// Wir müssen die defineirten Werte für MINOVA speichern
			String ticketResolutions[] = new String[] { "gelöst", "nicht zutreffend", "wird nicht gelöst",
					"doppeltes Ticket", "nicht nachstellbar", "bitte installieren", "Kunde am Zug", "bitte patchen",
					"bestellt", "nicht bestellt" };
			for (String string : ticketResolutions) {
				TicketResolution tc = new TicketResolution();
				tc.setName(string);
				ticketResolutionService.save(tc);
				if (resolutionName.equals(tc.getName())) {
					result = ticketResolutionService.get(tc.getId()).orElse(null);
				}
			}
		}

		return result;
	}

}

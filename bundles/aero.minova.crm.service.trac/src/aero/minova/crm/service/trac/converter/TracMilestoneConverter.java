package aero.minova.crm.service.trac.converter;

import java.util.Vector;

import aero.minova.crm.model.jpa.Milestone;
import aero.minova.crm.model.service.MilestoneService;
import aero.minova.trac.TracService;
import aero.minova.trac.domain.TracTicket;

public class TracMilestoneConverter {

	public static Milestone get(TracTicket tracTicket, TracService tracService,
			MilestoneService ticketMilestoneService) {
		String milestoneName = tracTicket.getMilestoneName();
		Milestone result = null;
		if (milestoneName == null || milestoneName.trim().isEmpty())
			return null;

		result = ticketMilestoneService.get(milestoneName).orElse(null);
		if (result == null) {
			// Wir müssen nachladen und speichern
			Vector<String> milestones = tracService.getMilestones();
			for (String string : milestones) {
				Milestone m = new Milestone();
				m.setName(string);
				ticketMilestoneService.save(m);
				if (milestoneName.equals(m.getName())) {
					result = ticketMilestoneService.get(m.getId()).orElse(null);
				}
			}
		}

		return result;
	}

}

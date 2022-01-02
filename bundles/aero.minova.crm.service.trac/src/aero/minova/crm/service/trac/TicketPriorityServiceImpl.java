package aero.minova.crm.service.trac;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.TicketPriority;
import aero.minova.crm.model.service.TicketPriorityService;

@Component(service = TicketPriorityService.class)
public class TicketPriorityServiceImpl extends TicketPropertyService<TicketPriority>
		implements TicketPriorityService {

	@Reference
	DatabaseService databaseService;

	public TicketPriorityServiceImpl() {
		super(TicketPriority.class);
	}

	@Override
	protected DatabaseService getDatabaseService() {
		return databaseService;
	}
}

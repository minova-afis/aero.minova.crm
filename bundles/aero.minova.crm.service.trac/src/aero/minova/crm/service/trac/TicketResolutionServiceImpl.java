package aero.minova.crm.service.trac;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.TicketResolution;
import aero.minova.crm.model.service.TicketResolutionService;

@Component(service = TicketResolutionService.class)
public class TicketResolutionServiceImpl extends TicketPropertyService<TicketResolution>
		implements TicketResolutionService {

	@Reference
	DatabaseService databaseService;

	public TicketResolutionServiceImpl() {
		super(TicketResolution.class);
	}

	@Override
	protected DatabaseService getDatabaseService() {
		return databaseService;
	}
}

package aero.minova.crm.service.trac;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.TicketComponent;
import aero.minova.crm.model.service.TicketComponentService;

@Component(service = TicketComponentService.class)
public class TicketComponentServiceImpl extends TicketPropertyService<TicketComponent>
		implements TicketComponentService {

	@Reference
	DatabaseService databaseService;
	
	public TicketComponentServiceImpl() {
		super(TicketComponent.class);
	}
	
	@Override
	protected DatabaseService getDatabaseService() {
		return databaseService;
	}
}

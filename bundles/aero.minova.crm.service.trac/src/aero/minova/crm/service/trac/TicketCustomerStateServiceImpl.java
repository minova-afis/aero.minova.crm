package aero.minova.crm.service.trac;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.TicketCustomerState;
import aero.minova.crm.model.service.TicketCustomerStateService;

@Component(service = TicketCustomerStateService.class)
public class TicketCustomerStateServiceImpl extends TicketPropertyService<TicketCustomerState>
		implements TicketCustomerStateService {

	@Reference
	DatabaseService databaseService;

	public TicketCustomerStateServiceImpl() {
		super(TicketCustomerState.class);
	}

	@Override
	protected DatabaseService getDatabaseService() {
		return databaseService;
	}
}

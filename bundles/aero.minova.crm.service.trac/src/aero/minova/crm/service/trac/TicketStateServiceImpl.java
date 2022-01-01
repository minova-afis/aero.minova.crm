package aero.minova.crm.service.trac;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.TicketState;
import aero.minova.crm.model.service.TicketStateService;

@Component(service = TicketStateService.class)
public class TicketStateServiceImpl extends TicketPropertyService<TicketState>
		implements TicketStateService {

	@Reference
	DatabaseService databaseService;

	public TicketStateServiceImpl() {
		super(TicketState.class);
	}

	@Override
	protected DatabaseService getDatabaseService() {
		return databaseService;
	}
}

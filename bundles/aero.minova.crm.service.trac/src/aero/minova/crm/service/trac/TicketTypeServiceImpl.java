package aero.minova.crm.service.trac;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.TicketType;
import aero.minova.crm.model.service.TicketTypeService;

@Component(service = TicketTypeService.class)
public class TicketTypeServiceImpl extends TicketPropertyService<TicketType>
		implements TicketTypeService {

	@Reference
	DatabaseService databaseService;

	public TicketTypeServiceImpl() {
		super(TicketType.class);
	}

	@Override
	protected DatabaseService getDatabaseService() {
		return databaseService;
	}
}

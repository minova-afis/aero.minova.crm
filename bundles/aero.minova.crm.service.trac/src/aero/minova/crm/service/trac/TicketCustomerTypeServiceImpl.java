package aero.minova.crm.service.trac;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.TicketCustomerType;
import aero.minova.crm.model.service.TicketCustomerTypeService;

@Component(service = TicketCustomerTypeService.class)
public class TicketCustomerTypeServiceImpl extends TicketPropertyService<TicketCustomerType>
		implements TicketCustomerTypeService {

	@Reference
	DatabaseService databaseService;

	public TicketCustomerTypeServiceImpl() {
		super(TicketCustomerType.class);
	}

	@Override
	protected DatabaseService getDatabaseService() {
		return databaseService;
	}
}

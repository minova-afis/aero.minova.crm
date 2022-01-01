package aero.minova.crm.service.trac;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.TicketCustomerPrio;
import aero.minova.crm.model.service.TicketCustomerPrioService;

@Component(service = TicketCustomerPrioService.class)
public class TicketCustomerPrioServiceImpl extends TicketPropertyService<TicketCustomerPrio>
		implements TicketCustomerPrioService {

	@Reference
	DatabaseService databaseService;

	public TicketCustomerPrioServiceImpl() {
		super(TicketCustomerPrio.class);
	}

	@Override
	protected DatabaseService getDatabaseService() {
		return databaseService;
	}
}

package aero.minova.crm.service.trac;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.Milestone;
import aero.minova.crm.model.service.MilestoneService;

@Component(service = MilestoneService.class)
public class MilestoneServiceImpl extends TicketPropertyService<Milestone>
		implements MilestoneService {

	@Reference
	DatabaseService databaseService;
	
	public MilestoneServiceImpl() {
		super(Milestone.class);
	}
	
	@Override
	protected DatabaseService getDatabaseService() {
		return databaseService;
	}
}

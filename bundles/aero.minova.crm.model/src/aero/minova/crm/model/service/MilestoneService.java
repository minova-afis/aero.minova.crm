package aero.minova.crm.model.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import aero.minova.crm.model.jpa.Milestone;

public interface MilestoneService {

	void getMilestones(Consumer<List<Milestone>> milestonesConsumer);

	boolean saveMilestone(Milestone newMilestone);

	Optional<Milestone> getMilestone(int id);

	Optional<Milestone> getMilestone(String name);
	
	boolean deleteMilestone(int id);
}
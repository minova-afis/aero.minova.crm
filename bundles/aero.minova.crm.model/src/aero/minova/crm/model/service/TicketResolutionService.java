package aero.minova.crm.model.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import aero.minova.crm.model.jpa.TicketResolution;

public interface TicketResolutionService {

	void getTicketResolutions(Consumer<List<TicketResolution>> ticketResolutionsConsumer);

	boolean saveTicketResolution(TicketResolution newTicketResolution);

	Optional<TicketResolution> getTicketResolution(int id);

	Optional<TicketResolution> getTicketResolution(String name);

	boolean deleteTicketResolution(int id);
}
package aero.minova.crm.model.service.jpa;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import aero.minova.crm.model.jpa.TicketPriority;

public interface TicketPriorityService {

	void getTicketPriorities(Consumer<List<TicketPriority>> ticketPrioritiesConsumer);

	boolean saveTicketPriority(TicketPriority newTicketPriority);

	Optional<TicketPriority> getTicketPriority(int id);

	Optional<TicketPriority> getTicketPriority(String name);

	Optional<TicketPriority> getTicketPriorityProposal();

	boolean deleteTicketPriority(int id);
}
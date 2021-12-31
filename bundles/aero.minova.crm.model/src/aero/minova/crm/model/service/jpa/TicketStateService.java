package aero.minova.crm.model.service.jpa;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import aero.minova.crm.model.jpa.TicketState;

public interface TicketStateService {

	void getTicketStates(Consumer<List<TicketState>> ticketStatesConsumer);

	boolean saveTicketState(TicketState newTicketState);

	Optional<TicketState> getTicketState(int id);

	Optional<TicketState> getTicketState(String name);

	boolean deleteTicketState(int id);
}
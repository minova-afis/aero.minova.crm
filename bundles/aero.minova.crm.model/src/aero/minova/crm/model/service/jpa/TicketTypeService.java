package aero.minova.crm.model.service.jpa;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import aero.minova.crm.model.jpa.TicketType;

public interface TicketTypeService {

	void getTicketTypes(Consumer<List<TicketType>> ticketTypesConsumer);

	boolean saveTicketType(TicketType newTicketType);

	Optional<TicketType> getTicketType(int id);

	Optional<TicketType> getTicketType(String name);

	boolean deleteTicketType(int id);
}
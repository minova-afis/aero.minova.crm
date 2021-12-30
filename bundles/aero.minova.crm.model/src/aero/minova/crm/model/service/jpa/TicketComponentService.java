package aero.minova.crm.model.service.jpa;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import aero.minova.crm.model.jpa.TicketComponent;

public interface TicketComponentService {

	void getTicketComponents(Consumer<List<TicketComponent>> ticketComponentsConsumer);

	boolean saveTicketComponent(TicketComponent newTicketComponent);

	Optional<TicketComponent> getTicketComponent(int id);

	Optional<TicketComponent> getTicketComponent(String name);

	boolean deleteTicketComponent(int id);
}
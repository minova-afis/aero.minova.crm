package aero.minova.crm.model.service.jpa;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import aero.minova.crm.model.jpa.TicketCustomerPrio;

public interface TicketCustomerPrioService {

	void getTicketCustomerPrios(Consumer<List<TicketCustomerPrio>> ticketCustomerPriosConsumer);

	boolean saveTicketCustomerPrio(TicketCustomerPrio newTicketCustomerPrio);

	Optional<TicketCustomerPrio> getTicketCustomerPrio(int id);

	Optional<TicketCustomerPrio> getTicketCustomerPrio(String name);

	boolean deleteTicketCustomerPrio(int id);
}
package aero.minova.crm.model.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import aero.minova.crm.model.jpa.Ticket;

public interface TicketService {

	void getTickets(Consumer<List<Ticket>> ticketsConsumer);

	boolean saveTicket(Ticket newTicket);

	Optional<Ticket> getTicket(int id);

	boolean deleteTicket(int id);
}
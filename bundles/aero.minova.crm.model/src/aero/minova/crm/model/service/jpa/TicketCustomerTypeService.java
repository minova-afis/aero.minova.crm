package aero.minova.crm.model.service.jpa;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import aero.minova.crm.model.jpa.TicketCustomerType;

public interface TicketCustomerTypeService {

	void getTicketCustomerTypes(Consumer<List<TicketCustomerType>> ticketCustomerTypesConsumer);

	boolean saveTicketCustomerType(TicketCustomerType newTicketCustomerType);

	Optional<TicketCustomerType> getTicketCustomerType(int id);

	Optional<TicketCustomerType> getTicketCustomerType(String name);

	boolean deleteTicketCustomerType(int id);
}
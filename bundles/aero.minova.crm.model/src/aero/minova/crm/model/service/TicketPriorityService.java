package aero.minova.crm.model.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface TicketPriorityService<TP> {

	public void get(Consumer<List<TP>> ticketPrioritiesConsumer);

	public List<TP> getAll();

	public boolean save(TP newTicketPriority);

	public Optional<TP> get(int id);

	public Optional<TP> get(String name);

	public boolean delete(int id);
}
package aero.minova.crm.model.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface TicketPropertyService<TP> {

	public void get(Consumer<List<TP>> ticketPropertiesConsumer);

	public List<TP> getAll();

	public boolean save(TP newTicketProperty);

	public Optional<TP> get(int id);

	public Optional<TP> get(String name);

	public boolean delete(int id);
}
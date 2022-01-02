package aero.minova.crm.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import aero.minova.crm.model.jpa.TicketList;

@Converter
public class TicketListConverter implements AttributeConverter<TicketList, String> {

	@Override
	public String convertToDatabaseColumn(TicketList attribute) {
		if (attribute == null)
			return null;

		StringBuilder sb = new StringBuilder();
		attribute.getList().forEach(i -> sb.append(i + " "));
		return sb.toString();
	}

	@Override
	public TicketList convertToEntityAttribute(String dbData) {
		TicketList ticketList = new TicketList();
		ticketList.setTicketString(dbData);
		return ticketList;
	}
}

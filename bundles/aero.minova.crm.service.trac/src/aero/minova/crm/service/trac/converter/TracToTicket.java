package aero.minova.crm.service.trac.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TracToTicket {

	protected static LocalDate getLocalDate(String date) {
		LocalDate result = null;
		try {
			result = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		} catch (Exception e) {
		}
		try {
			result = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		} catch (Exception e) {
		}
		return result;
	}

	protected static LocalDateTime getLocalDateTime(Date date) {
		return (date == null) ? null : LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC"));
	}

	protected static Double getDouble(String stringValue) {
		try {
			return Double.parseDouble(stringValue);
		} catch (NumberFormatException | NullPointerException e) {
			return null;
		}
	}

	public TracToTicket() {
		super();
	}

}
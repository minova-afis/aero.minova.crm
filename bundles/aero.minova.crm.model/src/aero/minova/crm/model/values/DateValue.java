package aero.minova.crm.model.values;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import aero.minova.crm.model.Constants;

public class DateValue extends Value {

	private Date date;

	public DateValue(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATEFORMAT);
		try {
			this.setDate(format.parse(dateString));
		} catch (ParseException e) {
			format = new SimpleDateFormat(Constants.DATEFORMATVCARD);
			try {
				this.setDate(format.parse(dateString));
			} catch (ParseException e1) {
				e.printStackTrace();
			}
		}
	}

	public DateValue(Date date) {
		this.setDate(date);
	}

	public DateValue(LocalDate birthday) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		this.setDate(Date.from(birthday.atStartOfDay(defaultZoneId).toInstant()));
	}

	@Override
	public String getStringRepresentation() {
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATEFORMAT);
		return format.format(getDate());
	}

	@Override
	public String getVCardString() {
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATEFORMATVCARD);
		return format.format(getDate());
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}

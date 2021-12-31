package aero.minova.trac.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import aero.minova.crm.model.jpa.Ticket;
import aero.minova.crm.model.service.jpa.TicketComponentService;
import aero.minova.trac.domain.TracTicket;

public class TracToModel {
	public static Ticket getTicket(TracTicket tracTicket, TicketComponentService ticketComponentService) {
		Ticket ticket = new Ticket();

		ticket.setId(tracTicket.getId());
		ticket.setCc(tracTicket.getCc());
		ticket.setOwner(tracTicket.getOwner());
		ticket.setReporter(tracTicket.getReporter());
		ticket.setBillable("1".equals(tracTicket.getBillable()));
		ticket.setBlockedBy(getListInteger(tracTicket.getBlockedBy()));
		ticket.setBlocking(getListInteger(tracTicket.getBlocking()));
		ticket.setComponent(ticketComponentService.getTicketComponent(tracTicket.getComponent()).orElse(null));
//		ticket.setCustomerPrio(TicketCustomerPrio.valueOf(tracTicket.getCustomerPrio()));
		ticket.setCustomerDescription(tracTicket.getCustomerDescripion());
//		ticket.setCustomerType(TicketCustomerType.valueOf(tracTicket.getCustomerType()));
		ticket.setDescription(null);
		ticket.setDueDate(getLocalDate(tracTicket.getDueDate()));
		ticket.setEstimatedHours(getDouble(tracTicket.getEstimatedHours()));
		ticket.setOfferedHours(getDouble(tracTicket.getOfferedHours()));
		ticket.setTotalHours(getDouble(tracTicket.getTotalHours()));
		ticket.setLastDate(getLocalDateTime(tracTicket.getChangeTime()));
		ticket.setMilestone(null);
		ticket.setModuleNames(tracTicket.getModuleNames());
		ticket.setParent(getListInteger(tracTicket.getParent()));
		ticket.setPriority(null);
		ticket.setRelease(null);
//		ticket.setResolution(Resolution.valueOf(tracTicket.getResolution()));
//		ticket.setState(TicketState.valueOf(tracTicket.getStatus()));
		ticket.setSummary(tracTicket.getSummary());
		ticket.setType(null);
		return ticket;
	}

	private static LocalDate getLocalDate(String dueDate) {
		// TODO Auto-generated method stub
		return null;
	}

	private static List<Integer> getListInteger(String blockedBy) {
		// TODO Auto-generated method stub
		return null;
	}

	private static LocalDateTime getLocalDateTime(Date date) {
		return (date == null) ? null : LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC"));
	}

	private static Double getDouble(String stringValue) {
		try {
			return Double.parseDouble(stringValue);
		} catch (NumberFormatException nfe) {
			return null;
		}
	}
}

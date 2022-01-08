package aero.minova.crm.service.trac.converter;

import aero.minova.crm.model.jpa.Ticket;
import aero.minova.crm.model.jpa.TicketList;
import aero.minova.crm.model.jpa.Wiki;
import aero.minova.trac.domain.TracTicket;
import aero.minova.trac.domain.TracWikiPage;

public class WikiToModel extends TracToTicket {
	public static Ticket getTicket(TracTicket tracTicket) {
		Ticket ticket = new Ticket();

		ticket.setId(tracTicket.getId());
		ticket.setCc(tracTicket.getCc());
		ticket.setOwner(tracTicket.getOwner());
		ticket.setReporter(tracTicket.getReporter());
		ticket.setBillable("1".equals(tracTicket.getBillable()));
		ticket.setBlockedBy(new TicketList(tracTicket.getBlockedBy()));
		ticket.setBlocking(new TicketList(tracTicket.getBlocking()));
//		ticket.setComponent(ticketComponentService.getTicketComponent(tracTicket.getComponent()).orElse(null));
//		ticket.setCustomerPrio(TicketCustomerPrio.valueOf(tracTicket.getCustomerPrio()));
		ticket.setCustomerDescription(tracTicket.getCustomerDescripion());
//		ticket.setCustomerType(TicketCustomerType.valueOf(tracTicket.getCustomerType()));
		ticket.setDescription(null);
		ticket.setKeywords(tracTicket.getKeywords());
		ticket.setStartDate(getLocalDate(tracTicket.getStartDate()));
		ticket.setDueDate(getLocalDate(tracTicket.getDueDate()));
		ticket.setEstimatedHours(getDouble(tracTicket.getEstimatedHours()));
		ticket.setOfferedHours(getDouble(tracTicket.getOfferedHours()));
		ticket.setTotalHours(getDouble(tracTicket.getTotalHours()));
		ticket.setLastDate(getLocalDateTime(tracTicket.getChangeTime()));
		ticket.setMilestone(null);
		ticket.setModuleNames(tracTicket.getModuleNames());
//		ticket.setParent(getListInteger(tracTicket.getParent()));
		ticket.setPriority(null);
		ticket.setRelease(null);
//		ticket.setResolution(Resolution.valueOf(tracTicket.getResolution()));
//		ticket.setState(TicketState.valueOf(tracTicket.getStatus()));
		ticket.setSummary(tracTicket.getSummary());
		ticket.setType(null);
		return ticket;
	}

	public static Wiki getWiki(TracWikiPage tracWiki) {
		Wiki wiki = new Wiki();
		
		wiki.setPath(tracWiki.getAddress());
		return wiki;
	}
}

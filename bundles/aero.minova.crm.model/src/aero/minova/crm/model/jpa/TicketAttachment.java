package aero.minova.crm.model.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class TicketAttachment extends Attachment {

	@ManyToOne
	private Ticket ticket;

	/**
	 * @return the ticket
	 */
	public Ticket getTicket() {
		return ticket;
	}

	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
}

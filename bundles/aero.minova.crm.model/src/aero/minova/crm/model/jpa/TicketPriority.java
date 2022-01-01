package aero.minova.crm.model.jpa;

import jakarta.persistence.Entity;

@Entity
public class TicketPriority extends TicketProperty {
	private boolean proposal;

	/**
	 * Der Wert mit true wird als Standard beim Anlagen neuer Tickets verwendet.
	 * 
	 * @return the proposal
	 */
	public boolean isProposal() {
		return proposal;
	}

	/**
	 * Der Wert mit true wird als Standard beim Anlagen neuer Tickets verwendet.
	 * 
	 * @param proposal the proposal to set
	 */
	public void setProposal(boolean proposal) {
		this.proposal = proposal;
	}
}

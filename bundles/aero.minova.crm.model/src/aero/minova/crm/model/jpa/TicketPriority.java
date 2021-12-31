package aero.minova.crm.model.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TicketPriority {
	@Id
	private int id;
	private String name;
	private boolean proposal;

	/**
	 * (entspricht der Reihenfolge) 1 entspricht der höchsten Priorität
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Der Wert mit true wird als Standard beim Anlagen neuer Tickets verwendet.
	 * 
	 * @return the proposal
	 */
	public boolean isProposal() {
		return proposal;
	}

	/**
	 * (entspricht der Reihenfolge) 1 entspricht der höchsten Priorität
	 * 
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

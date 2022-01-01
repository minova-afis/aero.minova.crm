package aero.minova.crm.model.jpa;

import jakarta.persistence.Entity;

@Entity
public class TicketComponent extends TicketProperty {

	private String markup;
	private String owner;

	/**
	 * @return the description as markup text
	 */
	public String getMarkup() {
		return markup;
	}

	/**
	 * @return the default owner for tickets of this component
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param markup
	 *            the description as markup text to set
	 */
	public void setMarkup(String markup) {
		this.markup = markup;
	}

	/**
	 * @param owner
	 *            the default owner for tickets of this component to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

}

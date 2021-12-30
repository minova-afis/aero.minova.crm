package aero.minova.crm.model.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TicketComponent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String markup;
	private String name;
	private String owner;

	/**
	 * @return the internal id (not synchronized)
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the description as markup text
	 */
	public String getMarkup() {
		return markup;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the default owner for tickets of this component
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param id
	 *            the internal id (not synchronized) to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param markup
	 *            the description as markup text to set
	 */
	public void setMarkup(String markup) {
		this.markup = markup;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param owner
	 *            the default owner for tickets of this component to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

}

package aero.minova.crm.model.jpa;

import java.time.Instant;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Embeddable
public class Attachment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private Instant lastDate;
	private String lastUser;
	private int size;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the lastDate
	 */
	public Instant getLastDate() {
		return lastDate;
	}

	/**
	 * @return the lastUser
	 */
	public String getLastUser() {
		return lastUser;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param lastDate the lastDate to set
	 */
	public void setLastDate(Instant lastDate) {
		this.lastDate = lastDate;
	}

	/**
	 * @param lastUser the lastUser to set
	 */
	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

}

package aero.minova.crm.model.jpa;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;

@Entity
public class Milestone extends TicketProperty {

	private MarkupText description;
	private boolean completed;
	private LocalDateTime due;

	/**
	 * @return the description
	 */
	public MarkupText getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(MarkupText description) {
		this.description = description;
	}

	/**
	 * @return the completed
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	/**
	 * @return the due
	 */
	public LocalDateTime getDue() {
		return due;
	}

	/**
	 * @param due the due to set
	 */
	public void setDue(LocalDateTime due) {
		this.due = due;
	}
}

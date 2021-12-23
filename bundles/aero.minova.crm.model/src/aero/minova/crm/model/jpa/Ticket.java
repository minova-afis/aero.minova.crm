package aero.minova.crm.model.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Ticket {

	@Id
	private int id;
	private String summary;
	@ManyToOne(cascade = CascadeType.ALL)
	private MarkupText description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public MarkupText getDescription() {
		return description;
	}

	public void setDescription(MarkupText description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", summary=" + summary + ", description=" + description.getMarkup() + "]";
	}

}
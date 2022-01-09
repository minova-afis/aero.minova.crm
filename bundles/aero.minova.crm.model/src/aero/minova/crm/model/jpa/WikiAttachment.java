package aero.minova.crm.model.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class WikiAttachment extends Attachment{

	@ManyToOne
	private Wiki wiki;

	/**
	 * @return the wiki
	 */
	public Wiki getWiki() {
		return wiki;
	}

	/**
	 * @param wiki the wiki to set
	 */
	public void setWiki(Wiki wiki) {
		this.wiki = wiki;
	}
}

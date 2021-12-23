package aero.minova.crm.model.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MarkupText {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String markup;
	private String html;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return HTML representation of the converted {@link #getMarkup()} Text. If it is null, the default implementation (Markdown) ist used.
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * @param html
	 *            HTML representation of the converted {@link #getMarkup()} Text. If it is null, the default implementation (Markdown) ist used.
	 */
	public void setHtml(String html) {
		this.html = html;
	}

	/**
	 * @return source text in a markup language (ML)
	 */
	public String getMarkup() {
		return markup;
	}

	/**
	 * @param markup
	 *            source text in a markup language (ML)
	 */
	public void setMarkup(String markup) {
		this.markup = markup;
	}

}

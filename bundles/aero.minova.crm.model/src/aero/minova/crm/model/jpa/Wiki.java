package aero.minova.crm.model.jpa;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Wiki {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String path;
	private String comment;
	@ManyToOne(cascade = CascadeType.ALL)
	private MarkupText description;
	private LocalDateTime lastModified;
	private String lastUser;
	private int version;

	/**
	 * @return Kommentar des Benutzers zur letzten Änderung
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return MessageDigest der Beschreibung
	 */
	public MarkupText getDescription() {
		return description;
	}

	/**
	 * @return Eindeutiger Schlüssel in der lokalen Datenbank {@link #getPath()}
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return Zeitpunkt der letzten Änderung
	 */
	public LocalDateTime getLastModified() {
		return lastModified;
	}

	/**
	 * @return Name des Benutzers der letzen Änderung
	 */
	public String getLastUser() {
		return lastUser;
	}

	/**
	 * @return "Dateiname" der Seite (muss eindeutig sein (siehe {@link #getId()}))
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return Versionsnummer beginnend bei 1 und dann hochzählend
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param comment Kommentar des Benutzers zur letzten Änderung
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param description Seitenquelltext und ggf. HTML-Repräsentation
	 */
	public void setDescription(MarkupText description) {
		this.description = description;
	}

	/**
	 * @param id Eindeutiger Schlüssel in der lokalen Datenbank {@link #getPath()}
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param lastModified Zeitpunkt der letzten Änderung
	 */
	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * @param lastUser Name des Benutzers der letzen Änderung
	 */
	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	/**
	 * @param path "Dateiname" der Seite (muss eindeutig sein (siehe
	 *             {@link #getId()}))
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @param version Versionsnummer beginnend bei 1 und dann hochzählend
	 */
	public void setVersion(int version) {
		this.version = version;
	}
}

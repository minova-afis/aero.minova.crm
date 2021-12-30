package aero.minova.trac.domain;

import java.util.Date;
// import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aero.minova.trac.TracService;

/**
 * @author wild
 * @since 12.0.0
 */
public class TracTicket {
	protected String billable; // billable:

	protected String blockedBy; // blockedby:
	protected String blocking; // blocking:
	protected String cc; // cc:
	protected Date changeTime; // changetime:
	protected String component; // component:
	protected Date creationTime; // time:
	protected String customerDescripion; // customerdescription
	protected String customerPrio; // customerprio
	protected String customerState; // customerstate
	protected String customerType; // customertype
	protected String description; // description:
	protected String dueDate; // duedate;
	protected String estimatedHours; // estimatedhours
	protected int id; // #:
	protected String keywords; // keywords:
	protected TracMilestone milestone = null; // milestone:
	protected String milestoneName;
	protected String moduleNames; // modulenames
	protected String offeredHours; // offeredhours:
	protected String owner; // owner:
	protected String priority; // priority:
	protected String release; // release:
	protected String reporter; // reporter:
	protected String resolution; // resolution:
	protected String startDate; // startdate
	protected String status; // status:
	protected String summary; // summary:
	protected String ticketType; // type
	protected String totalHours; // totalhours:
	private final TracService tracService;
	protected TracWikiPage wiki = null;
	protected String wikiAddress;

	public TracTicket(TracService tracService) {
		this.tracService = tracService;
	}

	/**
	 * @return the billable information ("1" means true)
	 */
	public String getBillable() {
		return billable;
	}

	/**
	 * @return the blockedBy
	 */
	public String getBlockedBy() {
		return blockedBy;
	}

	/**
	 * @return the blocking
	 */
	public String getBlocking() {
		return blocking;
	}

	/**
	 * @return the cc
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * @return the changeTime
	 */
	public Date getChangeTime() {
		return changeTime;
	}

	/**
	 * @return the component
	 */
	public String getComponent() {
		return component;
	}

	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @return the customerDescripion
	 */
	public String getCustomerDescripion() {
		return customerDescripion;
	}

	/**
	 * @return the customerPrio
	 */
	public String getCustomerPrio() {
		return customerPrio;
	}

	public String getCustomerState() {
		return customerState;
	}

	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * @return the dueDate
	 */
	public String getDueDate() {
		return dueDate;
	}

	/**
	 * @return the estimatedHours
	 */
	public String getEstimatedHours() {
		return estimatedHours;
	}

	public int getId() {
		return id;
	}

	public String getKeywords() {
		return keywords;
	}

	public TracMilestone getMilestone() {
		if (milestone == null && milestoneName != null && !milestoneName.isEmpty()) {
			milestone = tracService.getMilestone(milestoneName);
		}
		return milestone;
	}

	public String getMilestoneName() {
		return milestoneName;
	}

	/**
	 * @return the moduleNames
	 */
	public String getModuleNames() {
		return moduleNames;
	}

	/**
	 * @return the offeredHours
	 */
	public String getOfferedHours() {
		return offeredHours;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * @return the release
	 */
	public String getRelease() {
		return release;
	}

	/**
	 * @return the reporter
	 */
	public String getReporter() {
		return reporter;
	}

	/**
	 * @return the resolution
	 */
	public String getResolution() {
		return resolution;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	public String getSummary() {
		return summary;
	}

	/**
	 * @return the ticketType
	 */
	public String getTicketType() {
		return ticketType;
	}

	/**
	 * @return the totalHours
	 */
	public String getTotalHours() {
		return totalHours;
	}

	/**
	 * @return the tracService
	 */
	public TracService getTracService() {
		return tracService;
	}

	public TracWikiPage getWiki() {
		if (wikiAddress == null) {
			return null;
		} else if (wiki == null) {
			wiki = tracService.getWiki(wikiAddress);
		}
		return wiki;
	}

	/**
	 * @return the wikiAddress
	 */
	public String getWikiAddress() {
		return wikiAddress;
	}

	public void setKeywords(String keywords) {
		Pattern keywordPattern = Pattern.compile("([a-zA-Z\\-_0-9]*)");
		this.keywords = keywords;

		// Adresse für die Wikiseite des Projektes zusammensetzen
		wiki = null; // damit ist auch die wiki-Seite ggf. neu zu laden
		Matcher m = keywordPattern.matcher(keywords);
		if (m.find() && m.group(1).length() > 0) {
			if (m.group(1).contains("_")) {
				wikiAddress = "ISO/project/mpks/steckbriefe/" + m.group(1);
			} else {
				wikiAddress = "kontrakte/" + m.group(1);
			}
		} else {
			wikiAddress = null;
		}
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public String toString() {
		return "#" + id + ": " + summary;
	}

	public void update(String text) {
		tracService.updateTicketSummary(this, text);
	}
}
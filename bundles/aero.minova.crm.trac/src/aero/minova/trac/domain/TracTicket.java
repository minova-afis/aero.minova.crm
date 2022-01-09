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
	private String parent;
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
	public String getParent() {
		return parent;
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
	
	/**
	 * @param billable the billable to set
	 */
	public void setBillable(String billable) {
		this.billable = billable;
	}

	/**
	 * @param blockedBy the blockedBy to set
	 */
	public void setBlockedBy(String blockedBy) {
		this.blockedBy = blockedBy;
	}

	/**
	 * @param blocking the blocking to set
	 */
	public void setBlocking(String blocking) {
		this.blocking = blocking;
	}

	/**
	 * @param cc the cc to set
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}

	/**
	 * @param changeTime the changeTime to set
	 */
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	/**
	 * @param component the component to set
	 */
	public void setComponent(String component) {
		this.component = component;
	}

	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @param customerDescripion the customerDescripion to set
	 */
	public void setCustomerDescripion(String customerDescripion) {
		this.customerDescripion = customerDescripion;
	}

	/**
	 * @param customerPrio the customerPrio to set
	 */
	public void setCustomerPrio(String customerPrio) {
		this.customerPrio = customerPrio;
	}

	/**
	 * @param customerState the customerState to set
	 */
	public void setCustomerState(String customerState) {
		this.customerState = customerState;
	}

	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @param estimatedHours the estimatedHours to set
	 */
	public void setEstimatedHours(String estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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

	/**
	 * @param milestone the milestone to set
	 */
	public void setMilestone(TracMilestone milestone) {
		this.milestone = milestone;
	}

	/**
	 * @param milestoneName the milestoneName to set
	 */
	public void setMilestoneName(String milestoneName) {
		this.milestoneName = milestoneName;
	}

	/**
	 * @param moduleNames the moduleNames to set
	 */
	public void setModuleNames(String moduleNames) {
		this.moduleNames = moduleNames;
	}

	/**
	 * @param offeredHours the offeredHours to set
	 */
	public void setOfferedHours(String offeredHours) {
		this.offeredHours = offeredHours;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * @param release the release to set
	 */
	public void setRelease(String release) {
		this.release = release;
	}

	/**
	 * @param reporter the reporter to set
	 */
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	/**
	 * @param resolution the resolution to set
	 */
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @param ticketType the ticketType to set
	 */
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	/**
	 * @param totalHours the totalHours to set
	 */
	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	/**
	 * @param wiki the wiki to set
	 */
	public void setWiki(TracWikiPage wiki) {
		this.wiki = wiki;
	}

	/**
	 * @param wikiAddress the wikiAddress to set
	 */
	public void setWikiAddress(String wikiAddress) {
		this.wikiAddress = wikiAddress;
	}

	@Override
	public String toString() {
		return "#" + id + ": " + summary;
	}

	public void update(String text) {
		tracService.updateTicketSummary(this, text);
	}
}
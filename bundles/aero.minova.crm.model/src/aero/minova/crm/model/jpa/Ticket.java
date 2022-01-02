package aero.minova.crm.model.jpa;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Ticket {

	@Id
	private int id;
	private boolean billable;
	private TicketList blockedBy; // blockedby
	private TicketList blocking;
	private String cc;
	@ManyToOne(cascade = CascadeType.ALL)
	private TicketComponent component;
	private String customerDescription; // customerdescription
	private TicketCustomerPrio customerPrio; // customerprio
	private TicketCustomerState customerState; // customerstate
	private TicketCustomerType customerType; // customertype
	@ManyToOne(cascade = CascadeType.ALL)
	private MarkupText description;
	private LocalDate startDate; // startdate
	private LocalDate dueDate; // duedate
	private Double estimatedHours; // esitmatedhours
	private String keywords;
	private LocalDateTime lastDate; // changetime
	@ManyToOne(cascade = CascadeType.ALL)
	private Milestone milestone;
	private String moduleNames; // modulenames
	private Double offeredHours; // offeredhours
	private String owner;
	@ManyToOne(cascade = CascadeType.ALL)
	private Ticket parent;
	private TicketPriority priority;
	private String release;
	private String reporter;
	private TicketResolution resolution;
	private TicketState state; // status
	private String summary;
	private Double totalHours; // totalhours
	private TicketType type;
	/**
	 * @return the tickets (ticket ids) blocking this ticket
	 */
	public TicketList getBlockedBy() {
		return blockedBy;
	}
	/**
	 * @return the blocking
	 */
	public TicketList getBlocking() {
		return blocking;
	}

	/**
	 * @return the carbon copies
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * @return the component
	 */
	public TicketComponent getComponent() {
		return component;
	}

	/**
	 * @return the customerDescription
	 */
	public String getCustomerDescription() {
		return customerDescription;
	}

	/**
	 * @return the priority visible for the customer
	 */
	public TicketCustomerPrio getCustomerPrio() {
		return customerPrio;
	}

	/**
	 * @return the state visible for the customer
	 */
	public TicketCustomerState getCustomerState() {
		return customerState;
	}

	/**
	 * @return the type of the ticket visible for the customer
	 */
	public TicketCustomerType getCustomerType() {
		return customerType;
	}

	/**
	 * @return the markup description
	 */
	public MarkupText getDescription() {
		return description;
	}
	
	/**
	 * @return the dueDate
	 */
	public LocalDate getDueDate() {
		return dueDate;
	}

	/**
	 * @return the estimatedHours
	 */
	public Double getEstimatedHours() {
		return estimatedHours;
	}

	/**
	 * @return ticket id
	 */
	public int getId() {
		return id;
	}

	public String getKeywords() {
		return keywords;
	}

	/**
	 * @return the timestamp of last change
	 */
	public LocalDateTime getLastDate() {
		return lastDate;
	}

	/**
	 * @return the milestone
	 */
	public Milestone getMilestone() {
		return milestone;
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
	public Double getOfferedHours() {
		return offeredHours;
	}

	/**
	 * @return the owner of the ticket
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @return the parents
	 */
	public Ticket getParent() {
		return parent;
	}

	/**
	 * @return the priority
	 */
	public TicketPriority getPriority() {
		return priority;
	}

	/**
	 * @return the release
	 */
	public String getRelease() {
		return release;
	}

	/**
	 * @return the reporter of the ticket
	 */
	public String getReporter() {
		return reporter;
	}

	/**
	 * @return the resolution
	 */
	public TicketResolution getResolution() {
		return resolution;
	}

	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @return the state
	 */
	public TicketState getState() {
		return state;
	}

	/**
	 * @return the short summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @return the totalHours
	 */
	public Double getTotalHours() {
		return totalHours;
	}

	/**
	 * @return the type
	 */
	public TicketType getType() {
		return type;
	}

	/**
	 * @return the billable
	 */
	public boolean isBillable() {
		return billable;
	}

	/**
	 * @param billable the billable to set
	 */
	public void setBillable(boolean billable) {
		this.billable = billable;
	}

	/**
	 * @param blockedBy the tickets (ticket ids) blocking this ticket to set
	 */
	public void setBlockedBy(TicketList blockedBy) {
		this.blockedBy = blockedBy;
	}

	/**
	 * @param blocking the blocking to set
	 */
	public void setBlocking(TicketList blocking) {
		this.blocking = blocking;
	}

	/**
	 * @param cc the carbon copies to set
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}

	/**
	 * @param component the component to set
	 */
	public void setComponent(TicketComponent component) {
		this.component = component;
	}

	/**
	 * @param customerDescription the customerDescription to set
	 */
	public void setCustomerDescription(String customerDescription) {
		this.customerDescription = customerDescription;
	}

	/**
	 * @param customerPrio the customerPrio to set
	 */
	public void setCustomerPrio(TicketCustomerPrio customerPrio) {
		this.customerPrio = customerPrio;
	}

	/**
	 * @param customerState the state visible for the customer to set
	 */
	public void setCustomerState(TicketCustomerState customerState) {
		this.customerState = customerState;
	}

	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(TicketCustomerType customerType) {
		this.customerType = customerType;
	}
	
	/**
	 * @param description the markup description to set
	 */
	public void setDescription(MarkupText description) {
		this.description = description;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @param estimatedHours the estimatedHours to set
	 */
	public void setEstimatedHours(Double estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	/**
	 * @param id the ticket id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * @param lastDate the timestamp of last change to set
	 */
	public void setLastDate(LocalDateTime lastDate) {
		this.lastDate = lastDate;
	}

	/**
	 * @param milestone the milestone to set
	 */
	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
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
	public void setOfferedHours(Double offeredHours) {
		this.offeredHours = offeredHours;
	}

	/**
	 * @param owner the owner of the ticket to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @param parent the parent ticket id to set
	 */
	public void setParent(Ticket parent) {
		this.parent = parent;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(TicketPriority priority) {
		this.priority = priority;
	}

	/**
	 * @param release the release to set
	 */
	public void setRelease(String release) {
		this.release = release;
	}

	/**
	 * @param reporter the reporter of the ticket to set
	 */
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	/**
	 * @param resolution the resolution to set
	 */
	public void setResolution(TicketResolution resolution) {
		this.resolution = resolution;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(TicketState state) {
		this.state = state;
	}

	/**
	 * @param summary the short summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @param totalHours the totalHours to set
	 */
	public void setTotalHours(Double totalHours) {
		this.totalHours = totalHours;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TicketType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", summary=" + summary + "]";
	}

}
package aero.minova.crm.model.jpa;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
	private LocalDateTime lastDate; // changetime
	private String cc;
	private String owner;
	private String reporter;
	private List<Integer> blockedBy; // blockedby
	private String moduleNames; // modulenames
	private TicketPriority priority;
	@ManyToOne(cascade = CascadeType.ALL)
	private Ticket parent;
	private boolean billable;
	private TicketType type;
	private LocalDate dueDate; // dueDate
	private TicketState state; // status
	private Double estimatedHours; // esitmatedhours
	private Double offeredHours; // offeredhours
	private Double totalHours; // totalhours
	@ManyToOne(cascade = CascadeType.ALL)
	private TicketComponent component;
	@ManyToOne(cascade = CascadeType.ALL)
	private Milestone milestone;
	private List<Integer> blocking;
	private String release;
	private Resolution resolution;
	private String customerDescription; // customerdescription
	private TicketCustomerPrio customerPrio; // customerprio
	private TicketCustomerType customerType; // customertype

	/**
	 * @return ticket id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the ticket id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the short summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            the short summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the markup description
	 */
	public MarkupText getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the markup description to set
	 */
	public void setDescription(MarkupText description) {
		this.description = description;
	}

	/**
	 * @return the timestamp of last change
	 */
	public LocalDateTime getLastDate() {
		return lastDate;
	}

	/**
	 * @param lastDate
	 *            the timestamp of last change to set
	 */
	public void setLastDate(LocalDateTime lastDate) {
		this.lastDate = lastDate;
	}

	/**
	 * @return the carbon copies
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * @param cc
	 *            the carbon copies to set
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}

	/**
	 * @return the owner of the ticket
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner of the ticket to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the reporter of the ticket
	 */
	public String getReporter() {
		return reporter;
	}

	/**
	 * @param reporter
	 *            the reporter of the ticket to set
	 */
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	/**
	 * @return the tickets (ticket ids) blocking this ticket
	 */
	public List<Integer> getBlockedBy() {
		return blockedBy;
	}

	/**
	 * @param blockedBy
	 *            the tickets (ticket ids) blocking this ticket to set
	 */
	public void setBlockedBy(List<Integer> blockedBy) {
		this.blockedBy = blockedBy;
	}

	/**
	 * @return the moduleNames
	 */
	public String getModuleNames() {
		return moduleNames;
	}

	/**
	 * @param moduleNames
	 *            the moduleNames to set
	 */
	public void setModuleNames(String moduleNames) {
		this.moduleNames = moduleNames;
	}

	/**
	 * @return the priority
	 */
	public TicketPriority getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(TicketPriority priority) {
		this.priority = priority;
	}

	/**
	 * @return the parents
	 */
	public Ticket getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent ticket id to set
	 */
	public void setParent(Ticket parent) {
		this.parent = parent;
	}

	/**
	 * @return the billable
	 */
	public boolean isBillable() {
		return billable;
	}

	/**
	 * @param billable
	 *            the billable to set
	 */
	public void setBillable(boolean billable) {
		this.billable = billable;
	}

	/**
	 * @return the type
	 */
	public TicketType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(TicketType type) {
		this.type = type;
	}

	/**
	 * @return the dueDate
	 */
	public LocalDate getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate
	 *            the dueDate to set
	 */
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the state
	 */
	public TicketState getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(TicketState state) {
		this.state = state;
	}

	/**
	 * @return the estimatedHours
	 */
	public Double getEstimatedHours() {
		return estimatedHours;
	}

	/**
	 * @param estimatedHours
	 *            the estimatedHours to set
	 */
	public void setEstimatedHours(Double estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	/**
	 * @return the offeredHours
	 */
	public Double getOfferedHours() {
		return offeredHours;
	}

	/**
	 * @param offeredHours
	 *            the offeredHours to set
	 */
	public void setOfferedHours(Double offeredHours) {
		this.offeredHours = offeredHours;
	}

	/**
	 * @return the totalHours
	 */
	public Double getTotalHours() {
		return totalHours;
	}

	/**
	 * @param totalHours
	 *            the totalHours to set
	 */
	public void setTotalHours(Double totalHours) {
		this.totalHours = totalHours;
	}

	/**
	 * @return the component
	 */
	public TicketComponent getComponent() {
		return component;
	}

	/**
	 * @param component
	 *            the component to set
	 */
	public void setComponent(TicketComponent component) {
		this.component = component;
	}

	/**
	 * @return the milestone
	 */
	public Milestone getMilestone() {
		return milestone;
	}

	/**
	 * @param milestone
	 *            the milestone to set
	 */
	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
	}

	/**
	 * @return the blocking
	 */
	public List<Integer> getBlocking() {
		return blocking;
	}

	/**
	 * @param blocking
	 *            the blocking to set
	 */
	public void setBlocking(List<Integer> blocking) {
		this.blocking = blocking;
	}

	/**
	 * @return the release
	 */
	public String getRelease() {
		return release;
	}

	/**
	 * @param release
	 *            the release to set
	 */
	public void setRelease(String release) {
		this.release = release;
	}

	/**
	 * @return the resolution
	 */
	public Resolution getResolution() {
		return resolution;
	}

	/**
	 * @param resolution
	 *            the resolution to set
	 */
	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	/**
	 * @return the customerDescription
	 */
	public String getCustomerDescription() {
		return customerDescription;
	}

	/**
	 * @param customerDescription
	 *            the customerDescription to set
	 */
	public void setCustomerDescription(String customerDescription) {
		this.customerDescription = customerDescription;
	}

	/**
	 * @return the customerPrio
	 */
	public TicketCustomerPrio getCustomerPrio() {
		return customerPrio;
	}

	/**
	 * @param customerPrio
	 *            the customerPrio to set
	 */
	public void setCustomerPrio(TicketCustomerPrio customerPrio) {
		this.customerPrio = customerPrio;
	}

	/**
	 * @return the customerType
	 */
	public TicketCustomerType getCustomerType() {
		return customerType;
	}

	/**
	 * @param customerType
	 *            the customerType to set
	 */
	public void setCustomerType(TicketCustomerType customerType) {
		this.customerType = customerType;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", summary=" + summary + "]";
	}

}

package aero.minova.crm.main.parts;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.help.EHelpService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import aero.minova.crm.http.HttpService;
import aero.minova.crm.main.jobs.LoadTicketJob;
import aero.minova.crm.model.jpa.Ticket;
import aero.minova.crm.model.service.TicketComponentService;
import aero.minova.crm.model.service.TicketService;
import aero.minova.trac.TracService;

public class TicketPart {
	private Text summaryField;
	private Browser browser;
	private Text descriptionText;
	private Text lastUserText;
	private Text lastModifiedText;
	private TabFolder tabFolder;
	private TabItem descriptionTabItem;

	MPart part;
	private Ticket ticket;
	
	@Inject
	EHandlerService handlerService;

	@Inject
	ECommandService commandService;
	
	@Inject
	UISynchronize sync;

	@Inject
	private TicketService ticketService;

	@Inject
	private TicketComponentService ticketComponentService;

	@Inject
	MApplication application;

	@Inject
	HttpService httpService;
	private TabItem detailsTabItem;
	private Composite detailsComposite;
	private Text reporterField;
	private Text ownerField;
	private Text ccField;
	private Text estimatedHoursField;
	private Text offeredHoursField;
	private Text totalHoursField;
	private Text typeField;
	private Text startDateField;
	private Text dueDateField;
	private Text priorityField;
	private Text milestoneField;
	private Text parentField;
	private Text componentField;
	private Text blockedByField;
	private Text keywordsField;
	private Text blockingField;

	@Inject
	public TicketPart() {
		// httpService.start(application, 8082);
	}

	@PostConstruct
	public void postConstruct(MApplication application, Composite parent, MPart part) {
		this.part = part;
		this.application = application;

		int ticketId = -1;
		try {
			ticketId = Integer.parseInt(part.getLabel().substring(1));
		} catch (Exception e) {
		}

		parent.setLayout(new FormLayout());

		summaryField = new Text(parent, SWT.BORDER);
		tabFolder = new TabFolder(parent, SWT.NONE);
		createDetailsTabItem(tabFolder);
		descriptionTabItem = new TabItem(tabFolder, SWT.NONE);
		descriptionText = new Text(tabFolder, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		lastUserText = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		lastModifiedText = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		browser = new Browser(parent, SWT.NONE);
		browser.addLocationListener(new BrowserLocationListener(handlerService, commandService));
		Label lastUserLabel = new Label(parent, SWT.NONE);
		Label lastModifiedLabel = new Label(parent, SWT.NONE);

		FormData fd_tabFolder = new FormData();
		fd_tabFolder.bottom = new FormAttachment(lastUserText, -10);
		fd_tabFolder.top = new FormAttachment(summaryField, 10);
		fd_tabFolder.right = new FormAttachment(50, -5);
		fd_tabFolder.left = new FormAttachment(0, 10);
		tabFolder.setLayoutData(fd_tabFolder);

		descriptionTabItem.setText("Beschreibung");
		descriptionTabItem.setControl(descriptionText);

		lastUserLabel.setAlignment(SWT.RIGHT);
		FormData fd_lastUserLabel = new FormData();
		fd_lastUserLabel.right = new FormAttachment(25, -5);
		fd_lastUserLabel.left = new FormAttachment(0, 10);
		lastUserLabel.setLayoutData(fd_lastUserLabel);
		lastUserLabel.setText("zuletzt geändert von");
		FormData fd_lastUserText = new FormData();
		fd_lastUserText.left = new FormAttachment(25, 5);
		fd_lastUserText.right = new FormAttachment(50, -5);
		lastUserText.setLayoutData(fd_lastUserText);

		fd_lastUserLabel.bottom = new FormAttachment(lastModifiedLabel, -10);
		fd_lastUserText.bottom = new FormAttachment(lastModifiedLabel, -10);
		lastModifiedLabel.setAlignment(SWT.RIGHT);
		FormData fd_lastModifiedLabel = new FormData();
		fd_lastModifiedLabel.bottom = new FormAttachment(100, -10);
		fd_lastModifiedLabel.right = new FormAttachment(25, -5);
		fd_lastModifiedLabel.left = new FormAttachment(0, 10);
		lastModifiedLabel.setLayoutData(fd_lastModifiedLabel);
		lastModifiedLabel.setText("am");
		FormData fd_lastModifiedText = new FormData();
		fd_lastModifiedText.left = new FormAttachment(25, 5);
		fd_lastModifiedText.right = new FormAttachment(50, -5);
		fd_lastModifiedText.bottom = new FormAttachment(100, -10);
		lastModifiedText.setLayoutData(fd_lastModifiedText);

		FormData fd_browser = new FormData();
		fd_browser.bottom = new FormAttachment(100, -10);
		fd_browser.left = new FormAttachment(50, 5);
		fd_browser.right = new FormAttachment(100, -10);
		fd_browser.top = new FormAttachment(summaryField, 10);
		browser.setLayoutData(fd_browser);

		// Layout
		FormData fd;

		fd = new FormData();
		fd.right = new FormAttachment(100, -10);
		fd.left = new FormAttachment(0, 10);
		fd.top = new FormAttachment(0, 10);
		summaryField.setLayoutData(fd);

		descriptionText.addModifyListener(e ->

		refreshMarkdown());

		if (part.getLabel().startsWith("#")) {
			setTicketId(ticketId);
		}
	}

	private void createDetailsTabItem(TabFolder tabFolder) {
		detailsTabItem = new TabItem(tabFolder, SWT.NONE);
		detailsTabItem.setText("Details");
		ScrolledComposite detailsScrolledComposite = new ScrolledComposite(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL);
		detailsTabItem.setControl(detailsScrolledComposite);
		FocusAdapter focusGained = new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				detailsScrolledComposite.showControl((Control) e.widget);
			}
		};

		detailsComposite = new Composite(detailsScrolledComposite, SWT.NONE);
		Label reporterLabel = new Label(detailsComposite, SWT.RIGHT);
		reporterLabel.setText("Reporter");
		reporterLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		reporterField = new Text(detailsComposite, SWT.BORDER);
		reporterField.setLayoutData(new GridData(120, SWT.DEFAULT));
		reporterField.addFocusListener(focusGained);
		Label estimatedHoursLabel = new Label(detailsComposite, SWT.RIGHT);
		estimatedHoursLabel.setText("Geschätzt");
		estimatedHoursLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		estimatedHoursField = new Text(detailsComposite, SWT.BORDER | SWT.RIGHT);
		estimatedHoursField.setLayoutData(new GridData(96, SWT.DEFAULT));
		estimatedHoursField.addFocusListener(focusGained);

		Label ownerLabel = new Label(detailsComposite, SWT.RIGHT);
		ownerLabel.setText("Owner");
		ownerLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		ownerField = new Text(detailsComposite, SWT.BORDER);
		ownerField.setLayoutData(new GridData(120, SWT.DEFAULT));
		ownerField.addFocusListener(focusGained);
		Label offeredHoursLabel = new Label(detailsComposite, SWT.RIGHT);
		offeredHoursLabel.setText("Angeboten");
		offeredHoursLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		offeredHoursField = new Text(detailsComposite, SWT.BORDER | SWT.RIGHT);
		offeredHoursField.setLayoutData(new GridData(96, SWT.DEFAULT));
		offeredHoursField.addFocusListener(focusGained);

		Label ccLabel = new Label(detailsComposite, SWT.RIGHT);
		ccLabel.setText("CC");
		ccLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		ccField = new Text(detailsComposite, SWT.BORDER);
		ccField.setLayoutData(new GridData(120, SWT.DEFAULT));
		ccField.addFocusListener(focusGained);
		Label totalHoursLabel = new Label(detailsComposite, SWT.RIGHT);
		totalHoursLabel.setText("Geleistet");
		totalHoursLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		totalHoursField = new Text(detailsComposite, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		totalHoursField.setLayoutData(new GridData(96, SWT.DEFAULT));
		totalHoursField.addFocusListener(focusGained);

		Label typeLabel = new Label(detailsComposite, SWT.RIGHT);
		typeLabel.setText("Typ");
		typeLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		typeField = new Text(detailsComposite, SWT.BORDER);
		typeField.setLayoutData(new GridData(120, SWT.DEFAULT));
		typeField.addFocusListener(focusGained);
		Label startDateLabel = new Label(detailsComposite, SWT.RIGHT);
		startDateLabel.setText("Beginnt am");
		startDateLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		startDateField = new Text(detailsComposite, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		startDateField.setLayoutData(new GridData(96, SWT.DEFAULT));
		startDateField.addFocusListener(focusGained);

		Label priorityLabel = new Label(detailsComposite, SWT.RIGHT);
		priorityLabel.setText("Priorität");
		priorityLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		priorityField = new Text(detailsComposite, SWT.BORDER);
		priorityField.setLayoutData(new GridData(120, SWT.DEFAULT));
		priorityField.addFocusListener(focusGained);
		Label dueDateLabel = new Label(detailsComposite, SWT.RIGHT);
		dueDateLabel.setText("Erledigt bis");
		dueDateLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		dueDateField = new Text(detailsComposite, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		dueDateField.setLayoutData(new GridData(96, SWT.DEFAULT));
		dueDateField.addFocusListener(focusGained);

		Label milestoneLabel = new Label(detailsComposite, SWT.RIGHT);
		milestoneLabel.setText("Meilenstein");
		milestoneLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		milestoneField = new Text(detailsComposite, SWT.BORDER);
		milestoneField.setLayoutData(new GridData(120, SWT.DEFAULT));
		milestoneField.addFocusListener(focusGained);
		Label parentLabel = new Label(detailsComposite, SWT.RIGHT);
		parentLabel.setText("Parent Tickets");
		parentLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		parentField = new Text(detailsComposite, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		parentField.setLayoutData(new GridData(96, SWT.DEFAULT));
		parentField.addFocusListener(focusGained);

		Label componentLabel = new Label(detailsComposite, SWT.RIGHT);
		componentLabel.setText("Komponente");
		componentLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		componentField = new Text(detailsComposite, SWT.BORDER);
		componentField.setLayoutData(new GridData(120, SWT.DEFAULT));
		componentField.addFocusListener(focusGained);
		Label blockedByLabel = new Label(detailsComposite, SWT.RIGHT);
		blockedByLabel.setText("Wartet auf");
		blockedByLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		blockedByField = new Text(detailsComposite, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		blockedByField.setLayoutData(new GridData(120, SWT.DEFAULT));
		blockedByField.addFocusListener(focusGained);

		Label keywordsLabel = new Label(detailsComposite, SWT.RIGHT);
		keywordsLabel.setText("Stichworte");
		keywordsLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		keywordsField = new Text(detailsComposite, SWT.BORDER);
		keywordsField.setLayoutData(new GridData(120, SWT.DEFAULT));
		keywordsField.addFocusListener(focusGained);
		Label blockingLabel = new Label(detailsComposite, SWT.RIGHT);
		blockingLabel.setText("Blockiert");
		blockingLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		blockingField = new Text(detailsComposite, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		blockingField.setLayoutData(new GridData(120, SWT.DEFAULT));
		blockingField.addFocusListener(focusGained);

		detailsScrolledComposite.setExpandHorizontal(true);
		detailsScrolledComposite.setExpandVertical(true);

		detailsComposite.setLayout(new GridLayout(4, false));
		detailsComposite.setTabList(new Control[] {
				// 1. Spalte
				reporterField, ownerField, ccField, typeField, priorityField, milestoneField, componentField,
				keywordsField,
				// 2. Spalte
				estimatedHoursField, offeredHoursField, totalHoursField, startDateField, dueDateField, parentField,
				blockedByField, blockingField });

		detailsScrolledComposite.setContent(detailsComposite);
		detailsScrolledComposite.setMinSize(detailsComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

	}

	private void refreshMarkdown() {
		httpService.start(application, 8082);
		browser.setUrl("http:/localhost:8082/markdown?text="
				+ URLEncoder.encode(descriptionText.getText(), StandardCharsets.UTF_8));
	}

	@Focus
	public void onFocus() {
		descriptionText.forceFocus();
	}

	@Persist
	public void save() {
		ticketService.saveTicket(ticket);
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
		sync.syncExec(() -> update());
	}

	/**
	 * Es wurde ein neues Ticket gesetzt und muss jetzt angezeigt werden.
	 */
	private void update() {
		reporterField.setText((ticket == null || ticket.getReporter() == null) ? "" : ticket.getReporter());
		summaryField.setText((ticket == null || ticket.getSummary() == null) ? "" : ticket.getSummary());
		ownerField.setText((ticket == null || ticket.getOwner() == null) ? "" : ticket.getOwner());
		ccField.setText((ticket == null || ticket.getCc() == null) ? "" : ticket.getCc());
		descriptionText.setText(
				(ticket == null || ticket.getDescription() == null) ? "" : ticket.getDescription().getMarkup());
		lastModifiedText.setText((ticket == null || ticket.getLastDate() == null) ? ""
				: ticket.getLastDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
		estimatedHoursField.setText((ticket == null || ticket.getEstimatedHours() == null) ? ""
				: MessageFormat.format("{0,number,#,##0.0}", ticket.getEstimatedHours()));
		offeredHoursField.setText((ticket == null || ticket.getOfferedHours() == null) ? ""
				: MessageFormat.format("{0,number,#,##0.0}", ticket.getOfferedHours()));
		totalHoursField.setText((ticket == null || ticket.getTotalHours() == null) ? ""
				: MessageFormat.format("{0,number,#,##0.0}", ticket.getTotalHours()));
		typeField.setText((ticket == null || ticket.getType() == null) ? "" : ticket.getType().getName());
		priorityField.setText((ticket == null || ticket.getPriority() == null) ? "" : ticket.getPriority().getName());
		milestoneField
				.setText((ticket == null || ticket.getMilestone() == null) ? "" : ticket.getMilestone().getName());
		componentField
				.setText((ticket == null || ticket.getComponent() == null) ? "" : ticket.getComponent().getName());
		keywordsField.setText((ticket == null || ticket.getKeywords() == null) ? "" : ticket.getKeywords());

		startDateField.setText((ticket == null || ticket.getStartDate() == null) ? ""
				: ticket.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
		dueDateField.setText((ticket == null || ticket.getDueDate() == null) ? ""
				: ticket.getDueDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
		parentField.setText((ticket == null || ticket.getParent() == null) ? "" : "" + ticket.getParent().getId());
		blockedByField.setText(
				(ticket == null || ticket.getBlockedBy() == null) ? "" : ticket.getBlockedBy().getTicketString());
		blockingField.setText(
				(ticket == null || ticket.getBlocking() == null) ? "" : ticket.getBlocking().getTicketString());

		summaryField.setMessage("");
	}

	public void focusDetails() {
		tabFolder.setSelection(detailsTabItem);
		reporterField.forceFocus();
	}

	public void setTicketId(int ticketId) {
		if (ticket == null || ticket.getId() != ticketId) {
			ticket = null;
			update();
			summaryField.setMessage("lade Ticket...");
			LoadTicketJob job = new LoadTicketJob(ticketService, ticketId, this);
			job.schedule();
		} else {
			summaryField.setMessage("");
		}
	}
}
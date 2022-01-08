
package aero.minova.crm.main.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import aero.minova.crm.http.HttpService;
import aero.minova.crm.main.jobs.LoadWikiJob;
import aero.minova.crm.model.jpa.Wiki;
import aero.minova.crm.model.service.WikiService;

public class WikiPart {
	private Text pathField;
	private Browser editBrowser;
	private Browser viewBrowser;

	private Wiki page;

	@Inject
	EHandlerService handlerService;

	@Inject
	ECommandService commandService;

	@Inject
	UISynchronize sync;

	@Inject
	private WikiService wikiService;

	@Inject
	MApplication application;
	
	@Inject
	MPart part;

	@Inject
	HttpService httpService;
	private TabFolder tabFolder;
	private TabItem editTabItem;
	private TabItem viewTabItem;
	private String path;

	@Inject
	public WikiPart() {
	}

	@PostConstruct
	public void createUI(Composite parent) {

		tabFolder = new TabFolder(parent, SWT.NONE);

		editTabItem = new TabItem(tabFolder, SWT.NONE);
		editTabItem.setText("Bearbeiten");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		editTabItem.setControl(composite);
		GridLayout gl_composite = new GridLayout(4, false);
		gl_composite.marginHeight = 0;
		composite.setLayout(gl_composite);

		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1));

		SashForm sashForm = new SashForm(composite, SWT.BORDER);
		new Label(composite, SWT.NONE);

		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.VERTICAL));
		composite_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));

		editBrowser = new Browser(composite_1, SWT.NONE);

		viewTabItem = new TabItem(tabFolder, SWT.NONE);
		viewTabItem.setText("View");

		viewBrowser = new Browser(tabFolder, SWT.NONE);
		viewBrowser.addLocationListener(new BrowserLocationListener(handlerService, commandService));

		viewTabItem.setControl(viewBrowser);

		setPath((String)part.getPersistedState().get("path"));
	}

	private void refreshMarkdown() {
//TODO		httpService.start(application, 8082);
//		if (page != null && page.getDescription() != null) {
//			page.getDescription().setMarkup(descriptionText.getText());
//			httpService.setTicket(page);
//			browser.setUrl("http:/localhost:8082/markdown?part=all");
//		}
	}

	@Focus
	public void onFocus() {
		viewBrowser.forceFocus();
	}

	@Persist
	public void save() {
//TODO		ticketService.saveTicket(page);
		part.setDirty(false);
	}

	/**
	 * Es wurde ein neues Ticket gesetzt und muss jetzt angezeigt werden.
	 */
	private void update() {
//		reporterField.setText((page == null || page.getReporter() == null) ? "" : page.getReporter());
//		pathField.setText((page == null || page.getSummary() == null) ? "" : page.getSummary());
//		ownerField.setText((page == null || page.getOwner() == null) ? "" : page.getOwner());
//		ccField.setText((page == null || page.getCc() == null) ? "" : page.getCc());
//		descriptionText.setText((page == null || page.getDescription() == null) ? "" : page.getDescription().getMarkup());
//		lastModifiedText.setText(
//				(page == null || page.getLastDate() == null) ? "" : page.getLastDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
//		estimatedHoursField
//				.setText((page == null || page.getEstimatedHours() == null) ? "" : MessageFormat.format("{0,number,#,##0.0}", page.getEstimatedHours()));
//		offeredHoursField
//				.setText((page == null || page.getOfferedHours() == null) ? "" : MessageFormat.format("{0,number,#,##0.0}", page.getOfferedHours()));
//		totalHoursField.setText((page == null || page.getTotalHours() == null) ? "" : MessageFormat.format("{0,number,#,##0.0}", page.getTotalHours()));
//		typeField.setText((page == null || page.getType() == null) ? "" : page.getType().getName());
//		priorityField.setText((page == null || page.getPriority() == null) ? "" : page.getPriority().getName());
//		milestoneField.setText((page == null || page.getMilestone() == null) ? "" : page.getMilestone().getName());
//		componentField.setText((page == null || page.getComponent() == null) ? "" : page.getComponent().getName());
//		keywordsField.setText((page == null || page.getKeywords() == null) ? "" : page.getKeywords());
//
//		startDateField
//				.setText((page == null || page.getStartDate() == null) ? "" : page.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
//		dueDateField.setText((page == null || page.getDueDate() == null) ? "" : page.getDueDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
//		parentField.setText((page == null || page.getParent() == null) ? "" : "" + page.getParent().getId());
//		blockedByField.setText((page == null || page.getBlockedBy() == null) ? "" : page.getBlockedBy().getTicketString());
//		blockingField.setText((page == null || page.getBlocking() == null) ? "" : page.getBlocking().getTicketString());

//		pathField.setMessage("");
//		part.setDirty(false);
		httpService.start(application, 8082);
		if (page != null && page.getDescription() != null) {
			httpService.setWiki(page);
			viewBrowser.setUrl("http:/localhost:8082/markdown?part=all");
		}
	}

	public void focusDetails() {
		tabFolder.setSelection(editTabItem);
		pathField.forceFocus();
	}

	public void setPath(String path) {
		this.path = path;
		if (page == null || !page.getPath().equals(path)) {
			page = null;
			update();
//			pathField.setMessage("lade Seite...");
			LoadWikiJob job = new LoadWikiJob(wikiService, path, this);
			job.schedule();
		} else {
			pathField.setMessage("");
		}
	}

	public void setPage(Wiki page) {
		this.page = page;
		sync.syncExec(() -> update());
	}
}
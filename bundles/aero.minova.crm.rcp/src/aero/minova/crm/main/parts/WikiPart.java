
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import aero.minova.crm.http.HttpService;
import aero.minova.crm.main.jobs.LoadWikiJob;
import aero.minova.crm.model.jpa.Wiki;
import aero.minova.crm.model.service.WikiService;

public class WikiPart {
	private Text pathField;
	private Text editField;
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
	private boolean updating = false;

	@Inject
	public WikiPart() {
	}

	@PostConstruct
	public void createUI(Composite parent) {

		tabFolder = new TabFolder(parent, SWT.NONE);

		viewTabItem = new TabItem(tabFolder, SWT.NONE);
		viewTabItem.setText("View");
		editTabItem = new TabItem(tabFolder, SWT.NONE);
		editTabItem.setText("Bearbeiten");

		viewBrowser = new Browser(tabFolder, SWT.NONE);
		viewBrowser.addLocationListener(new BrowserLocationListener(handlerService, commandService));

		viewTabItem.setControl(viewBrowser);

		SashForm sashForm = new SashForm(tabFolder, SWT.NONE);
		editTabItem.setControl(sashForm);

		editField = new Text(sashForm, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		editField.addModifyListener(e -> {
			part.setDirty(!updating );
			refreshMarkdown();
		});

		editBrowser = new Browser(sashForm, SWT.NONE);

		sashForm.setOrientation(SWT.HORIZONTAL);

		setPath((String) part.getPersistedState().get("path"));
	}

	private void refreshMarkdown() {
		httpService.start(application, 8082);
		if (page != null && page.getDescription() != null) {
			String text = editField.getText();
			page.getDescription().setMarkup(text);
			int caretPosition = editField.getCaretPosition();

			while (caretPosition > 0 && text.charAt(caretPosition - 1) != '\n') {
				caretPosition--;
			}

			text = text.substring(0, caretPosition) + "<a id=\"caret\" />" + text.substring(caretPosition);
			httpService.setText(text);
			editBrowser.setUrl("http:/localhost:8082/markdown/text#caret");
		}
	}

	@Focus
	public void onFocus() {
		viewBrowser.forceFocus();
	}

	@Persist
	public void save() {
		wikiService.saveWiki(page);
		part.setDirty(false);
		update();
	}

	/**
	 * Es wurde ein neues Ticket gesetzt und muss jetzt angezeigt werden.
	 */
	private void update() {
		updating = true;
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

			editField.setText(page.getDescription().getMarkup());
			editBrowser.setUrl("http:/localhost:8082/markdown?part=all");
		}
		updating = false;
	}

	public void focusDetails() {
		tabFolder.setSelection(editTabItem);
		pathField.forceFocus();
	}

	public void setPath(String path) {
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

package aero.minova.crm.main.parts;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import aero.minova.crm.http.HttpService;
import aero.minova.crm.main.jobs.LoadTicketJob;
import aero.minova.crm.model.jpa.Ticket;
import aero.minova.crm.model.service.jpa.TicketService;
import aero.minova.trac.TracService;
import aero.minova.trac.domain.TracTicket;

public class TicketPart {
	private Text summaryText;
	private Browser browser;
	private Text descriptionText;
	private Text lastUserText;
	private Text lastModifiedText;
	private TabFolder tabFolder;
	private TabItem tbtmBeschreibung;

	MPart part;
	private Ticket ticket;
	private int ticketId = -1;

	@Inject
	private TicketService ticketService;

	@Inject
	private TracService tracService;

	@Inject
	MApplication application;

	@Inject
	HttpService httpService;

	@Inject
	public TicketPart() {
		// httpService.start(application, 8082);
	}

	@PostConstruct
	public void postConstruct(MApplication application, Composite parent, MPart part) {
		this.part = part;
		this.application = application;
		try {
			ticketId = Integer.parseInt(part.getLabel().substring(1));
		} catch (Exception e) {}

		parent.setLayout(new FormLayout());

		summaryText = new Text(parent, SWT.BORDER);
		tabFolder = new TabFolder(parent, SWT.NONE);
		tbtmBeschreibung = new TabItem(tabFolder, SWT.NONE);
		descriptionText = new Text(tabFolder, SWT.BORDER | SWT.MULTI);
		lastUserText = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		lastModifiedText = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		browser = new Browser(parent, SWT.NONE);

		Label lastUserLabel = new Label(parent, SWT.NONE);
		Label lastModifiedLabel = new Label(parent, SWT.NONE);

		FormData fd_tabFolder = new FormData();
		fd_tabFolder.bottom = new FormAttachment(lastUserText, -10);
		fd_tabFolder.top = new FormAttachment(summaryText, 10);
		fd_tabFolder.right = new FormAttachment(50, -5);
		fd_tabFolder.left = new FormAttachment(0, 10);
		tabFolder.setLayoutData(fd_tabFolder);

		tbtmBeschreibung.setText("Beschreibung");
		tbtmBeschreibung.setControl(descriptionText);

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
		fd_browser.top = new FormAttachment(summaryText, 10);
		browser.setLayoutData(fd_browser);

		// Layout
		FormData fd;

		fd = new FormData();
		fd.right = new FormAttachment(100, -10);
		fd.left = new FormAttachment(0, 10);
		fd.top = new FormAttachment(0, 10);
		summaryText.setLayoutData(fd);

		if (ticket == null) {
			LoadTicketJob job = new LoadTicketJob(tracService, ticketService, ticketId, this);
			job.schedule();
		}

		descriptionText.addModifyListener(e -> refreshMarkdown());
	}

	private void refreshMarkdown() {
		httpService.start(application, 8082);
		browser.setUrl("http:/localhost:8082/markdown?text=" + URLEncoder.encode(descriptionText.getText(), StandardCharsets.UTF_8));
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
		update();
	}

	/**
	 * Es wurde ein neues Ticket gesetzt und muss jetzt angezeigt werden.
	 */
	private void update() {}

	public void setTicketId(int ticketId) {
		Ticket ticket;
		Optional<Ticket> ticketOptional = ticketService.getTicket(ticketId);
		if (ticketOptional.isEmpty()) {
			TracTicket tracTicket = tracService.getTicket(ticketId);
		}
	}
}
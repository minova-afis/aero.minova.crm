package aero.minova.crm.main.parts;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.widgets.TextFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import aero.minova.crm.http.HttpService;
import aero.minova.crm.model.jpa.Contact;
import aero.minova.crm.model.jpa.MarkupText;
import aero.minova.crm.model.jpa.Ticket;
import aero.minova.crm.model.service.TicketService;
import aero.minova.trac.TracService;
import aero.minova.trac.domain.TracTicket;
import ezvcard.Ezvcard;
import ezvcard.VCard;

public class SamplePart {

	private Browser browser;

	private Text text;

	private Pattern ticketPattern = Pattern.compile("#([0-9]*) ");

	@Inject
	TicketService ticketService;

	@Inject
	TracService tracService;

	@Inject
	HttpService httpService;

	@Inject
	MApplication application;

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		text = TextFactory.newText(SWT.BORDER) //
				.message("Enter text to mark part as dirty") //
				.onModify(e -> refresh()) //
				.layoutData(new GridData(GridData.FILL_HORIZONTAL))//
				.create(parent);

		browser = new Browser(parent, SWT.NONE);
		browser.setLayoutData(new GridData(GridData.FILL_BOTH));
		refresh();

		String str = "BEGIN:VCARD\r\n" + "VERSION:4.0\r\n" + "N:Doe;Jonathan;;Mr;\r\n" + "FN:John Doe\r\n" + "END:VCARD\r\n";

		VCard vcard = Ezvcard.parse(str).first();
		String fullName = vcard.getFormattedName().getValue();
		String lastName = vcard.getStructuredName().getFamily();

		System.out.println(fullName);
		System.err.println(lastName);
	}

	public void refresh() {
		String t = text.getText();
		if (t.startsWith("#") && Pattern.matches("#([0-9]*) ", t)) {
			Matcher m = ticketPattern.matcher(t);
			m.find();
			String ticketId = m.group(1);
			int id = Integer.parseInt(ticketId);
			Optional<Ticket> ticketOptional = ticketService.getTicket(id);
			if (ticketOptional.isPresent()) {
				showTicket(ticketOptional.get());
				return;
			}
			Ticket ticket = null;
			TracTicket tracTicket = null;

			tracTicket = tracService.getTicket(id);
			ticket = new aero.minova.crm.model.jpa.Ticket();
			ticket.setId(id);
			ticket.setSummary((String) tracTicket.getSummary());
			MarkupText mt = new MarkupText();
			mt.setMarkup((String) tracTicket.getDescription());
			mt.setHtml(tracService.wikiToHtml(tracTicket.getDescription()));
			ticket.setDescription(mt);
			ticketService.saveTicket(ticket);
			showTicket(ticket);
			return;
		}
		httpService.start(application, 8082);
		browser.setUrl("http:/localhost:8082/test?x=" + text.getText());
	}

	public void refresh(String string) {
		httpService.start(application, 8082);
		browser.setUrl("http:/localhost:8082/test?x=" + string);
	}

	public void showTicket(Ticket ticket) {
		httpService.start(application, 8082);
		browser.setUrl("http:/localhost:8082/ticket?id=" + ticket.getId());
	}

	public void showTicket(int ticketId) {
		httpService.start(application, 8082);
		browser.setUrl("http:/localhost:8082/ticket?id=" + ticketId);
	}
}
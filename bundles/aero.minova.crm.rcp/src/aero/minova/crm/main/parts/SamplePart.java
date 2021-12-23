package aero.minova.crm.main.parts;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.jface.widgets.TextFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import aero.minova.crm.main.jetty.TicketServlet;
import aero.minova.crm.model.jpa.MarkupText;
import aero.minova.crm.model.jpa.Ticket;
import aero.minova.crm.model.jpa.service.TicketService;
import aero.minova.trac.domain.Server;

public class SamplePart {

	private Browser browser;

	private Text text;

	private Pattern ticketPattern = Pattern.compile("#([0-9]*) ");

	@Inject
	TicketService ticketService;

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
				refresh(ticketOptional.get());
				return;
			}
			Ticket ticket = null;
			aero.minova.trac.domain.Ticket tracTicket = null;

			Server server = Server.getInstance();

			tracTicket = server.getTicket(id);
			ticket = new aero.minova.crm.model.jpa.Ticket();
			ticket.setId(id);
			ticket.setSummary((String) tracTicket.getSummary());
			MarkupText mt = new MarkupText();
			mt.setMarkup((String) tracTicket.getDescription());
			mt.setHtml(server.wikiTiHtml(tracTicket.getDescription()));
			ticket.setDescription(mt);
			ticketService.saveTicket(ticket);
			refresh(ticket);
			return;
		}
		browser.setUrl("http:/localhost:8082/test?x=" + text.getText());
	}

	public void refresh(String string) {
		browser.setUrl("http:/localhost:8082/test?x=" + string);
	}

	public void refresh(Ticket ticket) {
		TicketServlet.setLastTicket(ticket);
		browser.setUrl("http:/localhost:8082/ticket?id=" + ticket.getId());
	}

}
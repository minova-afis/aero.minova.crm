package aero.minova.crm.http;

import aero.minova.crm.model.jpa.Ticket;
import aero.minova.crm.model.jpa.Wiki;

import org.eclipse.e4.ui.model.application.MApplication;

public interface HttpService {
	public void start(MApplication application, int port);

	public void setTicket(Ticket ticket);

	public void setWiki(Wiki page);

	public void setText(String text);
}

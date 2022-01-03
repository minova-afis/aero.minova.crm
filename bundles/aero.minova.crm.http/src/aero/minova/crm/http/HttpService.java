package aero.minova.crm.http;

import aero.minova.crm.model.jpa.Ticket;
import org.eclipse.e4.ui.model.application.MApplication;

public interface HttpService {
	public void start(MApplication application, int port);

	public void setTicket(Ticket ticket);
}

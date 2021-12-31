
package aero.minova.crm.main.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import aero.minova.crm.http.HttpService;
import aero.minova.crm.main.parts.SamplePart;
import aero.minova.crm.model.service.TicketService;

public class StartWebserverHandler {

	@Execute
	public void execute(HttpService httpService, MApplication application, @Optional MPart part, TicketService ticketService) throws Exception {
		if (application.getContext().get("CRM-SERVER") == null) startServer(httpService, application, ticketService);

		if (part == null) return;
		if (!(part.getObject() instanceof SamplePart)) return;

		SamplePart samplePart = (SamplePart) part.getObject();
		samplePart.refresh();
	}

	private void startServer(HttpService httpService, MApplication application, TicketService ticketService) throws Exception {
		httpService.start(application, 8082);
		application.getContext().set("CRM-SERVER", httpService);
	}
}
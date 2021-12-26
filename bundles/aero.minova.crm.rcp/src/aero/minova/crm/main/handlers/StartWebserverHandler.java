
package aero.minova.crm.main.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

import aero.minova.crm.main.jetty.ExempleServlet;
import aero.minova.crm.main.jetty.TicketServlet;
import aero.minova.crm.main.parts.SamplePart;
import aero.minova.crm.model.service.jpa.TicketService;

public class StartWebserverHandler {
	@Execute
	public void execute(MApplication application, @Optional MPart part, TicketService ticketService) throws Exception {
		if (application.getContext().get("CRM-SERVER") == null) startServer(application, ticketService);

		if (part == null) return;
		if (!(part.getObject() instanceof SamplePart)) return;

		SamplePart samplePart = (SamplePart) part.getObject();
		samplePart.refresh();
	}

	private void startServer(MApplication application, TicketService ticketService) throws Exception {
		Server server = new Server();
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(8082);
		server.setConnectors(new Connector[] { connector });

		ServletHandler servletHandler = new ServletHandler();
		server.setHandler(servletHandler);

		servletHandler.addServletWithMapping(ExempleServlet.class, "/test");
		servletHandler.addServletWithMapping(TicketServlet.class, "/ticket");

		server.start();

		application.getContext().set("CRM-SERVER", server);
	}
}
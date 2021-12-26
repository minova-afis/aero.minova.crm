package aero.minova.crm.service.http;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.osgi.service.component.annotations.Component;

import aero.minova.crm.http.HttpService;
import aero.minova.crm.service.http.servlets.ExempleServlet;
import aero.minova.crm.service.http.servlets.MarkdownServlet;
import aero.minova.crm.service.http.servlets.TicketServlet;

@Component(service = HttpService.class)
public class HttpServiceImpl implements HttpService {

	@Override
	public void start(MApplication application, int port) {
		if (application.getContext().get("HttpService.port") != null) return;

		Server server = new Server();
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(port);
		server.setConnectors(new Connector[] { connector });

		ServletHandler servletHandler = new ServletHandler();
		server.setHandler(servletHandler);

		servletHandler.addServletWithMapping(ExempleServlet.class, "/test");
		servletHandler.addServletWithMapping(TicketServlet.class, "/ticket");
		servletHandler.addServletWithMapping(MarkdownServlet.class, "/markdown");

		try {
			server.start();
			application.getContext().set("HttpService.port", port);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

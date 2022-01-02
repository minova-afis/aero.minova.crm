package aero.minova.crm.service.http;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.osgi.service.datalocation.Location;
import org.osgi.service.component.annotations.Component;

import aero.minova.crm.http.HttpService;
import aero.minova.crm.service.http.servlets.ExempleServlet;
import aero.minova.crm.service.http.servlets.MarkdownServlet;
import aero.minova.crm.service.http.servlets.TicketServlet;

@Component(service = HttpService.class)
public class HttpServiceImpl implements HttpService {

	@Override
	public void start(MApplication application, int port) {
		if (application.getContext().get("HttpService.port") != null)
			return;

		try {
			Server server = new Server();
			ServerConnector connector = new ServerConnector(server);
			connector.setPort(port);
			server.setConnectors(new Connector[] { connector });

			ServletHandler handler = new ServletHandler();
			handler.addServletWithMapping(ExempleServlet.class, "/test");
			handler.addServletWithMapping(TicketServlet.class, "/ticket");
			handler.addServletWithMapping(MarkdownServlet.class, "/markdown");

			ResourceHandler resourceHandler = new ResourceHandler();
			String basePath = Platform.getInstanceLocation().getURL().getPath() + "static";
			resourceHandler.setResourceBase(basePath);
			resourceHandler.setDirectoriesListed(true);
			ContextHandler contextHandler = new ContextHandler("/static");
			contextHandler.setHandler(resourceHandler);

			HandlerList handlers = new HandlerList();
			handlers.setHandlers(new Handler[] {contextHandler, handler});
			server.setHandler(handlers);
			server.start();
			application.getContext().set("HttpService.port", port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

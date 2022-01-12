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
import org.eclipse.jetty.util.resource.Resource;
import org.osgi.service.component.annotations.Component;

import aero.minova.crm.http.HttpService;
import aero.minova.crm.model.jpa.Ticket;
import aero.minova.crm.model.jpa.Wiki;
import aero.minova.crm.service.http.servlets.ExempleServlet;
import aero.minova.crm.service.http.servlets.MarkdownServlet;
import aero.minova.crm.service.http.servlets.PluginResourceHandler;
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
			handler.addServletWithMapping(MarkdownServlet.class, "/markdown/*");

			ResourceHandler staticResourceHandler = new ResourceHandler();
			String basePath = Platform.getInstanceLocation().getURL().getPath() + "static";
			staticResourceHandler.setResourceBase(basePath);
			staticResourceHandler.setDirectoriesListed(true);
			ContextHandler contextHandler = new ContextHandler("/static");
			contextHandler.setHandler(staticResourceHandler);
			
			ResourceHandler attachmentResourceHandler = new ResourceHandler();
			basePath = Platform.getInstanceLocation().getURL().getPath() + "attachment";
			attachmentResourceHandler.setResourceBase(basePath);
			attachmentResourceHandler.setDirectoriesListed(true);
			ContextHandler attachmentContextHandler = new ContextHandler("/attachment");
			attachmentContextHandler.setHandler(attachmentResourceHandler);
			
			ResourceHandler cssResourceHandler = new PluginResourceHandler();
			cssResourceHandler.setBaseResource(Resource.newResource(PluginResourceHandler.class.getResource("/css")));
			cssResourceHandler.setResourceBase(basePath);
			cssResourceHandler.setDirectoriesListed(true);
			ContextHandler cssContextHandler = new ContextHandler("/css");
			cssContextHandler.setHandler(cssResourceHandler);
			

			HandlerList handlers = new HandlerList();
			handlers.setHandlers(new Handler[] { cssContextHandler, contextHandler, attachmentContextHandler, handler });
			server.setHandler(handlers);
			server.start();
			application.getContext().set("HttpService.port", port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setTicket(Ticket ticket) {
		MarkdownServlet.setTicket(ticket);
	}

	@Override
	public void setWiki(Wiki page) {
		MarkdownServlet.setWiki(page);
	}

	@Override
	public void setText(String text) {
		MarkdownServlet.setText(text);
	}
}

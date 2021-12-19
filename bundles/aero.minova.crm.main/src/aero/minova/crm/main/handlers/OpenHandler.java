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
import aero.minova.crm.main.parts.SamplePart;

public class OpenHandler {

	@Execute
	public void execute(MApplication application, @Optional MPart part) throws Exception {
		if (application.getContext().get("CRM-SERVER") == null) startServer(application);

		if (part == null) return;
		if (!(part.getObject() instanceof SamplePart)) return;

		SamplePart samplePart = (SamplePart) part.getObject();
		samplePart.refresh();
	}

	private void startServer(MApplication application) throws Exception {
		Server server = new Server();
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(8082);
		server.setConnectors(new Connector[] { connector });

		ServletHandler servletHandler = new ServletHandler();
		server.setHandler(servletHandler);

		servletHandler.addServletWithMapping(ExempleServlet.class, "/test");

		server.start();

		application.getContext().set("CRM-SERVER", server);
	}
}

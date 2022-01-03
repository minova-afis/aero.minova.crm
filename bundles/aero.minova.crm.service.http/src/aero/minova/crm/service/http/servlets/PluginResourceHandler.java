package aero.minova.crm.service.http.servlets;

import java.io.IOException;

import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

public class PluginResourceHandler extends ResourceHandler {
	@Override
	public Resource getResource(String path) throws IOException {
		Resource newResource = Resource.newResource(PluginResourceHandler.class.getResource("/css" + path));
		return newResource;
	}
}

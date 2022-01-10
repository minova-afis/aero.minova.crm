package aero.minova.crm.main.parts;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;

public class BrowserLocationListener implements LocationListener {

	private EHandlerService handlerService;
	private ECommandService commandService;

	public BrowserLocationListener(EHandlerService handlerService, ECommandService commandService) {
		this.handlerService = handlerService;
		this.commandService = commandService;
	}

	@Override
	public void changing(LocationEvent event) {
		if (!event.location.startsWith("http://localhost:8082/issues/")
				&& !event.location.startsWith("http://localhost:8082/wiki/"))
			return;
		String searchCriteria;
		if (event.location.startsWith("http://localhost:8082/issues/")) {
			searchCriteria = "#" + event.location.substring(event.location.lastIndexOf("/") + 1);
		} else {
			searchCriteria = "/" + event.location.substring(27);
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("aero.minova.crm.rcp.commandparameter.searchtext", searchCriteria);
		ParameterizedCommand createCommand = commandService.createCommand("aero.minova.crm.rcp.command.searchcommand",
				parameters);
//		createCommand.getParameterMap().put("aero.minova.crm.rcp.commandparameter.searchtext", "#5263");
		handlerService.executeHandler(createCommand);
		event.doit = false;
	}

	@Override
	public void changed(LocationEvent event) {
	}

}

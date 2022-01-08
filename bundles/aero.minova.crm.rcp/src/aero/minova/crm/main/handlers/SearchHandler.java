
package aero.minova.crm.main.handlers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import aero.minova.crm.main.dialogs.SearchDialog;
import aero.minova.crm.main.parts.WikiPart;

public class SearchHandler {

	@Inject
	EModelService modelService;
	@Inject
	MApplication application;
	
	MWindow window;

	@Execute
	public void execute(ParameterizedCommand command, EPartService partService, Shell shell, MWindow window) {
		this.window = window;
		String searchText = (String) command.getParameterMap().get("aero.minova.crm.rcp.commandparameter.searchtext");
		if (searchText == null) {
			SearchDialog dialog = new SearchDialog(shell);
			dialog.setBlockOnOpen(true);
			int result = dialog.open();

			if (result != Window.OK)
				return;

			searchText = dialog.getSearchText();
		}

		// suchen wir ein Ticket (Eingabe: "#5228")?
		Pattern ticketPattern = Pattern.compile("^#([0-9]+)$");
		Matcher ticketMatcher = ticketPattern.matcher(searchText);
		if (ticketMatcher.find()) {
			openTicket(window, modelService, ticketMatcher);
			return;
		}

		// Suchen wir eine Seite mit Namen (Eingabe: "/WikiStart")?
		Pattern wikiPattern = Pattern.compile("^(/[^]\\s]*)$");
		Matcher wikiMatcher = wikiPattern.matcher(searchText);
		if (wikiMatcher.find()) {
			openWiki(wikiMatcher);
			return;
		}

	}

	private void openWiki(Matcher wikiMatcher) {
		String path = wikiMatcher.group(1);
		if (path == null || path.length() == 0)
			return;
		if ("/".equals(path))
			path = "/WikiStart";

		MPartStack partStack = (MPartStack) modelService.find("aero.minova.crm.partstack.crm", window);
		MPart wikiPart = (MPart) modelService.find("aero.minova.crm.rcp.part.wiki" + path, partStack);

		if (wikiPart != null) {
			wikiPart.setVisible(true);
			wikiPart.setToBeRendered(true);
			partStack.setSelectedElement(wikiPart);
			return;
		}

		wikiPart = (MPart) modelService.cloneSnippet(application, "aero.minova.crm.rcp.part.wiki", null);
		if (wikiPart != null) {
			wikiPart.setElementId("aero.minova.crm.rcp.part.wiki" + path);
			wikiPart.setLabel(path.substring(path.lastIndexOf("/")+1));
			wikiPart.getPersistedState().put("path", path);
		}

		if (partStack != null && wikiPart != null) {
			partStack.getChildren().add(wikiPart);
			partStack.setSelectedElement(wikiPart);
		}
	}

	private void openTicket(MWindow window, EModelService modelService, Matcher ticketMatcher) {
		int ticket = Integer.parseInt(ticketMatcher.group(1));

		MPartStack partStack = (MPartStack) modelService.find("aero.minova.crm.partstack.crm", window);
		MPart ticketPart = (MPart) modelService.find("aero.minova.crm.rcp.part.ticket#" + ticket, partStack);

		if (ticketPart != null) {
			ticketPart.setVisible(true);
			ticketPart.setToBeRendered(true);
			partStack.setSelectedElement(ticketPart);
			return;
		}

		ticketPart = (MPart) modelService.cloneSnippet(application, "aero.minova.crm.rcp.part.ticket", null);
		if (ticketPart != null) {
			ticketPart.setElementId("aero.minova.crm.rcp.part.ticket#" + ticket);
			ticketPart.setLabel("#" + ticket);
		}

		if (partStack != null && ticketPart != null) {
			partStack.getChildren().add(ticketPart);
			partStack.setSelectedElement(ticketPart);
		}
	}

}
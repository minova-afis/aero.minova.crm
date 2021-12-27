
package aero.minova.crm.main.handlers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class SearchHandler {
	@Execute
	public void execute(MApplication application, MWindow window, EModelService modelService, EPartService partService, Shell shell) {
		SearchDialog dialog = new SearchDialog(shell);
		dialog.setBlockOnOpen(true);
		int result = dialog.open();

		if (result != Window.OK) return;

		Pattern ticketPattern = Pattern.compile("^#([0-9]+)$");
		String searchText = dialog.getSearchText();
		Matcher ticketMatcher = ticketPattern.matcher(searchText);
		if (!ticketMatcher.find()) return;
		int ticket = Integer.parseInt(ticketMatcher.group(1));
		
		MPartStack partStack = (MPartStack) modelService.find("aero.minova.crm.partstack.crm", window);
		MPart ticketPart = (MPart) modelService.find("aero.minova.crm.rcp.part.ticket#" + ticket, partStack);

		if (ticketPart != null) {
			boolean b = ticketPart.isVisible();
			b = ticketPart.isToBeRendered();
			ticketPart.setVisible(true);
			ticketPart.setToBeRendered(true);
			partStack.setSelectedElement(ticketPart);
			return;
		}

		ticketPart = (MPart) modelService.cloneSnippet(application, "aero.minova.crm.rcp.part.ticket", null);
		if (ticketPart != null) {
			ticketPart.setElementId("aero.minova.crm.rcp.part.ticket#" + ticket);
			ticketPart.setLabel("#" + ticket);
// TODO			((TicketPart) ticketPart).setTicketId(ticket);
		}

		if (partStack != null && ticketPart != null) {
			partStack.getChildren().add(ticketPart);
			partStack.setSelectedElement(ticketPart);
		}
	}

}
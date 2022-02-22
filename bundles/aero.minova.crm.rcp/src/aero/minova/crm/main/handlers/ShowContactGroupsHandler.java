package aero.minova.crm.main.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;
import org.eclipse.swt.widgets.ToolItem;

import aero.minova.crm.main.parts.ContactPart;

public class ShowContactGroupsHandler {

	@Execute
	public void execute(MPart part) {
		boolean selection = false;

		for (MToolBarElement item : part.getToolbar().getChildren()) {
			if ("aero.minova.crm.rcp.handledtoolitem.gruppenanzeigen".equals(item.getElementId()) //
					&& item.getWidget() instanceof ToolItem toolItem) {
				selection = toolItem.getSelection();
				break;
			}
		}

		if (part.getObject() instanceof ContactPart contactPart) {
			contactPart.setGroupListVisible(selection);
		}
	}

	@CanExecute
	public boolean canExecute() {
		return true;
	}
}
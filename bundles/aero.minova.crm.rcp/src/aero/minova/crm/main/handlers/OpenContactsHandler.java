package aero.minova.crm.main.handlers;

import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.widgets.Shell;

public class OpenContactsHandler {

	@Inject
	EModelService modelService;

	@Inject
	MApplication application;

	@Execute
	public void execute(ParameterizedCommand command, EPartService partService, Shell shell, MWindow window) {
		MPartStack partStack = (MPartStack) modelService.find("aero.minova.crm.partstack.crm", window);
		MPart contactPart = (MPart) modelService.find("aero.minova.crm.rcp.part.contacts", partStack);

		if (contactPart != null) {
			contactPart.setVisible(true);
			contactPart.setToBeRendered(true);
			partStack.setSelectedElement(contactPart);
			return;
		}

		contactPart = (MPart) modelService.cloneSnippet(application, "aero.minova.crm.rcp.part.contacts", null);
		if (contactPart != null) {
			contactPart.setElementId("aero.minova.crm.rcp.part.contacts");
		}

		if (partStack != null && contactPart != null) {
			partStack.getChildren().add(contactPart);
			partStack.setSelectedElement(contactPart);
		}
	}
}


package aero.minova.crm.main.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

import aero.minova.crm.model.Constants;

public class EditContactHandler {

	@Inject
	IEventBroker broker;
	boolean currentlyEditable = false;

	@Execute
	public void execute(MApplication application, EModelService service) {
		currentlyEditable = !currentlyEditable;
		broker.send(Constants.TOPIC_EDIT, currentlyEditable);
	}

	@CanExecute
	public boolean canExecute() {
		return true;
	}

}
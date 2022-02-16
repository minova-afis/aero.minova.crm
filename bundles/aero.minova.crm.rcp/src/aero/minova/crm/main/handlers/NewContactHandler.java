package aero.minova.crm.main.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

import aero.minova.crm.model.Constants;
import aero.minova.crm.model.jpa.Contact;
import aero.minova.crm.model.jpa.Database;

public class NewContactHandler {

	@Inject
	IEventBroker broker;

	@Execute
	public void execute(MApplication application, EModelService service) {
		Contact c = Database.getInstance().addContact();
		broker.send(Constants.NEW_CONTACT, c);
	}

	@CanExecute
	public boolean canExecute() {
		return true;
	}

}

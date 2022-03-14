package aero.minova.crm.main.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import aero.minova.crm.model.Constants;

public class ExportGroupHandler {

	@Inject
	IEventBroker broker;

	@Execute
	public void execute(MPart part) {
		broker.send(Constants.EXPORT_GROUP, "");
	}

	@CanExecute
	public boolean canExecute() {
		return true;
	}
}

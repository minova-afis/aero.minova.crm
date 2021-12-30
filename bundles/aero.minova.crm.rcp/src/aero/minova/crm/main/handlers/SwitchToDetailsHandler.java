 
package aero.minova.crm.main.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import aero.minova.crm.main.parts.TicketPart;

public class SwitchToDetailsHandler {
	@Execute
	public void execute(MPart part) {
		TicketPart ticketPart = (TicketPart) part.getObject();
		ticketPart.focusDetails();
	}

	@CanExecute
	public boolean canExecute(MPart part) {
		return (part.getObject() instanceof TicketPart);
	}
		
}
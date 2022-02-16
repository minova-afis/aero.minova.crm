package aero.minova.crm.main.handlers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import aero.minova.crm.model.Constants;
import aero.minova.crm.model.jpa.Contact;
import aero.minova.crm.model.jpa.Group;
import aero.minova.crm.model.vCard.VCardOptions;

public class SendMailHandler {

	@Inject
	IEventBroker broker;

	@Execute
	public void execute(MPart part) {
		broker.send(Constants.SEND_MAIL, "");
	}

	@CanExecute
	public boolean canExecute() {
		return true;
	}

	public static void sendMail(Group g) {
		Desktop desktop;
		if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {

			String recipients = ",";
			for (Contact c : g.getMembers()) {
				if (!c.getValueString(VCardOptions.EMAIL).equals("")) {
					recipients += c.getValueString(VCardOptions.EMAIL) + ",";
				}
			}

			URI mailto;
			try {
				mailto = new URI("mailto:" + recipients);
				desktop.mail(mailto);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("desktop doesn't support mailto");
		}
	}

}

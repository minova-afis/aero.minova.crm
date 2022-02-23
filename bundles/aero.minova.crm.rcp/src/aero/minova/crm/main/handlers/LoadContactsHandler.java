package aero.minova.crm.main.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import aero.minova.cloud.crm.frontend.adapter.in.web.ApiClient;
import aero.minova.cloud.crm.frontend.adapter.in.web.ApiException;
import aero.minova.cloud.crm.frontend.adapter.in.web.api.ContactsPersonsApi;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsAllPersonsResponse;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsPersonEntry;
import aero.minova.crm.model.Constants;
import aero.minova.crm.model.jpa.Contact;
import aero.minova.crm.model.jpa.ContactConverter;
import aero.minova.crm.model.vCard.VCardOptions;

public class LoadContactsHandler {

	@Inject
	IEventBroker broker;

	@Execute
	public void execute(MPart part) {
		ApiClient client = new ApiClient();
		client.updateBaseUri("http://localhost:8080/crm/api/v1");

		ContactsPersonsApi api = new ContactsPersonsApi(client);
		try {
			ContactsAllPersonsResponse allPersons = api.getAllPersons("minova");
			System.out.println(allPersons);

			ContactsPersonEntry personById = api.getPersonById("minova", allPersons.getPersons().get(0).getId());
			System.out.println(personById);

			Contact c = ContactConverter.contactsPersonEntryToContact(personById);
			if (c.getValue(VCardOptions.N) != null && !c.getValueString(VCardOptions.N).isBlank()) {
				broker.send(Constants.NEW_CONTACT, c);
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

}

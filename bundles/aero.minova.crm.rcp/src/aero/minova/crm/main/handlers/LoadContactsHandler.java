package aero.minova.crm.main.handlers;

import java.util.UUID;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import aero.minova.cloud.crm.frontend.adapter.in.web.ApiClient;
import aero.minova.cloud.crm.frontend.adapter.in.web.ApiException;
import aero.minova.cloud.crm.frontend.adapter.in.web.api.ContactsPersonsApi;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsAllPersonsResponse;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsPersonAllPhoneNumbersResponse;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsPersonEntry;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsPersonShortEntry;
import aero.minova.crm.main.parts.ContactPart;
import aero.minova.crm.model.jpa.Contact;
import aero.minova.crm.model.jpa.ContactConverter;

public class LoadContactsHandler {

	@Inject
	IEventBroker broker;

	@Execute
	public void execute(MPart part) {
		ApiClient client = new ApiClient();
		client.updateBaseUri("http://localhost:8080/crm/api/v1");
		ContactsPersonsApi api = new ContactsPersonsApi(client);

		String tenantID = "minova";

		try {
			ContactsAllPersonsResponse allPersons = api.listAllPersons(tenantID);
			for (ContactsPersonShortEntry person : allPersons.getPersons()) {
				UUID personID = person.getId();

				ContactsPersonEntry personById = api.retrievePersonById(tenantID, personID);

				ContactsPersonAllPhoneNumbersResponse listAllPhoneNumbersFromPerson = api.listAllPhoneNumbersFromPerson(tenantID, personID);

//				ContactsPersonAllAddressesResponse listAllAddressesFromPerson = api.listAllAddressesFromPerson(tenantID, personID);
//				ContactsPersonAllEmailAddressesResponse listAllEmailAddressesFromPerson = api.listAllEmailAddressesFromPerson(tenantID, personID);
//				ContactsPersonAllPropertiesResponse listAllPropertiesFromPerson = api.listAllPropertiesFromPerson(tenantID, personID);

				Contact c = ContactConverter.contactsPersonEntryToContact(personById);
				ContactConverter.addPhoneNumberToContact(c, listAllPhoneNumbersFromPerson);
			}

			ContactPart contactPart = (ContactPart) part.getObject();
			contactPart.refreshTables();

		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

}

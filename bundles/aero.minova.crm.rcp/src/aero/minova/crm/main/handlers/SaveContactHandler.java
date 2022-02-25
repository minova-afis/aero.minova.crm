package aero.minova.crm.main.handlers;

import java.util.Map.Entry;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import aero.minova.cloud.crm.frontend.adapter.in.web.ApiClient;
import aero.minova.cloud.crm.frontend.adapter.in.web.ApiException;
import aero.minova.cloud.crm.frontend.adapter.in.web.api.ContactsPersonsApi;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsPersonEntry;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsPersonEntryWithoutId;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.PhoneNumberEntry;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.PhoneNumberEntryWithoutId;
import aero.minova.crm.main.parts.ContactPart;
import aero.minova.crm.model.jpa.Contact;
import aero.minova.crm.model.jpa.ContactConverter;
import aero.minova.crm.model.vCard.VCardOptions;
import aero.minova.crm.model.values.Value;

public class SaveContactHandler {

	@Inject
	IEventBroker broker;

	@Execute
	public void execute(MPart part) {
		ApiClient client = new ApiClient();
		client.updateBaseUri("http://localhost:8080/crm/api/v1");
		ContactsPersonsApi api = new ContactsPersonsApi(client);

		String tenantID = "minova";

		try {

			ContactPart contactsPart = (ContactPart) part.getObject();

			for (Contact c : contactsPart.getSelectedContacts()) {

				if (c.getUuid() != null) {
					ContactsPersonEntry cpe = ContactConverter.contactToContactsPersonEntry(c);
					api.modifyPersonById(tenantID, c.getUuid(), cpe);
				} else {
					ContactsPersonEntryWithoutId cpe = ContactConverter.contactToContactsPersonEntryWithoutID(c);
					ContactsPersonEntry createPerson = api.createPerson(tenantID, cpe);
					c.setUuid(createPerson.getId());
				}

				if (c.getTypesAndValues(VCardOptions.TEL) == null) {
					continue;
				}
				for (Entry<String, Value> e : c.getTypesAndValues(VCardOptions.TEL).entrySet()) {
					if (e.getValue().getUuid() != null) {
						PhoneNumberEntry pne = ContactConverter.createPhoneNumberEntry(e.getValue(), e.getKey());
						api.modifyPhoneNumberOfPersonById(tenantID, c.getUuid(), e.getValue().getUuid(), pne);
					} else {
						PhoneNumberEntryWithoutId pne = ContactConverter.createPhoneNumberEntryWithoutID(e.getValue(), e.getKey());
						api.createPhoneNumberForPerson(tenantID, c.getUuid(), pne);
					}
				}

			}

		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

}

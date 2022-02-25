package aero.minova.crm.model.jpa;

import aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsGroupEntryContacts.TypeEnum;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsPersonAllAddressesResponse;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsPersonAllPhoneNumbersResponse;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsPersonEntry;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsPersonEntryWithoutId;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsPersonEntryWithoutId.GenderEnum;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsPersonPhoneNumberShortEntry;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.PhoneNumberEntry;
import aero.minova.cloud.crm.frontend.adapter.in.web.model.PhoneNumberEntryWithoutId;
import aero.minova.crm.model.vCard.VCardMapping;
import aero.minova.crm.model.vCard.VCardOptions;
import aero.minova.crm.model.values.DateValue;
import aero.minova.crm.model.values.NameValue;
import aero.minova.crm.model.values.TextValue;
import aero.minova.crm.model.values.Value;

public class ContactConverter {

	private ContactConverter() {}

	public static ContactsPersonEntryWithoutId contactToContactsPersonEntryWithoutID(Contact c) {

		ContactsPersonEntryWithoutId cpe = new ContactsPersonEntryWithoutId();

		NameValue name = (NameValue) c.getValue(VCardOptions.N);
		cpe.setFirstNames(name.getFirstName());
		cpe.setLastNames(name.getLastName());
		cpe.setFormattedName(name.getStringRepresentation());

		cpe.setText(c.getValueString(VCardOptions.NOTE));

		DateValue date = (DateValue) c.getValue(VCardOptions.BDAY);
		if (date != null) {
			cpe.setBirthday(date.getLocalDate());
		}

		cpe.setGender(GenderEnum.UNKNOWN);

		return cpe;
	}

	public static ContactsPersonEntry contactToContactsPersonEntry(Contact c) {

		ContactsPersonEntry cpe = new ContactsPersonEntry();

		NameValue name = (NameValue) c.getValue(VCardOptions.N);
		cpe.setFirstNames(name.getFirstName());
		cpe.setLastNames(name.getLastName());
		cpe.setFormattedName(name.getStringRepresentation());

		cpe.setText(c.getValueString(VCardOptions.NOTE));

		DateValue date = (DateValue) c.getValue(VCardOptions.BDAY);
		if (date != null) {
			cpe.setBirthday(date.getLocalDate());
		}

		cpe.setGender(aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsPersonEntry.GenderEnum.UNKNOWN);

		return cpe;
	}

	public static Contact contactsPersonEntryToContact(ContactsPersonEntry cpe) {

		Contact c = Database.getInstance().getContactByUUID(cpe.getId());
		if (c == null) {

			c = Database.getInstance().getContactByName(new NameValue(cpe.getLastNames() + ";" + cpe.getFirstNames() + ";;;"));

			if (c == null) {
				c = Database.getInstance().addContact();
			}

		}
		c.setUuid(cpe.getId());

		c.setProperty(VCardOptions.N, new NameValue(cpe.getLastNames() + ";" + cpe.getFirstNames() + ";;;"));
		c.setProperty(VCardOptions.BDAY, new DateValue(cpe.getBirthday()));
		c.setProperty(VCardOptions.NOTE, new TextValue(cpe.getText()));

		return c;
	}

	public static Contact addAddressToContact(Contact c, ContactsPersonAllAddressesResponse addresses) {

		return c;
	}

	public static Contact addPhoneNumberToContact(Contact c, ContactsPersonAllPhoneNumbersResponse listAllPhoneNumbersFromPerson) {
		for (int i = 0; i < listAllPhoneNumbersFromPerson.getPhoneNumbers().size(); i++) {
			ContactsPersonPhoneNumberShortEntry entry = listAllPhoneNumbersFromPerson.getPhoneNumbers().get(i);
			String type = VCardMapping.purposeToProp.get(entry.getPurpose());

			Value phone = new TextValue(entry.getNumber());
			phone.setUuid(entry.getId());
			c.setProperty(VCardOptions.TEL, type, phone);
		}
		return c;
	}

	public static PhoneNumberEntry createPhoneNumberEntry(Value v, String property) {
		PhoneNumberEntry pne = new PhoneNumberEntry();

		pne.setId(v.getUuid());
		pne.setNumber(v.getStringRepresentation());
		pne.setPurpose(VCardMapping.propToPurpose.get(property));

		pne.setType(aero.minova.cloud.crm.frontend.adapter.in.web.model.PhoneNumberEntry.TypeEnum.UNKNOWN);
		pne.setPreferred(false);
		
		return pne;
	}

	public static PhoneNumberEntryWithoutId createPhoneNumberEntryWithoutID(Value v, String property) {
		PhoneNumberEntryWithoutId pne = new PhoneNumberEntryWithoutId();

		pne.setNumber(v.getStringRepresentation());
		pne.setPurpose(VCardMapping.propToPurpose.get(property));

		pne.setType(aero.minova.cloud.crm.frontend.adapter.in.web.model.PhoneNumberEntryWithoutId.TypeEnum.UNKNOWN);
		pne.setPreferred(false);
		
		return pne;
	}

}

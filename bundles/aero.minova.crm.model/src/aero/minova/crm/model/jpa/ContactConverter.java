package aero.minova.crm.model.jpa;

import aero.minova.cloud.crm.frontend.adapter.in.web.model.ContactsPersonEntry;
import aero.minova.crm.model.vCard.VCardOptions;
import aero.minova.crm.model.values.DateValue;
import aero.minova.crm.model.values.NameValue;

public class ContactConverter {

	private ContactConverter() {}

	public static ContactsPersonEntry contactToContactsPersonEntry(Contact c) {

		ContactsPersonEntry cpe = new ContactsPersonEntry();
		cpe.setFirstNames(c.getValueString(VCardOptions.N));

		return cpe;
	}

	public static Contact contactsPersonEntryToContact(ContactsPersonEntry cpe) {

		Contact c = Database.getInstance().addContact();

		c.setProperty(VCardOptions.N, new NameValue(cpe.getLastNames() + ";" + cpe.getFirstNames() + ";;;"));
		c.setProperty(VCardOptions.BDAY, new DateValue(cpe.getBirthday()));

		return c;
	}

}

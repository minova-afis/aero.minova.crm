package aero.minova.crm.main.entries;

import aero.minova.crm.model.jpa.Contact;

public abstract class PropertyEntry {

	public abstract void setInput(Contact c);

	public abstract void setEditable(boolean editable);

	public abstract TypeEntry getTypeEntryByType(String string);

	public abstract void updateContact();

}

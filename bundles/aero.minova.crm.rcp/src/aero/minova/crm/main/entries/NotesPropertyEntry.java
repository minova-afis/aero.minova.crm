package aero.minova.crm.main.entries;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import aero.minova.crm.model.jpa.Contact;
import aero.minova.crm.model.vCard.VCardMapping;
import aero.minova.crm.model.vCard.VCardOptions;
import aero.minova.crm.model.values.TextValue;

public class NotesPropertyEntry extends PropertyEntry {

	private Contact currentContact;

	private Label label;
	private Text input;

	public NotesPropertyEntry(Composite body) {
		new Label(body, SWT.NONE); // Leeres Label um Platz zu lassen
		label = new Label(body, SWT.RIGHT | SWT.TOP);
		GridData gd = new GridData(SWT.RIGHT, SWT.TOP, true, false);
		label.setLayoutData(gd);
		label.setText(VCardMapping.getLabel(VCardOptions.NOTE));

		input = new Text(body, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		input.setLayoutData(new GridData(GridData.FILL_BOTH));

		input.addModifyListener(e -> updateContact());
	}

	@Override
	public void setInput(Contact c) {
		currentContact = c;
		if (c.getValue(VCardOptions.NOTE) != null && c.getValue(VCardOptions.NOTE).getStringRepresentation() != null) {
			input.setText(c.getValue(VCardOptions.NOTE).getStringRepresentation());
		} else {
			input.setText("");
		}
	}

	@Override
	public void setEditable(boolean editable) {}

	@Override
	public TypeEntry getTypeEntryByType(String string) {
		return null;
	}

	@Override
	public void updateContact() {
		currentContact.setProperty(VCardOptions.NOTE, new TextValue(input.getText()));
	}

}

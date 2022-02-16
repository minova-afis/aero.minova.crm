package aero.minova.crm.model.vCard;

import java.util.List;

import aero.minova.crm.model.values.AddressValue;
import aero.minova.crm.model.values.DateValue;
import aero.minova.crm.model.values.NameValue;
import aero.minova.crm.model.values.PhotoValue;
import aero.minova.crm.model.values.TextValue;
import aero.minova.crm.model.values.Value;
import ezvcard.property.Address;
import ezvcard.property.DateOrTimeProperty;
import ezvcard.property.ImageProperty;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;
import ezvcard.property.TextListProperty;
import ezvcard.property.TextProperty;
import ezvcard.property.VCardProperty;

public class VCardMapping {

	private VCardMapping() {}

	public static String getPropertyString(VCardProperty vCardProp) {
		switch (vCardProp.getClass().getName()) {
		case ("ezvcard.property.Photo"):
			return VCardOptions.PHOTO;
		case ("ezvcard.property.StructuredName"):
			return VCardOptions.N;
		case ("ezvcard.property.FormattedName"):
			return VCardOptions.FN;
		case ("ezvcard.property.Organization"):
			return VCardOptions.ORG;
		case ("ezvcard.property.Telephone"):
			return VCardOptions.TEL;
		case ("ezvcard.property.Email"):
			return VCardOptions.EMAIL;
		case ("ezvcard.property.Address"):
			return VCardOptions.ADR;
		case ("ezvcard.property.Note"):
			return VCardOptions.NOTE;
		case ("ezvcard.property.Birthday"):
			return VCardOptions.BDAY;
		case ("ezvcard.property.ProductId"):
			return null;
		default:
			System.err.println("Unbekannte Property: " + vCardProp.getClass().getName());
			return null;
		}
	}

	public static Value getValue(VCardProperty prop) {
		if (prop instanceof TextListProperty textList) { // Categories, Nickname, Organization
			return new TextValue(textList.getValues().get(0));
		}
		if (prop instanceof TextProperty text) { // Classification, Email, Expertise, FormattedName, Hobby, Interest, Kind, Label, Language, Mailer, Note,
			// ProductId, Profile, RawPropertie, Role, SortString, SourceDisplayText, Title, UriProperty
			return new TextValue(text.getValue());

		}
		if (prop instanceof ImageProperty img) { // Photo, Logo
			String filetype = VCardOptions.PHOTOTYPES[0];
			if (img.getType() != null) {
				filetype = img.getType();
			}
			return new PhotoValue(img.getData(), filetype);

		}
		if (prop instanceof DateOrTimeProperty dateTime) { // Birthday, Deathday, Anniversary
			return new DateValue(dateTime.getDate());

		}
		if (prop instanceof StructuredName sName) {
			String val = "";
			val += ((sName.getFamily() == null) ? "" : sName.getFamily()) + ";";
			val += ((sName.getGiven() == null) ? "" : sName.getGiven()) + ";";
			val += getListAsString(sName.getAdditionalNames()) + ";";
			val += getListAsString(sName.getPrefixes()) + ";";
			val += getListAsString(sName.getSuffixes());
			return new NameValue(val);

		}
		if (prop instanceof Telephone tel) {
			return new TextValue(tel.getText());

		}
		if (prop instanceof Address addr) {
			String val = "";
			val += ((addr.getPoBox() == null) ? "" : addr.getPoBox()) + ";";
			val += ((addr.getExtendedAddress() == null) ? "" : addr.getExtendedAddress()) + ";";
			val += ((addr.getStreetAddress() == null) ? "" : addr.getStreetAddress()) + ";";
			val += ((addr.getLocality() == null) ? "" : addr.getLocality()) + ";";
			val += ((addr.getRegion() == null) ? "" : addr.getRegion()) + ";";
			val += ((addr.getPostalCode() == null) ? "" : addr.getPostalCode()) + ";";
			val += ((addr.getCountry() == null) ? "" : addr.getCountry());
			return new AddressValue(val);
		}

		return null;
	}

	public static String getLabel(String prop) {
		switch (prop) {
		case (VCardOptions.PHOTO):
			return "Bild";
		case (VCardOptions.N):
			return "Name";
		case (VCardOptions.FN):
			return "Strukturierter Name";
		case (VCardOptions.ORG):
			return "Firma";
		case (VCardOptions.TEL):
			return "Telefonnummer";
		case (VCardOptions.EMAIL):
			return "E-Mail";
		case (VCardOptions.ADR):
			return "Addresse";
		case (VCardOptions.NOTE):
			return "Notiz";
		case (VCardOptions.BDAY):
			return "Geburtstag";
		case (VCardOptions.HOME):
			return "Privat";
		case (VCardOptions.WORK):
			return "Arbeit";
		case (VCardOptions.CELL):
			return "Mobil";
		case (VCardOptions.INTERNET):
			return "Internet";
		default:
			return prop;
		}
	}

	public static String getListAsString(List<String> list) {
		String res = "";
		for (String s : list) {
			res += s + " ";
		}
		return res.trim();
	}
}

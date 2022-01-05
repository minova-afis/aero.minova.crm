package aero.minova.crm.model.jpa;

import java.util.UUID;

public class Contact {

	UUID uuid;

	public Contact() {
		uuid = UUID.nameUUIDFromBytes("Wilfried Saak|saak@minova.com".getBytes());


	}
}

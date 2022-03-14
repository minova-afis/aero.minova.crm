package aero.minova.crm.model.values;

import java.util.UUID;

public abstract class Value {

	private UUID uuid;

	public abstract String getStringRepresentation();

	public abstract String getVCardString();

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}

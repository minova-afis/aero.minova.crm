package aero.minova.crm.service.trac;

import jakarta.persistence.EntityManager;

public interface DatabaseService {
	public EntityManager getEntityManager();
}

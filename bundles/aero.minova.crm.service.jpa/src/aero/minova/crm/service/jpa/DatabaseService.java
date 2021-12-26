package aero.minova.crm.service.jpa;

import jakarta.persistence.EntityManager;

public interface DatabaseService {
	public EntityManager getEntityManager();
}

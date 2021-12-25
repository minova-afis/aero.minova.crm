package aero.minova.jpa.service;

import jakarta.persistence.EntityManager;

public interface DatabaseService {
	public EntityManager getEntityManager();
}

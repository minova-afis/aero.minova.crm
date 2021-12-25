package aero.minova.jpa.service;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@Component(service = DatabaseService.class)
public class DatabaseServiceImpl implements DatabaseService {

	private EntityManagerFactory entityManagerFactory = null;

	@Activate
	protected void activateComponent() {
	}

	@Override
	public EntityManager getEntityManager() {
		if (entityManagerFactory != null) return entityManagerFactory.createEntityManager();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(PersistenceUnitProperties.CLASSLOADER, getClass().getClassLoader());
//		map.put(PersistenceUnitProperties.WEAVING, "true");
		map.put(PersistenceUnitProperties.WEAVING_LAZY, "true");

		PersistenceProvider persistenceProvider = new PersistenceProvider();
		entityManagerFactory = persistenceProvider.createEntityManagerFactory("h2-eclipselink", map);

		return entityManagerFactory.createEntityManager();
	}

	@Deactivate
	protected void deactivateComponent() {
		if (entityManagerFactory == null) return;
		entityManagerFactory.close();
		entityManagerFactory = null;
	}

}

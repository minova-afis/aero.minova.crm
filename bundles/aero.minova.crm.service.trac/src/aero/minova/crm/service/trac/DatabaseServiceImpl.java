package aero.minova.crm.service.trac;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
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

		/*
		 * Datenbank
		 */
		map.put("jakarta.persistence.jdbc.driver", "org.h2.Driver");
		map.put("jakarta.persistence.jdbc.user", "sa");
		map.put("jakarta.persistence.jdbc.password", "Hallo0192*+");
		map.put("jakarta.persistence.jdbc.url", "jdbc:h2:" + Platform.getInstanceLocation().getURL().getPath() + "crm");
		map.put("eclipselink.ddl-generation", "create-or-extend-tables");
		map.put("eclipselink.ddl-generation.output-mode", "database");
		map.put("eclipselink.logging.level", "FINE");

		PersistenceProvider persistenceProvider = new PersistenceProvider();
		entityManagerFactory = persistenceProvider.createEntityManagerFactory("crm", map);

		return entityManagerFactory.createEntityManager();
	}

	@Deactivate
	protected void deactivateComponent() {
		if (entityManagerFactory == null) return;
		entityManagerFactory.close();
		entityManagerFactory = null;
	}

}

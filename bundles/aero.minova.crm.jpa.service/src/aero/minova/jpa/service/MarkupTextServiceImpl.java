package aero.minova.jpa.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import aero.minova.crm.model.jpa.MarkupText;
import aero.minova.crm.model.jpa.service.MarkupTextService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@Component(service = MarkupTextService.class)
public class MarkupTextServiceImpl implements MarkupTextService {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	@Activate
	@SuppressWarnings("unchecked")
	protected void activateComponent() {
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put(PersistenceUnitProperties.CLASSLOADER, getClass().getClassLoader());

		PersistenceProvider persistenceProvider = new PersistenceProvider();
		entityManagerFactory = persistenceProvider.createEntityManagerFactory("h2-eclipselink", map);
		entityManager = entityManagerFactory.createEntityManager();
	}

	@Deactivate
	protected void deactivateComponent() {
		entityManager.close();
		entityManagerFactory.close();
		entityManager = null;
		entityManagerFactory = null;
	}

	public MarkupTextServiceImpl() {}

	// create or update an existing instance of Todo
	@Override
	public synchronized boolean saveMarkupText(MarkupText newMarkupText) {
		// hold the Optional object as reference to determine, if the Todo is
		// newly created or not
		Optional<MarkupText> markupTextOptional = getMarkupText(newMarkupText.getId());

		// get the actual todo or create a new one
		MarkupText markupText = markupTextOptional.orElse(new MarkupText());
		markupText.setMarkup(newMarkupText.getMarkup());
		markupText.setHtml(newMarkupText.getHtml());

		// send out events
		if (markupTextOptional.isPresent()) {
			entityManager.getTransaction().begin();
			entityManager.merge(markupText);
			entityManager.getTransaction().commit();
		} else {
			entityManager.getTransaction().begin();
			entityManager.persist(markupText);
			entityManager.getTransaction().commit();
		}
		return true;
	}

	@Override
	public Optional<MarkupText> getMarkupText(int id) {
		entityManager.getTransaction().begin();
		MarkupText find = entityManager.find(MarkupText.class, id);
		entityManager.getTransaction().commit();

		return Optional.ofNullable(find);
	}
}

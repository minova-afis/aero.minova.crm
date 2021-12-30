package aero.minova.crm.service.jpa;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import aero.minova.crm.model.jpa.MarkupText;
import aero.minova.crm.model.service.jpa.MarkupTextService;
import jakarta.persistence.EntityManager;

@Component(service = MarkupTextService.class)
public class MarkupTextServiceImpl implements MarkupTextService {
	private EntityManager entityManager;

	@Reference
	private DatabaseService databaseService;

	@Deactivate
	protected void deactivateComponent() {
		if (entityManager == null) return;
		entityManager.close();
		entityManager = null;
	}

	private void checkEntityManager() {
		if (entityManager != null) return;
		entityManager = databaseService.getEntityManager();
	}

	// create or update an existing instance of Todo
	@Override
	public synchronized boolean saveMarkupText(MarkupText markupText) {
		checkEntityManager();
		// hold the Optional object as reference to determine, if the Todo is
		// newly created or not
		Optional<MarkupText> markupTextOptional = getMarkupText(markupText.getId());

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
		checkEntityManager();
		entityManager.getTransaction().begin();
		MarkupText find = entityManager.find(MarkupText.class, id);
		entityManager.getTransaction().commit();

		return Optional.ofNullable(find);
	}
}

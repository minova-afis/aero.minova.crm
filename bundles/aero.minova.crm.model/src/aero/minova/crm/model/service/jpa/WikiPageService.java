package aero.minova.crm.model.service.jpa;

import java.util.Optional;

import aero.minova.crm.model.jpa.WikiPage;

public interface WikiPageService {

	boolean saveWikiPage(WikiPage newWikiPage);

	Optional<WikiPage> getWikiPage(int id);

	Optional<WikiPage> getWikiPage(String path);
}
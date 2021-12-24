package aero.minova.crm.model.jpa.service;

import java.util.Optional;

import aero.minova.crm.model.jpa.WikiPage;

public interface WikiService {

	boolean saveWikiPage(WikiPage newWikiPage);

	Optional<WikiPage> getWikiPage(int id);

	Optional<WikiPage> getWikiPage(String path);
}
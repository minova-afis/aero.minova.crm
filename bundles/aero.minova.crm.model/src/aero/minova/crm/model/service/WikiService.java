package aero.minova.crm.model.service;

import java.util.Optional;

import aero.minova.crm.model.jpa.Wiki;

public interface WikiService {

	boolean saveWikiPage(Wiki newWikiPage);

	Optional<Wiki> getWikiPage(int id);

	Optional<Wiki> getWikiPage(String path);
}
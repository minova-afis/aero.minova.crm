package aero.minova.crm.model.service;

import java.util.Optional;

import aero.minova.crm.model.jpa.Wiki;

public interface WikiService {

	boolean saveWiki(Wiki newWiki);

	Optional<Wiki> getWiki(int id);

	Optional<Wiki> getWiki(String path);
}
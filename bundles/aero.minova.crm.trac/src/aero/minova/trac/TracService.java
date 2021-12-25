package aero.minova.trac;

import java.util.Hashtable;
import java.util.List;

import aero.minova.trac.domain.Ticket;
import aero.minova.trac.domain.Wiki;

public interface TracService {

	public String getPage(String pagename);

	public String getPageHTML(String pagename);

	public Hashtable<String, ?> getPageInfo(String pagename);

	public Ticket getTicket(int id);

	public Wiki getWiki(String wikiAddress);

	public List<String> listWikiPages();

	/**
	 * Liest den Inhalt der angegebenen Wiki-Seite
	 * 
	 * @param wikiAddress
	 *            die (interne) Adresse der Wiki-Seite, z.B. "Module/ch.minova.sap.sales"
	 * @return {@link Wiki}
	 */
	public String wikiToHtml(String wikiText);
}

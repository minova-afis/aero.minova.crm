package aero.minova.trac;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import aero.minova.trac.domain.TracMilestone;
import aero.minova.trac.domain.TracTicket;
import aero.minova.trac.domain.TracWikiPage;

public interface TracService {

	public TracMilestone getMilestone(String milestoneName);

	public String getPage(String pagename);

	public String getPageHTML(String pagename);

	public Hashtable<String, ?> getPageInfo(String pagename);

	public TracTicket getTicket(int id);

	public TracWikiPage getWiki(String wikiAddress);

	public List<String> listWikiPages();

	public void updateTicketSummary(TracTicket newTicket, String comment);

	/**
	 * Liest den Inhalt der angegebenen Wiki-Seite
	 * 
	 * @param wikiAddress die (interne) Adresse der Wiki-Seite, z.B.
	 *                    "Module/ch.minova.sap.sales"
	 * @return {@link TracWikiPage}
	 */
	public String wikiToHtml(String wikiText);

	public Vector<String> getTicketComponents();

	public Vector<String> getTicketPriorities();

//	public Vector<String> getTicketResolutions(); // Diese Methode gibt es auf unserem TracServer nicht

	public Vector<String> getTicketStates();

	public Vector<String> getTicketTypes();

	public Vector<String> getMilestones();
	
	public Vector<?> getTicketAttachments(int id);

	public byte[] getTicketAttachment(int id, String name);

	public Vector<?> getWikiAttachments(String path);
	
	public byte[] getWikiAttachment(String path, String name);
}

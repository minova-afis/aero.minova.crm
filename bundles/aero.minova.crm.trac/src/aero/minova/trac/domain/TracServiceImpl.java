package aero.minova.trac.domain;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.osgi.service.component.annotations.Component;

import aero.minova.trac.TracService;
import aero.minova.trac.xmlprc.TrackerDynamicProxy;

/**
 * @author saak
 * @since 12.0.0
 */
@Component(service = TracService.class)
public class TracServiceImpl implements TracService {
	private TrackerDynamicProxy trackerDynamicProxy = null;

	public TracServiceImpl() {
		XmlRpcClientConfigImpl conf = new XmlRpcClientConfigImpl();
		conf.setBasicUserName("bugzilla");
		conf.setBasicPassword("123wilfried");
		try {
			conf.setServerURL(new URL("https://trac.minova.com/trac/minova/login/xmlrpc"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(conf);

		trackerDynamicProxy = new TrackerDynamicProxy(client);
	}

	/**
	 * Diese Methode holt ein Ticket von Trac Server
	 * 
	 * @param id
	 *            Ticketnummer
	 * @return null, wenn das Ticket nicht geladen werden konnte; sonst das gefudene Ticket
	 */
	public Ticket getTicket(int id) {
		return getTicket(id, false);
	}

	public Ticket getTicket(int id, boolean debug) {
		aero.minova.trac.xmlprc.Ticket ticket = (aero.minova.trac.xmlprc.Ticket) trackerDynamicProxy.newInstance(aero.minova.trac.xmlprc.Ticket.class);

		Ticket newTicket = new Ticket();
		try {
			Vector<?> details = ticket.get(id);
			// @SuppressWarnings("unused")
			// Object x = ticket.query("max=100,modified=2013-02-22..2013-02-23,status=closed");
			for (Iterator<?> i = details.iterator(); i.hasNext();) {
				Object value = i.next();
				if (value instanceof HashMap) {
					@SuppressWarnings("unchecked")
					HashMap<String, ?> attributes = (HashMap<String, ?>) value;
					newTicket.setId(id);
					newTicket.setSummary((String) attributes.get("summary"));
					newTicket.setDescription((String) attributes.get("description"));
					newTicket.setKeywords((String) attributes.get("keywords"));
					newTicket.setMilestoneName((String) attributes.get("milestone"));

					if (debug) {
						for (Iterator<String> attributeName = attributes.keySet().iterator(); attributeName.hasNext();) {
							Object object = attributeName.next();
							System.out.println(object + ": " + attributes.get(object));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			newTicket = null;
		}
		return newTicket;
	}

	public Milestone getMilestone(String milestoneName) {
		aero.minova.trac.xmlprc.Ticket.Milestone milestone = (aero.minova.trac.xmlprc.Ticket.Milestone) trackerDynamicProxy
				.newInstance(aero.minova.trac.xmlprc.Ticket.Milestone.class);

		Milestone newMilestone = new Milestone();
		try {
			Hashtable<String, ?> attributes = milestone.get(milestoneName);
			newMilestone.setName(milestoneName);
			newMilestone.setDescription((String) attributes.get("description"));
			if (attributes.get("due") instanceof Date) {
				newMilestone.setDue((Date) attributes.get("due"));
			}
			newMilestone.setCompleted((Integer) attributes.get("completed"));
		} catch (Exception e) {
			newMilestone = null;
		}
		return newMilestone;
	}

	/**
	 * Liest den Inhalt der angegebenen Wiki-Seite
	 * 
	 * @param wikiAddress
	 *            die (interne) Adresse der Wiki-Seite, z.B. "Module/ch.minova.sap.sales"
	 * @return {@link Wiki}
	 */
	public Wiki getWiki(String wikiAddress) {
		aero.minova.trac.xmlprc.Wiki wiki = (aero.minova.trac.xmlprc.Wiki) trackerDynamicProxy.newInstance(aero.minova.trac.xmlprc.Wiki.class);

		Wiki newWiki = new Wiki(wikiAddress);
		try {
			String content = wiki.getPage(wikiAddress);
			if (content == null) {
				newWiki = null;
			} else {
				newWiki.setContent(content);
			}
		} catch (Exception e) {
			newWiki = null;
		}
		return newWiki;
	}

	public String wikiToHtml(String wikiText) {
		aero.minova.trac.xmlprc.Wiki wiki = (aero.minova.trac.xmlprc.Wiki) trackerDynamicProxy.newInstance(aero.minova.trac.xmlprc.Wiki.class);
		String html = wiki.wikiToHtml(wikiText);
		return html;
	}

	/**
	 * Schreibt den Inhalt auf die angegebene Wiki-Seite.
	 * 
	 * @param internalWiki
	 *            Objekt der internen {@link Wiki}-Klasse
	 * @author wild
	 * @since 11.0.0
	 */
	public void setWiki(Wiki internalWiki) {
		aero.minova.trac.xmlprc.Wiki wiki = (aero.minova.trac.xmlprc.Wiki) trackerDynamicProxy.newInstance(aero.minova.trac.xmlprc.Wiki.class);

		// null ist nicht zulässig...
		Hashtable<String, ?> pageInfo = new Hashtable<>();
		try {
			// wir geben einfach die bisherigen Infos wieder mit rein, das WIKI macht es dann richtig (neue Versionsnr. usw.)
			pageInfo = wiki.getPageInfo(internalWiki.getAddress());
		} catch (Exception ex) {
			// wenn ein Fehler auftritt, ist die Seite wohl noch nicht vorhanden
		}
		wiki.putPage(internalWiki.getAddress(), internalWiki.getContent(), pageInfo);
	}

	// theoretisches Update eines Tickets
	public void updateTicket(Ticket newTicket, String text) {
		aero.minova.trac.xmlprc.Ticket ticket = (aero.minova.trac.xmlprc.Ticket) trackerDynamicProxy.newInstance(aero.minova.trac.xmlprc.Ticket.class);

		try {
			// Vector<?> details = ticket.get(newTicket.getId());
			// Vector <HashMap> hM = ticket.getTicketFields();

			// Zeige Inhalt - zur Kontrolle
			// System.out.println("------------");
			// System.out.println("Inhalt von getTicketFields()");
			// System.out.println(hM.toString());
			// System.out.println("------------");
			// System.out.println("Inhalt von get(i)");
			// System.out.println(details.toString());
			// System.out.println("------------");

			Hashtable<String, Object> ht = new Hashtable<>();

			ht.put("summary", newTicket.getSummary().toString());
			ticket.update(newTicket.getId(), text, ht, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> listWikiPages() {
		aero.minova.trac.xmlprc.Wiki wiki = (aero.minova.trac.xmlprc.Wiki) trackerDynamicProxy.newInstance(aero.minova.trac.xmlprc.Wiki.class);
		Vector<?> pageList = wiki.getAllPages();
		Vector<String> pageNames = new Vector<>();
		for (Object object : pageList) {
			pageNames.add((String) object);
		}
		return pageNames;
	}

	public String getPage(String pagename) {
		aero.minova.trac.xmlprc.Wiki wiki = (aero.minova.trac.xmlprc.Wiki) trackerDynamicProxy.newInstance(aero.minova.trac.xmlprc.Wiki.class);
		return wiki.getPage(pagename);
	}

	public String getPageHTML(String pagename) {
		aero.minova.trac.xmlprc.Wiki wiki = (aero.minova.trac.xmlprc.Wiki) trackerDynamicProxy.newInstance(aero.minova.trac.xmlprc.Wiki.class);
		return wiki.getPageHTML(pagename);
	}

	public Hashtable<String, ?> getPageInfo(String pagename) {
		aero.minova.trac.xmlprc.Wiki wiki = (aero.minova.trac.xmlprc.Wiki) trackerDynamicProxy.newInstance(aero.minova.trac.xmlprc.Wiki.class);
		return wiki.getPageInfo(pagename);
	}

	private static TracServiceImpl tracServiceImpl = null;

	protected static TracServiceImpl getInstance() {
		if (tracServiceImpl != null) return tracServiceImpl;

		tracServiceImpl = new TracServiceImpl();
		return tracServiceImpl;
	}
}
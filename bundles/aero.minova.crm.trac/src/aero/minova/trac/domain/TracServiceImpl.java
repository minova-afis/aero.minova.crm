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
	@Override
	public TracTicket getTicket(int id) {
		return getTicket(id, false);
	}

	public TracTicket getTicket(int id, boolean debug) {
		aero.minova.trac.xmlprc.Ticket xmlTicket = (aero.minova.trac.xmlprc.Ticket) trackerDynamicProxy.newInstance(aero.minova.trac.xmlprc.Ticket.class);

		TracTicket tracTicket = new TracTicket(this);
		try {
			Vector<?> details = xmlTicket.get(id);
			// @SuppressWarnings("unused")
			// Object x = ticket.query("max=100,modified=2013-02-22..2013-02-23,status=closed");
			for (Iterator<?> i = details.iterator(); i.hasNext();) {
				Object value = i.next();
				if (value instanceof HashMap) {
					@SuppressWarnings("unchecked")
					HashMap<String, ?> attributes = (HashMap<String, ?>) value;
					tracTicket.id = id;
					tracTicket.summary = (String) attributes.get("summary");
					tracTicket.description = (String) attributes.get("description");
					tracTicket.setKeywords((String) attributes.get("keywords"));
					tracTicket.milestoneName = (String) attributes.get("milestone");

					try {
						tracTicket.billable = (String) attributes.get("billable");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.reporter = (String) attributes.get("reporter");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.ticketType = (String) attributes.get("type");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.release = (String) attributes.get("release");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.creationTime = (Date) attributes.get("time");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.changeTime = (Date) attributes.get("changetime");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.blockedBy = (String) attributes.get("blockedby");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.blocking = (String) attributes.get("blocking");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.component = (String) attributes.get("component");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.customerDescripion = (String) attributes.get("customerdescription");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.customerPrio = (String) attributes.get("customerprio");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.customerState = (String) attributes.get("customerstate");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.customerType = (String) attributes.get("customertype");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.totalHours = (String) attributes.get("totalhours");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.status = (String) attributes.get("status");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.resolution = (String) attributes.get("resolution");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.priority = (String) attributes.get("priority");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.offeredHours = (String) attributes.get("offeredhours");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.owner = (String) attributes.get("owner");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.dueDate = (String) attributes.get("duedate");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.startDate = (String) attributes.get("startdate");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.estimatedHours = (String) attributes.get("estimatedhours");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.moduleNames = (String) attributes.get("modulenames");
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						tracTicket.cc = (String) attributes.get("cc");
					} catch (Exception e) {
						System.out.println(e);
					}

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
			tracTicket = null;
		}
		return tracTicket;
	}

	@Override
	public TracMilestone getMilestone(String milestoneName) {
		aero.minova.trac.xmlprc.Ticket.Milestone milestone = (aero.minova.trac.xmlprc.Ticket.Milestone) trackerDynamicProxy
				.newInstance(aero.minova.trac.xmlprc.Ticket.Milestone.class);

		TracMilestone newMilestone = new TracMilestone();
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
	 * @return {@link TracWikiPage}
	 */
	@Override
	public TracWikiPage getWiki(String wikiAddress) {
		aero.minova.trac.xmlprc.Wiki wiki = (aero.minova.trac.xmlprc.Wiki) trackerDynamicProxy.newInstance(aero.minova.trac.xmlprc.Wiki.class);

		TracWikiPage newWiki = new TracWikiPage(wikiAddress);
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

	@Override
	public String wikiToHtml(String wikiText) {
		aero.minova.trac.xmlprc.Wiki wiki = (aero.minova.trac.xmlprc.Wiki) trackerDynamicProxy.newInstance(aero.minova.trac.xmlprc.Wiki.class);
		String html = wiki.wikiToHtml(wikiText);
		return html;
	}

	/**
	 * Schreibt den Inhalt auf die angegebene Wiki-Seite.
	 * 
	 * @param internalWiki
	 *            Objekt der internen {@link TracWikiPage}-Klasse
	 * @author wild
	 * @since 11.0.0
	 */
	public void setWiki(TracWikiPage internalWiki) {
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
	@Override
	public void updateTicketSummary(TracTicket newTicket, String text) {
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

	@Override
	public List<String> listWikiPages() {
		aero.minova.trac.xmlprc.Wiki wiki = (aero.minova.trac.xmlprc.Wiki) trackerDynamicProxy.newInstance(aero.minova.trac.xmlprc.Wiki.class);
		Vector<?> pageList = wiki.getAllPages();
		Vector<String> pageNames = new Vector<>();
		for (Object object : pageList) {
			pageNames.add((String) object);
		}
		return pageNames;
	}

	@Override
	public String getPage(String pagename) {
		aero.minova.trac.xmlprc.Wiki wiki = (aero.minova.trac.xmlprc.Wiki) trackerDynamicProxy.newInstance(aero.minova.trac.xmlprc.Wiki.class);
		return wiki.getPage(pagename);
	}

	@Override
	public String getPageHTML(String pagename) {
		aero.minova.trac.xmlprc.Wiki wiki = (aero.minova.trac.xmlprc.Wiki) trackerDynamicProxy.newInstance(aero.minova.trac.xmlprc.Wiki.class);
		return wiki.getPageHTML(pagename);
	}

	@Override
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
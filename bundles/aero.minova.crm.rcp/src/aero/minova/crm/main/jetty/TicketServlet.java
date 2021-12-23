package aero.minova.crm.main.jetty;

import java.io.IOException;
import java.io.PrintWriter;

import aero.minova.crm.model.jpa.Ticket;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TicketServlet extends HttpServlet {

	private static final long serialVersionUID = 202112230629L;

	private static Ticket lastTicket;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.getWriter().println("<html><body>");
		if (lastTicket != null) {
			PrintWriter writer = resp.getWriter();
			writer.println("<h1>#" + lastTicket.getId() + " " + lastTicket.getSummary() + "</h1>");
			if (lastTicket.getDescription().getHtml() != null) {
				writer.print(lastTicket.getDescription().getHtml());
			} else {
				writer.print("<p>");
				writer.print(lastTicket.getDescription().getMarkup());
				writer.print("</p>");
			}
			resp.setStatus(200);
		} else {
			resp.setStatus(404); // kein Ticket vorhanden
		}

		resp.getWriter().println("</body></html>");
	}

	public static Ticket getLastTicket() {
		return lastTicket;
	}

	public static void setLastTicket(Ticket lastTicket) {
		TicketServlet.lastTicket = lastTicket;
	}
}

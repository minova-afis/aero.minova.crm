package aero.minova.crm.service.http.servlets;

import java.io.IOException;
import java.util.Arrays;

import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.gfm.issues.GfmIssuesExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.superscript.SuperscriptExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import aero.minova.crm.model.jpa.Ticket;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MarkdownServlet extends HttpServlet {

	private static final long serialVersionUID = 202112261420L;
	private static Ticket ticket;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String text = req.getParameter("part");
		resp.setContentType("text/html");
		resp.getWriter().println("<html>" + "<head><link rel=\"stylesheet\" href=\"/css/markdown.css\"></head>" + "<body>");

		// <style>\n" + "body {\n" + " background-color: linen;\n" + "}\n" + "\n" + "h1 {\n" + " color: maroon;\n" + " margin-left: 40px;\n" + "} \n" +
		// "</style>

		switch (text) {
		case "all":
			resp.getWriter().print(convertMarkdown(ticket.getDescription().getMarkup()));
			break;
		}
//		if (text != null) resp.getWriter().print(convertMarkdown(text));
//		else resp.getWriter().print("<p>Test OK!" + text + "</p>");

		resp.getWriter().println("</body></html>");
		resp.setStatus(200);
	}

	private String convertMarkdown(String text) {
		MutableDataSet options = new MutableDataSet();

		// uncomment to set optional extensions
		options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), StrikethroughExtension.create(), TaskListExtension.create(),
				SuperscriptExtension.create(), GfmIssuesExtension.create(), DefinitionExtension.create()));

		// uncomment to convert soft-breaks to hard breaks
		// options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

		Parser parser = Parser.builder(options).build();
		HtmlRenderer renderer = HtmlRenderer.builder(options).build();

		// You can re-use parser and renderer instances
		Node document = parser.parse(text);
		String html = renderer.render(document); // "<p>This is <em>Sparta</em></p>\n"
		return html;
	}

	public static void setTicket(Ticket ticket) {
		MarkdownServlet.ticket = ticket;
	}
}

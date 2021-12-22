package aero.minova.crm.main.jetty;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map.Entry;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExempleServlet extends HttpServlet {

	private static final long serialVersionUID = 202112181714L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setStatus(200);
		resp.getWriter().println("<html><head>Hallo</head><body><h1>Header</h1>");
		resp.getWriter().println("<p>Es ist " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyy HH:mm:ss", req.getLocale())) + "</p>");
		for (Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
			String key = entry.getKey();
			String[] val = entry.getValue();
			if (val.length == 1) {
				resp.getWriter().println("<p>Parameter " + key + " = " + val[0] + "</p>");
			}
		}
		resp.getWriter().println("<p>Dies ist ein Absatz</p></body></html>");
	}
}

package hr.fer.zemris.java.hw15.servleti;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet koji odgovara na glasanje za neku od opcija.
 * Pronalazi zapis u bazi za zadanu opciju i povećava joj broj glasova za jedan.
 * @author Petra Marče
 *
 */
@SuppressWarnings("serial")
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer id;
		Integer pollID;
		try {
			id = Integer.parseInt(req.getParameter("id"));
			pollID = Integer.parseInt(req.getParameter("pollID"));
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Id benda i id glasanja moraju biti brojevi!");
			req.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(req, resp);
			return;
		}
	
		DAOProvider.getDao().glasaj(id, pollID);
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID="+pollID);
	}

	
}

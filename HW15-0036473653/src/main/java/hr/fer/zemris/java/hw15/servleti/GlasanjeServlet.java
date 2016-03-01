 package hr.fer.zemris.java.hw15.servleti;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Glasanje;
import hr.fer.zemris.java.tecaj_13.model.Result;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet koji odgovara na zahtjev za prikazom opcija za glasanje.
 * Iz baze dohvaća sve opcije u okviru zadanog glasanja za koje se može glasati.
 * Element liste instanca je razreda Result.
 * @author Petra Marče
 *
 */
@SuppressWarnings("serial")
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		
		Long pollid;
		try {
			pollid = Long.parseLong(req.getParameter("pollID"));
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Id glasanja mora biti broj!");
			req.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(req, resp);
			return;
		}
		
		
		Glasanje glasanje=DAOProvider.getDao().dohvatiGlasanje(pollid);
		if(glasanje==null){
			req.setAttribute("message", "U bazi nema glasanja sa predanim ID-om!");
			req.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(req, resp);
			return;
		}
		
		List<Result> zapisi=DAOProvider.getDao().dohvatiRezultate(pollid);
		if(zapisi==null){
			req.setAttribute("message", "U bazi nema nijedne opcije vezane za to glasanje!");
			req.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(req, resp);
			return;
		}
		
		req.setAttribute("glasanje", glasanje);
		req.setAttribute("opcije", zapisi);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
		
		
	
		
	}



	
}

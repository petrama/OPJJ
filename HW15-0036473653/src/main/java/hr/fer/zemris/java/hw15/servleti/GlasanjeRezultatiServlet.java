package hr.fer.zemris.java.hw15.servleti;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Result;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet koji svakom bendu pridružuje njegov broj glasova.
 * Servlet dohvaća sve zapise iz baze za predani id glasanja i kreira listu instanci razreda Result.
 * Kreirana lista sadrži sve podatke koji su potrebni da bi se rezultati glasovanja mogli prikazati na bilo koji način.
 * Listu rezultata sprema kao parametar zahtjeva pod ključem 'rezultati'.
 * Iz liste rezultata izdvaja one pobjedničke te novu listu sprema kao parametar pod ključem 'pobjednici'.
 * Zahtjev prosljeđuje stranici glasanjeRez.jsp
 * @author Petra Marče.
 *
 */
@SuppressWarnings("serial")
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		
		Long pollID;
		try {
			
			pollID=Long.parseLong(req.getParameter("pollID"));
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Id benda i id glasanja moraju biti brojevi!");
			req.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(req, resp);
			return;
		}
		
		
		List<Result> results=DAOProvider.getDao().dohvatiRezultate(pollID);
		
		
		if(results==null ||results.isEmpty()){
			req.setAttribute("message", "Rezultata za taj id nema!");
			req.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(req, resp);
			return;
		}
		Collections.sort(results);
		Long max = results.get(0).getGlasovi();
		List<Result> winners=new ArrayList<>();
		for (Result r : results) {
			if (r.getGlasovi() < max) {
				break;
			}
			winners.add(r);
		}
		req.setAttribute("rezultati", results);
		req.setAttribute("pobjednici", winners);
		req.setAttribute("pollID", pollID);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req,
				resp);

	}
}
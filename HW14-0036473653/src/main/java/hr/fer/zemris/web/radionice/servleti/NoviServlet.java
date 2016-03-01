package hr.fer.zemris.web.radionice.servleti;


import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadioniceBaza;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet koji odgovara na zahtjev za dodavanjem nove radionice.
 * Ukoliko korisnik nije autoriziran poziv ovog servleta rezultirat će renderiranjem stranice s prikazom pogreske.
 * Ako je korisnik ulogiran servlet će kao odgovor prikazati prazan formular za dodavanje nove radionice.
 * @author Petra Marče
 *
 */
@SuppressWarnings("serial")
@WebServlet("/new")
public class NoviServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(req.getSession().getAttribute("current.user")==null){
			req.setAttribute("poruka", "Neovlašten pristup, potrebna autorizacija!");
			req.getRequestDispatcher("/WEB-INF/pages/greska.jsp").forward(req, resp);
			return;
		}

		Radionica r=new Radionica();
		RadionicaForm f = new RadionicaForm();
		f.popuniIzRadionice(r);
		
		req.setAttribute("zapisi", f);
		RadioniceBaza baza = RadioniceBaza.ucitaj(req.getServletContext()
				.getRealPath("/WEB-INF/baza"));
		req.setAttribute("oprema", baza.getPopisOpreme());
		req.setAttribute("publika", baza.getPopisPublike());
		req.setAttribute("trajanja", baza.getPopisTrajanja());

		req.getRequestDispatcher("/WEB-INF/pages/formular.jsp").forward(req, resp);
	}
}

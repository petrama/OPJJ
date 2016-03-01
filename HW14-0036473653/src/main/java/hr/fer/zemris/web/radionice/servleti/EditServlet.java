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
 * Servlet koji odgovara na zahtjev za uređivanjem postojeće radionice čiji id dohvaća kao parametar.
 * Ako korisnik nije autoriziran ili zahtjev ne sadrži parametar id, servlet korisnika preusmjerava na stranicu sa ispisom pogreške.
 * Ako su predani ispravni parametri servlet odgovara na zahtjev tako da prikazuje formular za uređivanje tražene radionice.
 * @author Petra Marče
 *
 */
@SuppressWarnings("serial")
@WebServlet("/edit")
public class EditServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(req.getSession().getAttribute("current.user")==null){
			req.setAttribute("poruka", "Neovlašten pristup, potrebna autorizacija!");
			req.getRequestDispatcher("/WEB-INF/pages/greska.jsp").forward(req, resp);
			return;
		}
		Long id = null;
		try {
			id= Long.parseLong(req.getParameter("id"));
		} catch (Exception ex) {
			req.setAttribute("poruka", "Primljeni su neispravni parametri");
			req.getRequestDispatcher("/WEB-INF/pages/greska.jsp").forward(req, resp);
			return;
		}
		
		RadioniceBaza baza = RadioniceBaza.ucitaj(req.getServletContext()
				.getRealPath("/WEB-INF/baza"));
		Radionica r=baza.getRadionica(id);
		if(r==null) {
			req.setAttribute("poruka", "Traženi zapis ne postoji");
			req.getRequestDispatcher("WEB-INF/pages/greska.jsp").forward(req, resp);
			return;
		}
		
		RadionicaForm f = new  RadionicaForm();
		f.popuniIzRadionice(r);
		
		req.setAttribute("zapisi", f);
		req.setAttribute("oprema", baza.getPopisOpreme());
		req.setAttribute("publika", baza.getPopisPublike());
		req.setAttribute("trajanja", baza.getPopisTrajanja());
		
		req.getRequestDispatcher("WEB-INF/pages/formular.jsp").forward(req, resp);
	}
}

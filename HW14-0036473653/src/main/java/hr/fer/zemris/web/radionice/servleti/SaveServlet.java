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
 * Servlet koji odgovara na zahtjev za spremanjem radionice koju trenutno prikazani formular predstavlja.
 * Servlet preuzima parametre iz formulara, provjerava njihovu ispravnost te zatim ako su ispravni stvara primjerak radionice te ga dodaje u bazu.
 * Bazu nakon dodavanja snima na disk.
 * U slučaju da podatci nisu valjani servlet korisnika preusmjerava na ponovno uređivanje uz opise što ne valja.
 * Ako korisnik nije autoriziran poziv ovog servleta rezultirat ce prikazom stranice s opsom pogreske.
 * @author Petra Marče
 *
 */
@SuppressWarnings("serial")
@WebServlet("/save")
public class SaveServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		obradi(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		obradi(req, resp);
	}

	private void obradi(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(req.getSession().getAttribute("current.user")==null){
			req.setAttribute("poruka", "Neovlašten pristup, potrebna autorizacija!");
			req.getRequestDispatcher("/WEB-INF/pages/greska.jsp").forward(req, resp);
			return;
		}

		req.setCharacterEncoding("UTF-8");

		String metoda = req.getParameter("metoda");
		if (!"Pohrani".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath()
					+ "/listaj");
			return;
		}

		RadionicaForm f = new RadionicaForm();
		f.popuniIzHttpRequesta(req);
		f.validiraj();

		RadioniceBaza baza = RadioniceBaza.ucitaj(req.getServletContext()
				.getRealPath("/WEB-INF/baza"));
		
		if (f.imaPogresaka()) {
			req.setAttribute("zapisi", f);
			req.setAttribute("oprema", baza.getPopisOpreme());
			req.setAttribute("publika", baza.getPopisPublike());
			req.setAttribute("trajanja", baza.getPopisTrajanja());
			req.getRequestDispatcher("/WEB-INF/pages/formular.jsp").forward(
					req, resp);
			return;
		}

		Radionica r = new Radionica();
		
		f.popuniURadionicu(r, baza.getPopisPublike(), baza.getPopisTrajanja(),
				baza.getPopisOpreme());

		baza.snimi(r);
		baza.snimi();

		resp.sendRedirect(req.getServletContext().getContextPath() + "/listaj");
	}
}

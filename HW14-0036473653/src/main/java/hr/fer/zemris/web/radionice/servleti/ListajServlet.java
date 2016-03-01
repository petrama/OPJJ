package hr.fer.zemris.web.radionice.servleti;

import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadioniceBaza;
import hr.fer.zemris.web.radionice.komparatori.RadioniceKomparator;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet koji odgovara na zahtjev za prikazom svih radionica u bazi.
 * Ukoliko je korisnik autoriziran na popisu će se uz svaku radionicu nalaziti link na edit, a na dnu popisa link na opciju new.
 * @author Petra Marče.
 *
 */
@SuppressWarnings("serial")
@WebServlet({"/listaj",""})
public class ListajServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		RadioniceBaza baza = RadioniceBaza.ucitaj(req.getServletContext()
				.getRealPath("/WEB-INF/baza"));
		List<Radionica> sveRadionice = baza.getRadionice();
		Collections.sort(sveRadionice,RadioniceKomparator.DATUM_IME);
	
		req.setAttribute("radionice", sveRadionice);
		req.getRequestDispatcher("/WEB-INF/pages/listaj.jsp")
				.forward(req, resp);

	}
	
	
}

package hr.fer.zemris.java.hw15.servleti;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Glasanje;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet odgovara na zahtjev za prikazom svih raspoloživih glasovanja u okviru ove aplikacije.
 * Servlet dohvaća sva glasovanja iz baze, ako ne nađe nijedno poziva inicijalizacijski servlet koji obavlja posao nužan za nastavak rada aplikacije.
 * Poziv ovog servleta rezultira prikazom svih raspoloživih glasanja
 * @author Petra Marče
 *
 */
@SuppressWarnings("serial")
@WebServlet({"/servleti/index.html","/servleti"})
public class OdabirGlasanja extends HttpServlet {
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
	
	List<Glasanje> glasanja=DAOProvider.getDao().dohvatiSvaGlasanja();
	if(glasanja==null || glasanja.isEmpty()){
		resp.sendRedirect(req.getContextPath()+"/servleti/init");
		return;
	}
	req.setAttribute("glasanja", glasanja);
	req.getRequestDispatcher("/WEB-INF/pages/glasanja.jsp").forward(req, resp);
}
}

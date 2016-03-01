package hr.fer.zemris.web.radionice.servleti.korisnici;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@SuppressWarnings("serial")
@WebServlet("/logout")
/**
 * Servlet koji odgovara na korisnikov zahtjev za logoutom.
 * Nakon operacije korisnik postaje anoniman.
 * @author Petra Marče
 *
 */
public class LogoutServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	req.getSession().invalidate();
	resp.sendRedirect(req.getServletContext().getContextPath()+"/listaj");
	}
}

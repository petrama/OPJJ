package hr.fer.zemris.java.tecaj_14.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji odgovara na korisnikov zahtjev za logoutom. Nakon operacije
 * korisnik postaje anoniman.
 * 
 * @author Petra Marče
 * 
 */

@SuppressWarnings("serial")
@WebServlet("/servleti/logout")
public class LogoutServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getSession().invalidate();
		resp.sendRedirect(req.getServletContext().getContextPath()+"/servleti/main");
	}
}

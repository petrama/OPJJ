package hr.fer.zemris.java.tecaj_13.servleti;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji odgovara na korisnikov zahtjev za promjenu pozadinske boje ove aplikacije.
 * Servlet odabranu boju postavlja kao sesijski parametar pod nazivom 'pickedBgColor'.
 * @author Petra Marče
 *
 */
@SuppressWarnings("serial")
@WebServlet("/setcolor")
public class ColorServlet extends HttpServlet {

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
	
	String a=req.getParameter("a");
	System.out.println(a);
	req.getSession().setAttribute("pickedBgCol",a);
	resp.sendRedirect(req.getContextPath() + "/index.jsp");
	
	}
	
	
	
	
}


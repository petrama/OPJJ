package hr.fer.zemris.web.radionice.servleti.korisnici;

import hr.fer.zemris.web.radionice.korisnici.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet odgovara na zahtjev za logiranjem korisnika.
 * Ako korisnik sa predanim korisnickim imenom postoji i sifra je ispravna operacija uspjeva.
 * Ulogirani korisnik ima pristup svim mogućnostima ove aplikacije.
 * @author Petra Marče
 *
 */
@SuppressWarnings("serial")
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		obradi(req,resp);
		}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	obradi(req,resp);
	}
	
	private void obradi(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String usname=req.getParameter("username");
		if(usname!=null){
			User korisnik=provjeri(usname,req.getParameter("password"));
			if(korisnik!=null){
				req.getSession().setAttribute("current.user", korisnik);
				resp.sendRedirect(req.getServletContext().getContextPath()+"/listaj");
				return;
			}else{
				req.setAttribute("poruka", "Username ili password je pogrešan!");
				req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
				return;
			}}
			else{
		
				req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
				return;
			}
		
	}

	private User provjeri(String usname, String pass) {
		if(usname.equals("aante") && pass.equals("tajna")){

			return new User("aante","tajna", "Ante", "Antić");
		
		}else{
		
				return null;
			}
		}
	
	
	
	}
	



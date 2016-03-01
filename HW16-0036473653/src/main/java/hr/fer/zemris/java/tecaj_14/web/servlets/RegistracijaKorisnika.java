package hr.fer.zemris.java.tecaj_14.web.servlets;

import hr.fer.zemris.java.tecaj_14.model.forms.BlogUserRegistrationForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/servleti/register")
public class RegistracijaKorisnika extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BlogUserRegistrationForm form=new BlogUserRegistrationForm();
		req.setAttribute("form", form);
		req.getRequestDispatcher("/WEB-INF/pages/Registracija.jsp").forward(req, resp);
	}
	
}

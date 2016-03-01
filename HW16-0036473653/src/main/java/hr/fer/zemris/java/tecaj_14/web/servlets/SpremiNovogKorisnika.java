package hr.fer.zemris.java.tecaj_14.web.servlets;

import hr.fer.zemris.java.tecaj_14.model.forms.BlogUserRegistrationForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/servleti/save")
public class SpremiNovogKorisnika extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		obradi(req,resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		obradi(req,resp);
	}
	private void obradi(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		req.setCharacterEncoding("UTF-8");

		String metoda = req.getParameter("metoda");
		if (!"Pohrani".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath()
					+ "/servleti/main");//NEMA LISTAJ
			return;
		}
		BlogUserRegistrationForm f=new BlogUserRegistrationForm();
		f.popuniIzHttpRequesta(req);
		f.validiraj();
		
		if(f.imaPogresaka()){
			req.setAttribute("form", f);
			req.getRequestDispatcher("/WEB-INF/pages/Registracija.jsp").forward(req, resp);
			return;
		}
		
		
		f.stvoriNovogUsera();
		//nije dobar redirect111
		resp.sendRedirect(req.getServletContext().getContextPath()+"/servleti/main");
		
		
	}
	
}

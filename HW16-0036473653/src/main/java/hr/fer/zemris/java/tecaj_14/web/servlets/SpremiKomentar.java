package hr.fer.zemris.java.tecaj_14.web.servlets;

import hr.fer.zemris.java.tecaj_14.model.forms.BlogCommentForm;
import hr.fer.zemris.java.tecaj_14.model.forms.BlogEntryForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/servleti/saveComment")
public class SpremiKomentar extends HttpServlet {
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
			throws IOException, ServletException {

		req.setCharacterEncoding("UTF-8");

		String metoda = req.getParameter("metoda");
		if (!"Pohrani".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath()
					+ "/servleti/main");// NEMA LISTAJ
			return;
		}

		BlogCommentForm f = new BlogCommentForm();
		f.popuniIzHttpRequesta(req);

		f.validiraj();

		if (f.imaPogresaka()) {
			req.setAttribute("form", f);
			req.getRequestDispatcher(
				
							"author/"+f.getNick() + "/" + f.getBlogEntry()).forward(
					req, resp);
			return;
		}

		f.spremi();
		// nije dobar redirect111
		resp.sendRedirect(req.getServletContext().getContextPath()
				+ "/servleti/main");

	}

}

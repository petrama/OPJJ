package hr.fer.zemris.java.tecaj_14.web.servlets;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;
import hr.fer.zemris.java.tecaj_14.model.forms.BlogEntryForm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servleti/author/*")
@SuppressWarnings("serial")
public class AutorServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		obradi(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		obradi(req, resp);

	}

	private void obradi(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		String ulogirani=(String) req.getSession().getAttribute("current.user.nick");
		System.out.println(req.getPathInfo());
		String[] params = req.getPathInfo().substring(1).trim().split("/");

		if (params.length < 1 || params.length > 3) {// neispravan link
			resp.sendRedirect(req.getServletContext().getContextPath()
					+ "/servleti/main");
			return;
		}

		List<BlogUser> korisnik = DAOProvider.getDAO().getUserByNick(params[0]);
		if (korisnik.isEmpty()) {
			req.setAttribute("poruka", "Pogreška takvog korisnika u bazi nema!");
			req.getRequestDispatcher("/WEB-INF/pages/greska.jsp").forward(req,
					resp);
			return;
		}

		req.setAttribute("korisnik", korisnik.get(0));
		req.setAttribute("nick", params[0]);

		if (params.length == 1) {
			req.getRequestDispatcher("/WEB-INF/pages/PostoviBloga.jsp")
					.forward(req, resp);
			return;
		}

		if (params[1].equals("new")) {
			if(ulogirani.equals(korisnik.get(0).getNick())){
				req.setAttribute("zapisi", new BlogEntryForm());
				req.getRequestDispatcher("/WEB-INF/pages/FormularPosta.jsp")
						.forward(req, resp);
				return;
			}
			resp.sendRedirect(req.getServletContext().getContextPath()
					+ "/servleti/main");
			return;
		}

		Long id = null;
		try {
			id = Long.parseLong(params[1]);
		} catch (NumberFormatException ne) {
			resp.sendRedirect(req.getServletContext().getContextPath()
					+ "/servleti/main");
			return;
		}
		BlogEntry entryToHandle = DAOProvider.getDAO().getBlogEntry(id);
//				korisnik.get(0), id);
				
		if(entryToHandle==null){
			resp.sendRedirect(req.getServletContext().getContextPath()
					+ "/servleti/author/"+params[0]);
			return;
		}
		req.setAttribute("entry", entryToHandle);
		if (params.length == 2) {
			req.getRequestDispatcher("/WEB-INF/pages/PrikazPosta.jsp").forward(
					req, resp);
			return;
		}
		if (params[2].equals("edit")) {
			if(ulogirani.equals(korisnik.get(0).getNick())){
			req.setAttribute("zapisi",
					new BlogEntryForm().popuniIzPosta(entryToHandle));
			req.getRequestDispatcher("/WEB-INF/pages/FormularPosta.jsp")
					.forward(req, resp);
			return;
			}
			resp.sendRedirect(req.getServletContext().getContextPath()
					+ "/servleti/main");
			return;
		}
		System.out.println("OVO JE KRIVA STAZA:" + req.getPathInfo());
		resp.sendRedirect(req.getServletContext().getContextPath()
				+ "/servleti/main");
	}
}
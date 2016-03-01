package hr.fer.zemris.java.tecaj_14.web.servlets;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;




import hr.fer.zemris.java.tecaj_14.model.forms.BlogUserRegistrationForm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet({"/servleti/main","/servleti/login"})
public class GlavniServlet extends HttpServlet{

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
		String username=req.getParameter("nick");
		String password=req.getParameter("password");
		req.setAttribute("registrirani", DAOProvider.getDAO().getAllUsers());
		if(username!=null && password!=null){
			
			
			BlogUser korisnik=provjeri(username,req.getParameter("password"));
			
			
			if(korisnik!=null){
				req.getSession().setAttribute("current.user.id", korisnik.getId());
				req.getSession().setAttribute("current.user.fn", korisnik.getFirstName());
				req.getSession().setAttribute("current.user.ln", korisnik.getLastName());
				req.getSession().setAttribute("current.user.nick", korisnik.getNick());
				req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
				return;
			}else{
				req.setAttribute("poruka", "Username ili password je pogrešan!");
				req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
				return;
			}}
			else{
		
				req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
				return;
			}
		
		
		
	}

	private BlogUser provjeri(String username, String pass) {
	List<BlogUser> list=DAOProvider.getDAO().getUserByNick(username);
	if(!list.isEmpty()){
		BlogUser usr=list.get(0);
		if(BlogUserRegistrationForm.calculateSHA(pass).equals(usr.getPasswordHash())){
			return usr;
		}
	}
	return null;
	
	}
	
}

package hr.fer.zemris.java.tecaj_13.servleti;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet koji na zaslon ispisuje tablicu brojeva i njihovih kvadrata.
 * Rang brojeva može se zadati kao ulazni parametar u protivnom ispisuje se tablica brojeva od 1 do 20.
 * @author Petra Marče
 *
 */
@SuppressWarnings("serial")
@WebServlet("/squares")
public class SquaresServlet extends HttpServlet {
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer a = null;
		Integer b = null;
		try {
			a = Integer.valueOf(req.getParameter("a"));
		} catch (Exception ex) {
			a = 0;


		}
		try {
			b = Integer.valueOf(req.getParameter("b"));
		} catch (Exception ex) {
			b = 20;
		}
		if (a > b) {
			Integer tmp = a;
			a = b;
			b = tmp;
		}
		
		if(b>a+20){
			b=a+20;
		}
		
		List<Par> lista = new ArrayList<>();
		for (int i = a; i <= b; i++) {
			lista.add(new Par(i, i*i));
		}
		req.setAttribute("parovi", lista);
		
		req.getRequestDispatcher("WEB-INF/pages/squares.jsp").forward(req, resp);

	}
	/**
	 * Razred koji predstavlja jedan redak tablice koju servlet treba generirati.
	 * Sastoji se od broja i njegovog kvadrata.
	 * @author Petra Marče
	 *
	 */
	public static class Par {
		int broj;
		int vrijednost;

		public Par(int broj, int vrijednost) {
			super();
			this.broj = broj;
			this.vrijednost = vrijednost;
		}
		/**
		 * Metoda za dohvat broja.
		 * @return broj.
		 */
		public int getBroj() {
			return broj;
		}
		/**
		 * Metoda za dohvat kvadrata broja.
		 * @return kvadrat broja.
		 */
		public int getVrijednost() {
			return vrijednost;
		}

	}

}

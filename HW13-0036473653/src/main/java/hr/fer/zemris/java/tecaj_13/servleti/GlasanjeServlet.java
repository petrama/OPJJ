 package hr.fer.zemris.java.tecaj_13.servleti;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet koji odgovara na zahtjev za prikazom opcija za glasanje.
 * Otvara datoteku s bendovima za koje se može glasati i generira listu tih bendova.
 * Element liste instanca je razreda Trio.
 * @author Petra Marče
 *
 */
@SuppressWarnings("serial")
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String fileName = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-definicija.txt");
		
		List<String> lines=Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		req.setAttribute("brojBendova", lines.size());
		req.setAttribute("bendovi", listFile(lines));
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
		
	}

	/**
	 * Pomoćna metoda koja iz predane liste linija datoteke s bendovima generira listu raspoloživih bendova.
	 * @param lines linije datoteke.
	 * @return lista svih bendova.
	 */
	protected static List<Trio> listFile(List<String> lines){
	
	List<Trio> lista=new ArrayList<>();
		for(String l:lines){
		String[] splittedLine=l.split("\t");
		lista.add(new Trio(Integer.parseInt(splittedLine[0]), splittedLine[1],splittedLine[2]));
		
	}
		return lista;
	}
	/**
	 * Razred predstavlja jedan bend.
	 * Bend označava njegov id, naziv benda i String koji predstavlja link na jednu reprezentativnu pjesmu benda.
	 * @author Petra Marče.
	 *
	 */
	public static class Trio {
		int id;
		String bend;
		String pjesma;
		
		
		public Trio(int id, String band, String pjesma) {
			super();
			this.id = id;
			this.bend = band;
			this.pjesma = pjesma;
		}
		/**
		 * Metoda za dohvat id-a
		 * @return id benda.
		 */
		public int getId() {
			return id;
		}
		/**
		 * Metoda za dohvat naziva benda.
		 * @return bend.
		 */
		public String getBend() {
			return bend;
		}
		/**
		 * Metoda za dohvat linka na pjesmu benda.
		 * @return pjesama.
		 */
		public String getPjesma() {
			return pjesma;
		}

		

	}
}

package hr.fer.zemris.java.tecaj_13.servleti;

import hr.fer.zemris.java.tecaj_13.servleti.GlasanjeServlet.Trio;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet koji svakom bendu pridružuje njegov broj glasova.
 * Servlet čita datoteku s popisom bendova te datoteku s popisom rezltata te ih spaja i kreira listu instanci razreda Result.
 * Kreirana lista sadrži sve podatke koji su potrebni da bi se rezultati glasovanja mogli prikazati na bilo koji način.
 * Listu rezultata sprema kao parametar zahtjeva pod ključem 'rezultati'.
 * Iz liste rezultata izdvaja one pobjedničke te novu listu sprema kao parametar pod ključem 'pobjednici'.
 * Zahtjev prosljeđuje stranici glasanjeRez.jsp
 * @author Petra Marče.
 *
 */
@SuppressWarnings("serial")
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		
		List<Result> results=createResults(req);
		List<Result> winners = new ArrayList<>();
		int max = results.get(0).getGlasovi();

		for (Result r : results) {
			if (r.brojGlasova < max) {
				break;
			}
			winners.add(r);
		}
		req.setAttribute("rezultati", results);
		req.setAttribute("pobjednici", winners);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req,
				resp);

	}
	/**
	 * Metoda koja radi spajanje datoteke sa bendovima i trenutnim brojem glasova te vraća listu rezultata.
	 * @param req request, služi samo za dohvat stvarne putanje
	 * @return lista rezultata.
	 * @throws IOException u slučaju pogreske s ulazno izlaznim operacijama baca iznimku.
	 */
	protected  static List<Result> createResults(HttpServletRequest req) throws IOException{
		String fileName = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-definicija.txt");

		List<String> lines = Files.readAllLines(Paths.get(fileName),
				StandardCharsets.UTF_8);
		req.setAttribute("brojBendova", lines.size());
		//učitaj listu svih bendova
		List<Trio> listaBendova = GlasanjeServlet.listFile(lines);
//		req.setAttribute("bendovi", GlasanjeServlet.listFile(lines));

		String fileResults = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-rezultati.txt");
		GlasanjeGlasajServlet.checkFileExistance(new File(fileResults),lines.size());
		List<String> linesRes = Files.readAllLines(Paths.get(fileResults),
				StandardCharsets.UTF_8);
		//mapa broja glasova oblika id-brojGlasova
		Map<Integer, Integer> res = mapResults(linesRes);

		
		List<Result> results = new ArrayList<>();
		
		//svakom bendu pridruzuje rezultat i kreira jedan Result i dodaje ga u listu rezultata
		for (Trio bend : listaBendova) {
			results.add(new Result(bend, res.get(bend.getId())));
		}
		//sortira rezultate
		Collections.sort(results);
		return results;
	}

	/**
	 * Pomoćna metoda koja prima listu linija iz datoteke s rezultatima te iz njih radi mapu oblika id-brojGlasova.
	 * @param linesRes mapa u kojoj je ključ id benda a vrijednost broj glasova.
	 * @return mapa broja glasova.
	 */
	protected static Map<Integer, Integer> mapResults(List<String> linesRes) {
		Map<Integer, Integer> results = new HashMap<Integer, Integer>();
		for (String line : linesRes) {
			String[] parts = line.split("\t");
			results.put(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
		}
		return results;
	}

	/**
	 * Razred koji predstavlja jedan rezultat glasovanja.
	 * Sastoji se od instance razreda  Trio i broja glasova.
	 * @author Petra Marče
	 *
	 */
	public static class Result implements Comparable<Result> {
		Trio bend;
		Integer brojGlasova;

		public Result(Trio bend, Integer brojGlasova) {
			super();
			this.bend = bend;
			this.brojGlasova = brojGlasova;
		}
		/**
		 * Metoda za dohvat benda.
		 * @return bend.
		 */
		public Trio getTrio() {
			return bend;
		}
		/**
		 * Metoda za dohvat broja glasova.
		 * @return broj glasova.
		 */

		public Integer getGlasovi() {
			return brojGlasova;
		}

	
		@Override
		public int compareTo(Result o) {
			return o.getGlasovi().compareTo(this.getGlasovi());
		}

	}
}

package hr.fer.zemris.java.tecaj_13.servleti;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet koji odgovara na glasanje za neki od bendova.
 * Otvara datoteku u kojoj bilježi glasove i povećava odabranom bendu broj glasova za 1.
 * @author Petra Marče
 *
 */
@SuppressWarnings("serial")
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer id = Integer.parseInt(req.getParameter("id"));
		Integer n = (Integer) req.getSession().getAttribute("n");
		if (id>n || id <1) return;
		String fileName = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-rezultati.txt");
		File file = new File(fileName);
		checkFileExistance(file,n);
		updateResults(fileName, id);
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

	/**
	 * Metoda koja provjerava postoji li datoteka s rezultatima glasnja te ako ne postoji stvara novu.
	 * @param file putanja do datoteke s rezultatima glasovanja.
	 * @param n broj bendova, tj broj redaka koji treba biti u datoteci.
	 * @throws IOException u slučaju pogreške s ulazno izlaznim operacijama metoda baca iznimku.
	 */
	protected static void checkFileExistance(File file,int n) throws IOException{
		 if (!file.exists()) {
			   file.createNewFile();
//			   FileWriter fw = new FileWriter(file.getAbsoluteFile());
			
			   
			   OutputStreamWriter ow=new OutputStreamWriter(new FileOutputStream(file.getAbsoluteFile()),"UTF-8");
			   BufferedWriter bw = new BufferedWriter(ow);
			   for (int i = 1, id = n; i <= id; i++) {
			    bw.write(i + "\t0\r\n");
			   }
			   bw.close();
			  }
	}
	
	/**
	 * Metoda koja otvara datoteku s glasovima i zadanom bendu povećava broj glasova za 1- 
	 * @param fileName putanja do datoteke.
	 * @param id id benda kojemu treba dodati glas.
	 * @throws IOException u slučaju pogreške s ulazno izlaznim operacijama metoda baca iznimku.
	 */
	private static void updateResults(String fileName,Integer id) throws IOException{
		List<String> lines = Files.readAllLines(Paths.get(fileName),
				StandardCharsets.UTF_8);
		int i = 0;
		String newLine = null;
		for (i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if (line.startsWith(id.toString())) {
				String[] pair = line.split("\t");
				Integer temp = Integer.parseInt(pair[1]);
				temp++;
				newLine = id + "\t" + temp;
				break;
			}
		}
		lines.set(i, newLine);
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(fileName), "UTF-8"));
		for (String line:lines) {
			wr.write(line);
			wr.newLine();
		}
		wr.close();
	}
}

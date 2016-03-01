package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Razred koji ima niz statičkih metoda koje služe kao pomoć razredu
 * SmartHttpServer.
 * Razred uključuje metode koje se bave čitanjem iz datoteke ali i slanjem poruka klijentu.
 * @author Petra Marče.
 * 
 */
public class ServerAssistant {


	
	/**
	 * Pomoćna metoda koja utvrđuje mime tip na temelju predanog imena datoteke i mape.
	 * Prvo iz putanje izdvaja ekstenziju.
	 * Ako je ekstenzija 'smscr' metoda vraća 'smscr'.
	 * Ako ekstenzija ne postoji ili je nema u mapi vraća 'application/octet-stream'.
	 * Ako u mapi kao ključ pronađe  predanu ekstenziju vraća vrijednost toga ključa u mapi.
	 * @param name ime datoteke
	 * @param mimeTypes mapa mime tipova
	 * @return vraća traženi mime tip.
	 */

	protected static String utvrdiMimeTip(String name,
			Map<String, String> mimeTypes) {
		int tocka = name.lastIndexOf('.');
		if (tocka == -1) {
			return "application/octet-stream";

		}

		String eks = name.substring(tocka + 1).toLowerCase();
		if (eks.equals("smscr")) {
			return eks;
		}
		if (mimeTypes.containsKey(eks)) {
		
			return mimeTypes.get(eks);
		}
		return "application/octet-stream";
	}

	
	/**
	 * Metoda učitava datoteku predane putanje, pretvara je u string.
	 * Razdjeljuje sve retke po znaku '=' i sprema u predanu mapu.
	 * Ova metoda ima smisla samo ako se poziva nad datotekama kojima je svaki redak oblika 'kljuc' '=' 'vrijednost'.
	 * @param map mapa u koju treba pospremiti isparsirane linije datoteke.
	 * @param s putanja do datoteke iz koje treba učitati informacije u predanu mapu.
	 */

	protected static void loadInMap(Map<String, String> map, String s) {
		String fileContent = "";
		try {
			fileContent = readFile(s);
		} catch (IOException e) {
			System.err.println("Cannot open or read file: " + s);
			e.printStackTrace();
		}
		String[] lines = fileContent.split("\r\n");
		for (String line : lines) {
			String[] keyValue = line.split("=");
			if (keyValue.length != 2) {
				throw new IllegalArgumentException(
						"Each line in given file must be 'key'='value'");
			}
			keyValue[0] = keyValue[0].trim();
			keyValue[1] = keyValue[1].trim();
			map.put(keyValue[0].trim(), keyValue[1].trim());

		}

	}

	/**
	 * Metoda koja iz linije uklanja komentare.
	 * Prepoznaje sljedeće oznake komentara: '#' '%' i 'REM'.
	 * @param line linija iz koje treba ukloniti komentare.
	 * @return linija bez komentara.
	 */
	protected static String ignoreComments(String line) {
		int pozicija = line.indexOf('#');
		if (pozicija >= 0) {
			line = line.substring(0, pozicija);
		}
		pozicija = line.indexOf('%');
		if (pozicija >= 0) {
			line = line.substring(0, pozicija);
		}
		if (line.contains("REM")) {
			line = "";
		}

		return line;
	}
	
	/**
	 * Metoda koja vraća stringovnu reprezentaciju datoteke na predanoj putanji.
	 * Metoda otvori datoteku te čita iz nje liniju po liniju i sprema u string.
	 * Metoda vraća tijelo datoteke s uklonjenim komentarima i bez praznih redaka.
	 * @param filePath putanja do datoteke koju treba učitati.
	 * @return vraća stringovni zapis predane datoteke.
	 * @throws IOException u slučaju problema s I/O operacijama baca odgovarajucu iznimku.
	 */
	protected static String readFile(String filePath) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
		String everything = "";
		try {
			StringBuilder sb = new StringBuilder();
			String line = "";
			do {
				line = br.readLine();
			} while (line.isEmpty());
			while (line != null) {

				String nesto = ignoreComments(line);
				if (!nesto.isEmpty()) {
					sb.append(nesto);
					sb.append(System.lineSeparator());

				}
				line = br.readLine();
			}
			everything = sb.toString();
		} finally {
			br.close();
		}
		;
		return everything;
	}

	

	protected static void parseParameters(String paramString,
			Map<String, String> params) {
		String[] parameters = paramString.split("&");
		for (String param : parameters) {
			param = param.trim();
			String[] twoParts = param.split("=");
			params.put(twoParts[0].trim(), twoParts[1].trim());
		}

	}
	
	/**
	 * Metoda koja čita zahtjeve klijenata servera.
	 * @param istream ulazni stream sa kojega čita liniju po liniju zahtjeva.
	 * @return vraća listu linija zahtjeva.
	 * @throws IOException u slučaju problema sa čitanjem linije baca iznimku.
	 */
	protected static List<String> readRequest(InputStream istream)
			throws IOException {
		List<String> lines = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(istream), StandardCharsets.ISO_8859_1));

		while (true) {
			String rest = reader.readLine();
			if (rest == null || rest.isEmpty()) {
				break;
			}
			lines.add(rest);

		}

		return lines;
	}

}

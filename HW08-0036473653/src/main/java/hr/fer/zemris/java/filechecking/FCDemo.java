package hr.fer.zemris.java.filechecking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import hr.fer.zemris.java.filechecking.FCFileVerifier;
import hr.fer.zemris.java.filechecking.FCProgramChecker;

/**
 * Razred koji služi testiranju izvođenja programa pisanih u jeziku <i>fmagician</i>.
 * @author Petra Marče
 *
 */
public class FCDemo {
	/**
	 * Metoda koja se poziva pri pokretanju programa.
	 * @param args argumenti iz komandne linije.
	 * Program očekuje putanju do datoteke koja se obrađuje, ime te datoteke, te putanju do skripte.
	 */
	public static void main(String[] args) {
		File file = null;
		if (args.length != 3) {
			System.err
					.println("Program expects 3 arguments: path to zipfile,name of file and path to file with tests!");
			System.exit(1);
		}
		try {
			file = new File(args[0]);
		} catch (NullPointerException n) {
			System.err.println("First argument must be path to zipfile!");
			System.exit(1);
		}
		String program = ucitaj(args[2]);
		String fileName = (args[1]); // definiraj stvarno ime arhive
		FCProgramChecker checker = new FCProgramChecker(program);
		if (checker.hasErrors()) {
			System.out.println("Predani program sadrži pogreške!");
			for (String error : checker.errors()) {
				System.out.println(error);
			}
			System.out.println("Odustajem od daljnjeg rada.");
			System.exit(0);
		}
		Map<String, Object> initialData = new HashMap<>();
		initialData.put("jmbag", "0036473653");
		initialData.put("lastName", "Petra");
		initialData.put("firstName", "Marče");
		FCFileVerifier verifier = new FCFileVerifier(file, fileName, program,
				initialData);
		if (!verifier.hasErrors()) {
			System.out.println("Yes! Uspjeh! Ovakav upload bi bio prihvaćen.");
		} else {
			System.out
					.println("Ups! Ovaj upload se odbija! Što nam još ne valja?");
			for (String error : verifier.errors()) {
				System.out.println(error);
			}
		}
	}
	/**
	 * Metoda koja učitava datoteku sa skriptom pisanu u jeziku <i>fmagician</i>.
	 * Iz datoteke uklanja komentare i trima sve retke.
	 * @param fileName putanja do datoteke koja se priprema za izvođenje.
	 * @return vraća stringovnu reprezentaciju sadržaja datoteke.
	 */
	private static String ucitaj(String fileName) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			try {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(fileName), StandardCharsets.UTF_8));
			} catch (FileNotFoundException e) {
				System.err.println("File" + fileName + " not found!");
				System.exit(1);

			}
			while (true) {
				String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					System.err.println("Cannot read line from file");
				}
				if (line == null)
					break;
				if (line.isEmpty())
					continue;
				line = removeComments(line);
				line = line.trim();
				sb.append(line).append(" ");
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception ignorable) {
				}
			}
		}
		return sb.toString();

	}
	/**
	 * Metoda koja iz linije uklanja komentare.
	 * Komentarima se smatraju retci koji počinju oznakama: '#','%','REM'.
	 * @param line ulazni niz, linija datoteke iz koje se komentar zeli ukloniti.
	 * @return linija bez komentara.
	 */
	
	private static String removeComments(String line) {
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
}
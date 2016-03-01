package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Razred predstavlja jednu od komandi razreda MyShell.
 * Ova komanda služi sa promjenu ili ispis ključnih znakova komande.
 * @author Petra Marče
 *
 */
public class SymbolShellCommand implements ShellCommand {
	private BufferedWriter out;

	/**
	 * Metoda čijim se pozivom izvršava sama komanda.
	 * Metoda prema broju primljenih argumenata utvrđuje da li korisnik želi
	 * promjeniti ili samo ispisati neki od znakova komande.
	 * Očekivani broj argumenata za promjenu je 2-kljucna rijec i novi znak kojim mjenjamo stari,
	 * dok je ocekivani broj argumenata za ispis 1-ime kljucnog znaka ciju vrijednost zelimo provjeriti tj. ispisati.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) {

		this.out = out;

		if (arguments.length < 1 || arguments.length > 2) {
			return MyShell.writeMessage(
					"'symbol command expects one or two arguments!", out);
		}

		if (ShellSymbol.containsSymbol(arguments[0]) == false) {
			return MyShell.writeMessage("Symbol '" + arguments[0]
					+ "' is invalid!", out);
		}

		if (arguments.length == 1) {
			writeSymbol(arguments[0]);
		} else {
			changeSymbol(arguments[0], arguments[1]);
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda koja se poziva kada je potrebno promjeniti neki od kljucnih
	 * znakova ljuske.
	 * 
	 * @param name
	 *            ime znaka kojeg zelimo zamjeniti nekim novim.
	 * @param newSymbol
	 *            novi simbol kojeg zelimo koristiti za znak predanog imena.
	 *            Ocekivana imena su: 'PROMPT' , 'MORELINES' ili 'MULTILINES'
	 */

	private void changeSymbol(String name, String newSymbol) {
		String oldSymbol = ShellSymbol.getSymbol(name);
		try {
			switch (name) {
			case "PROMPT":
				ShellSymbol.setPrompt(newSymbol);
				break;
			case "MORELINES":
				ShellSymbol.setMorelines(newSymbol);
				break;
			case "MULTILINES":
				ShellSymbol.setMultilines(newSymbol);
				break;
			default:
				break;
			}
			out.write("Symbol for " + name + " changed from '" + oldSymbol
					+ "' to '" + newSymbol + "'");
			out.newLine();
		} catch (IOException e) {
			System.out.println("Error with output stream!");

		}
	}

	/**
	 * Metoda koja se poziva ako je potrebno ispisati trenutni simbol za kljucni znak ljuske.
	 * @param name ime znaka kojeg treba ispisati.
	 */
	private void writeSymbol(String name) {
		try {
			out.write("Symbol for " + name + " is '"
					+ ShellSymbol.getSymbol(name) + "'");
			out.newLine();
			out.flush();
		} catch (IOException e) {
			System.out.println("Error with output stream!");
		}
	}
}

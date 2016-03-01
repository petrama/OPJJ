package hr.fer.zemris.java.tecaj.hw6.shell;

/**
 * Razred koji predstavlja jednu od komandi ljuske MyShell.
 * Ova komanda služi za ispis ili ažuriranje kljucnih znakova u ljusci.
 * Ti znakovi su znak za unos-prompt, znak da se linija proteže kroz vise redaka
 * te znak da je trenutni unos nastavak unosa iz prethodnog retka.
 * @author Petra Marče
 *
 */
public class ShellSymbol {
	protected static String prompt = ">";
	protected  static String morelines = "\\";
	protected  static String multilines = "|";

	/**
	 * Metoda za postavljanje znaka za unos.
	 * @param prom
	 */
	public static void setPrompt(String prom) {
		prompt = prom;
	}
	/**
	 * Metoda za postavljanje znaka za više redaka.
	 * @param name
	 */
	public static void setMorelines(String name) {
		morelines = name;
	}

	/**
	 * Metoda za postavljanje znaka za multiredak.
	 * @param mult
	 */
	public static void setMultilines(String mult) {
		multilines = mult;
	}

	/**
	 * Metoda koja vraća traženi znak prema imenu znaka.
	 * @param nameOfSymbol ime znaka koji se trazi.
	 * @return znak koji se trazi.
	 */
	public static String getSymbol(String nameOfSymbol) {
		switch (nameOfSymbol) {
		case "PROMPT":
			return prompt;
		case "MORELINES":
			return morelines;
		case "MULTILINE":
			return multilines;
		default:
			return "";
		}
	}
	
	public static boolean containsSymbol(String name) {
		if (name.equals("PROMPT") || name.equals("MULTILINE")
				|| name.equals("MORELINES")) {
			return true;
		}

		return false;
	}

}

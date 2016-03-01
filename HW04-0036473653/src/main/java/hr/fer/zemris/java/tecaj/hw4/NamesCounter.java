package hr.fer.zemris.java.tecaj.hw4;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Program koji traži unos imena dok se ne unese riječ quit.
 * Na zaslon ispisuje koliko je koje ime puta unešeno.
 * @author Petra Marče
 *
 */
public class NamesCounter {
	public static void main(String args[]) {
		Scanner scanInput = new Scanner(System.in);
		String ulaznipodatak;
		boolean ucitavanje = true;
		HashMap<String, Integer> names = new HashMap<>();
		while (ucitavanje) {
			ulaznipodatak = scanInput.nextLine().trim();
			if (obradiUlaz(ulaznipodatak, names) == false) {
				ucitavanje = false;
			}
		}
		scanInput.close();
		System.out.println(names);
	}
	/**
	 * Metoda koja obrađuje ulaz. Dodaje ime u Mapu.
	 * @param ulaz ulazni niz.
	 * @param map mapa u koju se dodaje ime.
	 * @return Vraća true ako unos nije gotov, false inače.
	 */
	public static boolean obradiUlaz(String ulaz, HashMap<String, Integer> map) {
		if (ulaz.compareTo("quit") == 0) {
			return false;
		}

		if (map.containsKey(ulaz)) {
			int broj = map.get(ulaz);
			broj++;
			map.put(ulaz, broj);
		} else {
			map.put(ulaz, 1);
		}

		return true;

	}
}

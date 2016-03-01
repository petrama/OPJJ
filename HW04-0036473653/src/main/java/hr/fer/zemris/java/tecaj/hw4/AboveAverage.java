package hr.fer.zemris.java.tecaj.hw4;

import java.util.Collections;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * Program koji traži unos brojeva dok se ne unese riječ quit.
 * Računa prosjek brojeva, i ispisuje one koji su bar 20% veći od prosjeka.
 * @author Petra Marče
 *
 */
public class AboveAverage {
	/**
	 * Metoda koja se poziva pri pokretanju programa.
	 * @param args argumenti iz komandne linije.
	 */

	public static void main(String args[]) {
		Scanner scanInput = new Scanner(System.in);
		String ulaznipodatak;
		boolean ucitavanje = true;
		List<Double> poljeVrijednosti = new ArrayList<>();
		while (ucitavanje) {
			ulaznipodatak = scanInput.nextLine().trim();
			if (obradiUlaz(ulaznipodatak, poljeVrijednosti) == false) {
				ucitavanje = false;
			}
		}
		scanInput.close();
		System.out.println("Unjeli ste:" + poljeVrijednosti);
		double prosjek = getAverage(poljeVrijednosti);
		double granica;
		if (prosjek > 0) {
			granica = prosjek * 1.2;
		} else {
			granica = prosjek * 0.8;
		}
		System.out.println(prosjek);
		Collections.sort(poljeVrijednosti);
		for (int i = 0; poljeVrijednosti.get(i) <= granica;) {

			poljeVrijednosti.remove(i);

		}

		System.out.println("Nova lista " + poljeVrijednosti);

	}
	/**
	 * Metoda koja obrađuje ulazni niz.
	 * Parsira ga i dodaje u listu brojeva.
	 * @param ulaz ulazni niz
	 * @param polje polje brojeva
	 * @return vraća true ako unos brojeva još nije završio, false inače
	 */

	public static boolean obradiUlaz(String ulaz, List<Double> polje) {
		if (ulaz.compareTo("quit") == 0) {
			return false;
		}
		try {
			double broj = Double.parseDouble(ulaz);
			polje.add(broj);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("Invalid argument");
		}

		return true;

	}
	/**
	 * Metoda koja vraća prosjek svih brojeva u zadanoj listi.
	 * @param polje ulazna lista
	 * @return izračunati prosjek.
	 */

	public static double getAverage(List<Double> polje) {
		double suma = 0;
		for (Double dub : polje) {
			suma += dub.floatValue();
		}
		return suma / polje.size();

	}

}

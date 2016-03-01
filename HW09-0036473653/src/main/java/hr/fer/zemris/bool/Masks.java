package hr.fer.zemris.bool;

import java.util.Arrays;
import java.util.List;

/**
 * Razred koji omogućuje višestruko stvaranje maski.
 * @author Petra Marče
 *
 */
public class Masks extends Mask {
	
	/**
	 * Konstruktor.
	 * Posao prosljeđuje nadređenom konstruktoru.
	 * 
	 * @param val
	 */
	private Masks(MaskValue[] val) {
		super(val);
	}

	/**
	 * Metoda koja vraća listu novih maski, stvorenih iz stringova.
	 * @param strings nizovi iz kojih treba stvoriti maske
	 * @return lista novih maski
	 */
	public static List<Mask> fromStrings(String... strings) {
		Mask[] arrayList = new Mask[strings.length];
		for (int i = 0; i < strings.length; i++) {
			arrayList[i] = parse(strings[i]);

		}
		return Arrays.asList(arrayList);
	}
	
	/**
	 * Metoda koja vraća listu novih maski, stvorenih iz indeksa, zadane duljine.
	 * @param indexes polje koje sadrži zadanu duljinu maski,potom brojeve koje maske trebaju prestavljati
	 * @return Lista novih maski
	 */

	public static List<Mask> fromIndexes(Integer... indexes) {
		Mask[] arrayList = new Mask[indexes.length - 1];
		for (int i = 0; i < indexes.length - 1; i++) {
			arrayList[i] = fromIndex(indexes[0], indexes[i+1] );

		}
		return Arrays.asList(arrayList);
	}
	
	

}

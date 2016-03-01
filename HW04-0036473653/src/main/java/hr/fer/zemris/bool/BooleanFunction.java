package hr.fer.zemris.bool;
/**
 * Sučelje koje služi za rad s Boolovim funkcijama.
 * @author petra
 *
 */

public interface BooleanFunction extends NamedBooleanSource {
	/**
	 * Metoda koja provjerava ima li funkcija zadani minterm.
	 * @param index minterm koji tražimo.
	 * @return vraća true ako fja sadrži minterm, false inače
	 */
	boolean hasMinterm(int index);
	
	/**
	 * Metoda koja provjerava ima li funkcija zadani maksterm.
	 * @param index maksterm koji tražimo.
	 * @return vraća true ako fja sadrži maksterm, false inače
	 */

	boolean hasMaxterm(int index);
	/**
	 * Metoda koja provjerava ima li funkcija zadani dont-care.
	 * @param index dont-care koji tražimo.
	 * @return vraća true ako fja sadrži dont-care, false inače
	 */
	boolean hasDontCare(int index);

	/**
	 * Metoda koja omogućava iteriranje svih minterma fje.
	 * @return metoda vraća poredanu listu minterma
	 */
	Iterable<Integer> mintermIterable();
	/**
	 * Metoda koja omogućava iteriranje svih maksterma fje.
	 * @return metoda vraća poredanu listu maksterma
	 */

	Iterable<Integer> maxtermIterable();
	/**
	 * Metoda koja omogućava iteriranje svih dontcareova fje.
	 * @return metoda vraća poredanu listu dontcareova
	 */

	Iterable<Integer> dontcareIterable();

}

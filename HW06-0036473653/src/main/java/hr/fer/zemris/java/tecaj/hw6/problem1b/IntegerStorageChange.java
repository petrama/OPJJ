package hr.fer.zemris.java.tecaj.hw6.problem1b;
/**
 * Razred koji predstavlja jednu promjenu stanja objekta koji se promatra.
 * Razred čuva referencu na objekt koji se promjenio, njegovu vrijednost tik prije promjene,
 * te njegovu novu vrijednost.
 * @author Petra Marče.
 *
 */
public class IntegerStorageChange {
	private IntegerStorage storage;
	private int valueBefore;
	private int valueNow;
	
	/**
	 * Konstruktor.
	 * @param object objekt kod kojeg se promjena dogodila.
	 * @param valueBefore vrijednost prije promjene.
	 * @param valueNow nocva vrijednost.
	 */
	public IntegerStorageChange(IntegerStorage object, int valueBefore,
			int valueNow) {
		super();
		this.storage = object;
		this.valueBefore = valueBefore;
		this.valueNow = valueNow;
	}

	/**
	 * Metoda koja sluzi za dohvat objekta koji se mjenja.
	 */
	public IntegerStorage getStorage() {
		return storage;
	}
	/**
	 * Metoda za dohvat prethodne vrijednosti.
	 * @return vraca vrijednost prije promjene.
	 */
	public int getValueBefore() {
		return valueBefore;
	}
	/**
	 * Metoda koja vraća truntu vrijednost objekta kojeg cuva.
	 * @return
	 */

	public int getValueNow() {
		return valueNow;
	}
	
	
	
	

}

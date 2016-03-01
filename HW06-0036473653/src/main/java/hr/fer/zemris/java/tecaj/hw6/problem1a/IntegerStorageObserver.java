package hr.fer.zemris.java.tecaj.hw6.problem1a;
/**
 * Sučelje koje predstavlja promatrača razreda IntegerStorage.
 * @author Petra Marče.
 *
 */
public interface IntegerStorageObserver {
	/**
	 * Metoda koja predstavlja akciju koju će promatrači poduzeti u slučaju promjene vrijednosti.
	 * @param istorage referenca na objekt čija se vrijednost promjenila.
	 */
	public void valueChanged(IntegerStorage istorage);
}

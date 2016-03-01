package hr.fer.zemris.java.tecaj.hw6.problem1b;

import java.util.ArrayList;
import java.util.List;
/**
 * Razred koji predstavlja promatrani objekt.
 * Ovaj razred čuva neku cjelobrojnu vrijednost.
 * U trenutku kad se ta vrijednost promjeni, on obavjestava svoje promatrace.
 * @author Petra Marče;
 *
 */
public class IntegerStorage {
	private int value;
	private List<IntegerStorageObserver> observers;
	
	/**
	 * Konstruktor.
	 * Postavlja se inicijalna vrijednost.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Metoda koja služi za dodavanje promatrača.
	 * @param observer novi promatrač koji se dodaje.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observers == null) {
			observers = new ArrayList<>();
			observers.add(observer);
			return;
		}
		if (observers.contains(observer)) {
			return;
		}
		observers.add(observer);
		return;
	}
	
	/**
	 * Metoda koja služi za uklanjanje promatrača.
	 * @param observer jedan od postojećih promatrača kojeg treba ukloniti.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observers.contains(observer) == false) {
			return;
		}
		observers.remove(observer);
		return;
	}
	
	/**
	 * Metoda koja uklanja sve promatrače.
	 */
	public void clearObservers() {
		observers.clear();
	}
	
	/**
	 * Metoda kojom se dohvaća trenutna čuvana vrijednost.
	 * @return trenutna vrijednost.
	 */
	public int getValue() {
		return value;
	}	
	
	/**
	 * Metoda za postavljanje nove vrijednosti.
	 * U trenutku postavljanja nove vrijednosti, ova metoda će obavijestiti promatrače ovog objekta.
	 * @param value nova vrijednost koju treba postaviti.
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			IntegerStorageChange change=new IntegerStorageChange(this,this.value , value);
			// Update current value
			this.value = value;
			// Notify all registered observers
			if (observers != null) {
				List<IntegerStorageObserver> nova=new ArrayList<>(observers);
				for (IntegerStorageObserver observer : nova) {
					observer.valueChanged(change);
				}
			}
		}
	}

}

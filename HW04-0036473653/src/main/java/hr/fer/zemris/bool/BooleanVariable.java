package hr.fer.zemris.bool;

import java.util.List;
import java.util.ArrayList;

/**
 * Razred koji predstavlja neku varijablu Boolove algebre.
 * @author Petra Marče
 *
 */

public class BooleanVariable implements NamedBooleanSource {
	BooleanValue value;
	String name;

	/**
	 * Konstruktor.
	 * Postavlja ime i vrijednost funkcije.
	 * Defaultna vrijednost je FALSE
	 * @param initName ime funkcije
	 */
	public BooleanVariable(String initName) {
		name = initName;
		value = BooleanValue.FALSE;
	}
	
	/**
	 * Metoda koja vraća vrijednost varijable.
	 */

	public BooleanValue getValue() {
		return value;
	}
	/**
	 * Metoda koja omogućuje postavljanje vrijednosti na zadanu vrijednost.
	 * @param value vrijednost na koju se varijabla postavlja.
	 */

	public void setValue(BooleanValue value) {
		this.value = value;
	}
	/**
	 * Metoda koja vraća ime varijable.
	 */

	public String getName() {
		return name;
	}
	/**
	 * Metoda koja vraća domenu varijable.
	 * Vraća listu čiji je jedini član sama varijabla.
	 */

	public List<BooleanVariable> getDomain() {
		List<BooleanVariable> lista = new ArrayList<>();
		lista.add(this);
		return lista;
	}

}

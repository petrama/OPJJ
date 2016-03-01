package hr.fer.zemris.bool;

import java.util.List;
import java.util.ArrayList;
/**
 * Razred koji nudi rad sa konstantama.
 * Sadrži dvije instance razreda TRUE i FALSE koje čuvaju te dvije vrijednosti.
 * @author Petra Marče
 *
 */
public class BooleanConstant implements BooleanSource {
	private BooleanValue value;
	public final static BooleanConstant TRUE = new BooleanConstant(BooleanValue.TRUE);
	public final static BooleanConstant FALSE = new BooleanConstant(BooleanValue.FALSE);

	/**
	 * Konstruktor. 
	 * @param initialValue vrijednost na koju postavljamo konstantu.
	 */
	private BooleanConstant(BooleanValue initialValue) {
		value = initialValue;

	}

	/**
	 * Metoda koja vraća vrijednost konstante.
	 */
	public BooleanValue getValue() {
		return value;
	}

	/**
	 * Metoda koja vraća domenu konstante.
	 * Domena su varijable o kojima vrijednost konstante ovisi.
	 * Domena je prazan skup.
	 */
	public List<BooleanVariable> getDomain() {
		return new ArrayList<>();
	}
}

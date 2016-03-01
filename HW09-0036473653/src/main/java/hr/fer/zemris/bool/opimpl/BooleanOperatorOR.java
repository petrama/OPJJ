package hr.fer.zemris.bool.opimpl;

import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.*;
import java.util.List;

/**
 * Razred koji predstavlja logičko ILI.
 * @author Petra Marče
 */
public class BooleanOperatorOR extends BooleanOperator {
	/**
	 * Konstruktor.
	 * @param lista ulaznih operanada.
	 */

	public BooleanOperatorOR(List<BooleanSource> list) {
		super(list);
	}

	/**
	 * Metoda koja vraća rezultat operacije ILI nad operandima.
	 */
	public BooleanValue getValue() {
		for (BooleanSource v : this.getSources()) {
			if (v.getValue() == BooleanValue.TRUE) {
				return BooleanValue.TRUE; //ako je bilo koja vrijednost true, to je true
			}
		}
		//ako nema vrijednosti true rezultat je ili dont care ili false
		for (BooleanSource v : this.getSources()) {
			if (v.getValue() == BooleanValue.DONT_CARE) {
				return BooleanValue.DONT_CARE; 
			}
		}
		return BooleanValue.FALSE;
	}

}

package hr.fer.zemris.bool.opimpl;

import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.*;
import java.util.List;

/**
 * Razred koji predstavlja logičko I.
 * @author Petra Marče
 *
 */
public class BooleanOperatorAND extends BooleanOperator {
	
	/**
	 * Konstruktor.
	 * Metoda koja prima listu operanada.
	 * @param list lista operanada
	 */
	public BooleanOperatorAND(List<BooleanSource> list){
		super(list);
	}
	
	/**
	 * Metoda koja vraća vrijednost koju je operator izračunao.
	 * Moguće vrijednosti su TRUE,FALSE i DONT-CARE
	 */
	public BooleanValue getValue() {

		for (BooleanSource v : this.getSources()) { //idem po listi
			if (v.getValue() == BooleanValue.FALSE) { //čim naiđem na vrijednost false, ukupan rezultat je false
				return BooleanValue.FALSE;
			}
		}
			//Ako sam to preživio idem gledat ima li dont-careova
		for (BooleanSource v : this.getSources()) {
			if (v.getValue() == BooleanValue.DONT_CARE) { //ako naiđem na dont-care, ukupan razultat je dont care
				return BooleanValue.DONT_CARE;
			}
		}
		
		//ako sam tu sve vrijednosti su true
		return BooleanValue.TRUE;
	}

}

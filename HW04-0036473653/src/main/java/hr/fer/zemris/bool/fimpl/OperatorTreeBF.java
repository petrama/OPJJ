package hr.fer.zemris.bool.fimpl;

import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.bool.*;

/**
 * Razred koji predstavlja funkciju zadanu logičkim izrazom.
 * @author Petra Marče
 *
 */

public class OperatorTreeBF implements BooleanFunction {
	String name;
	List<BooleanVariable> domain;
	BooleanOperator OperatorTree;

	public OperatorTreeBF(String name, List<BooleanVariable> domain,
			BooleanOperator OperatorTree) {
		this.name = new String(name);
		this.domain = new ArrayList<>(domain);
		this.OperatorTree = OperatorTree;

	}

	public String getName() {
		return name;
	}

	public List<BooleanVariable> getDomain() {
		return domain;
	}

	public BooleanValue getValue() {

		return OperatorTree.getValue();
	}

	/**
	 * Metoda koja postavlja varijable domene.
	 * Postavlja varijable na sve valjane kombinacije vrijednosti,od manje prema većoj.
	 * Provjerava vrijednost funkcije za tu vrijednost domene i gradi niz.
	 * Taj niz je zapis funkcije pomoću niza 1,0 i x-eva.
	 * @return niz koji prestavlja vrijednost funkcije.
	 */
	public String setAndCalculate() {
		String s = "";
		int n = (int) Math.pow(2, getDomain().size());

		for (int i = 0; i < n; i++) {
		 setFromIndex(i);

//			for (int j = 0; j < getDomain().size(); j++) {
//				if (numberAsMask.getValue(j) == MaskValue.ONE) {
//					getDomain().get(j).setValue(BooleanValue.TRUE);
//
//				} else if (numberAsMask.getValue(j) == MaskValue.ZERO) {
//					getDomain().get(j).setValue(BooleanValue.FALSE);
//
//				} else {
//					getDomain().get(j).setValue(BooleanValue.DONT_CARE);
//
//				}
//
//			}
			
			if (getValue() == BooleanValue.TRUE) {
				s += "1";
			} else if (getValue() == BooleanValue.FALSE) {
				s += "0";
			} else if (getValue() == BooleanValue.DONT_CARE) {
				s += "x";

			}

		}

		return s;
	}

	public Iterable<Integer> mintermIterable() {
		String functionValues = setAndCalculate();
		List<Integer> listOfMinterms = new ArrayList<>();
		for (int i = 0; i < functionValues.length(); i++) {
			if (functionValues.charAt(i) == '1') {
				listOfMinterms.add(i);
			}

		}
		return listOfMinterms;

	}

	public Iterable<Integer> maxtermIterable() {
		String functionValues = setAndCalculate();
		List<Integer> listOfMaxterms = new ArrayList<>();
		for (int i = 0; i < functionValues.length(); i++) {
			if (functionValues.charAt(i) == '0') {
				listOfMaxterms.add(i);
			}

		}
		return listOfMaxterms;

	}

	public Iterable<Integer> dontcareIterable() {
		String functionValues = setAndCalculate();
		List<Integer> listOfDonts = new ArrayList<>();
		for (int i = 0; i < functionValues.length(); i++) {
			if (functionValues.charAt(i) == 'x') {
				listOfDonts.add(i);
			}

		}
		return listOfDonts;

	}

	/**
	 * Metoda koja postavlja vrijednosti varijabli domene.
	 * @param index broj na koji domenu treba postaviti
	 */
	public void setFromIndex(int index) {
		Mask values = Mask.fromIndex(getDomain().size(), index);

		for (int i = 0; i < getDomain().size(); i++) {
			switch (values.getValue(i)) {
			case ONE:
				getDomain().get(i).setValue(BooleanValue.TRUE);
				break;
			case ZERO:
				getDomain().get(i).setValue(BooleanValue.FALSE);
				break;
			case DONT_CARE:
				getDomain().get(i).setValue(BooleanValue.FALSE);
				break;
			 default: break;
			}
		}

	}

	public boolean hasMinterm(int index) {
		setFromIndex(index);
		if (getValue() == BooleanValue.TRUE) {
			return true;
		}
		return false;
	}

	public boolean hasMaxterm(int index) {
		setFromIndex(index);
		if (getValue() == BooleanValue.FALSE) {
			return true;
		}
		return false;
	}

	public boolean hasDontCare(int index) {
		setFromIndex(index);
		if (getValue() == BooleanValue.DONT_CARE) {
			return true;
		}
		return false;
	}

}

package hr.fer.zemris.bool.fimpl;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.bool.BooleanConstant;
import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.MaskValue;
import hr.fer.zemris.bool.opimpl.BooleanOperators;

/**
 * Razred koji predtavlja Boolovu funkciju zadanu kao produkt suma ili sumu produkata.
 * @author Petra Marče
 *
 */

public class IndexedBF implements BooleanFunction {
	private String name;
	private List<BooleanVariable> domain;
	private boolean indexesAreMinterms;
	private List<Integer> indexes;
	private List<Integer> dontcares;
	
	/**
	 * Konstruktor.
	 * Ako je presjek listi minterma/maksterma i liste dontcareova,
	 * različit od praznog skupa funkcija ne može biti stvorena, baca se iznimka
	 * @param name pridruzuje ime funkciji
	 * @param domain zadaje domenu funkcije
	 * @param indexesAreMinterms zastavica koja je true ako zadajemo minterme,false inače
	 * @param indexes indeksi koji su mintermi ili makstermi ovisno o zastavici
	 * @param dontcares indeksi koji su dontcareovi.
	 * 
	 */

	public IndexedBF(String name, List<BooleanVariable> domain,
			boolean indexesAreMinterms, List<Integer> indexes,
			List<Integer> dontcares) {
		this.indexes = new ArrayList<>(indexes);
		this.dontcares = new ArrayList<>(dontcares);

		if (this.indexes.removeAll(this.dontcares)) {
			;
			throw new IllegalArgumentException(
					"Boolean function can not have two values at the same time!");

		}
		;
		this.name =name;
		this.domain = new ArrayList<>(domain);
		this.indexesAreMinterms = Boolean.valueOf(indexesAreMinterms);
		
		

	}
	

	public String getName() {
		return name;
	}
	

	public List<BooleanVariable> getDomain() {
		return domain;
	}
	/**
	 * Metoda koja vraća funkciju u algebarskom obliku.
	 * @param listOfMinterms mintermi koje funkcija ima
	 * @return vraća instancu razreda BooleanOperator koji predstavlja algebarski oblik funkcije.
	 */

	public BooleanOperator algebarskiOblikFunkcije(List<Integer> listOfMinterms) {
		BooleanOperator funkcijaSumaMinterma = BooleanOperators
				.or(BooleanConstant.FALSE);
		for (Integer minterm : listOfMinterms) {
			BooleanOperator mintermProdukt = BooleanOperators
					.and(BooleanConstant.TRUE);
			Mask mintermAsMask = Mask.fromIndex(getDomain().size(), minterm);
			for (int i = 0; i < getDomain().size(); i++) {
				if (mintermAsMask.getValue(i) == MaskValue.ONE) {
					mintermProdukt = BooleanOperators.and(mintermProdukt,
							getDomain().get(i));

				} else {
					mintermProdukt = BooleanOperators.and(mintermProdukt,
							BooleanOperators.not(getDomain().get(i)));

				}
			}// stvoren je jedan produkt
			funkcijaSumaMinterma = BooleanOperators.or(funkcijaSumaMinterma,
					mintermProdukt);
		}
		return funkcijaSumaMinterma;
	}

	
	public BooleanValue getValue() {
		boolean imaDontCareova = false;

		for (BooleanVariable varijabla : getDomain()) { 
			if (varijabla.getValue() == BooleanValue.DONT_CARE) {
				imaDontCareova = true;
				break;
			}
		}
		if (imaDontCareova == true) {
			BooleanOperator funkcija = algebarskiOblikFunkcije(mintermIterable());
			return funkcija.getValue();

		} else {
			Integer domainValueBinary = 0;

			for (int i = 0; i < domain.size(); i++) {
				if (domain.get(i).getValue() == BooleanValue.TRUE) {
					domainValueBinary += (int) Math.pow(2, domain.size() - 1
							- i);

				}

			}

			if (hasMinterm(domainValueBinary)) {
				return BooleanValue.TRUE;
			}
			if (hasMaxterm(domainValueBinary)) {
				return BooleanValue.FALSE;
			}

			return BooleanValue.DONT_CARE;
		}
	}

	/**
	 * Metoda koja provjerava da li je indeks valjan.
	 * @param index indeks koji se provjerava
	 * @return vraća true ako je indeks valjan, false inače
	 */

	public boolean isValidIndex(int index) {
		if (index < 0 || index >= Math.pow(2, getDomain().size())) {
			return false;
		}
		return true;
	}
	
	
	
	public boolean hasMinterm(int index) {
		if (indexesAreMinterms == true) {
			return indexes.contains(index);
		} else {
			if (isValidIndex(index) == false) {
				return false;
			} else {
				return !(hasMaxterm(index) || hasDontCare(index));
			}
		}
	}
	

	public boolean hasDontCare(int index) {

		if (dontcares.contains(index) == true) {
			return true;

		}
		return false;
	}
	
	public boolean hasMaxterm(int index) {
		if (indexesAreMinterms == false) {
			return indexes.contains(index);
		} else {
			if (isValidIndex(index) == false) {
				return false;
			} else {
				return !(hasMinterm(index) || hasDontCare(index));
			}
		}
	}
	
	/**
	 * Metoda koja vraća listu indeksa.
	 * Indeksi  su u domeni valjanih kombinacija, a nisu u listi
	 * članske varijable indexes i nisu u listi dontcareova.
	 * Metoda vraća "one treće".
	 * @return Metoda vraća listu indeksa
	 */

	public List<Integer> getThirdList() {
		int n = (int) (Math.pow(2, getDomain().size()));
		List<Integer> thirdList = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			thirdList.add(i);
		}

		thirdList.removeAll(dontcares);
		thirdList.removeAll(indexes);
		return thirdList;

	}
	
	

	public List<Integer> mintermIterable() {
		if (indexesAreMinterms == true) {
			return indexes;
		}
		return getThirdList();

	}
	
	public Iterable<Integer> maxtermIterable() {
		if (indexesAreMinterms == false) {
			return indexes;
		}
		return getThirdList();

	}
	

	public Iterable<Integer> dontcareIterable() {
		return dontcares;
	}
	
	public List<Integer> getMinterms(){
		if (indexesAreMinterms == true) {
			return indexes;
		}
		return getThirdList();
	}
	
	public List<Integer> getMaxterms() {
		if (indexesAreMinterms == false) {
			return indexes;
		}
		return getThirdList();

	}


	@Override
	public List<Integer> getDonts() {
		return dontcares;
	}
}

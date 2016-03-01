package hr.fer.zemris.bool.fimpl;

import hr.fer.zemris.bool.*;

import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.opimpl.BooleanOperators;

/**
 * Razred koji omogućava zadavanje Boolove funkcije preko maski.
 * @author Petra Marče
 *
 */

public class MaskBasedBF implements BooleanFunction {
	private String name;
	private List<BooleanVariable> domain;
	private boolean masksAreMinterms;
	private List<Mask> masks;
	private List<Mask> dontCareMasks;
	
		
	/**
	 * Konstruktor.
	 * @param name ime funkcije.
	 * @param domain -domena funkcije-
	 * @param masksAreMinterms- zastavica koja je true ako ulazne masks predstavljaju minterme, false inače 
	 * @param masks -lista maski koje prestavljaju minterme ili maksterme ovisno o zastavici masksAreMinterms
	 * @param dontCareMasks lista maski koje prestavljaju dont-careove.
	 */
	public MaskBasedBF(String name, List<BooleanVariable> domain,
			boolean masksAreMinterms, List<Mask> masks, List<Mask> dontCareMasks) {
		this.name = new String(name);
		this.domain = new ArrayList<>(domain);
		this.masksAreMinterms = new Boolean(masksAreMinterms);
		this.dontCareMasks = new ArrayList<>(dontCareMasks);
		this.masks = new ArrayList<>(masks);
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

		for (BooleanVariable varijabla : getDomain()) { // provjerava kako su
														// zadane vrijednosti
														// varijabli
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

	public Iterable<Integer> dontcareIterable() {
		int n = (int) (Math.pow(2, getDomain().size()));
		List<Integer> listOfDonts = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			if (hasDontCare(i)) {
				listOfDonts.add(i);
			}
		}
		return listOfDonts;
	}

	public List<Integer> mintermIterable() {
		int n = (int) (Math.pow(2, getDomain().size()));
		List<Integer> listOfMinterms = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			if (hasMinterm(i)) {
				listOfMinterms.add(i);
			}
		}
		return listOfMinterms;
	}

	public Iterable<Integer> maxtermIterable() {
		int n = (int) (Math.pow(2, getDomain().size()));
		List<Integer> listOfMaxterms = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			if (hasMaxterm(i)) {
				listOfMaxterms.add(i);
			}
		}
		return listOfMaxterms;
	}
	


	/**
	 * Metoda koja provjerava da li je indeks valjan.
	 * @param index indeks koji se ispituje.
	 * @return vraća true ako se indeks nalazi u domeni funkcije,false inače.
	 */
	public boolean isValidIndex(int index) {
		if (index < 0 || index >= Math.pow(2, getDomain().size())) {
			return false;
		}
		return true;
	}

	
	public boolean hasMinterm(int index) {

		if (masksAreMinterms) {
			return hasListIndex(masks, index);

		} else {
			if (isValidIndex(index) == false)
				return false;
			return !(hasMaxterm(index) || hasDontCare(index));
		}
	}

	public boolean hasMaxterm(int index) {
		if (masksAreMinterms == false) {
			return hasListIndex(masks, index);

		} else {
			if (isValidIndex(index) == false)
				return false;
			return !(hasMinterm(index) || hasDontCare(index));
		}
	}
	
	public boolean hasDontCare(int index) {
		return hasListIndex(dontCareMasks, index);
	}
	
	/**
	 * Metoda koja provjerava sadrži li zadana lista maski masku koja predstavlja određen indeks.
	 * @param listOfMasks lista u kojoj tražimo indeks.
	 * @param index indeks koji se traži
	 * @return vraća true ako je indeks u listi, false inače
	 */

	public boolean hasListIndex(List<Mask> listOfMasks, int index) {
		Mask maskaIzIndexa = Mask.fromIndex(getDomain().size(), index);
		for (Mask m : listOfMasks) {
			if (m.isMoreGeneralThan(maskaIzIndexa) || m.equals(maskaIzIndexa)) {
				return true;
			}
		}
		return false;

	}

	

}

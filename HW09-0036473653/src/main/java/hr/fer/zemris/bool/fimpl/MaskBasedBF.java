package hr.fer.zemris.bool.fimpl;


import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Mask;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.bool.BooleanFunction;

/**
 * Razred koji omogućava zadavanje Boolove funkcije preko maski.
 * @author Petra Marče
 *
 */

public class MaskBasedBF implements BooleanFunction {
	
	private IndexedBF indexed;
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
		this.masksAreMinterms =  Boolean.valueOf(masksAreMinterms);
		this.dontCareMasks = new ArrayList<>(dontCareMasks);
		this.masks = new ArrayList<>(masks);
		this.indexed=new IndexedBF(name, domain, masksAreMinterms, allIndexes(masks,domain.size()), allIndexes(dontCareMasks,domain.size()));
	}
	
	/**
	 * Metoda koja vraća maske koje predtsavljaju minterme/maksterme funkcije.
	 * Ako je zastavica masksAreMinterms true onda su to mintermi, inače su makstermi.
	 * @return
	 */
	public List<Mask> getMasks() {
		return masks;
	}
	
	/**
	 * Metoda koja vraća maske koje su mintermi ove funkcije.
	 * @return lista maski minterma.
	 */
	public List<Mask> getMintermsMask(){
		if(masksAreMinterms){
			return getMasks();
		}
		return getThirdList();
	}
	/**
	 * Metoda koja vraća maske koje su makstermi ove funkcije.
	 * @return lista maski maksterma.
	 */
	public List<Mask> getMaxtermsMask(){
		if(masksAreMinterms==false){
			return getMasks();
		}
		return getThirdList();
	}
	
	/**
	 * Metoda koja vraća listu svih maski iz domene funkcije,
	 * bez maski <code>masks</code> i bez maski <code>dontCareMasks</code>.
	 * @return vraća treću listu.
	 */
	public List<Mask> getThirdList(){
		List<Mask> treca=new ArrayList<>();
		for(int i=0;i<Math.pow(2, getDomain().size());i++){
			treca.add(Mask.fromIndex(getDomain().size(), i));
		}
		treca.removeAll(getMasks());
		treca.removeAll(getDontCareMasks());
		return treca;
	}

	/**
	 * Metoda koja vraća maske koje su dont-care vrijednosti ove funkcije.
	 * @return lista maski dont-careova.
	 */
	public List<Mask> getDontCareMasks() {
		return dontCareMasks;
	}
	
	/**
	 * Metoda koja oznacava da li su  <code>masks</code> mintermi ili makstermi.
	 * @return true ako su maske makstermi.
	 */
	public boolean areMasksProducts(){
		return !masksAreMinterms;
	}
	
	
	@Override
	public List<Integer> getDonts(){
		return indexed.getDonts();
	}

	/**
	 * Pomoćna metoda koja vraća listu indeksa koju predana lista maski pokriva.
	 * @param masks predana lista maski.
	 * @param domainSize broj varijabli funkcije.
	 * @return lista indeksa.
	 */
	private List<Integer> allIndexes(List<Mask> masks,int domainSize){
		List<Integer> in=new ArrayList<Integer>();
		for(int i=0;i<Math.pow(2,domainSize);i++){
			if (hasListIndex(masks, i,domainSize)){
				in.add(i);
			}
		}
		return in;
	}
	
	/**
	 * Metoda koja provjerava sadrži li zadana lista maski masku koja predstavlja određen indeks.
	 * @param listOfMasks lista u kojoj tražimo indeks.
	 * @param index indeks koji se traži
	 * @return vraća true ako je indeks u listi, false inače
	 */

	public boolean hasListIndex(List<Mask> listOfMasks, int index,int domainSize) {
		Mask maskaIzIndexa = Mask.fromIndex(domainSize, index);
		for (Mask m : listOfMasks) {
			if (m.isMoreGeneralThan(maskaIzIndexa) || m.equals(maskaIzIndexa)) {
				return true;
			}
		}
		return false;

	}
	
	@Override
	public String getName() {
		return indexed.getName();
	}
	
	

	@Override
	public List<BooleanVariable> getDomain() {
		return indexed.getDomain();
	}

	
	/**
	 * Metoda koja vraća funkciju u algebarskom obliku.
	 * @param listOfMinterms mintermi koje funkcija ima
	 * @return vraća instancu razreda BooleanOperator koji predstavlja algebarski oblik funkcije.
	 */
	public BooleanOperator algebarskiOblikFunkcije(List<Integer> listOfMinterms) {
		return indexed.algebarskiOblikFunkcije(indexed.mintermIterable());
	}
	

	@Override
	public BooleanValue getValue() {
		return indexed.getValue();
				}

			
	@Override
	public Iterable<Integer> dontcareIterable() {
		return indexed.dontcareIterable();
	}

	@Override
	public List<Integer> mintermIterable() {
		return indexed.mintermIterable();
	}

	@Override
	public Iterable<Integer> maxtermIterable() {
		return indexed.maxtermIterable();
	}
	
	
	@Override
	public boolean hasMinterm(int index) {

		return indexed.hasMinterm(index);
	}

	@Override
	public boolean hasMaxterm(int index) {
		return indexed.hasMaxterm(index);
	}
	
	@Override
	public boolean hasDontCare(int index) {
		return indexed.hasDontCare(index);
	}


	@Override
	public List<Integer> getMinterms() {
		return indexed.getMinterms();
	}


	@Override
	public List<Integer> getMaxterms() {
		return indexed.getMaxterms();
	}
	
	

	

}



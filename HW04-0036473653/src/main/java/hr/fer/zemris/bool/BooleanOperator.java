package hr.fer.zemris.bool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Apstraktan razred koji predstavlja Boolove operatore.
 * @author Petra Marče
 *
 */
public abstract class BooleanOperator implements BooleanSource {
	private List<BooleanSource> sources;

	/**
	 * Konstruktor.
	 * Metoda koja kopira listu operanada i pridružuje je privatnoj listi.
	 * @param list lista operanada, ako je ulazna lista prazna baca iznimku
	 */
	protected BooleanOperator(List<BooleanSource> list) {
		if (list.isEmpty()) {
			throw new IllegalArgumentException("No arguments provided!");
		}
		sources = new ArrayList<>(list);

	}

	/**
	 * Metoda vraća listu operanada ovog operatora.
	 * @return lista operanada
	 */
	public List<BooleanSource> getSources() {
		return sources;
	}
	
	/**
	 * Metoda koja vraća domenu ovog operanda.
	 * Vraća listu svih objekata algebre o kojima krajnji rezultat ovisi.
	 */

	public List<BooleanVariable> getDomain(){
		HashSet<BooleanVariable> set=new HashSet<>();
		for(BooleanSource s:this.getSources()){
			set.addAll(s.getDomain());
		}
		return new ArrayList<>(set);
	}
	
	




}




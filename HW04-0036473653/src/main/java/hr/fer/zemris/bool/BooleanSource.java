package hr.fer.zemris.bool;

import java.util.List;

/**
 * Sučelje koje predstavlja bilo kakav objekt koji može imati logičku vrijednost.
 * @author Petra Marče
 *
 */

public interface BooleanSource {
	
	/**
	 * Metoda koja vraća vrijednost objekta.
	 * @return vraća logičku vrijednost.
	 */
	BooleanValue getValue();
	
	/**
	 * Metoda koja vraća domenu objekta.
	 * Domena je skup objekata na temelju čijih vrijednosti metoda getValue() računa svoju vrijednost.
	 * @return vraća listu koja predstavlja domenu.
	 */
	List<BooleanVariable> getDomain();

}

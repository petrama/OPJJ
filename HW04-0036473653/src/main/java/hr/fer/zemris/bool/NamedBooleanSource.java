package hr.fer.zemris.bool;
/**
 * Sučelje koje predstavlja objekt boolove algebre.
 * Taj objekt ima svoju vrijednost, domenu i ime.
 * Nasljeđuje razred BooleanSource
 * 
 * @author Petra Marče
 *
 */

public interface NamedBooleanSource extends BooleanSource {
	
	/**
	 * Metoda koja vraća ime objekta boolove algebre.
	 * @return ime objekta.
	 */
	String getName();

}

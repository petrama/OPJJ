package hr.fer.zemris.web.radionice.iznimke;


/**
 * Razred predstavlja pogrešku koja govori da su podatci  bazi nekonzistentni.
 * Točnije neka od radionica u bazi ima nepoznat property koji u ovoj bazi nije dostupan.
 * @author Petra Marče
 *
 */
@SuppressWarnings("serial")
public class InconsistentDatabaseException extends RuntimeException {

	/**
	 * Konstruktor iznimkee koji prima opis greske.
	 * @param message poruka o pogrešci.
	 */
	public InconsistentDatabaseException(String message) {
		super(message);
	}
}

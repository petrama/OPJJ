package hr.fer.zemris.java.filechecking.fmagician.executors;

import hr.fer.zemris.java.filechecking.fmagician.FMagicianException;
/**
 * Iznimka vezana uz izvođenje programa
 * pisanog jezikom <i>fmagician</i>.
 * 
 * @author Petra Marče
 */
public class FMagicianExecutionException extends FMagicianException {
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public FMagicianExecutionException() {
	}

	/**
	 * Konstruktor.
	 * @param message opis pogreške
	 */
	public FMagicianExecutionException(String message) {
		super(message);
	}
}

package hr.fer.zemris.java.filechecking.fmagician.syntax;

import hr.fer.zemris.java.filechecking.fmagician.FMagicianException;

/**
 * Iznimka koja opisuje pogreške nastale pri sintaksnoj analizi
 * programa.
 * 
 * @author Petra Marče
 */
public class FMagicianSyntaxException extends FMagicianException {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public FMagicianSyntaxException() {
	}

	/**
	 * Konstruktor.
	 * @param message opis pogreške
	 */
	public FMagicianSyntaxException(String message) {
		super(message);
	}

}

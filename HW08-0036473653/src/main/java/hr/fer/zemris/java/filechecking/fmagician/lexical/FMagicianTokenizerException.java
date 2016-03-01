package hr.fer.zemris.java.filechecking.fmagician.lexical;

import hr.fer.zemris.java.filechecking.fmagician.FMagicianException;


/**
 * Iznimka koja opisuje pogreške pri tokenizaciji programa.
 * 
 * @author Petra Marče
 */
public class FMagicianTokenizerException extends FMagicianException {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public FMagicianTokenizerException() {
	}

	/**
	 * Konstruktor.
	 * @param message opis pogreške
	 */
	public FMagicianTokenizerException(String message) {
		super(message);
	}


}
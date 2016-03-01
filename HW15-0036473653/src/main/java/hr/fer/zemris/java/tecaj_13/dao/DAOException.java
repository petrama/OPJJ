package hr.fer.zemris.java.tecaj_13.dao;

/**
 * Razred koji predstavlja iznimku do koje može doći u toku rada s bazom.
 * @author Petra Marče
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DAOException() {
	}

	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}
}
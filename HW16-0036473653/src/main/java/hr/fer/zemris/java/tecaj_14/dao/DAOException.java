package hr.fer.zemris.java.tecaj_14.dao;

/**
 * Iznimka koja se događa u komunikaciji s bazom.
 * @author Petra Marče
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}
}
package hr.fer.zemris.java.custom.collections;

/**
 * Razred koji predstavlja pogrešku koja može nastati u radu sa stogom
 * 
 * @author Petra Marče
 * 
 */

public class EmptyStackException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmptyStackException(String message) {
		super(message);
	}

}

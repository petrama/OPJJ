package hr.fer.zemris.java.filechecking.fmagician;


	/**
	 * Najopćenitija iznimka vezana uz prevođenje i izvođenje programa
	 * pisanog jezikom <i>fmagician</i>.
	 * 
	 * @author Petra Marče
	 */
	public class FMagicianException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		/**
		 * Konstruktor.
		 */
		public FMagicianException() {
		}

		/**
		 * Konstruktor.
		 * @param message opis pogreške
		 */
		public FMagicianException(String message) {
			super(message);
		}
}

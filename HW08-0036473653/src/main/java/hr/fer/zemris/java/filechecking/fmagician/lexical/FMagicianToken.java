package hr.fer.zemris.java.filechecking.fmagician.lexical;
/**
 * Razred modelira jedan token ulaznog programa.
 * 
 * @author Petra Marče
 */

public class FMagicianToken {
	/**
	 * Vrsta tokena.
	 */
	private FMagicianTokenType tokenType;
	/**
	 * Vrijednost tokena.
	 */
	private Object value;
	
	/**
	 * Konstruktor.
	 * @param tokenType vrsta tokena
	 * @param value vrijednost tokena
	 */
	public FMagicianToken(FMagicianTokenType tokenType, Object value) {
		super();
		if (tokenType == null)
			throw new IllegalArgumentException("Token type can not be null.");
		this.tokenType = tokenType;
		this.value = value;
	}
	
	/**
	 * Dohvat vrste tokena.
	 * @return vrsta tokena
	 */
	public FMagicianTokenType getTokenType() {
		return tokenType;
	}
	
	/**
	 * Dohvat vrijednosti tokena.
	 * @return vrijednost tokena ili <code>null</code> ako token ove vrste nema pridruženu vrijednost
	 */
	public Object getValue() {
		return value;
	}
	
	public String toString(){
		return value.toString();
	}
}

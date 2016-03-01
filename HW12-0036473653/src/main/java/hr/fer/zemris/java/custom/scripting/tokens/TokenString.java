package hr.fer.zemris.java.custom.scripting.tokens;
/**
 * Razred koji prestavlja neki znakovni niz
 * @author Petra Marče
 *
 */

public class TokenString extends Token {
	String value;

	public TokenString(String argValue) {
		super();
		value = argValue;
	}

	public String getValue() {
		return value;
	}


	@Override
	public String asText(){
		return "\""+value+"\"";
	}



}

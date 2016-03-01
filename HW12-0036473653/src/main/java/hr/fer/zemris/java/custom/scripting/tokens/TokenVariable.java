package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Razred koji prestavlja neku varijablu
 * 
 * @author Petra Marče
 * 
 */

public class TokenVariable extends Token {
	String name;

	public TokenVariable(String argName) {
		super();
		name = argName;

	}

	public String getName() {
		return name;
	}

	@Override
	public String asText() {
		return name;

	}

}

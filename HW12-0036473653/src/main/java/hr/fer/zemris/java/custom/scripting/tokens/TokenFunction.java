package hr.fer.zemris.java.custom.scripting.tokens;
/**
 * Razred koji prestavlja neku funkciju
 * @author Petra Marče
 *
 */

public class TokenFunction extends Token {
	String name;

	public TokenFunction(String varName){
		super();
		name=varName;
	}


	public String getName() {
		return name;
	}


	@Override
	public String asText()
	{
		return "@"+name;
	}
}

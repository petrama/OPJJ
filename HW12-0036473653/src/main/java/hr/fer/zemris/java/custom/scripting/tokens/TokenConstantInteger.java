package hr.fer.zemris.java.custom.scripting.tokens;
/**
 * Razred koji prestavlja neku cjelobrojnu konstantu
 * @author Petra Marče
 *
 */
public class TokenConstantInteger extends Token {
	int value;



	public TokenConstantInteger(int argValue){
		super();
		value=argValue;
	}



	public int getValue() {
		return value;
	}




	@Override
	public String asText(){
		return Integer.toString(value);
	}


}
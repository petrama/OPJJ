package hr.fer.zemris.java.custom.scripting.tokens;
/**
 * Razred koji prestavlja neki operator
 * @author Petra Marče
 *
 */

public class TokenOperator extends Token {
	String symbol;

	public TokenOperator(String varSim){
		super();
		symbol=varSim;
	}


	public String getSymbol() {
		return symbol;
	}


	@Override
	public String asText(){
		return symbol;
	}
}

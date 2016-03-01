package hr.fer.zemris.java.custom.scripting.tokens;
/**
 * Razred koji prestavlja neku realnu konstantu
 * @author Petra Marče
 *
 */

public class TokenConstantDouble extends Token {
	double value;

	public TokenConstantDouble(double argValue) {
		super();
		value = argValue;
	}

	public Double getValue() {
		return value;
	}

	
		@Override
		public String asText(){
			return Double.toString(this.value);
		}


	}



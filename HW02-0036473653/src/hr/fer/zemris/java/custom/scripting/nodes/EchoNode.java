package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.*;

/**
 * Razred koji pretsavlja tijelo =-taga
 * 
 * @author Petra Marče
 * 
 */

public class EchoNode extends Node {
	public Token[] tokens;

	public EchoNode(Token[] polje) {

		super();

		tokens = polje;

	}

	public Token[] getTokens() {
		return tokens;
	}




}

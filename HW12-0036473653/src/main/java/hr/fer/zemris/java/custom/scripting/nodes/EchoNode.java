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

	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}

	/**
	 * Metoda koja vraća reprezentaciju klase kao string - ispisuje sve Tokene.
	 * 
	 * @return String reprezentacija klase.
	 */

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{$= ");
		
		for (int i = 0; i < tokens.length; i++){
			
			sb.append(tokens[i].asText() + " ");
		}
		sb.append("$}");
		
		return sb.toString();
	}



}

package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.*;
/**
 * Razred koji predstavlja tijelo for petlje
 * @author Petra Marče
 *
 */
public class ForLoopNode extends Node {

	public TokenVariable variable;
	public Token startExpression;
	public Token endExpression;
	public Token stepExpression;

	/**
	 * Konstruktor koji prima ime varijable, početnu i završnu vrijednost.
	 * Pretpostavljena step vrijednost je null.
	 * 
	 * @param variable
	 *            TokenVariable koja predstavlja ime varijable.
	 * @param startExpression
	 *            Token koji predstavlja početnu vrijednost petlje.
	 * @param endExpression
	 *            Token koji predstavlja završnu vrijednost petlje.
	 */

	public ForLoopNode(TokenVariable variable, Token startExpression,
			Token endExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = null;
	}

	/**
	 * Konstruktor koji prima ime varijable, početnu, završnu te step vrijednost
	 * petlje.
	 * 
	 * @param variable
	 *            TokenVariable koja predstavlja ime varijable.
	 * @param startExpression
	 *            Token koji predstavlja početnu vrijednost petlje.
	 * @param endExpression
	 *            Token koji predstavlja završnu vrijednost petlje.
	 * @param stepExpression
	 *            Token koji predstavlja vrijednost skoka pelje.
	 */
	public ForLoopNode(TokenVariable variable, Token startExpression,
			Token endExpression, Token stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}








	public TokenVariable getVariable() {
		return variable;
	}
	public Token getStartExpression() {
		return startExpression;
	}
	public Token getEndExpression() {
		return endExpression;
	}
	public Token getStepExpression() {

		return stepExpression;
	}



	/**
	 * 
	 * Metoda koja vraća reprezentaciju klase kao string - ispisuje sve
	 * argumente FOR petlje i sve naredbe unutar FOR petlje.
	 * 
	 * @return String reprezentacija FOR petlje i svih naredbi unutar petlje.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{$FOR " + variable.asText() + " " + startExpression.asText()
				+ " " + endExpression.asText() + " ");
		
		if (stepExpression != null){
			sb.append(stepExpression.asText() + " ");
		}

		sb.append("$}");
		
		for (int i = 0; i < this.numberOfChildren(); i++) {
			sb.append(this.getChild(i).toString());
		}


		sb.append("{$END$}");
		
		
		
		return sb.toString();
	}
}


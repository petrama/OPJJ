package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.*;
/**
 * Razred koji predstavlja tijelo for petlje
 * @author Petra Marče
 *
 */
public class ForLoopNode extends Node {

	public TokenVariable variable;
	public TokenConstantInteger startExpression;
	public TokenConstantInteger endExpression;
	public TokenConstantInteger stepExpression;

	/**
	 * Konstruktor za stvaranje nove instance razreda
	 * @param t1 predstavlja varijablu 
	 * @param t2 predstavlja početnu vrijednost brojaca
	 * @param t3 predstavlja krajnju vrijednost brojaca 
	 * @param t4 predstavlja korak
	 */


	public ForLoopNode(TokenVariable t1,TokenConstantInteger t2,TokenConstantInteger t3,TokenConstantInteger t4){
		super();
		variable=t1;
		startExpression=t2;
		endExpression=t3;
		stepExpression=t4;
	}

	/**
	 * Konstruktor za stvaranje nove instance razreda
	 * @param t1 predstavlja varijablu 
	 * @param t2 predstavlja početnu vrijednost brojaca
	 * @param t3 predstavlja krajnju vrijednost brojaca 
	 */


	public ForLoopNode(TokenVariable t1,TokenConstantInteger t2,TokenConstantInteger t3){
		super();
		variable=t1;
		startExpression=t2;
		endExpression=t3;
		stepExpression=null;
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




}


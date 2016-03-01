package hr.fer.zemris.java.filechecking.fmagician.syntax.nodes;

import java.util.List;
/**
 * Pomoćni razred koji predstavlja putanju jezika <i>fmagician</i>
 * Putanjom se smatra skup tokena unutar navodnika tipa STRING,PACKAGE ili SUPSTITUTION.
 * @author Petra Marče
 *
 */

public class PathNode extends TextNode{
	
	/**
	 * Konstrukotr.
	 * @param caseInsensitive oznaka nerazlikovanja velikih i malih slova.
	 * @param args lista ulaznih tokena.
	 */
	public PathNode(boolean caseInsensitive,List<String> args) {
		super( caseInsensitive, args);
	
	}
	
	

	
}

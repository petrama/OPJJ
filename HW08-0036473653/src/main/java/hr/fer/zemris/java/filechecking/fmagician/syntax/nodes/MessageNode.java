package hr.fer.zemris.java.filechecking.fmagician.syntax.nodes;

import java.util.List;
/**
 * Pomoćni razred koji predstavlja poruku jezika <i>fmagician</i>
 * Porukom se smatra skup tokena koji se nalaze unutar znaka navodnika.
 * Dozvoljeni tokeni su tipa SUPSTITUTION i STRING.
 * @author Petra Marle
 *
 */
public class MessageNode extends TextNode {
	
	/**
	 * Konstrukotr.
	 * @param caseInsensitive oznaka nerazlikovanja velikih i malih slova.
	 * @param args lista ulaznih tokena.
	 */
	public MessageNode(boolean caseInsensitive,List<String>args) {
		super(caseInsensitive,args);
	}
	
	
	
	
	
	
}

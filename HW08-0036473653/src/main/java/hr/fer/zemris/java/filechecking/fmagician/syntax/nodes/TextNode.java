package hr.fer.zemris.java.filechecking.fmagician.syntax.nodes;

import java.util.List;
/**
 * Razred koji predstavlja tekstualni čvor jezika <i>fmagician</i>.
 * @author Petra Marče
 *
 */
public class TextNode {
	/** lista stringova od kojih se cvor ststoji **/
	private List<String> arguments;
	/** oznaka za neosjetljivost na velika i mala slova **/
	private boolean caseInsensitive;

	public TextNode(boolean caseInsensitive,List<String> args) {
		arguments = args;
		this.caseInsensitive=caseInsensitive;

	}
	/**
	 * Dohvat liste argumenata čvora.
	 * @return lista stringova koji čine tekst-node.
	 */
	public List<String> getArguments() {
		return arguments;
	}

	/**
	 * Dohvat zastavice caseInsensitive.
	 * @return vraća true ako je čvor neosjetljiv na velika i mala slova, false inače
	 */
	public boolean isCaseInsensitive() {
		return caseInsensitive;
	}
	

}

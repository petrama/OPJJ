package hr.fer.zemris.java.filechecking.fmagician.syntax.nodes;

import hr.fer.zemris.java.filechecking.fmagician.syntax.FMagicianNodeVisitor;

/**
 * Razred koji predstavlja instrukciju terminate jezika <i>fmagician</i>.
 * Nakon izvođenja ove instrukcije, izvođenje staje.
 * @author Petra Marče.
 *
 */
public class TerminateStatement extends FMagicianNode {
	public void accept(FMagicianNodeVisitor visitor){
		visitor.visit(this);
	}
	public MessageNode getMessage() {
		return null;
	}
}

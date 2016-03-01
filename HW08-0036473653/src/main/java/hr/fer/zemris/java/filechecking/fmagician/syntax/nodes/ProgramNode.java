package hr.fer.zemris.java.filechecking.fmagician.syntax.nodes;

import hr.fer.zemris.java.filechecking.fmagician.syntax.FMagicianNodeVisitor;
/**
 * Model stabla naredbi programa napisanog jezikom 
 * <i>fmagician</i>.
 * 
 * @author Petra Marče
 */
public class ProgramNode extends FMagicianNode {

	public void accept(FMagicianNodeVisitor visitor){
		visitor.visit(this);
	}
	
	public MessageNode getMessage() {
		return null;
	}
}

package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Razred koji predstavlja tijelo dokumenta
 * 
 * @author Petra Marče
 * 
 */

public class DocumentNode extends Node {


	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}

}

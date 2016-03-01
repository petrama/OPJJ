package hr.fer.zemris.java.custom.scripting.nodes;
/**
 * Razred koji predstavlja neki tekst
 * @author Petra Marče
 *
 */

public class TextNode extends Node {
	public String text;
	
	
	public TextNode(String text){//*konstruktor prima string
		super();
		this.text=text;
	}

	public String getText() {
		return text;
	}
	
	public void accept(INodeVisitor visitor){
		visitor.visitTextNode(this);
	}
	
	/**
	 * Metoda koja vraća tekstualnu reprezentaciju objekta - sam tekst.
	 * 
	 * @return Tekstualna reprezentacija objekta.
	 */

	@Override
	public String toString() {
		return text;
	}
	

}
 

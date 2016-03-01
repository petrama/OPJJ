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
	
	

}
 

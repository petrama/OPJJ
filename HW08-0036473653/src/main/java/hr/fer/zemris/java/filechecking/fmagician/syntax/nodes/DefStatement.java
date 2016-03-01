package hr.fer.zemris.java.filechecking.fmagician.syntax.nodes;

import hr.fer.zemris.java.filechecking.fmagician.syntax.FMagicianNodeVisitor;
/**
 * Model naredbe "def" jezika <i>fmagician</i>.
 * 
 * @author Petra Marče
 */
public class DefStatement extends FMagicianNode{
	/** ime varijable **/
	private String variable;
	/** čvor koji prestavlja putanju koja se definira **/
	private PathNode path;

	/**
	 * Konstruktor.
	 * @param id ime varijable
	 * @param pat putanja koja se pamti.
	 */
	public DefStatement(String id,PathNode pat){
		variable=id;
		path=pat;
	}
	
	/**
	 * Meotda za dohvat poruke.
	 * Def ne sadrži poruku.
	 */
	@Override
	public MessageNode getMessage(){
		return null;
	}
	
	/**
	 * Metoda za dohvat putanje.
	 * @return vraća putanju.
	 */
	public PathNode getPath() {
		return path;
	}

	/** Metoda za dovat imena varijable**/
	public String getVariable() {
		return variable;
	}


	@Override
	public void accept(FMagicianNodeVisitor visitor){
		visitor.visit(this);
	}
}

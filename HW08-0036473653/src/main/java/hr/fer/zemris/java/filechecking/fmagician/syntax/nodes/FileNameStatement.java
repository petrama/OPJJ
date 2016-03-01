package hr.fer.zemris.java.filechecking.fmagician.syntax.nodes;

import hr.fer.zemris.java.filechecking.fmagician.syntax.FMagicianNodeVisitor;

/**
 * Razred predstavlja instrukciju koja provjerava ime predane datoteke.
 * Instrukcija se smatra uspješnom ako je stvarno ime datoteke u skladu s očekivanim.
 * @author Petra Marče.
 *
 */
public class FileNameStatement extends FMagicianNode {
	 /** Očekivano ime datoteke **/
	private MessageNode name;
	/** Poruka o pogrešci **/
	private MessageNode message;
	/** Oznaka invertiranja **/
	private boolean inverted;

	/**
	 * Konstruktor.
	 * @param n očekivano ime datoteke.
	 * @param mes poruka o pogrešci.
	 * @param i oznaka invertranja.
	 */
	public FileNameStatement(MessageNode n, MessageNode mes, boolean i) {

		name = n;
		message = mes;
		inverted = i;
	}

	/**
	 * Dohvat poruke o pogrešci.
	 */
	public MessageNode getMessage() {
		return message;
	}

	/**
	 * Dohvat očekivanog imena datoteke.
	 * @return
	 */
	public MessageNode getName() {
		return name;
	}
	/**
	 * Dohvat oznake invertiranja.
	 * @return
	 */
	public boolean isInverted() {
		return inverted;
	}

	// public String toString(){
	// return "This fileName statment checks if filename is "+name+
	// " and if it is not writes "+message;
	// }

	public void accept(FMagicianNodeVisitor visitor) {
		visitor.visit(this);
	}
}

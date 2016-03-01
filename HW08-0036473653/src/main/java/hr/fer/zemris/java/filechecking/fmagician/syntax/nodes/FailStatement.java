package hr.fer.zemris.java.filechecking.fmagician.syntax.nodes;

import hr.fer.zemris.java.filechecking.fmagician.syntax.FMagicianNodeVisitor;

/**
 * Instrukcija čiji je rezultat uvijek false.
 * Može sadržavati poruku u slučaju pogreške.
 * @author Petra Marče.
 *
 */
public class FailStatement extends FMagicianNode {
	 /** Poruka o pogrešci **/
	private MessageNode mess;
	/** Zastavica invertiranja **/
	private boolean inverted;

	/**
	 * Konstruktor. 
	 * @param mess poruka o pogrešci.
	 * @param i oznaka invertiranja.
	 */
	public FailStatement(MessageNode mess,boolean i) {
		super();
		this.mess = mess;
		inverted=i;
	}
	/**
	 * Dohvat oznake invertiranja.
	 * @return vraća true ako je instrukcija invertirana, false inače.
	 */
	public boolean isInverted() {
		return inverted;
	}
	/**
	 * Dohvat poruke o pogrešci.
	 */
	public MessageNode getMessage() {
		return mess;
	}

	public void accept(FMagicianNodeVisitor visitor){
		visitor.visit(this);
	}
}

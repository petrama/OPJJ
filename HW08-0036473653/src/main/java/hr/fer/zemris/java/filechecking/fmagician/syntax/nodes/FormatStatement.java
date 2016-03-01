package hr.fer.zemris.java.filechecking.fmagician.syntax.nodes;

import hr.fer.zemris.java.filechecking.fmagician.syntax.FMagicianNodeVisitor;
/**
 * Razred prestavlja instrukciju format jezika <i>fmagician</i>.
 * Instrukcija provjerava je li format datoteke u skladu s očekivanim.
 * @author Petra Marče
 *
 */
public class FormatStatement extends FMagicianNode {
	/** Očekivani format **/
	private String format;
	/** poruka o pogrešci **/
	private MessageNode message;
	/** Oznaka invertiranja **/
	private boolean inverted;

	/**
	 * Konstruktor.
	 * @param n očekivani format datoteke.
	 * @param mes poruka o pogrešci.
	 * @param i oznaka invertranja.
	 */
	public FormatStatement(String f, MessageNode m, boolean i) {
		format = f;
		message = m;
		inverted = i;
	}

	/**
	 * Dohvat oznake invertiranja.
	 * @return
	 */
	public boolean isInverted() {
		return inverted;
	}

	/**
	 * Dohvat očekivanog formata.
	 * @return format.
	 */
	public String getFormat() {
		return format;
	}
	
	/**
	 * Dohvat poruke o pogrešci.
	 */
	public MessageNode getMessage() {
		return message;
	}

	@Override
	public void accept(FMagicianNodeVisitor visitor) {
		visitor.visit(this);
	}

}

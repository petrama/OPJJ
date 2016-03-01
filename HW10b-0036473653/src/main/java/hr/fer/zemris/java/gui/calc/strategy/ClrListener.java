package hr.fer.zemris.java.gui.calc.strategy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Razred koji predstavlja <code>ActionListener</code> tipke clr.
 * Clr briše samo trenutni unos ali ne ponistava operaciju.
 * @author Petra Marče
 *
 */
public class ClrListener implements ActionListener {
	MyCalculatorStrategy strategy;
	/**
	 * Konstruktor, pridruzuje se referenca na instancu razreda <code>MyCalculatorStrategy</code> koja izvodi posao.
	 * @param strategy strategija.
	 */
	public ClrListener(MyCalculatorStrategy strategy) {
		super();
		this.strategy = strategy;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		strategy.execute(this);

	}

}

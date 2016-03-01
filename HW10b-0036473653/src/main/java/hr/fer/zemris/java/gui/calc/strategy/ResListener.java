package hr.fer.zemris.java.gui.calc.strategy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Razred koji predstavlja <code>ActionListener</code> tipke res koja predstavlja reset kalkulatora.
 * Reset brise trenutnu vrijednost i kompletnu memoriju kalkulatora.
 * @author Petra Marče
 *
 */
public class ResListener implements ActionListener {

	MyCalculatorStrategy strategy;
	/**
	 * Konstruktor, pridruzuje se referenca na instancu razreda <code>MyCalculatorStrategy</code> koja izvodi posao.
	 * @param strategy strategija.
	 */
	public ResListener(MyCalculatorStrategy strategy) {
		super();
		this.strategy = strategy;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		strategy.execute(this);
	}

}

package hr.fer.zemris.java.gui.calc.strategy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Razred koji predstavlja <code>ActionListener</code> tipke +/- koja predstavlja negaciju .
 * Pritiskom na tipku negacije trenutna vrijednost množi se s -1 i prikazuje na displayu.
 * @author Petra Marče
 *
 */
public class NegateListener implements ActionListener {
	MyCalculatorStrategy strategy;
	/**
	 * Konstruktor, pridruzuje se referenca na instancu razreda <code>MyCalculatorStrategy</code> koja izvodi posao.
	 * @param strategy strategija.
	 */
	public NegateListener(MyCalculatorStrategy strategy) {
		super();
		this.strategy = strategy;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		strategy.execute(this);
	}
}

package hr.fer.zemris.java.gui.calc.strategy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Razred koji predstavlja <code>ActionListener</code> tipke . koja predstavlja decimalnu tocku.
 * @author Petra Marče
 *
 */
public class DecimalDotListener implements ActionListener {

	MyCalculatorStrategy strategy;
	/**
	 * Konstruktor, pridruzuje se referenca na instancu razreda <code>MyCalculatorStrategy</code> koja izvodi posao.
	 * @param strategy strategija.
	 */
	public DecimalDotListener(MyCalculatorStrategy strategy) {
		super();
		this.strategy = strategy;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		strategy.execute(this);
	}
}
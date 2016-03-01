package hr.fer.zemris.java.gui.calc.strategy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Razred koji predstavlja <code>ActionListener</code> tipke pop.
 * Pop mjenja trenutnu vrijednost sa vrijednosti koji skine s vrha korisnickog stoga.
 * Ako je stog prazan pogrska se ispisuje na displayu kalkulatora.
 * @author Petra Marče
 *
 */
public class PopListener implements ActionListener {

	MyCalculatorStrategy strategy;
	/**
	 * Konstruktor, pridruzuje se referenca na instancu razreda <code>MyCalculatorStrategy</code> koja izvodi posao.
	 * @param strategy strategija.
	 */
	public PopListener(MyCalculatorStrategy strategy) {
		super();
		this.strategy = strategy;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		strategy.execute(this);
	}

}

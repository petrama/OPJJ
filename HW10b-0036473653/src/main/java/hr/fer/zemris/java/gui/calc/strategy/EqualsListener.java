package hr.fer.zemris.java.gui.calc.strategy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Razred koji predstavlja <code>ActionListener</code> tipke = .
 * Pritiskom na tipku jednakosti izracunava se dotad akumulirani izraz i ispisuje na display kalkulatora.
 * @author Petra Marče
 *
 */
public class EqualsListener implements ActionListener {
	MyCalculatorStrategy strategy;
	
	/**
	 * Konstruktor, pridruzuje se referenca na instancu razreda <code>MyCalculatorStrategy</code> koja izvodi posao.
	 * @param strategy strategija.
	 */
	public EqualsListener(MyCalculatorStrategy strategy) {
		super();
		this.strategy = strategy;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		strategy.execute(this);
		
	}

}

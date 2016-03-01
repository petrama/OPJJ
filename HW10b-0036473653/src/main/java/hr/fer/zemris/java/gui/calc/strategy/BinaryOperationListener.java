package hr.fer.zemris.java.gui.calc.strategy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Razred koji predstavlja <code>ActionListener</code> tipki + , - , * , / i x^n.
 * Ove naredbe su grupirane zajedno jer su to sve binarne operacije.
 * @author Petra Marče
 *
 */
public class BinaryOperationListener implements ActionListener {
	/** ime naredbe **/
	public String name;
	/** referenca na instancu razreda koja poduzima odgovarajucu akciju**/
	public MyCalculatorStrategy strategy;

	/**
	 * Konstruktor, pridruzuje se referenca na instancu razreda <code>MyCalculatorStrategy</code> koja izvodi posao i ime operacije.
	 * @param strategy strategija.
	 * @param name ime operacije.
	 */
	public BinaryOperationListener(MyCalculatorStrategy strategy, String name) {
		super();
		this.strategy = strategy;
		this.name = name;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		strategy.execute(this);

	}

}

package hr.fer.zemris.java.gui.calc.strategy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Razred koji predstavlja <code>ActionListener</code> tipki brojeva kalkulatora.
 * @author Petra Marče
 *
 */
public class NumberButtonListener implements ActionListener {

	String name;
	
	MyCalculatorStrategy strategy;
	/**
	 * Konstruktor, pridruzuje se referenca na instancu razreda <code>MyCalculatorStrategy</code> koja izvodi posao i ime tipke.
	 * @param strategy strategija.
	 * @param name ime tipke koje je jednako broju kojeg tipka predstavlja.
	 */
	public NumberButtonListener(MyCalculatorStrategy s, String name)
		{
		super();
		strategy=s;
		this.name = name;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		strategy.execute(this);

	}


}

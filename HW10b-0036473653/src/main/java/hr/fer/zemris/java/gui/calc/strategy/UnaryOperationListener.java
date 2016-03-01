package hr.fer.zemris.java.gui.calc.strategy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Razred koji predstavlja <code>ActionListener</code> tipki cos,sin,tan,ctg,1/x,ln.
 * Ove naredbe su grupirane zajedno jer su to sve unarne operacije.
 * Ako je označena tipka Inv tada će se računati inverz odgovarajuće operacije.
 * @author Petra Marče
 *
 */
public class UnaryOperationListener implements ActionListener {
	/** ime naredbe **/
	public String name;
	/** referenca na instancu razreda koja poduzima odgovarajucu akciju**/
	public MyCalculatorStrategy strategy;

	public UnaryOperationListener(MyCalculatorStrategy strategy, String name) {
		super();
		this.strategy = strategy;
		this.name = name;
	}
	/**
	 * Konstruktor, pridruzuje se referenca na instancu razreda <code>MyCalculatorStrategy</code> koja izvodi posao i ime operacije.
	 * @param strategy strategija.
	 * @param name ime operacije.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		strategy.execute(this);

	}

		
	}
	



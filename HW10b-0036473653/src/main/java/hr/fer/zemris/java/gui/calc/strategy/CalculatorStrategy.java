package hr.fer.zemris.java.gui.calc.strategy;

/**
 * Apstraktni izvrsitelj naredaba aplikacije <code>Calculator</code>
 * @author Petra Marče
 *
 */
public interface CalculatorStrategy {
	/**
	 * Reakcija na klik gumba na Calculatoru
	 * kojeg pokriva instanca razreda ({@link BinaryOperationListener})
	 * @param listener - listener naredbe
	 */
	public void execute(BinaryOperationListener listener);
	/**
	 * Reakcija na klik gumba na Calculatoru
	 * kojeg pokriva instanca razreda ({@link ClrListener})
	 * @param listener - listener naredbe
	 */
	public void execute(ClrListener clr);
	/**
	 * Reakcija na klik gumba na Calculatoru
	 * kojeg pokriva instanca razreda ({@link DecimalDotListener})
	 * @param listener - listener naredbe
	 */
	public void execute(DecimalDotListener dot);
	/**
	 * Reakcija na klik gumba na Calculatoru
	 * kojeg pokriva instanca razreda ({@link EqualsListener})
	 * @param listener - listener naredbe
	 */
	public void execute(EqualsListener eq);
	/**
	 * Reakcija na klik gumba na Calculatoru
	 * kojeg pokriva instanca razreda ({@link NubmerButtonListener})
	 * @param listener - listener naredbe
	 */
	public void execute(NumberButtonListener num);
	/**
	 * Reakcija na klik gumba na Calculatoru
	 * kojeg pokriva instanca razreda ({@link PushListener})
	 * @param listener - listener naredbe
	 */
	public void execute(PushListener push);
	/**
	 * Reakcija na klik gumba na Calculatoru
	 * kojeg pokriva instanca razreda ({@link BinaryOperationListener})
	 * @param listener - listener naredbe
	 */
	public void execute(ResListener res);
	/**
	 * Reakcija na klik gumba na Calculatoru
	 * kojeg pokriva instanca razreda ({@link UnaryOperationListener})
	 * @param listener - listener naredbe
	 */
	public void execute(UnaryOperationListener un);
	/**
	 * Reakcija na klik gumba na Calculatoru
	 * kojeg pokriva instanca razreda ({@link NegateListener})
	 * @param listener - listener naredbe
	 */
	public void execute(NegateListener lis);
	/**
	 * Reakcija na klik gumba na Calculatoru
	 * kojeg pokriva instanca razreda ({@link PopListener})
	 * @param listener - listener naredbe
	 */
	public void execute(PopListener p);
	/**
	 * Reakcija na klik gumba na Calculatoru
	 * kojeg pokriva instanca razreda ({@link InvListener})
	 * @param listener - listener naredbe
	 */
	public void execute(InvListener in);

}

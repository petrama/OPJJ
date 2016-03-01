package hr.fer.zemris.java.tecaj.hw6.problem1b;

/**
 * Razred predstavlja promatrača.
 * Promatrač je objekt koji čeka na promjenu statusa vrijednosti objekta kojeg promatra.
 * Kad objekt koji se promatra dojavi promjenu,promatrač poduzima neku akciju.
 * Ovaj razred dovaća novu vrijednost promatranog objekta,kvadrira je te ispisuje.
 * @author Petra Marče
 *
 */
public class SquareValue implements IntegerStorageObserver {
	
	/**
	 * Metoda koja se poziva kad se dogodi promjena.
	 * Dohvaća novu vrijednost ispisuje njen kvadrat.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int number = istorage.getValueNow();
		System.out.println("Provided new value: " + number + ", square is "
				+ number * number);
	}

}

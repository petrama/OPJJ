package hr.fer.zemris.java.tecaj.hw6.problem1b;
/**
 * Razred predstavlja promatrača.
 * Promatrač je objekt koji čeka na promjenu statusa vrijednosti objekta kojeg promatra.
 * Kad objekt koji se promatra dojavi promjenu,promatrač poduzima neku akciju.
 * Ovaj razred dovaća novu vrijednost promatranog objekta, množi je sa dva te ispisuje.
 * Nakon tri akcije,objekt prestaje promatrati.
 * @author Petra Marče
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	private int counter;
	private double value;
	
	/**
	 * Konstruktor.
	 * Inicijalizira brojac.
	 */
	public DoubleValue() {
		super();
		this.counter = 1;
	}
	
	/**
	 * Metoda koja se izvršava u trenutku promjene vrijednosti objekta koji se promatra.
	 * Metoda ispisuje novi broj pomnožen s dva, ali za samo prve tri promjene od trenutka početka promatranja.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		if (counter >= 3) {
			istorage.getStorage().removeObserver(this);
		} else {
			value = istorage.getValueNow();
			System.out.println("Double value: " + value * 2);
			counter++;
		}
	}
}

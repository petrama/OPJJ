package hr.fer.zemris.java.tecaj.hw6.problem1b;

/**
 * Razred predstavlja promatrača koji broji promjene.
 * Promatrač je objekt koji čeka na promjenu statusa vrijednosti objekta kojeg promatra.
 * Kad objekt koji se promatra dojavi promjenu,promatrač poduzima neku akciju.
 * Ovaj razred povećava svoj brojač te ga ispisuje na standardni izlaz.
 * @author Petra Marče
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	private Integer counter;
	
	/**
	 * Konstruktor.
	 * Postavlja brojač na nulu.
	 */
	public ChangeCounter() {
		super();
		counter = 0;
	}
	
	/**
	 * Metoda koja se izvršava u trenutku promjene vrijednosti objekta koji se promatra.
	 * Metoda ispisuje broj pojavljivanja promjena od trenutka pocetka promatranja.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		counter++;
		System.out
				.println("Number of value changes since tracking: " + counter);
	}

}

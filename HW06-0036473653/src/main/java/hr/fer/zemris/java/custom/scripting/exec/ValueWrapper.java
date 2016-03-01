package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Razred koji predstavlja neku brojcanu vrijednost.
 * Razred podržava operacije zbrajanja,oduzimanja,množenja i dijeljenja.
 * @author Petra Marče.
 *
 */
public class ValueWrapper {

	private Object value;

	/**
	 * Konstruktor.
	 * Prima vrijednost koju će nova instanca predstavljati.
	 * Ima smisla zadati cijeli,realni broj te znakovni niz koji ima brojcanu vrijednot.
	 * @param value nova vrijednost.
	 */
	public ValueWrapper(Object value) {
		super();
		this.value = value;
	}

	/**
	 * Metoda koja služi za dohvaćanje vrijednosti.
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	/**
	 * Metoda koja sluzi sa postavljanje vrijednosti.
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Metoda koja koja ulaz interpretira kao string.
	 * Taj string metoda parsira u broj,ako je to moguće.
	 * Ako to nije moguće metoda baca RuntimeException.
	 * Metoda prvo pokusava dobiti cijeli, a tek onda realan broj.
	 * @param second objekt koji se pokusava isparsirati u broj.
	 * @return vraća isparsirani broj
	 */
	public static Object parseToNumber(Object second) {
		if(second instanceof Integer || second instanceof Double || second==null){
			return second;
		}
		Object novi;
		try {
			novi = Integer.parseInt(((String) second));
			return novi;
		} catch (NumberFormatException n) {
			try {
				novi = Double.parseDouble((String) second);
				return novi;
			} catch (NumberFormatException ne) {
				throw new RuntimeException(
						"String canot be converted to any number");
			}
		}

	}
	/**
	 * Metoda koja vraća rezultat tražene operacije.
	 * Podržane operacije su: + - * i /
	 * @param first prvi operand
	 * @param second drugi operand
	 * @param operation znak za operaciju.
	 * @return rezultat operacije.
	 */

	private Double calculate(Double first, Double second, char operation) {
		switch (operation) {
		case '+':
			return first + second;
		case '-':
			return first - second;
		case '*':
			return first * second;
		case '/':
			return first / second;
		default:
			return null;
		}
	}

	private Integer calculate(Integer first, Integer second, char operation) {
		switch (operation) {
		case '+':
			return first + second;
		case '-':
			return first - second;
		case '*':
			return first * second;
		case '/':
			return first / second;
		default:
			return null;
		}
	}

	/**
	 * Metoda koja prilagođava operande te izvrašava traženu operaciju.
	 * Prvi operand svake operacije je vrijednost instance objekta.
	 * @param second drugi operand
	 * @param operation simbol tražene operacije.
	 * @return vraća rezultat tražene operacije.
	 */
	public Object aritmetic(Object second, char operation) {
		if (this.value == null) {
			this.value = Integer.valueOf(0);
		}
		if (second == null) {
			second = Integer.valueOf(0);
		}

		if (this.value instanceof String) {
			this.value = parseToNumber(value);
		}
		if (second instanceof String) {
			second = (parseToNumber(second));
		}

		if (value instanceof Integer && second instanceof Double) {
			this.value = new Double((Integer) value);

		} else {
			if (value instanceof Double && second instanceof Integer) {
				second = new Double((Integer) second);
			}
		}

		if (this.value instanceof Integer) {
			return calculate((Integer) value, (Integer) second, operation);
		}

		if (this.value instanceof Double) {
			return calculate((Double) value, (Double) second, operation);
		}

		return null;

	}

	/**
	 * Metoda koja na trenutnu vrijednost razreda dodaje zadanu vrijednost.
	 * @param incValue vrijednost koja se nadodaje.
	 */
	public void increment(Object incValue) {
		this.value = aritmetic(incValue, '+');

	}
	/**
	 * Metoda koja od trenutne vrijednosti razreda oduzima zadanu vrijednost.
	 * @param decValue vrijednost koja se oduzima.
	 */
	public void decrement(Object decValue) {
		this.value = aritmetic(decValue, '-');

	}
	/**
	 * Metoda koja trenutnu vrijednost varijable value množi zadanim brojem.
	 * @param mulValue broj kojim se mnozi.
	 */
	public void multiply(Object mulValue) {
		this.value = aritmetic(mulValue, '*');

	}
	/**
	 * Metoda koja trenutnu vrijednost varijable value dijeli zadanim brojem.
	 * @param divValue vrijednost kojom se dijeli.
	 */
	public void divide(Object divValue) {
		this.value = aritmetic(divValue, '/');
	}
	
	/**
	 * Metoda koja uspoređuje vrijednost instance razreda sa nekom drugom vrijednoscu.
	 * @param withValue vrijednost s kojom se uspoređuje.
	 * @return vraća 0 ako su vrijednosti iste, broj manji od nule ako je prva manja,broj veći od nule inače
	 */
	public int numCompare(Object withValue) {
		if (this.value == null && withValue == null) {
			return 0;
		}
		Object razlika = aritmetic(withValue, '-');
		if (razlika instanceof Integer) {
			return (Integer) razlika;
		} else if (razlika instanceof Double) {
			double r = (Double) razlika;
			if (r < -1E-6) {
				return -1;
			}

			else if (r > 1E-6) {
				return 1;
			}
		}

		return 0;
	}
	
	
}

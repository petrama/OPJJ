package hr.fer.zemris.java.custom.collections;

/**
 * Razred koji nudi standardne funkcije za rad sa stogom. Elementi stoga mogu
 * biti svih tipova osim null
 * 
 * @author Petra Marče
 * 
 */

public class ObjectStack {
	ArrayBackedIndexedCollection stack = new ArrayBackedIndexedCollection();

	public ObjectStack() {
		super();

	}

	/**
	 * Metoda dodaje zadani element na vrh stoga
	 * 
	 * @param value
	 *            vrijednost koja se stavlja na stog
	 * @throws IllegalArgumentException
	 *             ako je objekt null referenca javlja se odgovarajuća iznimka
	 */
	public void push(Object value) throws IllegalArgumentException {
		if (value == null)
			throw new IllegalArgumentException(
					"Stack does not accept null objects");
		stack.add(value);

	}

	/**
	 * Metoda koja skida element sa vrha stoga.
	 * 
	 * @return Vraća skinuti element.
	 * @throws EmptyStackException
	 *             ako pokušavamo skidati sa praznog stoga javlja se
	 *             odgovarajuća iznimka
	 */
	public Object pop() throws EmptyStackException {
		if (stack.isEmpty())
			throw new EmptyStackException("Stack is empty!");
		Object o = stack.get(stack.size() - 1);
		stack.remove(stack.size() - 1);
		return o;
	}

	/**
	 * Metoda koja vraća element sa vrha stoga, ali on ostaje na stogu
	 * 
	 * @return vraća element pročitan s vrha stoga
	 * @throws EmptyStackException
	 *             ako pokušavamo čitati sa praznog stoga javlja se odgovarajuća
	 *             iznimka
	 */
	public Object peek() throws EmptyStackException {
		if (stack.isEmpty())
			throw new EmptyStackException("Stack is empty!");
		return stack.get(stack.size() - 1);
	}

	/**
	 * Metoda koja prazni stog
	 */
	void clear() {
		stack.clear();
	}

	/**
	 * Metoda koja određuje je li stog prazan
	 * 
	 * @return vraća true ako je stog prazan, false inače
	 */
	boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * Metoda koja vraća broj elemenata na stogu
	 * 
	 * @return broj elemenata na stogu
	 */
	int size() {
		return stack.size();
	}

}

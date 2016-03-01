package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * Razred omogucava rad sa poljem objekata promjenjive velicine. Omogucava rad
 * sa svim vrstama objekata. Ne podrzava null elemente.
 * 
 * @author Petra Marče
 * 
 */

public class ArrayBackedIndexedCollection {
	private int size;
	private int capacity;
	private Object[] elements;

	/**
	 * Metoda koja se poziva kada se stvara nova instanca polja Pri stvaranju
	 * nove instance polja, ako korisnik ne zada velicinu ona ce biti
	 * postavljena na 16
	 */
	public ArrayBackedIndexedCollection() {
		size = 0;
		capacity = 16;
		elements = new Object[capacity];

	}

	/**
	 * Metoda koja se poziva kada se stvara novo polje Pri stvaranju nove
	 * instance polja korisnik moze inicijalizirati velicinu polja na
	 * proizvoljnu pozitivnu vrjednost
	 * 
	 * @param initialCapacity vrijednost na koju se polje inicijalizira
	 * @throws IllegalArgumentException ako korisnik unese neispravnu velicinu metoda
	 * javlja odgovarajucu iznimku
	 */
	public ArrayBackedIndexedCollection(int initialCapacity)
			throws IllegalArgumentException {
		if (initialCapacity < 1)
			throw new IllegalArgumentException(
					"Capacity must be greater than 0!");
		size = 0;
		capacity = initialCapacity;
		elements = new Object[capacity];

	}

	/**
	 * Metoda koja određuje da li je polje prazno
	 * 
	 * @return vraća true ako je prazno, false inače
	 */
	boolean isEmpty() {
		if (size == 0)
			return true;
		else
			return false;

	}

	/**
	 * Metoda koja vraća veličinu polja
	 * 
	 * @return veličina polja
	 */
	public int size() {
		return size;
	}

	/**
	 * Metoda koja provjerava je li polje popunjeno, ako jest, povećava
	 * kapacitet polja dva puta pri čemu dotad pohranjeni elementi ostaju
	 * netaknuti
	 */
	void reallocateIfFull() {

		if (size == capacity) {
			elements = Arrays.copyOf(elements, 2 * elements.length);
			capacity *= 2;

		}
		;
	}

	/**
	 * Metoda koja provjerava da li je zadani element null referenca
	 * 
	 * @param value vrijednost koju treba provjeriti
	 * @throws IllegalArgumentException
	 *             ako je objekt null metoda javlja odgovarajuću iznimku
	 */
	void checkValue(Object value) throws IllegalArgumentException {
		if (value == null)
			throw new IllegalArgumentException(
					"Collection doesn't accept null references!");

	}

	/**
	 * Metoda koja dodaje novi objekt u polje
	 * 
	 * @param value
	 *            objekt koji se dodaje
	 * @throws IllegalArgumentException
	 *             ako je objekt null metoda javlja iznimku
	 */
	public void add(Object value) throws IllegalArgumentException {

		checkValue(value);
		reallocateIfFull();
		size++;
		elements[size - 1] = value;

	}

	/**
	 * Metoda koja provjerava da li je zadani indeks između određene donje i
	 * gornje vrijednosti.
	 * 
	 * @param index indeks kojeg treba provjeriti
	 * @param lowerBoundary donja granica
	 * @param higherBounadry gornja granica
	 * @throws IndexOutOfBoundsExcept ao indeks nije u granicama metoda 
	 * javlja odgovarajuću iznimku
	 */

	void checkIndex(int index, int lowerBoundary, int higherBounadry)
			throws IndexOutOfBoundsException {
		if (index < lowerBoundary || index > higherBounadry)
			throw new IndexOutOfBoundsException("Index is invalid!");

	}

	/**
	 * Metoda koja vraća objekt na zadanoj poziciji iz polja
	 * 
	 * @param index zadana pozicija
	 * @return nađeni objekt
	 * @throws IndexOutOfBoundsException javlja iznimku ako indeks nije validan
	 */

	public Object get(int index) throws IndexOutOfBoundsException {
		checkIndex(index, 0, size - 1);
		return elements[index];

	}

	/**
	 * Metoda za određivanje veličine polja
	 * 
	 * @return vraća veličinu polja
	 */

	public int getSize() {
		return size;
	}

	/**
	 * Metoda za postavljanje veličine polja
	 * 
	 * @param size veličina polja koju želimo postaviti
	 * @return postavlja veličinu polja na zadanu vrijednost
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Metoda koja uklanja element sa zadane pozicije u polju;
	 * 
	 * @param index
	 *            pozicija sa koje treba ukloniti element
	 * @throws IndexOutOfBoundsException
	 *             ako indeks nije validan metoda javlja iznimku
	 */

	void remove(int index) throws IndexOutOfBoundsException {
		checkIndex(index, 0, size - 1);
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		elements[size - 1] = null;
		size--;

	}

	/**
	 * Metoda koja ubacuje zadani element na zadanu poziciju
	 * 
	 * @param value
	 *            vrijednost koju želimo ubaciti
	 * @param position
	 *            pozicija na koju tu vrijednost želimo ubaciti
	 * @throws IndexOutOfBoundsException
	 *             ako indeks nije validan metoda javlja iznimku
	 */
	void insert(Object value, int position) throws IndexOutOfBoundsException {
		checkIndex(position, 0, size);
		checkValue(value);
		reallocateIfFull();
		size++;
		for (int i = size; i > position; i--) {
			elements[i] = elements[i - 1];
		}
		elements[position] = value;

	}

	/**
	 * Metoda koja vraća indeks na kojoj se nalazi traženi element
	 * 
	 * @param value
	 *            element koji se traži
	 * @return nađeni indeks, u slučaju neuspjeha vraća -1
	 */
	int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (value.equals(elements[i]) == true)
				return i;
		}
		return -1;

	}

	/**
	 * Metoda koja provjerava sadrži li polje zadani objekt
	 * 
	 * @param value objekt koji se traži
	 * @return ako je objet u polju metoda vraća true, false inače
	 */

	boolean contains(Object value) {
		if (indexOf(value) == -1)
			return false;
		return true;

	}

	/**
	 * Metoda koja briše sve elemente iz polja
	 */
	void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

}

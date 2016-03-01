package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;

/**
 * Razred koji predstavlja određeni skup podataka.
 * Omogućava rad s instancom polja objekata
 * @author petra
 *
 */
public class Node {

	public ArrayBackedIndexedCollection col;

	public void addChildNode(Node child) throws IllegalArgumentException {

		col.add(child);

	}
	/**
	 * Metoda koja vraća broj čvorova pridruženim tom čvoru nad kojim je metoda pozvana
	 * @return broj djece čvora
	 */
	public int numberOfChildren() {
		int s;
		try {
			s = col.size();
		} catch (RuntimeException e) {
			s = 0;

		}
		return s;

	}
	/**
	 * Metoda koja vraća element koji je čvoru pridružen i-ti poredu. Gdje je i zadani indeks
	 * @param index zadani indeks
	 * @return element na traženom indeksu
	 * @throws IndexOutOfBoundsException ako je indeks invalidan funkcija javlja odgovarajuću iznimku
	 */
	public Node getChild(int index) throws IndexOutOfBoundsException {
		return (Node) col.get(index);
	}



}

package hr.fer.zemris.java.tecaj.hw3;

import java.util.Iterator;

/**
 * Razred omogućava silazno i uzlazno iteriranje brojeva.
 * Korak može biti proizvoljan.
 * @author Petra marče
 *
 */
public class IntegerSequence implements Iterable<Integer> {
	int firstNum;
	int n;
	int step;

	public IntegerSequence(int f, int l, int s) {
		
		if((s<0 && (f<l)) || (s>0 && f>l)){
			throw new IllegalArgumentException("Invalid arguments");
			
		}
		
		this.firstNum = f;
		this.n = (l - f) / s + 1;
		this.step = s;
	}
	

	@Override
	public Iterator<Integer> iterator() {
		return new IteratorSeq();
	}
	/**
	 * Razred koji implementira sučelje iteratora.
	 * @author Petra Marče
	 *
	 */

	private class IteratorSeq implements Iterator<Integer> {
		private int temporary;
		private int numLeft;
		private int st;

		public IteratorSeq() {
			this.numLeft = n;
			this.temporary = firstNum;
			this.st = step;
		}
		
		/**
		 * Metoda koja provjerava ima li više elemenata.
		 * Ako ima vraća true,inače false.
		 */
		@Override
		public boolean hasNext() {
			return numLeft > 0;
		}
		/**
		 * Metoda koja dohvaća idući element
		 * Ako nema više elemenata baca iznimku.
		 */
		@Override
		public Integer next() {
			if (numLeft < 1) {
				throw new RuntimeException("No elemets left!");

			}
			int val = temporary;
			temporary += st;
			numLeft--;
			return val;
		}
		
		public void remove() {
			throw new UnsupportedOperationException(
					"Deleting of elements not possible");
		}
	}

}

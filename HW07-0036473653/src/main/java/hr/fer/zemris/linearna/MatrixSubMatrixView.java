package hr.fer.zemris.linearna;

import java.util.Arrays;
/**
 * Razred koji predstavlja živi pogled na neku matricu.
 * Pogled na neki objekt znači da pri stvaranju razred čuva referencu na promatrani objekt.
 * Svaka promjena unutar ovog razreda rezultirat će promjenom u originalnoj matrici.
 * Razred nudi pogled na podmatricu originalne matrice.
 * Podmatrica znači matrica koja ima bilo koji podskup redaka ili stupaca originalne matrice.
 * 
 * @author Petra Marče
 *
 */
public class MatrixSubMatrixView extends AbstractMatrix {
	private int[] rowIndexes;
	private int[] colIndexes;
	private IMatrix matrix;

	/**
	 * Konstruktor. 
	 * Stvara novu podmatricu koja je zivi pogled na originalnu, bez proizvoljnog retka i stupca.
	 * @param matrica originalna matrica.
	 * @param brRetka redak koji zelimo izbaciti iz pogleda.
	 * @param brStupca stupac kojeg zelimo izbaciti iz pogleda.
	 */
	public MatrixSubMatrixView(IMatrix matrica, int brRetka, int brStupca) {
		this.matrix = matrica;
		rowIndexes = new int[matrica.getRowsCount() - 1];
		colIndexes = new int[matrica.getColsCount() - 1];
		for (int i = 0; i < brRetka; i++) {
			rowIndexes[i] = i;
		}
		for (int i = brRetka + 1; i < matrica.getRowsCount(); i++) {
			rowIndexes[i - 1] = i;
		}
		for (int i = 0; i < brStupca; i++) {
			colIndexes[i] = i;
		}
		for (int i = brStupca + 1; i < matrica.getColsCount(); i++) {
			colIndexes[i - 1] = i;
		}
	}

	/**
	 * Konstruktor.
	 * Stvara novu podmatricu koja se sastoji od proizvoljno zadanog skupa redaka i stupaca originalne.
	 * @param matrica matrica od koje radimo novi pogled
	 * @param redak skup redaka pogleda
	 * @param stupac skup stupaca pogleda
	 */
	private MatrixSubMatrixView(IMatrix matrica, int[] redak, int[] stupac) {
		this.matrix = matrica;
		rowIndexes = Arrays.copyOf(redak, redak.length);
		colIndexes = Arrays.copyOf(stupac, stupac.length);
	}
	
	/**
	 * Metoda za dohvat broja redaka matrice.
	 */
	@Override
	public int getRowsCount() {
		return rowIndexes.length;
	}
	/**
	 *Metoda za dohvat broja stupaca matrice.
	 */
	@Override
	public int getColsCount() {
		return colIndexes.length;
	}

	/**
	 * Metoda za dohvat elementa na zadanim pozicijama.
	 * @param row redak u kojemu se nalazi element kojeg zelimo dohvatiti.
	 * @param col stupac u kojemu se nalazi element kojeg zelimo dohvatiti.
	 * @return trazeni element.
	 */
	@Override
	public double get(int row, int col) {
		return matrix.get(rowIndexes[row], colIndexes[col]);
	}
	
	/**
	 * Metoda za postavljanje elementa na trazenoj poziciji.
	 * @param row redak trazenog elementa
	 * @param col stupac trazenog elementa
	 * @param value nova vrijednost
	 * @return vraća referencu na trenutnu matricu
	 */
	@Override
	public IMatrix set(int row, int col, double value) {
		matrix.set(rowIndexes[row], colIndexes[col], value);
		return this;
	}
	/**
	 * Metoda koja stvara novi primjerak pogleda na matricu.
	 * Novostvorena matrica ima iste vrijednost kao pocetna.
	 * Ove dvije matrice su potpuno neovisne.
	 */
	@Override
	public IMatrix copy() {
		Matrix nova = new Matrix(matrix.getRowsCount(), matrix.getColsCount(),
				((Matrix) matrix).elements, false);
		return new MatrixSubMatrixView(nova, rowIndexes, colIndexes);
	}
	
	/**
	 * Metoda koja stvara novi primjerak matrice zadanih dimenzija.
	 */
	@Override
	public IMatrix newInstance(int rows, int cols) {
		Matrix nova = new Matrix(rows, cols);
		return new MatrixSubMatrixView(nova, rows, cols);
	}
	/**
	 * Metoda stvara podmatricu od trenutne matrice.
	 * Nova matrica je kao početna ali bez zadanog retka i stupca.
	 * @param row redak kojeg treba izbaciti
	 * @param col stupac kojeg treba izbaciti.
	 * @param liveView zastavica koja oznacava da li je nova podmatrica pogled na trenutnu ili kopija.
	 * 
	 */
	public IMatrix subMatrix(int row, int col, boolean liveView) {
		return liveView ? new MatrixSubMatrixView(this, row, col)
				: new MatrixSubMatrixView(this.copy(), row, col);
	}
}

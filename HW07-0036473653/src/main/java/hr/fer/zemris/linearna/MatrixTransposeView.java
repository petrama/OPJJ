package hr.fer.zemris.linearna;

/**
 * Razred koji predstavlja živi pogled na neku matricu.
 * Pogled na neki objekt znači da pri stvaranju razred čuva referencu na promatrani objekt.
 * Svaka promjena unutar ovog razreda rezultirat će promjenom u originalnoj matrici.
 * Razred nudi pogled na transponiranu originalnu matrice.
 * Transponiranje je postupak zamjene redaka i stupaca u matrici.
 * 
 * @author Petra Marče
 *
 */
public class MatrixTransposeView extends AbstractMatrix {
	IMatrix matrix;

	/**
	 * Konstruktor.
	 * @param original referenca na originalnu matricu.
	 */
	public MatrixTransposeView(IMatrix original) {
		matrix = original;
	}
	/**
	 * Metoda za dohvat broja redaka matrice.
	 */
	@Override
	public int getRowsCount() {
		return matrix.getColsCount();
	}
	/**
	 *Metoda za dohvat broja stupaca matrice.
	 */
	@Override
	public int getColsCount() {
		return matrix.getRowsCount();
	}
	/**
	 * Metoda za dohvat elementa na zadanim pozicijama.
	 * @param row redak u kojemu se nalazi element kojeg zelimo dohvatiti.
	 * @param col stupac u kojemu se nalazi element kojeg zelimo dohvatiti.
	 * @return trazeni element.
	 */
	@Override
	public double get(int row, int col) {
		return matrix.get(col, row);
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
		this.matrix.set(col, row, value);
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
		return new MatrixTransposeView(nova);
	}
	/**
	 * Metoda koja stvara novi primjerak matrice zadanih dimenzija.
	 */
	@Override
	public IMatrix newInstance(int rows, int cols) {
		Matrix nova = new Matrix(rows, cols);
		return new MatrixTransposeView(nova);
	}

	@Override
	public double[][] toArray() {
		int r = getRowsCount();
		int c = getColsCount();
		double arr[][] = new double[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				arr[i][j] = get(r, c);
			}
		}
		return arr;
	}

}
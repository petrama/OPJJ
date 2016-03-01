package hr.fer.zemris.linearna;

import java.util.Arrays;
/**
 * Razred koji predstavlja konkretnu implementaciju matrice.
 * Matrica je određena brojem redaka i stupaca i elementima
 * koji se čuvaju u privatnoj instanci dvodimenzionalnog polja.
 * @author Petra Marče.
 *
 */
public class Matrix extends AbstractMatrix {
	protected double[][] elements;
	protected int rows;
	protected int cols;

	/**
	 * Konstruktor.
	 * @param rows broj redaka nove matrice.
	 * @param cols broj stupaca nove matrice.
	 * @param data dvodimenzinalno polje elemenata.
	 * @param privateData zastavica koja označava mora li se polje kopirati.
	 * Ako je true polje se ne kopira, inače se kopira.
	 */
	public Matrix(int rows, int cols, double[][] data, boolean privateData) {
		if (privateData) {
			elements = data;
		} else {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					elements[i][j] = data[i][j];
				}
			}
		}
		this.cols = cols;
		this.rows = rows;
	}

	/**
	 * Konstruktor.
	 * Stvara praznu matricu dimenzija rxc
	 * @param r broj redaka nove matrice.
	 * @param c broj stupaca nove matrice.
	 */
	public Matrix(int r, int c) {
		rows = r;
		cols = c;
		elements = new double[r][c];
	}

	/**
	 * Metoda koja stvara novu matricu iz predanog znakovnog niza.
	 * @param ulaznaMatrica ulazni niz u kojemu su retci odjeljeni '|' a pojedini elementi razmacima
	 * @return nova matrica koja nastaje parsiranjem niza.
	 */
	public static Matrix parseSimple(String ulaznaMatrica) {
		String[] retci = ulaznaMatrica.trim().split("[|]");
		int brojredaka = retci.length;
		int brstupaca = retci[0].trim().split(" +").length;
		if (brojredaka < 1 || brstupaca < 1) {
			throw new IllegalArgumentException(
					"Zero rows or cols!");
		}
		double[][] pomocni = new double[brojredaka][brstupaca];
		for (int i = 0; i < brojredaka; i++) {
			String[] stupci = retci[i].trim().split(" +");
			if (stupci.length != brstupaca) {
				throw new IllegalArgumentException("Wrong number of arguments");
			}
			for (int j = 0; j < brstupaca; j++) {
				try {
					pomocni[i][j] = Double.parseDouble(stupci[j].toString());
				} catch (NumberFormatException ne) {
					throw new IllegalArgumentException(
							"Matrix cannot be parsed!");
				}
			}
		}
		return new Matrix(brojredaka, brstupaca, pomocni, true);
	}

	/**
	 * Metoda za dohvat broja redaka matrice.
	 * @return rows broj redaka
	 */
	@Override
	public int getRowsCount() {
		return rows;
	}

	/**
	 * Metoda za dohvat broja stupaca matrice.
	 * @return cols broj stupaca
	 */
	@Override
	public int getColsCount() {
		return cols;
	}

	/**
	 * Metoda za dohvat elementa na trazenoj poziciji.
	 * @param row redak trazenog elementa
	 * @param col stupac trazenog elementa
	 * @return trazeni element
	 */
	@Override
	public double get(int row, int col) {
		Vector.checkIndex(col, 0, getColsCount() - 1);
		Vector.checkIndex(row, 0, getRowsCount() - 1);
		return elements[row][col];
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
		Vector.checkIndex(col, 0, getColsCount() - 1);
		Vector.checkIndex(row, 0, getRowsCount() - 1);
		elements[row][col] = value;
		return this;
	}

	/**
	 * Metoda koja vraća hash vrijednost matrice.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cols;
		result = prime * result + Arrays.hashCode(elements);
		result = prime * result + rows;
		return result;
	}

	/**
	 * Metoda koja utvrđuje da li su dvije matrice jednake.
	 * Vraća true ako su jednake, false inače.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Matrix other = (Matrix) obj;
		if (cols != other.cols)
			return false;
		if (!Arrays.deepEquals(elements, other.elements))
			return false;
		if (rows != other.rows)
			return false;
		return true;
	}

	/**
	 * Metoda koja stvara novi primjerak matrice.
	 * Novostvorena matrica ima iste vrijednost kao pocetna.
	 * Ove dvije matrice su potpuno neovisne.
	 */
	@Override
	public IMatrix copy() {
		return new Matrix(rows, cols, elements, false);
	}
	/**
	 * Metoda koja stvara novi primjerak matrice zadanih dimenzija.
	 */
	@Override
	public IMatrix newInstance(int rows, int cols) {
		return new Matrix(rows, cols);
	}

}
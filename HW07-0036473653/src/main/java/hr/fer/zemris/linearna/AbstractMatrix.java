package hr.fer.zemris.linearna;

import java.util.Locale;

/**
 * Apstraktan razred koji predstavlja rad sa matricom.
 * Razred nudi standardne operacije s matricama kao sto su zbrajanje,
 * oduzimanje,matricno mnozenje, računanje determinante i inverza.
 * Osim operacija nudi transponiranje te dobivanje algebarskih komplementa.
 * @author Petra Marče
 *
 */
public abstract class AbstractMatrix implements IMatrix {

	/**
	 * Metoda omogucava stvaranje transponirane matrice u odnosu
	 * na originalnu matricu. Ako je parametar liveView postavljen
	 * na true, objekt koji se vrati mora biti "zivi" pogled na
	 * originalnu matricu. U suprotnom stvara se nova matrica koja
	 * cuva vlastitu kopiju podataka.
	 * 
	 * @param liveView zeli li se dobiti zivi pogled
	 * @return referenca na transponiranu matricu
	 */
	@Override
	public IMatrix nTranspose(boolean liveView) {
		if (liveView) {
			return new MatrixTransposeView(this);
		} else {
			return new MatrixTransposeView(this.copy());
		}
	}

	/**
	 * Metoda trenutnoj matrici dodaje zadanu matricu (provodi
	 * operaciju zbrajanja); pri tome se originalna matrica direktno
	 * mijenja. 
	 * 
	 * @param other matrica koju treba pribrojiti
	 * @return referenca na trenutnu matricu
	 */
	@Override
	public IMatrix add(IMatrix other) {
		if (this.getColsCount() != other.getColsCount()
				|| this.getRowsCount() != other.getRowsCount()) {
			throw new IncompatibleOperandException(
					"For operation 'add' matrixes should be compatible!");
		}
		int m = this.getRowsCount();
		int n = this.getColsCount();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				this.set(i, j, this.get(i, j) + other.get(i, j));
			}
		}
		return this;
	}

	/**
	 * Metoda stvara novu matricu koja odgovara zbroju trenutne matrice
	 * i predane matrice.
	 * 
	 * @param other matrica koju treba pribrojiti
	 * @return referenca na novu matricu
	 */
	@Override
	public IMatrix nAdd(IMatrix other) {
		return this.copy().add(other);
	}

	/**
	 * Metoda od trenutne matrice oduzima zadanu matricu (provodi
	 * operaciju odbijanja); pri tome se originalna matrica direktno
	 * mijenja. 
	 * 
	 * @param other matrica koju treba odbiti
	 * @return referenca na trenutnu matricu
	 */
	@Override
	public IMatrix sub(IMatrix other) {
		if (this.getColsCount() != other.getColsCount()
				|| this.getRowsCount() != other.getRowsCount()) {
			throw new IncompatibleOperandException(
					"For operation 'sub' matrixes should be compatible!");
		}
		int m = this.getRowsCount();
		int n = this.getColsCount();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				this.set(i, j, this.get(i, j) - other.get(i, j));
			}
		}
		return this;
	}
	/**
	 * Metoda stvara novu matricu koja odgovara razlici trenutne matrice
	 * i predane matrice.
	 * 
	 * @param other matrica koju treba odbiti
	 * @return referenca na novu matricu
	 */
	@Override
	public IMatrix nSub(IMatrix other) {
		return this.copy().sub(other);
	}
	/**
	 * Metoda stvara novu matricu koja odgovara matricnom umnosku trenutne 
	 * matrice i predane matrice.
	 * 
	 * @param other matrica s kojom treba pomnoziti trenutnu
	 * @return referenca na novu matricu
	 */
	@Override
	public IMatrix nMultiply(IMatrix other) {

		if (this.getColsCount() != other.getRowsCount()) {
			throw new IncompatibleOperandException(
					"For matrix multiplication first matrix must have same number of columns as number of rows of the other matrix!");
		}
		int m = this.getRowsCount();
		int n = other.getColsCount();
		int innerDimension = this.getColsCount();
		double[][] p = new double[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				double sum = 0;
				for (int k = 0; k < innerDimension; k++) {
					sum += this.get(i, k) * other.get(k, j);
				}
				p[i][j] = sum;
			}
		}
		return new Matrix(m, n, p, true);
	}
	
	/**
	 * Metoda racuna determinantu trenutne matrice.
	 * @return determinantu
	 * @throws IncompatibleOperandException ako matrica nije kvadratna
	 */
	public double determinant() throws IncompatibleOperandException {
		if (this.getColsCount() != this.getRowsCount()) {
			throw new IncompatibleOperandException(
					"Determinant is defined for square matrix only!");

		}
		int dimension = this.getColsCount();
		if (dimension == 1) {
			return this.get(0, 0);
		} else {
			double sum = 0;
			for (int i = 0; i < dimension; i++) {
				sum += Math.pow(-1, i) * this.get(0, i)
						* (this.subMatrix(0, i, true)).determinant();
			}
			return sum;
		}

	}
	/**
	 * Metoda vraca matricu koja odgovara trenutnoj matrici nakon izbacivanja
	 * zadanog retka i zadanog stupca (oboje se numerira od 0). Ta nova matrica
	 * imat ce za jedan manje broj redaka i za jedan manje broj stupaca u odnosu
	 * na trenutnu matricu. Ako je parametar liveView postavljen na true,
	 * metoda mora vratiti zivi pogled na izvornu matricu; to znaci da, primjerice,
	 * ako u ovom pogledu izmjenimo neki element, promjena ce se vidjeti i u
	 * originalnoj matrici na odgovarajucem mjestu. Ako je liveView postavljen
	 * na false, stvara se nova matrica koja ima svoju vlastitu kopiju podataka.
	 * 
	 * @param row redak koji treba izbaciti
	 * @param col stupac koji treba izbaciti
	 * @param liveView zeli li se dobiti zivi pogled
	 * @return matricu koja predstavlja podmatricu
	 */

	@Override
	public IMatrix subMatrix(int row, int col, boolean liveView) {
		if (liveView) {
			return new MatrixSubMatrixView(this, row, col);
		} else {
			return new MatrixSubMatrixView(this.copy(), row, col);
		}
	}
	/**
	 * Metoda provodi postupak invertiranja zadane matrice i vraca novu matricu
	 * koja je inverz. Metoda mora biti implementirana uporabom matrice kofaktora
	 * kako je to opisano u knjizi. Ovdje nije naglasak na efikasnosti implementacije.
	 * U slucaju da matrica nije invertibilna, ocekuje se da ce biti izazvana
	 * {@link UnsupportedOperationException}.
	 * 
	 * @return referencu na novu matricu koja je inverz trenutne
	 */
	@Override
	public IMatrix nInvert() {
		if (this.getColsCount() != this.getRowsCount()) {
			throw new IncompatibleOperandException(
					"Only square matrix is possible to invert!");

		}

		double detA = this.determinant();
		if (detA == 0) {
			throw new IllegalArgumentException(
					"Matrix is singular, it has no inverse matrix!");
		}
		return this.cofactor().scalarMultiply(1 / detA).nTranspose(true);
	}
	/**
	 * Metoda koja vraca matricu istih dimenzija kao sto je zadana.
	 * Elementi nove matrice na su algebarski komplementi pocetne matrice,tj
	 * determinante matrice koja se dobije izbacivanjem i-tog retka i j-tog stupca.
	 * @return matrica algebarskih komplemenata pocetne matrice
	 */
	private IMatrix cofactor() {
		int n = this.getColsCount();
		IMatrix algCompl = newInstance(n, n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				double value = Math.pow(-1, i + j);
				IMatrix sub = this.subMatrix(i, j, true);
				Double det = sub.determinant();
				value *= det;
				algCompl.set(i, j, value);
			}
		}
		return algCompl;
	}

	/**
	 * Sadrzaj trenutne matrice kopira u dvodimenzijsko polje koje potom vraca.
	 * 
	 * @return polje s kopijom sadrzaja matrice
	 */
	@Override
	public double[][] toArray() {
		int m = this.getRowsCount();
		int n = this.getColsCount();
		double[][] arr = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				arr[i][j] = this.get(i, j);
			}
		}
		return arr;
	}

	/**
	 * Metoda koja vraća stringovnu reprezentaciju matrice.
	 * Elementi matrice ispisuju se na 3 decimale.
	 */
	@Override
	public String toString() {
		return toString(3);
	}

	/**
	 * Metoda vraća stringovnu reprezentaciju matrice.
	 * Elementi matrice su u prozivoljnoj preciznosti.
	 * @param precision zadana preciznost u kojoj se ispisuju elementi.
	 * @return stringovna reprezentacija.
	 */
	public String toString(int precision) {
		StringBuffer buff = new StringBuffer();
		int stupci, retci;
		
			retci = this.getRowsCount();
			stupci = this.getColsCount();
		
		for (int i = 0; i < retci; i++) {
			buff.append("[");
			for (int j = 0; j < stupci; j++) {
				buff.append(String.format(Locale.ENGLISH, "%." + precision
						+ "f", this.get(i, j)));
				if (j + 1 < stupci) {
					buff.append(", ");
				}
			}
			buff.append("]\n");
		}
		return buff.toString();
	}

	/**
	 * Temeljem trenutne matrice stvara vektor. Ovo je dakako legalno samo ako
	 * je matrica jednoretcana ili jednostupcana. Ako je liveView postavljen na
	 * true, treba se vratiti zivi pogled na trenutnu matricu. Ako je liveView
	 * postavljen na false, treba se vratiti referenca na vektor koji cuva svoju
	 * kopiju podataka.
	 * 
	 * @param liveView zeli li se dobiti zivi pogled
	 * @return referenca na odgovarajuci vektor
	 */
	@Override
	public IVector toVector(boolean liveView) {
		if (this.getColsCount() != 1 && this.getRowsCount() != 1) {
			throw new IncompatibleOperandException(
					"Matrix can be represented as vector only if number of rows or columns is 1");
		}
		return new VectorMatrixView(this);

	}

	/**
	 * Vraća novu matricu čiji su svi elementi jednaki elementima trenutne matrice
	 * pomnoženima s zadanom vrijednosti.
	 * 
	 * @param value vrijednost s kojom se množe svi elementi
	 * @return novu matricu
	 */
	@Override
	public IMatrix nScalarMultiply(double value) {
		return this.copy().scalarMultiply(value);

	}

	/**
	 * Sve elemente trenutne matrice množi sa zadanom vrijednostima.
	 * Vraća referencu na trenutnu matricu.
	 * 
	 * @param value vrijednost s kojom se množe svi elementi
	 * @return referencu na trenutnu matricu
	 */
	@Override
	public IMatrix scalarMultiply(double value) {
		int m = this.getRowsCount();
		int n = this.getColsCount();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				this.set(i, j, this.get(i, j) * value);
			}
		}
		return this;
	}

	/**
	 * Modificira trenutnu matricu tako da postaje jedinična matrica.
	 * 
	 * @return referencu na trenutnu matricu
	 */
	@Override
	public IMatrix makeIdentity() {
		int m = this.getRowsCount();
		int n = this.getColsCount();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					this.set(i, j, 1);
				} else {
					this.set(i, j, 0);
				}
			}

		}
		return this;
	}

}

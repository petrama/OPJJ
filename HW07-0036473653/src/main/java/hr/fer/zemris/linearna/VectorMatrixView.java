package hr.fer.zemris.linearna;

/**
 * Razred koji predtsavlja vektorsku reprezentaciju matrice.
 * Jedino se jednoretčane i jednostupčane matrice mogu prikazati instancom ovog razreda.
 * @author Petra Marče
 *
 */
public class VectorMatrixView extends AbstractVector {
	private int dimension;
	private boolean rowMatrix;
	private IMatrix matrix;

	/**
	 * Konstruktor.
	 * @param matrix originalna matrica.
	 */
	public VectorMatrixView(IMatrix matrix) {
		if (matrix.getRowsCount() == 1) {
			rowMatrix = false;
			dimension = matrix.getColsCount();
		} else {
			if (matrix.getColsCount() == 1) {
				rowMatrix = true;
				dimension = matrix.getRowsCount();
			} else {
				throw new IncompatibleOperandException(
						"Matrix must have one row or column!");
			}

		}
		this.matrix = matrix;
	}

	/**
	 * Metoda za dohvat tražene komponente vektora.
	 * @param index trazena komponenta
	 * @return element
	 */
	@Override
	public double get(int index) {
		if (rowMatrix) {
			return matrix.get(0, index);
		} else {
			return matrix.get(index, 0);
		}
	}
	/**
	 * Metoda koja sluzi za postavljanje zadane komponente vektora na predanu vrijednost.
	 * @param index komponenta koja se postavlja
	 * @param value vrijednost na koju ce se postaviti
	 */
	@Override
	public IVector set(int index, double value) {
		if (rowMatrix) {
			matrix.set(0, index, value);
		} else {
			matrix.set(index, 0, value);
		}
		return this;
	}
	/**
	 * Metoda za dohvat dimenzije vektora.
	 */
	@Override
	public int getDimension() {
		return dimension;
	}
	/**
	 * Metoda koja vraća potpuno neovisnu kopiju trenutnog vektora.
	 */
	@Override
	public IVector copy() {
		return new VectorMatrixView(matrix.copy());
	}
	/**
	 * Metoda koja vraća novi primjerak vektora zadane dimenzije.
	 * @param dimension dimenzija novog vektora
	 * @return novostvoreni vektor
	 */
	@Override
	public IVector newInstance(int dimension) {
		IMatrix nova;
		if (rowMatrix) {
			nova = new Matrix(1, dimension);
		} else {
			nova = new Matrix(dimension, 1);
		}
		return new VectorMatrixView(nova);

	}

}

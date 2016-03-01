package hr.fer.zemris.linearna;

/**
 * Razred koji predtsavlja matričnu reprezentaciju vektora.
 * Vektor je zapravo jednoretčana ili jednostupčana matrica.
 * @author Petra Marče.
 *
 */
public class MatrixVectorView extends AbstractMatrix {
	private boolean asRowMatrix;
	private IVector vector;

	/**
	 * Konstruktor.
	 * @param orig originalni vektor.
	 * @param asRowMatrix zastavica koja označava da li matrica ima jedan redak ili jedan stupac.
	 * Ako je zastavica true matrica ima jedan redak,inače jedan stupac.
	 */
	public MatrixVectorView(IVector orig, boolean asRowMatrix) {
		vector = orig;
		this.asRowMatrix = asRowMatrix;
	}
	
	/**
	 * Metoda za dohvat broja redaka matrice.
	 */
	@Override
	public int getRowsCount() {
		if (asRowMatrix == false) {
			return vector.getDimension();
		} else {
			return 1;
		}
	}

	/**
	 * Metoda za dohvat broja stupaca matrice.
	 */
	@Override
	public int getColsCount() {
		if (asRowMatrix) {
			return vector.getDimension();
		} else {
			return 1;
		}
	}
	/**
	 * Metoda za dohvat elementa na zadanim pozicijama.
	 * @param row redak u kojemu se nalazi element kojeg zelimo dohvatiti.
	 * @param col stupac u kojemu se nalazi element kojeg zelimo dohvatiti.
	 * @return trazeni element.
	 */
	@Override
	public double get(int row, int col) {
		if ((row != 0 && asRowMatrix == true)
				|| (col != 0 && asRowMatrix == false)) {
			throw new IncompatibleOperandException("Wrong indexes");

		}
		if (asRowMatrix) {
			return vector.get(col);
		} else {
			return vector.get(row);
		}
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
		if ((row != 0 && asRowMatrix == true)
				|| (col != 0 && asRowMatrix == false)) {
			throw new IncompatibleOperandException("Wrong indexes");

		}
		if (asRowMatrix) {
			vector.set(col, value);
		} else {
			vector.set(row, value);
		}
		return this;
	}
	
	/**
	 * Metoda koja stvara novi primjerak matrice zadanih dimenzija.
	 */
	@Override
	public IMatrix newInstance(int rows, int cols) {
		if (rows != 1 && cols != 1) {
			throw new IncompatibleOperandException(
					"Vector view can be created only...");
		}
		boolean rowFlag;
		double[] data;
		if (rows == 1) {
			rowFlag = true;
			data = new double[cols];
		} else {
			rowFlag = false;
			data = new double[rows];
		}
		IVector novi = new Vector(data);
		return new MatrixVectorView(novi, rowFlag);

	}
	/**
	 * Metoda stvara neovisnu kopiju trenutne matrice koju potom vraća.
	 */
	@Override
	public IMatrix copy() {
		return new MatrixVectorView(vector.copy(), asRowMatrix);
	}

}

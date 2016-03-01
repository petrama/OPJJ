package hr.fer.zemris.linearna;

/**
 * Razred koji sluzi za stvaranje defaultnih oblika
 * instanci razreda Matrix i Vector.
 * @author Petra Marče
 *
 */
public class LinAlgDefaults {
	/**
	 * Metoda vraća defaultni oblik instance razreda koji implementira sučelje IMatrix.
	 * @param rows broj redaka matrice.
	 * @param cols broj stupaca matrice.
	 * @return novi primjerak matrice.
	 */
	public static IMatrix defaultMatrix(int rows, int cols) {
		return new Matrix(rows, cols);
	}
	/**
	 * Metoda koja vraća defaultni oblik instance razreda koji imlementira sučelje IVector.
	 * @param dimension dimenzija vektora.
	 * @return novi primjerak vektora.
	 */
	public static IVector defaultVector(int dimension) {
		return new Vector(dimension);
	}
}

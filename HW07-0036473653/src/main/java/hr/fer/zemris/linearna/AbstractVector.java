package hr.fer.zemris.linearna;

import java.util.Arrays;

/**
 * Razred predstavlja model jednog proizvoljno
 * velikog vektora (veličina će, naravno, prilikom
 * stvaranja odgovarajućih objekata biti fiksirana
 * u trenutku stvaranja).
 **/

public abstract class AbstractVector implements IVector {
	
	/**
	 * Vraca novi vektor cija je dimenzionalnost jednaka n (argument metode).
	 * Pri tome se iz trenutnog vektora u novi kopira onoliko elemenata
	 * koliko to 'n' dopusti. Ako je novi vektor vece dimenzionalnosti,
	 * ostatak se inicijalizira na vrijednost 0.
	 * @return referencu na novi vektor
	 */
	@Override
	public IVector copyPart(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException(
					"Dimension of vector should be positive!");
		}
		double[] vrijednost = new double[n];
		int duljina = 0;
		if (n > this.getDimension()) {
			duljina = this.getDimension();
		} else {
			duljina = n;
		}
		for (int i = 0; i < duljina; i++) {
			vrijednost[i] = this.get(i);
		}
		return new Vector(vrijednost);
	}
	/**
	 * Trenutni vektor modificira tako sto mu dodaje predani vektor.
	 * @param other vektor za koji se treba uvecati trenutni vektor
	 * @return referencu na trenutni vektor
	 * @throws IncompatibleOperandException ako predani vektor nije kompatibilan s trenutnim
	 */
	@Override
	public IVector add(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException(
					"Operands for 'add' need to be compatible!");
		}
		for (int i = 0; i < this.getDimension(); i++) {
			this.set(i, this.get(i) + other.get(i));
		}
		return this;
	}
	/**
	 * Vraca novi vektor koji je jednak trenutnom vektoru uvecanom za predani vektor.
	 * @param other vektor za koji se treba uvecati trenutni vektor
	 * @return referencu na novi vektor koji predstavlja sumu
	 * @throws IncompatibleOperandException ako predani vektor nije kompatibilan s trenutnim
	 */
	@Override
	public IVector nAdd(IVector other) throws IncompatibleOperandException {
		return this.copy().add(other);
	}
	/**
	 * Trenutni vektor modificira tako sto mu oduzima predani vektor.
	 * @param other vektor za koji se treba umanjiti trenutni vektor
	 * @return referencu na trenutni vektor
	 * @throws IncompatibleOperandException ako predani vektor nije kompatibilan s trenutnim
	 */
	@Override
	public IVector sub(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException(
					"Operands for 'sub' need to be compatible!");
		}
		for (int i = 0; i < this.getDimension(); i++) {
			this.set(i, this.get(i) - other.get(i));
		}
		return this;
	}
	/**
	 * Vraca novi vektor koji je jednak trenutnom vektoru umanjenom za predani vektor.
	 * @param other vektor za koji se treba umanjiti trenutni vektor
	 * @return referencu na novi vektor koji predstavlja razliku
	 * @throws IncompatibleOperandException ako predani vektor nije kompatibilan s trenutnim
	 */
	@Override
	public IVector nSub(IVector other) throws IncompatibleOperandException {
		return (this.copy()).sub(other);
	}
	/**
	 * Trenutni vektor modificira tako sto ga skalarno mnozi sa zadanim skalarom.
	 * @param byValue vrijednost skalara
	 * @return referencu na trenutni vektor
	 */
	@Override
	public IVector scalarMultiply(double byValue) {
		for (int i = 0; i < this.getDimension(); i++) {
			this.set(i, this.get(i) * byValue);
		}
		return this;
	}
	/**
	 * Vraca novi vektor koji je jednak trenutnom koji je pomnozen sa
	 * zadanim skalarom.
	 * @param byValue vrijednost skalara
	 * @return referencu na novi vektor
	 */
	@Override
	public IVector nScalarMultiply(double byValue) {
		return (this.copy()).scalarMultiply(byValue);
	}
	/**
	 * Metoda vraca normu trenutnog vektora.
	 * @return normu
	 */
	@Override
	public double norm() {
		return Math.sqrt(this.scalarProduct(this));

	}
	/**
	 * Metoda normalizira trenutni vektor.
	 * @return referencu na trenutni vektor
	 */
	@Override
	public IVector normalize() {
		Double magnitude = this.norm();
		for (int i = 0; i < this.getDimension(); i++) {
			this.set(i, this.get(i) / magnitude);
		}
		return this;
	}
	/**
	 * Metoda vraca novi vektor koji je jednak normaliziranom trenutnom
	 * vektoru.
	 * @return referencu na novi vektor
	 */
	@Override
	public IVector nNormalize() {
		return this.copy().normalize();
	}
	
	/**
	 * Metoda računa i vraća kosinus kuta između trenutnog vektora i
	 * predanog vektora.
	 * @param other drugi vektor
	 * @return kosinus pripadnog kuta
	 * @throws IncompatibleOperandException ako predani vektor nije kompatibilan s trenutnim 
	 */
	@Override
	public double cosine(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException(
					"Operands for 'cosine' need to be compatible!");
		}
		double scalarProduct = this.scalarProduct(other);
		double magnitudes = this.norm() * other.norm();
		return scalarProduct / magnitudes;
	}
	/**
	 * Metoda racuna skalarni produkt trenutnog vektora i zadanog vektora.
	 * @param other vektor s kojim se trenutni mnozi
	 * @return vrijednost skalarnog produkta
	 * @throws IncompatibleOperandException ako predani vektor nije kompatibilan s trenutnim
	 */
	@Override
	public double scalarProduct(IVector other)
			throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException(
					"Operands for 'scalarProduct' need to be compatible!");
		}
		double sum = 0;
		for (int i = 0; i < this.getDimension(); i++) {
			sum += this.get(i) * other.get(i);
		}
		return sum;
	}
	/**
	 * Metoda vraca novi vektor koji je jednak vektorskom produktu
	 * izmedu trenutnog vektora i zadanog vektora.
	 * @param other vektor s kojim se racuna vektorski produkt
	 * @return referencu na novi vektor
	 * @throws IncompatibleOperandException ako dimenzionalnost trenutnog ili predanog vektora
	 *         nije 3
	 */
	@Override
	public IVector nVectorProduct(IVector other)
			throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException(
					"For operation 'VectorProduct' vectors must have same dimensions!");
		}
		double[] vrijednost = new double[this.getDimension()];
		for (int i = 0, duljina = this.getDimension(); i < duljina; i++) {
			vrijednost[i] = this.get((i + 1) % duljina)
					* other.get((i + 2) % duljina)
					- this.get((i + 2) % duljina)
					* other.get((i + 1) % duljina);
		}
		return new Vector(vrijednost);
	}
	/**
	 * Vraca vektor u radom prostoru koji se dobije iz trenutnog ako se
	 * trenutni promatra kao da je u homogenom prostoru. To ce biti
	 * vektor za jedan manje dimenzionalnosti od trenutnog kod kojeg su
	 * sve komponente jednake trenutnim podijeljenim sa zadnjom.
	 * @return referencu na novi vektor
	 */
	@Override
	public IVector nFromHomogeneus() {
		if (this.getDimension() < 2) {
			throw new IncompatibleOperandException(
					"Dimension must be at least 2!");
		}
		IVector homo = copyPart(getDimension() - 1);
		homo.scalarMultiply(1 / this.get(getDimension() - 1));
		return homo;

	}
	/**
	 * Trenutni vektor konvertira u jednoretcanu matricu. Ako je liveView postavljen
	 * na true, vraca objekt koji je zivi pogled na taj vektor -- primjerice, ako se
	 * u vektoru promijeni neka komponenta, i ovaj pogled ce toga biti svjestan; ako
	 * korisnik preko dobivene matrice napravi promjenu, ta ce promjena biti vidljiva
	 * i u vektoru. Ako je liveView postavljen na false, generirana matrica cuva
	 * kopiju izvornih podataka.
	 *  
	 * @param liveView treba li vratiti zivi pogled
	 * @return matrica dobivena temeljem trenutnog vektora
	 */
	@Override
	public IMatrix toRowMatrix(boolean liveView) {
		if (liveView) {
			return new MatrixVectorView(this, true);
		}
		return new MatrixVectorView(this.copy(), true);
	}
	/**
	 * Trenutni vektor konvertira u jednostupcanu matricu. Ako je liveView postavljen
	 * na true, vraca objekt koji je zivi pogled na taj vektor -- primjerice, ako se
	 * u vektoru promijeni neka komponenta, i ovaj pogled ce toga biti svjestan; ako
	 * korisnik preko dobivene matrice napravi promjenu, ta ce promjena biti vidljiva
	 * i u vektoru. Ako je liveView postavljen na false, generirana matrica cuva
	 * kopiju izvornih podataka.
	 *  
	 * @param liveView treba li vratiti zivi pogled
	 * @return matrica dobivena temeljem trenutnog vektora
	 */
	@Override
	public IMatrix toColumnMatrix(boolean liveView) {
		if (liveView) {
			return new MatrixVectorView(this, false);
		}
		return new MatrixVectorView(this.copy(), false);
	}
	/**
	 * Vraca kopiju vektora predstavljenog kao polje.
	 * @return polje
	 */
	@Override
	public double[] toArray() {
		double[] arr = new double[this.getDimension()];
		for (int i = 0; i < this.getDimension(); i++) {
			arr[i] = this.get(i);
		}
		return arr;
	}
	/**
	 * Vraća stringovnu reprezentaciju vektora.
	 */
	@Override
	public String toString() {
		return Arrays.toString(toArray());
	}

}

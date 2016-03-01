package hr.fer.zemris.linearna;

import java.util.Arrays;
/**
 * Razred koji predstavlja konkretnu implementaciju vektora.
 * Komponente vektora se čuvaju u jednodimenzionalnom polju.
 * @author Petra Marče
 *
 */
public class Vector extends AbstractVector {
	private double[] elements;
	private int dimension;
	private boolean readOnly;

	/**
	 * Konstruktor.
	 * Stvara novu instanct razreda.
	 * @param readOnly zastavica koja ako je postavljena oznacava da se vektor ne smije mjenjati.
	 * @param privateArray zastavica koja ako je postavljena oznacava da se predano polje moze koristiti bez kopiranja.
	 * @param data polje elemenata.
	 */
	public Vector(boolean readOnly, boolean privateArray, double[] data) {
		if (privateArray == true) {
			elements = data;
		} else {
			elements = Arrays.copyOf(data, data.length);
		}
		dimension = elements.length;
		this.readOnly = readOnly;
	}
	/**
	 * Konstruktor koji prima varijabilni broj argumenata koji se tumače kao komponente vektora.
	 * @param vrijdnostVektora
	 */
	public Vector(double... vrijednostVektora) {
		this(false, false, vrijednostVektora);
	}
	/**
	 * Metoda koja stvara novu instancu vektora iz znakovnog niza.
	 * U nizu su komponente odvojene razmacima.
	 * @param initString string iz kojeg nastaje vektor.
	 * @return nova instanca razreda dobivena parsiranjem niza.
	 */
	public static Vector parseSimple(String initString) {
		initString = initString.trim();
		String[] elementsString = initString.split("\\s+");
		double[] elements = new double[elementsString.length];
		for (int i = 0; i < elements.length; i++) {
			try {
				elements[i] = Double.parseDouble(elementsString[i]);
			} catch (NumberFormatException ne) {
				throw new IllegalArgumentException(
						"Illegal string to parse into vector");
			}
		}
		return new Vector(false, true, elements);
	}
	
	/**
	 * Metoda koja provjerava da li je zadani broj u zadanim granicama.
	 * @param index broj koji se provjerava.
	 * @param lower donja granica
	 * @param upper gornja granica
	 */
	public static void checkIndex(int index, int lower, int upper) {
		if(lower>upper){
			throw new IllegalArgumentException("Lower boundary should be smaller than higher!");
		}
		if (index < lower || index > upper) {
			throw new IllegalArgumentException("Index must be between " + lower
					+ " and " + upper);
		}
	}

	/**
	 * Metoda za dohvat tražene komponente vektora.
	 * @param index trazena komponenta
	 * @return element
	 */
	@Override
	public double get(int index) {
		checkIndex(index, 0, dimension - 1);
		return elements[index];
	}
	/**
	 * Metoda koja sluzi za postavljanje zadane komponente vektora na predanu vrijednost.
	 * @param index komponenta koja se postavlja
	 * @param value vrijednost na koju ce se postaviti
	 */
	@Override
	public IVector set(int index, double value)
			throws UnmodifiableObjectException {
		if (this.readOnly == true) {
			throw new UnmodifiableObjectException("This object is read-only!");
		}
		checkIndex(index, 0, dimension - 1);
		elements[index] = value;
		return this;
	}

	/**
	 * Metoda za dohvat dimenzije vektora.
	 */
	@Override
	public int getDimension() {
		return this.dimension;
	}

	/**
	 * Metoda koja vraća potpuno neovisnu kopiju trenutnog vektora.
	 */
	@Override
	public IVector copy() {

		return new Vector(this.elements);
	}

	/**
	 * Metoda koja vraća novi primjerak vektora zadane dimenzije.
	 * @param dimension dimenzija novog vektora
	 * @return novostvoreni vektor
	 */
	@Override
	public IVector newInstance(int dimension) {
		if (dimension < 1) {
			throw new IllegalArgumentException(
					"Dimension of vector must be positive!");
		}
		double[] novo = new double[dimension];
		return new Vector(novo);

	}

	/**
	 * Metoda koja racuna hash-vrijednost vektora te je potom vraća.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dimension;
		result = prime * result + Arrays.hashCode(elements);
		result = prime * result + (readOnly ? 1231 : 1237);
		return result;
	}

	/**
	 * Metoda koja utvrđuje da li su dva vektora jednaka.
	 * Vraća true ako su jednaki,false inač.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector other = (Vector) obj;
		if (dimension != other.dimension)
			return false;
		if (!Arrays.equals(elements, other.elements))
			return false;
		if (readOnly != other.readOnly)
			return false;
		return true;
	}

}

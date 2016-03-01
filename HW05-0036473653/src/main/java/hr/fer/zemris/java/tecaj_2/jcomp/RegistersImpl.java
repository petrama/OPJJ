package hr.fer.zemris.java.tecaj_2.jcomp;

import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
/**
 * Razred koji predstavlja registre racunala.
 * @author Petra Marče
 *
 */
public class RegistersImpl implements Registers {
	private Object[] registriOpceNamjene;
	private int programCounter;
	private Boolean flag;
	/**
	 * Konstruktor ocekuje broj registara.
	 * @param numOfRegisters
	 */
	public RegistersImpl(int numOfRegisters) {
		registriOpceNamjene = new Object[numOfRegisters];

	}
	/**
	 * Metoda koja provjerava da li je zadani indeks u određenom intervalu.
	 * @param index indeks koji se ispituje.
	 * @param low donja granica intervala.
	 * @param high gornja granica intervala.
	 * Ako indeks nije valjan metoda bacaa iznimku.
	 */
	public static void checkIndex(int index, int low, int high) {
		if (index < low || index > high)
			throw new IndexOutOfBoundsException("Index is invalid!");
		return;
	}
	/**
	 * Metoda koja vraća vrijednost registra rx,
	 * gdje je x oznaka registra.
	 */
	public Object getRegisterValue(int index) {
		checkIndex(index, 0, registriOpceNamjene.length - 1);
		return registriOpceNamjene[index];
	}
	/**
	 * Metoda u zadani registar upisuje predani objekt.
	 */
	public void setRegisterValue(int index, Object value) {
		checkIndex(index, 0, registriOpceNamjene.length - 1);
		registriOpceNamjene[index] = value;
	}
	/**
	 * @return Metoda vraća sadrzaj programskog brojila.
	 */
	public int getProgramCounter() {
		return programCounter;

	}
	/**
	 * Metoda postavljasadrzaj programskog brojila na neku vrijednost.
	 */
	public void setProgramCounter(int value) {
		programCounter = value;
	}

	/**
	 * Metoda vrijednost programskog brojila povecava za jedan.
	 */
	public void incrementProgramCounter() {
		programCounter++;

	}
	/**
	 * Metoda vraća sadrzaj registra zastavice.
	 */
	public boolean getFlag() {
		return flag;
	}
	/**
	 * Metoda postavlja zastavicu.
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}

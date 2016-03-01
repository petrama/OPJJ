package hr.fer.zemris.java.tecaj_2.jcomp;

/**
 * Razred koji predstavlja računalo.
 * @author Petra Marče
 *
 */
public class ComputerImpl implements Computer {
	private RegistersImpl registers;
	private MemoryImpl memory;

	/**
	 * Konstruktor.
	 * Metoda stvara novi primjerak razreda.
	 * @param sizeOfMem ulazni parametar veličina memorije mora biti veća od nule.
	 * @param sizeOfReg broj registara, mora biti veći od nule.
	 */
	public ComputerImpl(int sizeOfMem, int sizeOfReg) {
		if (sizeOfMem <= 0 || sizeOfReg <= 0) {
			throw new IllegalArgumentException(
					"Size of memory and registers must be positive!");
		}
		registers = new RegistersImpl(sizeOfReg);
		memory = new MemoryImpl(sizeOfMem);
	}

	/**
	 * Metoda koja vraća skup registara računala.
	 */
	@Override
	public RegistersImpl getRegisters() {
		return registers;
	}

	/**
	 * Metoda vraća sve memorijske lokacije računala.
	 */
	@Override
	public MemoryImpl getMemory() {
		return memory;
	}

}

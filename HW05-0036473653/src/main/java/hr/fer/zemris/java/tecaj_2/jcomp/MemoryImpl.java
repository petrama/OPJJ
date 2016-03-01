package hr.fer.zemris.java.tecaj_2.jcomp;

import hr.fer.zemris.java.tecaj_2.jcomp.Memory;
/**
 * Razred koji predstavlja memoriju racunala.
 * @author Petra Marče
 *
 */
public class MemoryImpl implements Memory {
	private Object[] memory;
	/**
	 * Konstruktor.
	 * @param sizeOfMemory ocekuje velicinu memorije.
	 */
	public MemoryImpl(int sizeOfMemory) {
		memory = new Object[sizeOfMemory];
	}
	/**
	 * Metoda koja na zadanu lokaciju upisuje predanu vrijednost.
	 */
	public void setLocation(int location, Object value) {
		RegistersImpl.checkIndex(location, 0, memory.length - 1);
		memory[location] = value;
	}
	/**
	 * Metoda koja dohvaća podatak s zadane memorijske lokacije.
	 */
	public Object getLocation(int location) {
		RegistersImpl.checkIndex(location, 0, memory.length - 1);
		return memory[location];
	}

}

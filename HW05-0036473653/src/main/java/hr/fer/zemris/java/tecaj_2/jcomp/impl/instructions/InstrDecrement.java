package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
/**
 * Razred koji predstavlja instrukciju koja umanjuje vrijednost registra za 1.
 * @author Petra Marče
 *
 */
public class InstrDecrement implements Instruction {
	int indexRegistra1;
	/**
	 * Konstruktor.
	 * @param arguments ocekuje jedan argument,reistar ciji sadrzaj treba umanjiti za 1.
	 */
	public InstrDecrement(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}

		this.indexRegistra1 = ((Integer) arguments.get(0).getValue())
				.intValue();

	}
	/**
	 * Metoda koja predstavlja izvršavanje instrukcije.
	 * Vraća false ako je instrukcija izvršena ispravno.
	 */

	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters()
				.getRegisterValue(indexRegistra1);

		computer.getRegisters().setRegisterValue(indexRegistra1,
				Integer.valueOf(((Integer) value1).intValue() - 1));
		return false;
	}

}

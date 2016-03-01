package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Razred koji predstavlja instrukciju učitavanja vrijednosti
 * zadane memorijske lokacije u zadani registar.
 * @author Petra Marče
 *
 */
public class InstrLoad implements Instruction {
	private int indexRegister;
	private int memoryLocation;
	
	/**
	 * Konstruktor.
	 * @param arguments očekuju se dva argumenta,registar i memorijska lokacija.
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if (!arguments.get(1).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		indexRegister = ((Integer) arguments.get(0).getValue()).intValue();
		memoryLocation = ((Integer) arguments.get(1).getValue()).intValue();

	}
	/**
	 * Metoda cijim se pozivom instrukcija izvrsava.
	 */
	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(indexRegister,
				computer.getMemory().getLocation(memoryLocation));
		return false;
	}
}

package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

public class InstrAdd implements Instruction {
	private int indexRegistra1;
	private int indexRegistra2;
	private int indexRegistra3;
	/**
	 * Konstruktor.
	 * Ocekuje tri argumenta.Odredisni registar, te dva registra ciji se sadrzaj zbraja.
	 * @param arguments
	 */
	public InstrAdd(List<InstructionArgument> arguments) {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if (!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		if (!arguments.get(2).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 2!");
		}
		this.indexRegistra1 = ((Integer) arguments.get(0).getValue())
				.intValue();
		this.indexRegistra2 = ((Integer) arguments.get(1).getValue())
				.intValue();
		this.indexRegistra3 = ((Integer) arguments.get(2).getValue())
				.intValue();
	}
	/**
	 * Metoda cijim se pozivom izvrsava instrukcija.
	 */
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters()
				.getRegisterValue(indexRegistra2);
		Object value2 = computer.getRegisters()
				.getRegisterValue(indexRegistra3);
		computer.getRegisters().setRegisterValue(
				indexRegistra1,
				Integer.valueOf(((Integer) value1).intValue()
						+ ((Integer) value2).intValue()));
		return false;
	}
}

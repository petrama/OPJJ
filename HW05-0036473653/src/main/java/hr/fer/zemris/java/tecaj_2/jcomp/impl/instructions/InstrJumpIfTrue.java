package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
/**
 * Razred predstavlja instrukciju uvjetnog skoka.
 * @author Petra Marče
 *
 */
public class InstrJumpIfTrue implements Instruction {
	int memLocation;
	/**
	 * Konstruktor.
	 * @param arguments ocekuje samo jedan element u listi argumenata.
	 * Memorijsku lokaciju na koju treba skociti.
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		this.memLocation = ((Integer) arguments.get(0).getValue()).intValue();
	}
	/**
	 * Metoda cijim se pozivom instrukcija izvrsava.
	 */
	public boolean execute(Computer computer) {
		if (computer.getRegisters().getFlag()) {
			computer.getRegisters().setProgramCounter(memLocation);
		}
		return false;
	}

}

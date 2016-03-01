package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Razred predstavlja instrukciju kopiranja sadržaja drugog registra u prvi registar.
 * @author Petra Marče
 *
 */
public class InstrMove implements Instruction {
	private int indexRegistra1;
	private int indexRegistra2;
	/**
	 * Konstruktor.
	 * @param arguments očekuje dva registra,vrijednost će sa drugoga biti zapisana u prvi.
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if (!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		this.indexRegistra1 = ((Integer) arguments.get(0).getValue())
				.intValue();
		this.indexRegistra2 = ((Integer) arguments.get(1).getValue())
				.intValue();
	}
	/**
	 * Metoda cijim se pozivom instrukcija izvrsava.
	 */
	public boolean execute(Computer computer) {
		
		Object value2 = computer.getRegisters()
				.getRegisterValue(indexRegistra2);
		computer.getRegisters().setRegisterValue(indexRegistra1,
				Integer.valueOf(((Integer) value2).intValue()));
		
		return false;
	}
}

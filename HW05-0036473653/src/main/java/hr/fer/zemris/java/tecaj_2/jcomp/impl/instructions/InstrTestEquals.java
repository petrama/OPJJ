package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
/**
 * Razred koji predstavlja instrukciju koja ispituje jednakost dva registra.
 * @author Petra Marče
 */
public class InstrTestEquals implements Instruction {
	private int indexRegistra1;
	private int indexRegistra2;
	
	/**
	 * Konstruktor
	 * @param arguments očekuje dva registra ciji ce sadrzaj biti uspoređen.
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
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
		Object value1 = computer.getRegisters()
				.getRegisterValue(indexRegistra1);
		Object value2 = computer.getRegisters()
				.getRegisterValue(indexRegistra2);
		boolean postavi = false;
		if (value1.equals(value2)) {
			postavi = true;
		}
		computer.getRegisters().setFlag(postavi);
		return false;
	}
}

package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.util.List;

/**
 * Razred simbolizira instrukciju ispisa.
 * @author Petra Marče
 */
public class InstrEcho implements Instruction {
	private int index;
	/**
	 * Konstruktor.
	 * @param arguments ocekuje jedan argument kojeg treba ispisati.
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		
		index = ((Integer) arguments.get(0).getValue()).intValue();

	}

	/**
	 * Pozivom ove metode izvršava se instrukcija.
	 */
	public boolean execute(Computer computer) {
		System.out.println(computer.getRegisters().getRegisterValue(index));
		return false;
	}
}

package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
/**
 * Razred koji simbolizira instrukciju kojom se zaustavlja procesor.
 * @author Petra Marče
 *
 */
public class InstrHalt implements Instruction{
	
	public InstrHalt(List<InstructionArgument> arguments){
		
	}
	
	@Override
	public boolean execute(Computer computer) {
	
		return true;
	}
}

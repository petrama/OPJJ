package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.Arrays;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.ComputerImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgumentImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
import hr.fer.zemris.java.tecaj_2.jcomp.RegistersImpl;

import org.junit.Test;
import org.mockito.Mockito;

public class InstrJumpTest {
	@Test
	public void getJumpTested() {
		Computer comp=Mockito.mock(ComputerImpl.class);
		Registers reg=Mockito.mock(RegistersImpl.class);
		
		InstructionArgument arg2 = new InstructionArgumentImpl("100");

		Mockito.when(comp.getRegisters()).thenReturn(reg);
		
		//stvaranje i izvođenje instrukcije
		
		Instruction instrJump=new InstrJump(Arrays.asList(arg2));
		instrJump.execute(comp);
		
		//provjeri je li skup registara zatražen jednom;
		Mockito.verify(comp, Mockito.times(1)).getRegisters();
		
		//provjeri je li se registru r1 pristupilo tocno jednom
		Mockito.verify(reg).setProgramCounter(new Integer(100));
		
			
	
	
	}
	
	

}

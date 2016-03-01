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

public class InstrJumpIfTrueTest {
	@Test
	public void getJumpifTrueTested() {
		Computer comp=Mockito.mock(ComputerImpl.class);
		Registers reg=Mockito.mock(RegistersImpl.class);
		
		//memorijska lokacija
		InstructionArgument arg2 = new InstructionArgumentImpl("100");
		
		//Definiranje sto mockito treba raditi.
		Mockito.when(comp.getRegisters()).thenReturn(reg);
		Mockito.when(reg.getFlag()).thenReturn(true);
		
		//stvaranje i izvođenje instrukcije
		Instruction instrLoad=new InstrJumpIfTrue(Arrays.asList(arg2));
		instrLoad.execute(comp);
		
		//provjeri je li skup registara zatražen dva puta;
		Mockito.verify(comp, Mockito.times(2)).getRegisters(); 
		
		//provjeri je li zastavica ispitivana jednom.
		Mockito.verify(reg).getFlag();
		
		//provjeri je li se registru r1 vrijednost upisala jednom
		Mockito.verify(reg).setProgramCounter(new Integer(100));
		
			
	
	
	}
	
	

}

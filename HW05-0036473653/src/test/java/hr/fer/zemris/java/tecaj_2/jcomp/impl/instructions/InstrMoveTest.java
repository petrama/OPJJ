package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.ComputerImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgumentImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
import hr.fer.zemris.java.tecaj_2.jcomp.RegistersImpl;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

public class InstrMoveTest {


		@Test
		public void getMoveTested() {
			
		  Computer comp=Mockito.mock(ComputerImpl.class);
			Registers reg=Mockito.mock(RegistersImpl.class);
			
			//stvaranje argumenata za naredbu Decrement
			InstructionArgument arg1 = new InstructionArgumentImpl("r1");
			InstructionArgument arg2 = new InstructionArgumentImpl("r2");

			
			
			//Postavljanje registara r1 r2.
			Mockito.when(comp.getRegisters()).thenReturn(reg);
			Mockito.when(reg.getRegisterValue(1)).thenReturn(new Integer(100));
			Mockito.when(reg.getRegisterValue(2)).thenReturn(new Integer(5));
		
			
			//stvaranje i izvođenje instrukcije
			
			Instruction instrMove=new InstrMove(Arrays.asList(arg1,arg2));
			instrMove.execute(comp);
			
			//provjeri je li skup registara zatražen dva puta;
			Mockito.verify(comp, Mockito.times(2)).getRegisters();
			
			//provjeri je li se registru r1 pristupilo tocno jednom
					Mockito.verify(reg).setRegisterValue(1, 5);
			
			//provjeri je li se registru r2 pristupilo tocno jednom
			Mockito.verify(reg).getRegisterValue(2);
				
					
	}

}



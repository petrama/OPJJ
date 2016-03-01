package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;
import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.ComputerImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgumentImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.Memory;
import hr.fer.zemris.java.tecaj_2.jcomp.MemoryImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
import hr.fer.zemris.java.tecaj_2.jcomp.RegistersImpl;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

public class InstrLoadTest {

       @Test
		public void getMoveTested() {
			
		  Computer comp=Mockito.mock(ComputerImpl.class);
		  Memory mem=Mockito.mock(MemoryImpl.class);
		  Registers reg=Mockito.mock(RegistersImpl.class);
			
			//stvaranje argumenata za naredbu Decrement
			InstructionArgument arg1 = new InstructionArgumentImpl("r1");
			InstructionArgument arg2 = new InstructionArgumentImpl("100");

			
			
			//Postavljanje registara r1 r2.
			Mockito.when(comp.getRegisters()).thenReturn(reg);
			Mockito.when(comp.getMemory()).thenReturn(mem);
			Mockito.when(mem.getLocation(100)).thenReturn(new Integer(25));
			
		
			
			//stvaranje i izvođenje instrukcije
			
			Instruction instrLoad=new InstrLoad(Arrays.asList(arg1,arg2));
			instrLoad.execute(comp);
			
			//provjeri je li pristup memoriji zatražen samo jednom;
			Mockito.verify(comp).getMemory();
			
			//provjeri je li se registru r1 pristupilo tocno jednom i vrijednost postavila ispravno
					Mockito.verify(reg).setRegisterValue(1, new Integer(25));
			
				
					
	}

}



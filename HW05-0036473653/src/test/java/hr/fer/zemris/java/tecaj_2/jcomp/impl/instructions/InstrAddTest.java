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
import org.mockito.*;


public class InstrAddTest {
	@Test
	public void getAdd110Plus90Tested() {
		
	  Computer comp=Mockito.mock(ComputerImpl.class);
		Registers reg=Mockito.mock(RegistersImpl.class);
		
		//stvaranje argumenata za naredbu Add
		InstructionArgument arg1 = new InstructionArgumentImpl("r4");
		InstructionArgument arg2 = new InstructionArgumentImpl("r5");
		InstructionArgument arg3 = new InstructionArgumentImpl("r6");
		
		//definicija toga šta mock radi
		Mockito.when(comp.getRegisters()).thenReturn(reg);
		Mockito.when(reg.getRegisterValue(5)).thenReturn(new Integer(110));
		Mockito.when(reg.getRegisterValue(6)).thenReturn(new Integer(90));
		
		//stvaranje i izvođenje instrukcije
		Instruction instrAdd=new InstrAdd(Arrays.asList(arg1,arg2,arg3));
		instrAdd.execute(comp);
		
		//provjeri je li skup registara zatražen tri puta;
		Mockito.verify(comp, Mockito.times(3)).getRegisters();
		
		//provjeri je li se registru r5 pristupilo tocno jednom
				Mockito.verify(reg).getRegisterValue(5);
				
		//provjeri je li se registru r6 pristupilo tocno jendom
	    Mockito.verify(reg).getRegisterValue(6);
				
		//provjeri je li se registru r4 pristupilo tocno jednom s upisom vrijednosti 40 (20+20)
		Mockito.verify(reg).setRegisterValue(4, new Integer(200));
		
	}
}

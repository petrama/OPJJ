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

public class InstrEqualsTest {


		@Test
		public void getEqualsTestedWhenEq() {
			
		  Computer comp=Mockito.mock(ComputerImpl.class);
			Registers reg=Mockito.mock(RegistersImpl.class);
			
			//stvaranje argumenata za naredbu 
			InstructionArgument arg1 = new InstructionArgumentImpl("r1");
			InstructionArgument arg3 = new InstructionArgumentImpl("r3");
			
			
			//Postavljanje registara r1 i r3.
			Mockito.when(comp.getRegisters()).thenReturn(reg);
			Mockito.when(reg.getRegisterValue(1)).thenReturn(new Integer(100));
			Mockito.when(reg.getRegisterValue(3)).thenReturn(new Integer(100));
		
			
			//stvaranje i izvođenje instrukcije
			Instruction instrEqual=new InstrTestEquals(Arrays.asList(arg1,arg3));
			instrEqual.execute(comp);
//			Instruction instrNotEqual=new InstrEcho(Arrays.asList(arg1,arg2));
//		  instrNotEqual.execute(comp);
			
			//provjeri je li skup registara zatražen tri puta;
			Mockito.verify(comp, Mockito.times(3)).getRegisters();
			
			//provjeri je li se registru r1 pristupilo tocno jednom
			Mockito.verify(reg).getRegisterValue(1);
		

			//provjeri je li se zastavica postavila na true
			 Mockito.verify(reg).setFlag(true);
			
		
	}
		@Test
		public void getEqualsTestedWhenNotEq() {
			
		   Computer comp=Mockito.mock(ComputerImpl.class);
			Registers reg=Mockito.mock(RegistersImpl.class);
			
			//stvaranje argumenata za naredbu
			InstructionArgument arg1 = new InstructionArgumentImpl("r1");
			InstructionArgument arg3 = new InstructionArgumentImpl("r3");
			
			
			//Postavljanje registara r1 i r3.
			Mockito.when(comp.getRegisters()).thenReturn(reg);
			Mockito.when(reg.getRegisterValue(1)).thenReturn(new Integer(200));
			Mockito.when(reg.getRegisterValue(3)).thenReturn(new Integer(100));
		
			
			//stvaranje i izvođenje instrukcije
			Instruction instrEqual=new InstrTestEquals(Arrays.asList(arg1,arg3));
			instrEqual.execute(comp);
			
			//provjeri je li skup registara zatražen tri puta;
			Mockito.verify(comp, Mockito.times(3)).getRegisters();
			
			//provjeri je li se registru r1 pristupilo tocno jednom
					Mockito.verify(reg).getRegisterValue(1);
			
			//provjeri je li se zastavica postavila na false
			 Mockito.verify(reg).setFlag(false);
			
		
	}

}


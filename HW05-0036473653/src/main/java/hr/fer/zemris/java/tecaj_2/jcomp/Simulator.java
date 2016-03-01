package hr.fer.zemris.java.tecaj_2.jcomp;

import java.io.File;


import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.ExecutionUnit;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionCreator;
import hr.fer.zemris.java.tecaj_2.jcomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.parser.ProgramParser;

/**
 * Razred koji simulira prevođenje i izvođenje programa pisanih u strojnom kodu.
 * @author Petra Marče
 *
 */
public class Simulator {
	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * @param args argumenti iz komandne linije.
	 * Ocekuje se jedan argument-putanja do datoteke pisane u strojnom kodu.
	 */
	public static void main(String []args) {
		
		if (args.length != 1) {
			System.err.println("Please provide sigle argument:path!");
			System.exit(1);
		}
		File dat = new File(args[0]);
		if (dat.isFile() == false) {
			System.err.println("Path does not represent file!");
		}
		
		// Stvori računalo s 256 memorijskih lokacija i 16 registara
		Computer comp = new ComputerImpl(256, 16);
		// Stvori objekt koji zna stvarati primjerke instrukcija
		InstructionCreator creator = new InstructionCreatorImpl(
		"hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions"
		);
		// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
		// uporabom predanog objekta za stvaranje instrukcija
		
		try {
			ProgramParser.parse(args[0], comp, creator);
		} catch (Exception e) {
			System.err.println("File cannot be parsed!");
			System.exit(1);
		}
		
		ExecutionUnit exec = new ExecutionUnitImpl();
		// Izvedi program
		exec.go(comp);
	}

}

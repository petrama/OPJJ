package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
/**
 * Razred koji predstavlja instrukciju koja prima cijeli broj iz standardnog ulaza.
 * Zatim taj broj zapisuje na zadanu memorijsku lokaciju.
 * @author Petra Marče
 *
 */
public class InstrIinput implements Instruction {
	private int memoryLocation;
	/**
	 * Konstruktor.
	 * @param arguments ocekuje jedan argument,memorijsku lokaciju, 
	 * na koju ce uneseni element biti spremljen.
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		memoryLocation = ((Integer) arguments.get(0).getValue()).intValue();
	}

	/**
	 * Metoda cijim se pozivom instrukcija izvrsava.
	 * Ako korisnik unese cijeli broj, taj broj se zapisuje na memorijsku
	 * lokaciju, i zastavica se postavlja na true.
	 * Ako korisnik unese broj koji se ne moze tumaciti kao cijeli broj,
	 * sadrzaj memorijske lokacije se ne mjenja,a zastavica je setirana na false.
	 */
	public boolean execute(Computer computer) {
		Scanner scan = new Scanner(System.in, Charset.forName("UTF-8").toString());
		
		Integer broj;
		System.out.println("Unesite početni broj:");
		try {
			broj = scan.nextInt();
		} catch (InputMismatchException e) {

			System.out.println("Ulaz nije moguće protumačiti kao cijeli broj.");
			computer.getRegisters().setFlag(false);
			return false;
		}
		scan.close();
		computer.getMemory().setLocation(memoryLocation, broj);
		computer.getRegisters().setFlag(true);
		return false;
	}

}

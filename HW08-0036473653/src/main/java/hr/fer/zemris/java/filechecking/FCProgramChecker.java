package hr.fer.zemris.java.filechecking;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.filechecking.fmagician.lexical.FMagicianTokenizer;
import hr.fer.zemris.java.filechecking.fmagician.lexical.FMagicianTokenizerException;
import hr.fer.zemris.java.filechecking.fmagician.syntax.FMagicianSyntaxException;
import hr.fer.zemris.java.filechecking.fmagician.syntax.Parser;
/**
 * Razred koji obavlja leksičku i sintaksnu analizu teksta.
 * @author Petra Marče
 *
 */
public class FCProgramChecker {
	/** Skripta sa provjerama cija se leksicka i sintaksna analiza provodi **/
	private String program;
	/** Lista pogresaka **/
	private List<String> errorList;

	/**
	 * Konstruktor.
	 * @param program prima skriptu kao string.
	 */
	public FCProgramChecker(String program) {
		this.program = program;

	}

	/**
	 * Metoda koja određuje da li je leksička i sintaksna analiza prosla uspjesno.
	 * @return Vraća true ako je pri analizi doslo do pogreske, false inače.
	 */
	public boolean hasErrors() {
		errorList = new ArrayList<>();
		FMagicianTokenizer tokenizer = new FMagicianTokenizer(program);
		Parser parser = new Parser(tokenizer);

		while (true) {

			try {
				parser.getProgramNode();

			} catch (FMagicianTokenizerException t) {
				errorList.add(t.toString());
			} catch (FMagicianSyntaxException s) {
				errorList.add(s.toString());
			}

			if (errorList.isEmpty()) {
				return false;
			}
			return true;
		}

	}
	/**
	 * Metoda koja vraća listu pogrešaka.
	 * @return lista pogrešaka.
	 */
	public List<String> errors() {
		return errorList;
	}
}

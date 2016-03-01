package hr.fer.zemris.java.filechecking;

import hr.fer.zemris.java.filechecking.fmagician.lexical.FMagicianTokenizer;
import hr.fer.zemris.java.filechecking.fmagician.syntax.Parser;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.ProgramNode;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.visitors.ProgramExecutorVisitor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Razred koji obavlja izvođenje programa. Na zaslon ispisuje je li predana
 * datoteka valjana ili nije.
 * 
 * @author Petra Marče
 * 
 */
public class FCFileVerifier {
	/** Datoteka koja se provjerava **/
	private File file;
	/** Stvarno ime datoteke **/
	private String fileName;
	/** skripta spremna za obradu **/
	private String program;
	/** mapa inicijalnih vrijednosti **/
	private Map<String, Object> initialData;
	/** lista pogreski **/
	private List<String> errors;

	/**
	 * Konstruktor.
	 * @param file datoteka za provjeru.
	 * @param fileName stvarno ime datoteke.
	 * @param program skripta provjera.
	 * @param initialData mapirane inicijalne vrijednosti varijabli.
	 */
	public FCFileVerifier(File file, String fileName, String program,
			Map<String, Object> initialData) {
		super();
		this.file = file;
		this.fileName = fileName;
		this.program = program;
		this.initialData = initialData;
		errors = new ArrayList<>();
	}

	/**
	 * Metoda koja određuje da li je izvođenje proteklo uspješno ili ne.
	 * @return Metoda vraća true ako su se dogodile pogreške, false inače.
	 */
	public boolean hasErrors() {
		ProgramNode pr = (new Parser((new FMagicianTokenizer(program))))
				.getProgramNode();
		ProgramExecutorVisitor executor = new ProgramExecutorVisitor(
				initialData, file, pr, fileName);
		do {
			executor.execute();
		} while (executor.isActive());
		if (executor.getErrorList().isEmpty()) {
			return false;
		} else {
			errors = executor.getErrorList();
			return true;
		}
	}

	/**
	 * Metoda koja vraća listu pogresaka koje su nastale u izvođenju.
	 * @return vraća nisu pogresaka.
	 */
	public List<String> errors() {
		return errors;
	}

}

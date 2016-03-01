package hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.visitors;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import hr.fer.zemris.java.filechecking.fmagician.syntax.FMagicianNodeVisitor;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.DefStatement;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.ExistsStatement;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.FMagicianNode;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.FailStatement;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.FileNameStatement;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.FormatStatement;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.MessageNode;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.ProgramNode;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.TerminateStatement;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.TextNode;

/**
 * Stroj za izvođenje naredbi napisanih jezikom <i>fmagician<i>.
 * @author Petra Marče.
 *
 */
public class ProgramExecutorVisitor implements FMagicianNodeVisitor {
	/** Mapa definiranih varijabli **/
	private Map<String, Object> variables;
	/** Predana datoteka cija se valjanost ispituje **/
	private File file;
	/** Stablo naredbi programa **/
	private ProgramNode tree;
	/** Zastavica koja ako je true označava da je izvođenje u tijeku **/
	private boolean active;
	/** Lista opisa pogreški koja se puni prilikom izvođenja **/
	private List<String> errorList;
	/** Skup svih datoteka iz predanog fajla **/
	private Set<String> setOfFiles;
	/** Skup svih direktorija iz predanog fajla **/
	private Set<String> setOfMaps;
	/** Pravo ime datoteke **/
	String fileName;
	
	/**
	 * Konstruktor,
	 * @param variables prima mapiranje inicijalne vrijednosti.
	 * @param file prima referencu na datoteku.
	 * @param tree prima stablo naredbi programa.
	 * @param fileName prima izvorno ime datoteke.
	 */
	public ProgramExecutorVisitor(Map<String, Object> variables, File file,ProgramNode tree,String fileName) {
		super();
		this.variables = variables;
		this.file = file;
		this.tree=tree;
		errorList=new ArrayList<>();
		setOfFiles=listAllFiles(false);
		setOfMaps=listAllFiles(true);
		this.fileName=fileName;
	}
	
	/**
	 * Metoda čijim se pozivom obavlja izvođenje.
	 */
	public void execute(){
		active=true;
		tree.accept(this);
		active=false;
	}
	
	/**
	 * Metoda dohvata liste pogrešaka.
	 * @return lista opisa pogrešaka.
	 */
	public List<String> getErrorList() {
		return errorList;
	}

	/**
	 * dovat zastavice aktivnosti.
	 * @return Vraća true ako se program izvodi, false inače.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Izvođenje instrukcije Def.
	 * U mapu varijabli se dodaje novodefinirana varijabla i njena vrijednost.
	 */
	@Override
	public void visit(DefStatement stmt) {
		String varName = stmt.getVariable();
		variables.put(varName, eliminateSubs(stmt.getPath()));
	}

	/**
	 * Izvođenje instrukcije Exists.
	 * Provjerava se postojanje traženog objekta.
	 * Dođe li do pogreške ili ako je rezultat testa false, odgovarajuća poruka,
	 * se dodaje u listu pogreški.
	 */
	@Override
	public void visit(ExistsStatement stmt) {

		boolean caseInsensitive = stmt.getPath().isCaseInsensitive();
		Set<String> setForSearch;
		boolean directory = stmt.isDir();
		if (directory) {
			setForSearch = setOfMaps;

		} else {
			setForSearch = setOfFiles;
		}
		if (setForSearch == null || setForSearch.isEmpty()) {
			errorList
					.add("Given file cannot be interpred as zip OR cointains no"
							+ (directory ? " directories!" : " files!"));
			return;
		}
		String expectedName = eliminateSubs(stmt.getPath());
		boolean resultOfTest = false;
		if (caseInsensitive) {
			for (String s : setForSearch) {
				if (s.equalsIgnoreCase(expectedName)) {
					resultOfTest = true;
				}
				break;
			}
		} else {

			resultOfTest = setForSearch.contains(expectedName);
		}

		resultOfTest = resultOfTest ^ stmt.isInverted();
		if (resultOfTest) {
			visitAllChildren(stmt);
		} else {
			testFailed(stmt,
					(directory ? "Direcotry" : "File") + " with name: '"
							+ expectedName + "' does "
							+ (stmt.isInverted() ? "" : "not") + " exist!");

		}
	}
	
	/**
	 * Pomoćna metoda koja predstavlja akcije koje se rade u slučaju palog testa.
	 * @param stmt instrukcija čiji je test pao.
	 * @param defMessage defaultna poruka koja se ispisuje ukoliko korisnik nije zadao svoju.
	 */
	public void testFailed(FMagicianNode stmt, String defMessage){
		String err=getMessage(stmt);
		if(err.isEmpty()){
			errorList.add(defMessage);
		}else{
			errorList.add(err);
		}
	}
		 
	
	/**
	 * Izvođenje naredbe format.
	 * Provjerava se da li je datoteka zadanog formata.
	 * Dođe li do pogreške ili ako je rezultat testa false, odgovarajuća poruka,
	 * se dodaje u listu pogreški.
	 */
	@Override
	public void visit(FormatStatement stmt) {
		boolean resultOfTest = false;
		String expectedFormat = stmt.getFormat();
		if (expectedFormat.equals("zip")) {
			ZipFile zip = null;
			try {
				zip = new ZipFile(file);
			} catch (IOException e) {

			} finally {
				try {
					zip.close();
				} catch (Exception ignorable) {
					// TODO: handle exception
				}

			}
			if (zip != null) {
				resultOfTest = true;
			}
			resultOfTest = resultOfTest ^ stmt.isInverted();
			if (resultOfTest) {
				visitAllChildren(stmt);
			} else {
				testFailed(stmt, "File format does not match format "
						+ expectedFormat);
			}
		} else {
			errorList.add("Only zip format for now is supported!");
		}

	}

	/**
	 * Izvođenje naredbe filename.
	 * Provjerava se da li je ime datoteke u skladu s očekivanim.
	 * Dođe li do pogreške ili ako je rezultat testa false, odgovarajuća poruka,
	 * se dodaje u listu pogreški.
	 */
	@Override
	public void visit(FileNameStatement stmt) {
		boolean resultOfTest=false;
		MessageNode expectedNameNode=stmt.getName();
		String expectedName=eliminateSubs(expectedNameNode);
		if(expectedNameNode.isCaseInsensitive()){
			resultOfTest=fileName.equalsIgnoreCase(expectedName);
		}else{
			resultOfTest=fileName.equals(expectedName);
		}
		resultOfTest=resultOfTest^stmt.isInverted();
		if(resultOfTest){
			visitAllChildren(stmt);
		}else{
			testFailed(stmt, "File name does not match "+expectedName);
		}
	}

	/**
	 * Izvođenje metode fail.
	 * Fail je naredba čiji test uvijek pada.
	 * Invertirana fail naredba je test koji uvijek prolazi.
	 * Ako je test pao dodaje se odgovarajuća poruka u listu pogreški.
	 */
	@Override
	public void visit(FailStatement stmt) {
		boolean resultOfTest=false;
		resultOfTest=resultOfTest^stmt.isInverted();
		if(resultOfTest){
			visitAllChildren(stmt);
		}else{
			testFailed(stmt, "Instruction failed!");
		}

	}
	/**
	 * Izvođenje naredbe terminate.
	 * Postavlja se zastavica active na false.
	 * Izvođenje prestaje.
	 */
	@Override
	public void visit(TerminateStatement stmt) {
		active=false;

	}

	/**
	 * Metoda koja predstavlja izvođenje stabla programa.
	 */
	@Override
	public void visit(ProgramNode node) {
		visitAllChildren(node);

	}
	
	/**
	 * Metoda koja predstavlja izvođenje djece pojedine naredbe.
	 * Ovu metodu poziva pojedina instrukcija ako je njen test prošao,
	 * jer tada treba izvesti naredbe u njenom bloku.
	 * @param stmt naredba čiji blok treba izvesti.
	 */
	public void visitAllChildren(FMagicianNode stmt){
		int n=stmt.numberOfChildren();
		if(n<=0){
			return;
		}
		for(int i=0;i<n;i++){
			stmt.getChild(i).accept(this);
			if(active==false)break;
		}
		
	}
	
	/**
	 * Metoda koja vraća poruku neuspjelog testa naredbe.
	 * @param stmt naredba čija se poruka neuspjelog testa traži.
	 * @return vraća poruku neuspjelog testa.
	 */
	public String getMessage(FMagicianNode stmt){
		if(stmt.getMessage()==null){
			return "";
		}
		String message=eliminateSubs(stmt.getMessage());
		return message;
	}
	
	/**
	 * Metoda koja prima objekt tipa TextNode i vraća njegovu stringovnu reprezentaciju.
	 * Metoda obrađuje čvor tako da lijepi elemente negove liste,
	 * mjenjajući pritom sve oznake zamjene varijable u trenutne vrijednosti.
	 * @param text čvor kojeg treba obraditi.
	 * @return obrađeni string.
	 */
	public String eliminateSubs(TextNode text) {
		List<String> components = text.getArguments();
		if (components.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < components.size(); i++) {
			String elem = components.get(i);
			if (elem.startsWith("${")) {
				String pom = elem.substring(2, elem.length() - 1).trim();
				if (variables.containsKey(pom)) {
					elem = (String) variables.get(pom);

				} else {
					errorList.add("Error:Variable '" + pom
							+ "' does not exist!");
				}
			}
			sb.append(elem);
		}
		return sb.toString();
	}

	/**
	 * Metoda koja otvara ZipFile i puni listu svim datotekama ili folderima koje sadrzi.
	 * @param dir ako je true, oznaka da se lista treba napuniti mapama, ako je false datotekama
	 * @return Skup naziva svih datoteka ili foldera u zipu
	 */
	private Set<String> listAllFiles(boolean dir) {
		Set<String> records = null;
		ZipFile zipFile = null;
		try {
			// open a zip file for reading
			zipFile = new ZipFile(file);
			records = new HashSet<>();
			// get an enumeration of the ZIP file entries
			Enumeration<? extends ZipEntry> e = zipFile.entries();
			while (e.hasMoreElements()) {
				ZipEntry entry = e.nextElement();
				// get the name of the entry
				if (dir && entry.isDirectory()) {
					String entryName = entry.getName();
					records.add(entryName.substring(0, entryName.length() - 1));
				}
				if (dir == false && entry.isDirectory() == false) {
					String entryName = entry.getName();
					records.add(entryName);
				}
			}

		} catch (IOException ioe) {
			errorList.add("Error opening file");
		} finally {
			try {
				if (zipFile != null) {
					zipFile.close();
				}
			} catch (IOException ignorable) {
			}
		}
		return records;
	}
}

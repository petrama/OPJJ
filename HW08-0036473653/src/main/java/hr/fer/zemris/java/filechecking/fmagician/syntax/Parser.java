package hr.fer.zemris.java.filechecking.fmagician.syntax;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.filechecking.fmagician.lexical.FMagicianTokenType;
import hr.fer.zemris.java.filechecking.fmagician.lexical.FMagicianTokenizer;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.DefStatement;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.ExistsStatement;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.FMagicianNode;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.FailStatement;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.FileNameStatement;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.FormatStatement;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.MessageNode;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.PathNode;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.ProgramNode;
import hr.fer.zemris.java.filechecking.fmagician.syntax.nodes.TerminateStatement;

/**
 * Razred koji obavlja parsiranje niza tokena.
 * Niz tokena dobiven je tokeniziranjem programa pisanog jezikom <i> fmagician </i>
 * Cilj parsiranja je izgradnja stabla naredbi kako bi se one mogle ispravno izvoditi.
 * @author Petra Marče
 *
 */
public class Parser {

	/** Tokenizator koji generira token po token **/
	private FMagicianTokenizer tokenizer;
	/** Stog koji sluzi kao pomoć parsiranju **/
	private Stack<FMagicianNode> treeStack;
	/** Čvor koji predstavlja cijeli program **/
	private ProgramNode progNode;
	/** Oznaka invertiranja instrukcije **/
	private boolean inverted;

	/**
	 * Konstruktor.
	 * @param tokenizer prima tokenizator.
	 * Stvara novu instancu razreda ProgramNode i stavlja ga na prethodno novostvoreni stog.
	 */
	public Parser(FMagicianTokenizer tokenizer) {
		inverted = false;
		this.tokenizer = tokenizer;
		treeStack = new Stack<>();
		progNode = new ProgramNode();
		treeStack.push(progNode);

	}
	
	/**
	 * Vraća Izgrađeno stablo instrukcija.
	 * Metoda provodi samo parsiranje i odmah vraća rezultat.
	 * @return ProgramNode stablo koje je rezultat parsiranja.
	 */
	public ProgramNode getProgramNode() {
		parse();
		return progNode;
	}

	/**
	 * Pomoćna metoda koja provjerava je li trenutni token danog tipa. Vraća
	 * <code>true</code> ako je, <code>false</code> inače.
	 * @param type tip s kojim se uspoređuje
	 * @return <code>true</code> ako je
	 */
	private boolean isTokenOfType(FMagicianTokenType type) {
		return tokenizer.getCurrentToken().getTokenType() == type;
	}
	
	/**
	 * Pomoćna metoda koja utvrđuje da li je sljedeći token tipa OPEN_BLOCK.
	 * Ako jest metoda stavlja prethodno stvorenu naredbu na stog,
	 * jer će ona biti "roditelj" naredbi koje slijede, sve do oznake CLOSE_BLOCK.
	 * @param state neposredno prethodno stvorena naredba.
	 */
	private void isOpenBlockNext(FMagicianNode state) {
		if (tokenizer.getCurrentToken().getTokenType() == FMagicianTokenType.OPEN_BLOCK) {
			tokenizer.nextToken();
			treeStack.push(state);
		}
	}

	/**
	 * Glavna metoda ovog razreda. Provodi parsiranje.
	 * Sastoji se od ispitivanja vrste trenutnog tokena na temelju čega poduzima odgovarajuću akciju.
	 * Njeno izvođenje završava čitanjem tokena tipa EOF.
	 */
	private void parse() {
		FMagicianNode lastParsedStatement = null;
		while (true) {
			//kraj izvođenja
			if (isTokenOfType(FMagicianTokenType.EOF)) {
				break;
			}

			//ako smo naišli na '}' popaj sa stoga
			if (isTokenOfType(FMagicianTokenType.CLOSE_BLOCK)) {
				tokenizer.nextToken();
				if (treeStack.isEmpty()) {
					throw new FMagicianSyntaxException(
							"Found '}' without pair!");
				} else {
					treeStack.pop();
				}
				continue;
			}

			//Ako token nije ključna riječ ili oznaka negacije baci odgovarajuću iznimku.
			if (!isTokenOfType(FMagicianTokenType.KEYWORD)
					&& !isTokenOfType(FMagicianTokenType.NEGATION)) {
				throw new FMagicianSyntaxException(
						"Keyword or negation expected.");
			}

			//ako je token negacije, zapamti to do stvaranja naredbe
			if (isTokenOfType(FMagicianTokenType.NEGATION)) {

				inverted = true;
				tokenizer.nextToken();
				//provjeri je li negacija neposredno ispred kljucne rijeci
				if (!isTokenOfType(FMagicianTokenType.KEYWORD)) {
					throw new FMagicianSyntaxException(
							"Negation '!' must be before keyword!");
				}
				continue;
			}
			//slucaji kljucna rijec
			if ("def".equals(tokenizer.getCurrentToken().getValue())) {
				tokenizer.nextToken();//daj sljedeci token
				treeStack.peek().addChildNode(parseDef());//stvori naredbu i stavi je kao dijete onoga sta se nalazi na vrhu stoga
				inverted = false; //resetriraj zastavicu inverted
				continue;//nastavi s parsiranjem
			}
			if ("exists".equals(tokenizer.getCurrentToken().getValue())) {
				tokenizer.nextToken();
				lastParsedStatement = parseExist();
				treeStack.peek().addChildNode(lastParsedStatement);
				isOpenBlockNext(lastParsedStatement);//provjeri je li iza tebe mozda sljedi '{'
				inverted = false;
				continue;
			}
			if ("filename".equals(tokenizer.getCurrentToken().getValue())) {
				tokenizer.nextToken();
				lastParsedStatement = parseFileName();
				treeStack.peek().addChildNode(lastParsedStatement);
				isOpenBlockNext(lastParsedStatement);
				inverted = false;
				continue;
			}
			if ("format".equals(tokenizer.getCurrentToken().getValue())) {
				tokenizer.nextToken();
				lastParsedStatement = parseFormat();
				treeStack.peek().addChildNode(lastParsedStatement);
				isOpenBlockNext(lastParsedStatement);
				inverted = false;
				continue;
			}
			if ("fail".equals(tokenizer.getCurrentToken().getValue())) {
				tokenizer.nextToken();
				lastParsedStatement = parseFail();
				treeStack.peek().addChildNode(lastParsedStatement);
				isOpenBlockNext(lastParsedStatement);
				inverted = false;
				continue;
			}
			if ("terminate".equals(tokenizer.getCurrentToken().getValue())) {
				tokenizer.nextToken();
				treeStack.peek().addChildNode(new TerminateStatement());
				inverted = false;
				continue;
			}
			throw new FMagicianSyntaxException("Unexpected keyword found!");

		}

	}

	/**
	 * Pomoćna metoda koja provjerava početak poruke ili putanje.
	 * Metoda očekuje da takav token počinje sa i, što označava case-insensitivity ili znakom navodnika.
	 * @return vraća true ako je početak navodnika legalan, false inače.
	 */
	private boolean checkStartOfQuotes() {
		boolean caseIns = false;
		if (!isTokenOfType(FMagicianTokenType.QUOTE)
				&& !isTokenOfType(FMagicianTokenType.CASE_INSENSITIVE)) {
			throw new FMagicianSyntaxException("Error: Expected 'i' or ' \" '!");
		}
		if (isTokenOfType(FMagicianTokenType.CASE_INSENSITIVE)) {
			caseIns = true;
			tokenizer.nextToken();

			if (!isTokenOfType(FMagicianTokenType.QUOTE)) {
				throw new FMagicianSyntaxException(
						"Error: Expected ' \" ' after 'i' !");
			}
		}
		// preskakanje navodnika

		tokenizer.nextToken();
		return caseIns;
	}

	/**
	 * Metoda koja iz niza tokena radi listu koja će biti argument PathNoda ili MessageNoda.
	 * Ta lista može sadržavati tokene tipa STRING SUPSTITUTION i PACKAGE ako se radi o path-listi.
	 * Ako se radi o message-listi onda ne smije sadrzavati PACKAGE.
	 * @param isPath zastavica koja ako je true, označava da se radi o pathu,inače se radi o message.
	 * @return Lista argumenata.
	 */
	private List<String> exstractList(boolean isPath) {
		String errorMessage = "";
		if (isPath) {
			errorMessage = "Error: Inside of path expected string, substitution or package token!";

		} else {
			errorMessage = "Error: Inside of Message expected string or package token!";
		}
		List<String> strings = new ArrayList<>();
		String pom = "";

		while (!isTokenOfType(FMagicianTokenType.QUOTE)) {
			if (isTokenOfType(FMagicianTokenType.SUPSTITUTION)) {
				if (pom.isEmpty() == false) {
					strings.add(pom);
				}
				pom = "${" + (String) tokenizer.getCurrentToken().getValue()
						+ "}";
				strings.add(pom);
				pom = "";
				tokenizer.nextToken();
				continue;
			}
			if (isTokenOfType(FMagicianTokenType.STRING)) {
				pom += (String) tokenizer.getCurrentToken().getValue();
				tokenizer.nextToken();
				continue;
			}
			if (isTokenOfType(FMagicianTokenType.PACKAGE) && isPath) {
				pom += (String) tokenizer.getCurrentToken().getValue();
				tokenizer.nextToken();
				break;
			}
			throw new FMagicianSyntaxException(errorMessage);
		}
		if (!isTokenOfType(FMagicianTokenType.QUOTE)) {
			throw new FMagicianSyntaxException(
					"Error: Expected closing ' \" '!");
		}
		tokenizer.nextToken();
		if (pom.isEmpty() == false) {
			strings.add(pom);
		}
		return strings;
	}

	/**
	 * Metoda koja vraća instancu razeda PathNode.
	 * Taj razred reprezentira putanju.
	 * @return putanja.
	 */
	private PathNode exstractPath() {

		boolean caseIns = checkStartOfQuotes();
		List<String> stringsForPath = exstractList(true);
		return new PathNode(caseIns, stringsForPath);
	}

	/**
	 * Metoda koja vraća instancu razeda MessageNode.
	 * Taj razred reprezentira string koji podržava zamjenu varijabli ali ne i pojavu paketa.
	 * @return poruka.
	 */
	private MessageNode exstractMessage() {
		boolean caseIns = checkStartOfQuotes();
		List<String> stringsForMessage = exstractList(false);
		return new MessageNode(caseIns, stringsForMessage);
	}

	/**
	 * Metoda koja parsira fail naredbu.
	 * Naredba kao argument može imati samo poruku.
	 * @return instanca razeda FailStatement.
	 */
	private FailStatement parseFail() {
		// tokenizer.nextToken();
		return new FailStatement(messageOfFailedTest(), inverted);

	}

	/**
	 * Metoda koja parsira format naredbu.
	 * Naredba mora uz kljucnu rijec imati identifikator.
	 * Naredba može imati poruku o pogrešci.
	 * @return instanca razeda FormatStatement.
	 */
	private FormatStatement parseFormat() {
		String argIden;
		if (!isTokenOfType(FMagicianTokenType.IDENT)) {
			throw new FMagicianSyntaxException("Identifier was expected.");
		}
		// prvi argument je identifikator
		argIden = (String) tokenizer.getCurrentToken().getValue();
		tokenizer.nextToken();
		MessageNode message = messageOfFailedTest();
		return new FormatStatement(argIden, message, inverted);

	}

	/**
	 * Metoda koja parsira filename naredbu.
	 * Naredba mora uz kljucnu rijec imati ocekivani naziv datoteke.
	 * Naredba može imati poruku o pogrešci.
	 * Ocekivani naziv je po tipu instanca razeda MessageNode.
	 * @return instanca razeda FileNameStatement.
	 */
	private FileNameStatement parseFileName() {
		MessageNode name = exstractMessage();
		MessageNode message = messageOfFailedTest();
		return new FileNameStatement(name, message, inverted);

	}

	/**
	 * Metoda koja parsira def naredbu.
	 * Naredba mora uz kljucnu rijec imati identifikator.
	 * Naredba ne smije imati poruku o pogrešci.
	 * @return instanca razeda DefStatement.
	 */
	private DefStatement parseDef() {
		String argIden;
		PathNode argPath;
		if (!isTokenOfType(FMagicianTokenType.IDENT)) {
			throw new FMagicianSyntaxException("Identifier was expected.");
		}
		// prvi argument je identifikator
		argIden = (String) tokenizer.getCurrentToken().getValue();
		tokenizer.nextToken();
		argPath = exstractPath();
		return new DefStatement(argIden, argPath);

	}
	/**
	 * Metoda koja parsira exists naredbu.
	 * Naredba mora uz kljucnu rijec imati oznaku za dirketorij ili datoteku, putanju.
	 * Naredba može imati poruku o pogrešci.
	 * @return instanca razeda ExistsStatement.
	 */

	private ExistsStatement parseExist() {
		boolean dir;
		PathNode argPath;

		if (!isTokenOfType(FMagicianTokenType.EXISTS_DIR)
				&& !isTokenOfType(FMagicianTokenType.EXISTS_FILE)) {
			throw new FMagicianSyntaxException(
					"Error: Expected 'd' , 'di' , 'dir' , 'f' , 'fi' , 'fil' , or 'file' !");
		}
		if (isTokenOfType(FMagicianTokenType.EXISTS_DIR)) {
			dir = true;
		} else {
			dir = false;
		}
		;
		tokenizer.nextToken();
		argPath = exstractPath();

		MessageNode message = messageOfFailedTest();

		return new ExistsStatement(dir, argPath, message, inverted);

	}
	
	/**
	 * Metoda koja izdvaja poruku o pogrešci.
	 * Poruka o pogrešci je instanca razreda MessageNode.
	 * @return instanca razreda MessageNode.
	 */
	private MessageNode messageOfFailedTest() {
		if (isTokenOfType(FMagicianTokenType.MESSAGE)) {
			tokenizer.nextToken();
			return exstractMessage();
		}
		return null;
	}

}

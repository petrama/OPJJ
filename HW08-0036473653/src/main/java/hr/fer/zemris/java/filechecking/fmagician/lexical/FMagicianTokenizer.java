package hr.fer.zemris.java.filechecking.fmagician.lexical;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Tokenizator izvornog koda programa naapisanog jezikom <i>fmagician</i>.
 * 
 * @author Petra Marče
 */
public class FMagicianTokenizer {

	/**
	 * Zastavica koja označava da li se trenutno nalazimo unutar navodnika.
	 */
	private boolean weAreInQuotes;
	/**
	 * Polje znakova koji čine izvorni kod programa koji se obrađuje.
	 */
	private char[] data;
	/**
	 * Kazaljka na prvi neobrađeni znak u polju <code>data</code>.
	 */
	private int curPos;
	/**
	 * Posljednji token koji je stvoren analizom izvornog koda programa.
	 */
	private FMagicianToken currentToken;

	/**
	 * Statička mapa tipova tokena koje je moguće utvrditi prema samo jednom
	 * znaku (znak == token).
	 */
	private static final Map<Character, FMagicianTokenType> mapper;
	// Inicijalizacija mape mapper
	static {
		mapper = new HashMap<>();
		mapper.put(Character.valueOf('"'), FMagicianTokenType.QUOTE);
		mapper.put(Character.valueOf('!'), FMagicianTokenType.NEGATION);
		mapper.put(Character.valueOf('{'), FMagicianTokenType.OPEN_BLOCK);
		mapper.put(Character.valueOf('}'), FMagicianTokenType.CLOSE_BLOCK);
		mapper.put(Character.valueOf('i'), FMagicianTokenType.CASE_INSENSITIVE);
		mapper.put(Character.valueOf('@'), FMagicianTokenType.MESSAGE);
	}

	/**
	 * Skup svih ključnih riječi programa.
	 */
	private static final Set<String> keywords;

	// Inicijalizacija skupa ključnih riječi
	static {
		keywords = new HashSet<>();
		keywords.add("def");
		keywords.add("terminate");
		keywords.add("exists");
		keywords.add("filename");
		keywords.add("format");
		keywords.add("fail");
	}

	/**
	 * Konstruktor. Prima izvorni kod programa kao <code>String</code>.
	 * 
	 * @param program
	 *            izvorni kod programa
	 * @throws FMagicianTokenizerException
	 *             ako dođe do pogreške pri tokenizaciji
	 */
	public FMagicianTokenizer(String program) {
		weAreInQuotes = false;
		data = program.toCharArray();
		curPos = 0;
		extractNextToken();
	}

	/**
	 * Metoda dohvaća trenutni token. Metoda se može zvati više puta i uvijek će
	 * vratiti isti token, sve dok se ne zatraži izlučivanje sljedećeg tokena.
	 * 
	 * @return trenutni token
	 */
	public FMagicianToken getCurrentToken() {
		return currentToken;
	}

	/**
	 * Metoda izlučuje sljedeći token, postavlja ga kao trenutnog i odmah ga i
	 * vraća.
	 * 
	 * @throws FMagicianTokenizerException
	 *             ako dođe do problema pri tokenizaciji
	 */
	public FMagicianToken nextToken() {
		extractNextToken();
		return getCurrentToken();
	}

	private void extractNextToken() {

		// Ako je već prije utvrđen kraj, ponovni poziv metode je greška:
		if (currentToken != null
				&& currentToken.getTokenType() == FMagicianTokenType.EOF) {
			throw new FMagicianTokenizerException("No tokens available.");
		}

		// Ako se NE nalazimo unutar navodnika preskoči praznine
		if (weAreInQuotes == false) {
			skipBlanks();
		}

		// Ako više nema znakova, generiraj token za kraj izvornog koda programa
		if (curPos >= data.length) {
			currentToken = new FMagicianToken(FMagicianTokenType.EOF, null);
			return;
		}

		// Ako smo naisli na znak navodnika treba promjeniti zastavicu
		if (data[curPos] == '"') {
			weAreInQuotes = !weAreInQuotes;
		}

		// Vidi je li trenutni znak neki od onih koji direktno generiraju token:
		FMagicianTokenType mappedType = mapper.get(Character
				.valueOf(data[curPos]));
		if (mappedType != null) {
			// Stvori takav token:
			currentToken = new FMagicianToken(mappedType, null);
			// Postavi trenutnu poziciju na sljedeći znak:
			curPos++;
			return;
		}

		// Inače:

		// Dva velika slučaja: ili smo unutar navodnika ili izvan njih
		// SAMO UNUTAR navodnika očekujem naredbu supstitucije ili paket

		if (weAreInQuotes == false) {
			// razdvajanje riječi prema razmacima (mogu jer sam izvan stringa)
			int startIndex = curPos;
			curPos++;
			while (curPos < data.length
					&& Character.isWhitespace(data[curPos]) == false
					&& data[curPos] != '{' && data[curPos] != '}') {
				curPos++;
			}
			int endIndex = curPos;
			String value = new String(data, startIndex, endIndex - startIndex);

			// ako je izdvojeno kljucna rijec
			if (keywords.contains(value)) {
				currentToken = new FMagicianToken(FMagicianTokenType.KEYWORD,
						value);
				return;
			}

			// ako je argument naredbe exists i to oznaka za direktorij
			if (value.equals("d") || value.equals("di") || value.equals("dir")) {
				currentToken = new FMagicianToken(
						FMagicianTokenType.EXISTS_DIR, null);
				return;
			}
			// ako je argument naredbe exists i to oznaka za file
			if (value.equals("f") || value.equals("fi") || value.equals("fil")
					|| value.equals("file")) {
				currentToken = new FMagicianToken(
						FMagicianTokenType.EXISTS_FILE, null);
				return;
			}
			// inače-rijec bi trebala biti identifikator tj varijabla

			checkVariable(value);
			currentToken = new FMagicianToken(FMagicianTokenType.IDENT, value);
			return;
			// Unutar navodnika razlikujem samo substitution package i string
		} else {
			// ako je substitution
			if (data[curPos] == '$') {
				currentToken = exstractSubToken();
				return;
			}
			// ako je package
			if (data[curPos] == ':') {
				currentToken = exstractPackageToken();
				return;
			}
			// nije sups i nije paket onda je "obican string ili putanja"
			// Ovaj Tokenizer NE razlikuje putanju i obican string
			currentToken = exstractStringToken();
			return;
		}

	}

	/**
	 * Metoda koja stvara token tipa STRING.
	 * Taj token mora ne smije sadržavati oznake: ':' '{' '}' '\' ni '$'
	 * @return vraća novostvoreni token.
	 */
	private FMagicianToken exstractStringToken() {
		// Tokenizer samo garantira da će ono što je STRING biti niz bez $ { } :
		// \
		int startIndex = curPos;
		// citanje znakova do $ ili : jer bi to trebao biti pocetak novog tokena
		// ili do kraja navodnika kad string zavrsava
		while (curPos < data.length - 1 && data[curPos] != '$'
				&& data[curPos] != ':' && data[curPos] != '"') {
			curPos++;
		}
		int endIndex = curPos;
		// ako smo citanjem stigli do zadnje pozicije i znak na toj poziciji
		// nije "
		if (endIndex == data.length - 1 && data[endIndex] != '"') {
			throw new FMagicianTokenizerException("Missing closing \" !");
			// ako smo stigli do $ a znak poslje toga nije { znaci da string
			// sadrzi $ sto nije dozvoljeno
		} else if (data[endIndex] == '$' && data[endIndex + 1] != '{') {
			throw new FMagicianTokenizerException("Error: string contains $");
		}

		String value = new String(data, startIndex, endIndex - startIndex);
		// Ostatak provjera (da li sadrzi { } \)
		// Nemam nikakvu posebnu provjeru za ':' nepotrebno??
		checkString(value);
		return new FMagicianToken(FMagicianTokenType.STRING, value);
	}

	/**
	 * Metoda koja stvara token tipa PACKAGE.
	 * Paketom se smatra sve unutar navodnika iza znaka ':'
	 * @return novostvoreni token.
	 */
	private FMagicianToken exstractPackageToken() {
		curPos++;
		int startIndex = curPos;
		if (data[startIndex] == '\"') {
			throw new FMagicianTokenizerException(
					"Error: Package should not be empty!");
		}
		curPos++;
		while (curPos < data.length - 1 && data[curPos] != '"') {
			curPos++;
		}
		if (data[curPos] != '"')
			throw new FMagicianTokenizerException("One quote is missing!");
		int endIndex = curPos;
		String value = new String(data, startIndex, endIndex - startIndex);
		value = "/" + value.trim().replaceAll("\\.", "/");
		return new FMagicianToken(FMagicianTokenType.PACKAGE, value);
	}

	/**
	 * Metoda koja stvara token tipa SUPSTITUTION
	 * Taj token mora počimati sa '${' i završavati sa '}'.
	 * Između ta dva znaka mora biti valjano ime varijable.
	 * @return novostvoreni token.
	 */
	private FMagicianToken exstractSubToken() {
		curPos++;
		if (data[curPos] != '{') {
			throw new FMagicianTokenizerException("$ must be with {!");
		}
		curPos++;
		int startIndex = curPos;
		curPos++;
		while (curPos < data.length && data[curPos] != '}'
				&& data[curPos] != '"') {
			curPos++;
		}
		if (data[curPos] != '}')
			throw new FMagicianTokenizerException("Sub tag never closed!");
		int endIndex = curPos;
		curPos++;
		String value = new String(data, startIndex, endIndex - startIndex);
		value = value.trim();
		checkVariable(value);
		return new FMagicianToken(FMagicianTokenType.SUPSTITUTION, value);
	}

	
	/**
	 * Metoda pomiče kazaljku trenutnog znaka tako da preskače sve prazne
	 * znakove (razmaci, prelasci u novi red, tabulatori).
	 */
	private void skipBlanks() {
		while (curPos < data.length) {
			char c = data[curPos];
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				curPos++;
				continue;
			}
			break;
		}
	}

	/**
	 * Metoda provjerava valjanost ulaznog niza znakova. Niz je valjan ako ne
	 * sadrži znakove '{','}' i '\'
	 * 
	 * @param checkIt
	 *            niz koji se provjerava.
	 * @throws FMagicianTokenizerException
	 *             ako string nije valjan metoda baca odgovarajuću iznimku.
	 */
	public static void checkString(String checkIt) {
		for (int i = 0; i < checkIt.length(); i++) {
			char c = checkIt.charAt(i);
			if (c == '{' || c == '}' || c == '\\') {
				throw new FMagicianTokenizerException(
						"Error string contains forbidden character : " + c);
			}
		}
	}

	/**
	 * Metoda koja provjerava da li je varijabla ima valjano ime.
	 * Varijabla je valjana kada se sastoji od samo slova,brojki točki i podvlaka,
	 * pri čemu prvi znak ne smije biti brojka.
	 * @param variable
	 */
	public static void checkVariable(String variable) {
		// System.err.println(variable);
		Character first = variable.charAt(0);
		if (Character.isLetter(first) == false && first.equals('.') == false
				&& first.equals('_') == false) {
			throw new FMagicianTokenizerException(
					"Invalid variable name! 1. char should not be : " + first);

		}
		for (int i = 1; i < variable.length(); i++) {
			Character c = variable.charAt(i);

			if (Character.isLetter(c) == false && Character.isDigit(c) == false
					&& c.equals('.') == false && c.equals('_') == false) {
				throw new FMagicianTokenizerException("Invalid variable name! "
						+ (i + 1) + ". char should not be : " + c);

			}
		}
	}
}

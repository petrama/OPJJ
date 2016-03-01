package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.collections.*;
import hr.fer.zemris.java.custom.scripting.tokens.*;

/**
 * Razred omogucava provjeru da li je zadani niz u skladu s pravilima, omogucava
 * parsiranje tj izgradnju hijerarhijske strukture dokumenta
 * 
 * @author Petra Marče
 * @version 2.0
 * 
 */

public class SmartScriptParser {
	ObjectStack stack;

	public SmartScriptParser(String doc) {// konstruktor
		stack = new ObjectStack();
		parse(doc);

		if (stack.size() != 1) {
			throw new SmartScriptParserException(
					"Nisu svi neprazni tagovi zatvoreni!");
		}

	}

	/**
	 * Metoda provjerava da li je ulazni string u skladu s pravilima za pisanje
	 * varijabli
	 * 
	 * @param var
	 *            ulazni niz kojeg treba provjeriti
	 */
	public void validateVariable(String var) {

		for (int i = 0; i < var.length(); i++) {
			char c = var.charAt(i);
			if (((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')
					|| (c >= '0' && c <= '9') || c == '_') == false) {
				System.out.println("invalid variable");
				throw new SmartScriptParserException();

			}

		}

	}

	/**
	 * Metoda koja provjerava da li je zadani niz u skladu s pravilima za
	 * pisanje funkcija
	 * 
	 * @param fun
	 *            ulazni niz kojeg treba provjeriti
	 */
	public void validateFunction(String fun) {

		for (int i = 0; i < fun.length(); i++) {
			char c = fun.charAt(i);
			if (((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')
					|| (c >= '0' && c <= '9') || c == '_') == false) {
				System.out.println("invalid function");
				throw new SmartScriptParserException();

			}
		}

	}

	/**
	 * Metoda koja mjenja niz prema odredjenim pravilima.
	 * 
	 * @param arr
	 *            ulazni niz kojeg treba promjeniti
	 * @return vraća promjenjeni niz
	 */
	public String changeString(String arr) {
	
		for (int i = 0; i <= arr.length() - 2; i++) {
			if (arr.charAt(i) == '\\') {
				switch (arr.charAt(i + 1)) {
				case '\\':
					arr = arr.substring(0, i + 1) + arr.substring(i + 2);
					i++;
					break;
				case '"':
					arr = arr.substring(0, i) + arr.substring(i + 1);
					break;
				case 't':
					arr = arr.substring(0, i) + (char) 9 + arr.substring(i + 2);
					break;

				case 'n':
					arr = arr.substring(0, i) + (char) 10
							+ arr.substring(i + 2);
					break;
				case 'r':
					arr = arr.substring(0, i) + (char) 13
							+ arr.substring(i + 2);
					break;
				default: throw new SmartScriptParserException("Invalid escaping!");
				}
			}

		}
	
		return arr;
		
	}

	/**
	 * Metoda koja radi parsiranje niza
	 * 
	 * @throws SmartScriptParserException
	 */
	public void parse(String doc) {
		int startScan = 0;
		int cursor = 0;
		stack.push(new DocumentNode());
		String openBracket = "{$";

		while (cursor < doc.length()) {

			cursor = doc.indexOf(openBracket, cursor);// nađi prvu sljedeću
														// pojavu
			// znaka {$

			if (cursor == -1){
				break; // ako traženje nije uspjelo gotovi smo
			}
			
			if (cursor >= 1 && doc.charAt(cursor - 1) == '\\' ) {
				cursor += openBracket.length(); // ako se ispred nalazi
												// backslash, to je tekst
				// preskoci to
				continue; // skeniraj dalje

			}

			else 
				if (startScan != cursor) {
					((Node) stack.peek()).addChildNode(new TextNode(doc
							.substring(startScan, cursor)));

				}

				cursor += openBracket.length();
				startScan = cursor;

				do {

					String closeBracket = "$}";
					// petlja se izvodi dok se ne nađe završni znak taga ciji je
					// pocetni znak nađen u prethodnom dijelu
					cursor = doc.indexOf(closeBracket, cursor);// nađi prvu
																// sljedeću
																// pojavu
					// znaka $}

					if (cursor != -1 && doc.charAt(cursor - 1) != '\\') {

						break;// jer smo našli validan završni znak taga
					}

					if (cursor == -1) {// kad smo stigli do ovdje znaci da smo
										// nasli
						// otvoreni tag, a zatvoreni nismo->greska
						throw new SmartScriptParserException(
								"One tag is not closed, invalid source!");
					} else {
						cursor += closeBracket.length();
						}

				} while (true);// izlazimo samo ako je nađen ispravan zavrsetak
								// taga

				String foundTag = doc.substring(startScan, cursor).trim();// u
																			// var

				// foundTag
				// spremam
				// novi
				// neobrađeni
				// tag

				if (foundTag.charAt(0) == '=') {// počima

					foundTag = foundTag.substring(1).trim();
					equalFun(foundTag);

				}// zavrsava obrada echo

				else if ((foundTag.substring(0, 3)).trim().compareToIgnoreCase(
						"for") == 0) {// počima
					// obrada
					// for

					foundTag = foundTag.substring(3).trim();// mičem riječ for i
					// uklanjam razmake

					forNode(foundTag);
				}// završava obrada for

				else if (foundTag.substring(0, 3).compareToIgnoreCase("end") == 0) {// počima
					// obrada
					// end

					try {
						stack.pop();
					} catch (EmptyStackException ex) {
						System.out.println("Problem with end tag");
						throw new SmartScriptParserException("Cannot pop!");
					}

				}// završava obrada end

				startScan = cursor + 2;// pomičem mjesto odakle obrađujem
				cursor += 2;// pomičem kursor
			};

			
			((Node) stack.peek()).addChildNode(new TextNode(doc.substring(
					startScan, doc.length())));

		}

	

	public DocumentNode getDocumentNode() {
		return (DocumentNode) stack.peek();
	}

	/**
	 * Metoda koja obrađuje tag za kojeg se ustanovilo da je tipa for
	 * 
	 * @param subStr
	 *            prima unutrašnjost taga
	 */
	public void forNode(String subStr) {
		ArrayBackedIndexedCollection arrayTokens=new ArrayBackedIndexedCollection(5);
		
	

		do {
			int space = subStr.indexOf(' ');
			String beauty;

			if (space == -1) {
				beauty = subStr.substring(0, subStr.length());
				subStr = "";

			} else {
				beauty = subStr.substring(0, space);
				subStr = subStr.substring(space).trim();

			}

			arrayTokens.add(tokenKind(beauty));
		

		} while (subStr.length() > 0);
	

		Token[] tokens = new Token[arrayTokens.size()];
		for (int index = 0; index < arrayTokens.size(); index++) {
			tokens[index] = (Token) arrayTokens.get(index);
		}
		
		if (arrayTokens.size() < 3 || arrayTokens.size() > 5) {
			throw new SmartScriptParserException(
					"Pogrešan broj argumenata FOR petlje!");
		}
		

		ForLoopNode forLoopNode;

		TokenVariable variable = (TokenVariable) arrayTokens.get(0);
		Token start = (Token) arrayTokens.get(1);
		Token stop = (Token) arrayTokens.get(2);

		if (arrayTokens.size() == 4) {
			// Ako postoji argument step.
			Token step = (Token) arrayTokens.get(3);
			forLoopNode = new ForLoopNode(variable, start, stop,
					step);
		} else {
			// Ako ne postoji argument step.
			forLoopNode = new ForLoopNode(variable, start, stop);
		}

		// Dodaj ForLoopNode kao dijete čvora na vrhu stoga i stavi
		// ForLoopNode na stog.
		((Node) stack.peek()).addChildNode(forLoopNode);
		stack.push((Object) forLoopNode);
	}

	/**
	 * Metoda koja obrađuje tag za kojeg se utvrdilo da je =-tipa
	 * 
	 * @param subStr
	 *            prima unutrašnjost taga
	 */
	public void equalFun(String subStr) {
		
		ArrayBackedIndexedCollection arrayTokens = new ArrayBackedIndexedCollection();

		do {
			int i=0;
			for( i=0;i<subStr.length();i++){
				if(Character.isWhitespace(subStr.charAt(i))){
					break;
				}
			}
			int blank = i;
			String bottle;
			if (subStr.charAt(0) == '"') {
				
					int pom=1;
					int closingQuote=-1;
					do{
					if((closingQuote=subStr.indexOf('"',pom))==-1){
						throw new SmartScriptParserException("Closing quote is missing!");
						
					};
					if(subStr.charAt(closingQuote-1)=='\\'){
					
					pom=closingQuote+1;}else{
						break;
					}
					}while (true);
//					bottle = subStr.substring(0, subStr.indexOf('"', 1) + 1);
//					subStr = subStr.substring(subStr.indexOf('"', 1) + 1)
//							.trim();
					bottle=subStr.substring(0,closingQuote+1);
					subStr=subStr.substring(closingQuote+1).trim();
				
			} else if (blank == -1) {
				bottle = subStr.substring(0, subStr.length());
				subStr = "";
			} else {
				bottle = subStr.substring(0, blank);
				subStr = subStr.substring(blank).trim();
			}

			// odredjujem tip tokena
			arrayTokens.add(tokenKind(bottle));
			

		} while (subStr.length() > 0);

		Token[] sendArr = new Token[arrayTokens.size()];
		for (int j = 0; j < sendArr.length; j++)
			sendArr[j] = (Token)arrayTokens.get(j);
		EchoNode echoNode;
		try {
			echoNode = new EchoNode(sendArr);
		} catch (RuntimeException ex) {

			throw new SmartScriptParserException("Cannot create EchoNode!");
		}
		((Node) stack.peek()).addChildNode(echoNode);
	}

	/**
	 * Metoda koja utvrđuje koje je vrste predani string. Da li je varijabla,
	 * funkcija, operator ili broj
	 * 
	 * @param str
	 *            ulazni parametar za kojeg je potrebno utvrditi što je
	 * @return ovisno o tome sto je utvrđeno vraća odgovarajući Token tip
	 */
	public Token tokenKind(String str) {

		switch (str.charAt(0)) {
		case '@':

			validateFunction(str.substring(1));
			TokenFunction tokenFun = new TokenFunction(str.substring(1));
			return tokenFun;
		case '"':
			if (str.charAt(str.length() - 1) != '"') {
				System.out.println("Invalid string declaration!");
				throw new SmartScriptParserException(
						"Invalid string declaration!");
			}
			str = changeString(str.substring(1, (str.length() - 1)));
			TokenString tokenStr = new TokenString(str);
			return tokenStr;
		}

		if ((str.charAt(0) >= 'A' && str.charAt(0) <= 'Z')
				|| (str.charAt(0) >= 'a' && str.charAt(0) <= 'z')) {
			validateVariable(str.substring(1));

			TokenVariable tokenVar = new TokenVariable(str);
			return tokenVar;
		}

		if (str.charAt(0) == '+' || str.charAt(0) == '-'
				|| str.charAt(0) == '*' || str.charAt(0) == '/') {
			if (str.length() != 1 && str.charAt(0) != '-') {
				throw new SmartScriptParserException("Invalid operator!");
			}

			TokenOperator tOperator = new TokenOperator(str);
			return tOperator;
		}

		try {
			int intConst;
			if (str.charAt(0) == '-') {
				intConst = Integer.parseInt(str.substring(1));
				intConst = -intConst;
			} else {
				intConst = Integer.parseInt(str);
			}
			TokenConstantInteger tokenInt = new TokenConstantInteger(intConst);
			return tokenInt;
		} catch (NumberFormatException n) {
			try {
				double doubleConst;
				if (str.charAt(0) == '-') {
					doubleConst = Integer.parseInt(str.substring(1));
					doubleConst = -doubleConst;
				} else {
					doubleConst = Double.parseDouble(str);
				}
				TokenConstantDouble tokenDouble = new TokenConstantDouble(
						doubleConst);
				return tokenDouble;
			} catch (NumberFormatException nfe2) {
				throw new SmartScriptParserException("Invalid token!");
			}
		}
	}

}

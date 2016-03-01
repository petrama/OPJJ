package hr.fer.zemris.java.hw2;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.custom.scripting.tokens.*;

/**
 * Razred koji sluzi za testiranje parsera
 * 
 * 
 */

public class SmartScriptTester {

	public static void main(String args[]) {

		String docBody = "This is sample text.\r\n{$ FOR i 1 10 1 $}\r\nThis is {$= i $}-th time this message is generated.\r\n{$END$}\r\n{$FOR i 0 10 2 $}\r\nsin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n{$END$}";
		SmartScriptParser parser = null;
		try {

			parser = new SmartScriptParser(docBody);

		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out
			.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = allBack(document);
		System.out.println(originalDocumentBody); // should write something like
		// original
		// content of docBody

	}

	/**
	 * Metoda koja iz parsiranog niza ponovo gradi jedan niz
	 * 
	 * @param node
	 *            ulazni cvor dokumenta
	 * @return s vraća ponovno sastavljen niz
	 */
	private static String allBack(Node node) {
		String s = "";

		for (int i = 0; i < node.numberOfChildren(); i++) {
			try {
				s = s + ((TextNode) node.getChild(i)).getText();
				continue;
			} catch (RuntimeException e) {
			}

			try {
				s = s
						+ "{$FOR "
						+ ((ForLoopNode) node.getChild(i)).getVariable()
						.asText()
						+ " "
						+ ((ForLoopNode) node.getChild(i)).getStartExpression()
						.asText()
						+ " "
						+ ((ForLoopNode) node.getChild(i)).getEndExpression()
						.asText() + " ";

				if (((ForLoopNode) node.getChild(i)).getStepExpression() != null) {

					s += ((ForLoopNode) node.getChild(i)).getStepExpression()
							.asText();
				}
				s += " $}";
				if (node.getChild(i).numberOfChildren() > 0) {
					s = s + allBack(node.getChild(i));
					s = s + "{$END$}";
				}
				continue;
			} catch (RuntimeException e) {
			}

			try {
				s = s + "{$= ";
				for (Token token : ((EchoNode) node.getChild(i)).getTokens()) {
					s = s + token.asText() + " ";
				}
				s = s + "$}";
				continue;
			} catch (RuntimeException e) {
				s = s + "$}";

			}
		}

		return s;
	}

}

package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * Razred koji služi za ispis čvorova stabla koje je nastalo parsiranjem
 * smart-skripte.
 * 
 * @author Petra Marče
 * 
 */
public class TreeWriter {

	static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.println(node);

		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.println(node);

		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.println(node);

		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}

		}

	}

	/**
	 * Metoda koja se poziva pri pokretanju programa.
	 * @param args argumenti iz komandne linije, očekuje putanju do datoteke.
	 * @throws IOException baca iznimku u slučaju greške u ulaznoj operaciji.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out
					.println("Only one argument is expected: path to smart script!");
			System.exit(1);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]),"UTF-8"));
		String everything = "";
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			everything = sb.toString();
		} finally {
			br.close();
		}
		;

		SmartScriptParser p = new SmartScriptParser(everything);
		// System.out.println(createOriginalDocumentBody(p.getDocumentNode()));
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);

	}

	public static String createOriginalDocumentBody(DocumentNode document) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < document.numberOfChildren(); i++) {
			sb.append(document.getChild(i).toString());
		}

		return sb.toString();
	}
}

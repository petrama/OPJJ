package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Razred služi testiranju razreda SmartScriptExecutor.
 * @author petra
 *
 */
public class ExecutorTest {
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out
					.println("Only one argument is expected: path to smart script!");
			System.exit(1);
		}

		String documentBody = readFromDisk(args[0]);
		Map<String, String> parameters = new HashMap<>();
		Map<String, String> persistentParameters = new HashMap<>();
		List<RequestContext.RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
//		parameters.put("a","4");
//		parameters.put("b", "2");
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out,parameters,persistentParameters,cookies)).execute();
	
		
//		String documentBody = readFromDisk("brojPoziva.smscr");
//		Map<String,String> parameters = new HashMap<String, String>();
//		Map<String,String> persistentParameters = new HashMap<String, String>();
//		List<RequestContext.RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
//		persistentParameters.put("brojPoziva", "3");
//		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
//		new SmartScriptEngine(
//		new SmartScriptParser(documentBody).getDocumentNode(), rc
//		).execute();
//		System.out.println("Vrijednost u mapi: "+rc.getPersistentParameter("brojPoziva"));
		
	
	}

	/**
	 * Metoda koja vraća stringovnu reprezentaciju sadržaja datoteke predane putanje.
	 * @param filename putanja do datoteke.
	 * @return tekst iz datoteke.
	 * @throws IOException
	 */
	public static String readFromDisk(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"));
		String documentBody = "";
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			documentBody = sb.toString();
		} finally {
			br.close();
		}
		;
		return documentBody;

	}

}

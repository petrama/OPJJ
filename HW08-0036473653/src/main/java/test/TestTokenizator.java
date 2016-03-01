package test;

import hr.fer.zemris.java.filechecking.fmagician.lexical.FMagicianToken;
import hr.fer.zemris.java.filechecking.fmagician.lexical.FMagicianTokenType;
import hr.fer.zemris.java.filechecking.fmagician.lexical.FMagicianTokenizer;


/**
 * Demonstracija rada tokenizatora.
 * 
 * @author Petra Marče
 */
public class TestTokenizator {
	/**
	 * Metoda s kojom započinje izvođenje programa. Argumenti se ignoriraju.
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
//		String program =  "def dir1 \"${src}:hr.fer.zemris.java.filechecking\"exists f \"${dir1}/FCFileVerifier.java\" "
//				+ "+exists f \"${dir1}/FCProgramChecker.java\" terminate filename i\"${jmbag}-dz1.zip\"";
		
		//primjer iz zadaće
//		String program="filename i\"${jmbag}-dz1.zip\""
//						+"format zip {"
//						+"exists dir \"homework04\""
//						+"exists file \"homework04/build.xml\""
//						+"!exists dir \"homework04/bin\""
//						+"!exists d   \"homework04/build\""
//						+"!exists di  \"homework04/dist\""
//
//						 
//						 +"def src \"homework04/src/main/java\""
//
//						 
//						 +"exists dir \"${src}\""
//
//						 
//
//						 +"def dir1 \"${src}:hr.fer.zemris.java.tecaj.hw5.problem1a\""
//						 +"exists f \"${dir1}/IntegerStorage.java\""
//						 +"exists f \"${dir1}/IntegerStorageObserver.java\""
//						 +"exists f \"${dir1}/ObserverExample.java\""
//
//						 
//						 +"def dir1 \"${src}:hr.fer.zemris.java.custom.scripting.demo\""
//						 +"exists f \"${dir1}/ObjectMultistackDemo.java\""
//
//						 +"def dir1 \"${src}:hr.fer.zemris.java.custom.scripting.exec\""
//						 +"exists f \"${dir1}/ObjectMultistack.java\""
//						 +"exists f \"${dir1}/ValueWrapper.java\""
//
//						 
//
//						 +"def dir1 \"${src}:hr.fer.zemris.java.tecaj.hw5.problem1c\""
//						 +"exists f \"${dir1}/VLister.java\""
//
//						 
//
//						 +"def dir1 \"${src}:hr.fer.zemris.java.tecaj.hw5.crypto\""
//						 +"exists f \"${dir1}/Crypto.java\""
//
//
//						 
//
//						 +"def dir1 \"${src}:hr.fer.zemris.java.tecaj.hw5\""
//						 +"exists f \"${dir1}/MyShell.java\""
//
//
//						+" def dir1 \"${src}:hr.fer.zemris.java.filechecking.demo\""
//						 +"exists f \"${dir1}/FCDemo.java\""
//
//						 +"def dir1 \"${src}:hr.fer.zemris.java.filechecking\""
//						 +"exists f \"${dir1}/FCFileVerifier.java\""
//						 +"exists f \"${dir1}/FCProgramChecker.java\""
//
//						 
//						 +"terminate}"
//
//						 
//
//
//						+"fail @\"Datoteka koju ste uploadali nije dozvoljenog formata.\""
//
//
//						+"terminate";
//		String novi="filename i\"PROBA.zip\" @\"krivo ime\" {def src \"HW07-0036475070/src\"exists di \"${src}\" @\"Direktorij src mora postojati\" {def paket \"${src}/main/java:hr.fer.zemris.linearna\""
//				 +" exists fil \"${paket}/Vector.java\" {fail @\"Bravo, taj file treba postojati\"}exists d \"${paket}/demo/\" {fail @\"${src} + ${paket} + ${jmbag}   ${lastName}   889  ${firstName}  \"terminate}}}";
//		
		String naj="filename i\"pETRa\" @\"krivo ime\""
				+"format zip{"
				+"def jmbag \"0036473653\""
				+"def src \"HW05-${jmbag}/src\""
				+"def srcmain \"${src}/main/java\""
				+"def srctest \"${src}/test/java\""
				+"exists dir \"${srcmain}\" @\"Missing directory 'src/main/java'\"{"
					+"exists dir \"${srctest}\" @\"Missing directory 'src/test/java'\"{"
						+"fail @\"Good work I found both src dirs!\""
					+"}"
				+"def sorting \"${srcmain}:hr.fer.zemris.java.tecaj.hw5.sorting\""
				+"!exists f \"${sorting}/BigSort.java\" @\"Good! file BigSort is here!\"{"
					+"!fail{"
						+"fail @\"Expected file BigSort.java\""
						+"}" 
					
				+"}";
//		String pom = "def arg1_. i\"sad/sad/${zu}:hr.fer.zemris.hr\""
//				+ "exists di \"${src}:hr.fer.zemris.java.tecaj.hw5\" @\"Pogresno\""
//				+ "filename i\"${jmbag}-dz1.zip\"";
		FMagicianTokenizer tokenizer = new FMagicianTokenizer(naj);
		
		while(true) {
			FMagicianToken token = tokenizer.getCurrentToken();
			
			System.out.println("Trenutni token: "+token.getTokenType()
					+", vrijednost '"+token.getValue()+"'");
			if(token.getTokenType() == FMagicianTokenType.EOF) break;
			tokenizer.nextToken();
		};

	}

}

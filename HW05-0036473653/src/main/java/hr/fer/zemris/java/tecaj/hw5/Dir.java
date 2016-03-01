package hr.fer.zemris.java.tecaj.hw5;

import hr.fer.zemris.java.tecaj.hw5.filters.BigFilter;
import hr.fer.zemris.java.tecaj.hw5.sorting.BigSort;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Program koji dohvaća sve datoteke iz zadanog direktorija.
 * Datoteke filtrira,sortira te ispisuje ovisno o zadanim specifikatorima.
 * @author Petra Marče
 *
 */
public class Dir {
	
	/**
	 * Metoda koja se poziva pri pokretanju programa.
	 * @param args argumenti iz komandne linije.
	 * 		  Metoda kao prvi argument ocekuje putanju do nekog direktorija.
	 *        Opcionalno je dodavanje jednog ili više specifikatora sortiranja oblika -s:* gdje je *=s/n/m/t/l/e
	 *        i/ili specifikatora filtriranja -f:* gdje je *=sSIZE,g,lSIZE,e
	 *        i/ili specifikatora ispisa -w:* gdje je *=n/t/s/m/h
	 *        Jedino kod filtriranja poredak specifikatora nije bitan.
	 */
	public static void main(String[] args) {
		//ulazna provjera
		if (args.length == 0) {
			System.err.println("Program requires at least one argument!");
			System.exit(1);
		}
		File dir = new File(args[0]);

		if (dir.isDirectory() == false) {
			System.err
					.println("Program expects first argument to be pathname!");
			System.exit(1);

		}
		
		//razdjeljivanje argumenata u liste
		List<String> sortParameters = new ArrayList<>();
		List<String> filterParameters = new ArrayList<>();
		List<String> printParameters = new ArrayList<>();
		for (int i = 1; i < args.length; i++) {
			switch (args[i].trim().substring(0, 3)) {
			case "-s:":
				sortParameters.add(args[i].trim().substring(3));
				break;
			case "-f:":
				filterParameters.add(args[i].trim().substring(3));
				break;
			case "-w:":
				printParameters.add(args[i].trim().substring(3));
				break;
			default:
				System.err.println("Invalid parameters!");
				System.exit(1);
			}
		}

		List<File> contentOfDirectory = new LinkedList<>(Arrays.asList(dir
				.listFiles()));
		
		//prvo se radi filtriranje
		if (filterParameters.isEmpty() == false) {
			BigFilter filters = new BigFilter(contentOfDirectory,
					filterParameters);
			filters.filterAllFiles();
			contentOfDirectory = filters.getAllFiles();
			}

        //zatim sortiranje
		if (sortParameters.isEmpty() == false) {
			BigSort sorters = new BigSort(contentOfDirectory, sortParameters);
			sorters.sortAllFiles();
			contentOfDirectory = new LinkedList<>(sorters.getAllFiles());
			}
		
		//na kraju ispis
		if(contentOfDirectory.isEmpty()==false){
		PrintingInf print=new PrintingInf(printParameters, contentOfDirectory);
		print.printAll();
	}
	}
}

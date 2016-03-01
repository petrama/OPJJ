package hr.fer.zemris.java.tecaj.hw5.filters;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Razred koji obrađuje podatke filtriranjem.
 * @author Petra Marče
 *
 */

public class BigFilter {
	private List<String> givenFilters;
	private List<File> allFiles;

	/**
	 * Konstruktor.
	 * Inicijalizira varijable, zatim zove metodu createFilters.
	 * @param files prima listu podataka koju treba obraditi.
	 * @param filters prima proizvoljan broj stringova koji specificiraju filtere
	 */
	public BigFilter(List<File>files, List<String>filters) {
		givenFilters = filters;
		allFiles = new LinkedList<>(files);
	
	}
	
	/**
	 * Metoda koja slijedno čita stringove koji predstavljaju filtere.
	 * Stvara konkretan filter i poziva metodu koja provodi filtriranje.
	 */

	public void filterAllFiles() {
		
		for (String filter : givenFilters) {
			FileFilter tempFilter = new BothFilter(filter);
			getFilesFiltered(tempFilter);
		}
	}
	/**
	 * Metoda koja provodi filtriranje podataka.
	 * @param filter Filter po kojem se fitrira.
	 */

	public void getFilesFiltered(FileFilter filter) {
		Iterator<File> it = allFiles.iterator();

		while (it.hasNext()) {
			File f = it.next();
			if (filter.accept(f) == false) {
				it.remove();
			}
		}
	}

	public List<File> getAllFiles() {
		return allFiles;
	}

	
}

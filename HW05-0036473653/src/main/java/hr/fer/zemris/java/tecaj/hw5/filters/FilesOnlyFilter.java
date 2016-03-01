package hr.fer.zemris.java.tecaj.hw5.filters;

import java.io.File;
import java.io.FileFilter;
/**
 * Razred koji predstavlja filtrira datoteke.
 * Propušta samo one koje su prave datoteke.
 * @author Petra Marče
 *
 */
public class FilesOnlyFilter implements FileFilter {
	public boolean accept(File f) {
		if (f.isFile()) {
			return true;
		}
		return false;
	}

}

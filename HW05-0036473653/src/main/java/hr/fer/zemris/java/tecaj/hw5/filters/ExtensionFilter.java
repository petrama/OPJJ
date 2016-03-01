package hr.fer.zemris.java.tecaj.hw5.filters;

import java.io.File;
import java.io.FileFilter;
/**
 * Razred koji predstavlja filter po ekstenziji datoteke.
 * Prihvaća datoteke koje imaju ekstenziju.
 * @author petra
 *
 */
public class ExtensionFilter implements FileFilter {
		public boolean accept(File f) {
		if (f.getName().contains(".")) {
			return true;
			}
		return false;
	}

}

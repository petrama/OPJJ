package hr.fer.zemris.java.tecaj.hw5.filters;

import java.io.File;
import java.io.FileFilter;

/**
 * Razred koji fitrira datoteke po duljini imena.
 * Propušta samo one čije je ime kraće ili jednako od zadane duljine.
 * @author Petra Marče
 *
 */
public class NameSizeFilter implements FileFilter {
	private int maxLenName;

	public NameSizeFilter(String lenName) {
		super();
		try {
			this.maxLenName = Integer.parseInt(lenName);
		} catch (NumberFormatException e) {
			System.err.println("Invalid argument for name-length filter");
			System.exit(1);
		}
	}

	@Override
	public boolean accept(File f) {
		if (f.getName().length() <= this.maxLenName) {
			return true;
		}
		return false;
	}

}

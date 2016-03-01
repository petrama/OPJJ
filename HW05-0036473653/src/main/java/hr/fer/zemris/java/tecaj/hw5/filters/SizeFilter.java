package hr.fer.zemris.java.tecaj.hw5.filters;

import java.io.File;
import java.io.FileFilter;

/**
 * Razred koji filtrira datoteke po veličini.
 * Propušta samo one koje su manje ili jednake od zadane veličine
 * @author Petra Marče
 *
 */
public class SizeFilter implements FileFilter {
	private long size;
	
	
	public SizeFilter(String size) {
		super();
		try {
			this.size = Long.parseLong(size);
		} catch (NumberFormatException e) {
			System.err.println("Invalid argument for size filter");
			System.exit(1);
		}
	}


	public boolean accept(File f){
		if(f.canRead() ==true && f.length()<=this.size){
			return true;
		}
		return false;
	}

}

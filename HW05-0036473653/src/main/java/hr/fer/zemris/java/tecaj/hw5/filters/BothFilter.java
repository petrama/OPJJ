package hr.fer.zemris.java.tecaj.hw5.filters;

import java.io.File;
import java.io.FileFilter;

/**
 * Razred koji stvara konkretnu vrstu filtera.
 *  @author Petra Marče
 * 
 */
public class BothFilter implements FileFilter {
	private boolean reverse;
	private FileFilter trenutniFilter;

	/**
	 * Konstruktor.
	 * @param filter ulazni string na temelju kojega se stvara ispravan razred filtera.
	 */

	public BothFilter(String filter) {
		if (filter.charAt(0) == '-') {
			reverse = true;
			filter = filter.substring(1);
		} else {
			reverse = false;
		}

		switch (filter.charAt(0)) {
		case 's':
			trenutniFilter = new SizeFilter(filter.substring(1));
			break;

		case 'f':
			trenutniFilter = new FilesOnlyFilter();
			break;

		case 'l':
			trenutniFilter = new NameSizeFilter(filter.substring(1));
			break;

		case 'e':
			trenutniFilter = new ExtensionFilter();
			break;
		default:
			throw new IllegalArgumentException("Wrong specificator for filter!");

		}
	}

	public boolean accept(File f) {
		if (reverse == true) {
			return !(trenutniFilter.accept(f));

		}
		return trenutniFilter.accept(f);
	}
}

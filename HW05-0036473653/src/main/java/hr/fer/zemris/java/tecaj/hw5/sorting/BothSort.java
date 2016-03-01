package hr.fer.zemris.java.tecaj.hw5.sorting;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;

/**
 * Razred koji predstavlja sve potrebne Komparatore.
 * @author Petra Marče
 *
 */

public class BothSort implements Comparator<File> {

	private boolean reverse;
	private Comparator<File> tempComparator;
	
	/**
	 * Konstruktor.
	 * @param filter prima string na temelju kojeg određuje koji se komparator traži.
	 */
	public BothSort(String filter) {
		if (filter.charAt(0) == '-') {
			reverse = true;
			filter = filter.substring(1);
		} else {
			reverse = false;
		}

		switch (filter.charAt(0)) {
		case 's':
			tempComparator = new Comparator<File>() {
				public int compare(File f1, File f2) {
					return Long.compare(f1.length(), f2.length());
				}
			};
			break;

		case 'n':
			tempComparator = new Comparator<File>() {
				public int compare(File f1, File f2) {
					return f1.getName().compareTo(f2.getName());
				}
			};
			break;

		case 'm':
			tempComparator = new Comparator<File>() {
				public int compare(File f1, File f2) {
					return Long.compare(f1.lastModified(), f2.lastModified());
				}
			};
			break;

		case 't':
			tempComparator = new Comparator<File>() {
				public int compare(File f1, File f2) {
					return Boolean.compare(f1.isFile(), f2.isFile());
				}
			};
			break;
			
		case 'l':
			tempComparator = new Comparator<File>() {
				public int compare(File f1, File f2) {
					return Integer.compare(f1.getName().length(), f2.getName()
							.length());
				}
			};
			break;
		case 'e':
			tempComparator = new Comparator<File>() {
				public int compare(File f1, File f2) {
					return Boolean.compare(f1.canExecute(), f2.canExecute());
				}
			};
			break;
		default:
			System.err.println("Wrong specificator for sort!");
			System.exit(1);
		}
	}

	@Override
	public int compare(File o1, File o2) {
		if (reverse) {
			return (Collections.reverseOrder(tempComparator)).compare(o1, o2);
		}
		return tempComparator.compare(o1, o2);
	}

}

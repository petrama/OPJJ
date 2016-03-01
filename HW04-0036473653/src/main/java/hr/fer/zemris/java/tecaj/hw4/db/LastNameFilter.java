package hr.fer.zemris.java.tecaj.hw4.db;
/**
 * Razred koji predstavlja filter za zapise po prezimenu.
 * Dozvoljeni filteri koji sadrže najviše jedan znak *
 * @author Petra Marče
 *
 */

public class LastNameFilter implements IFilter {
	String prefix;
	String sufix;
	int mode; // 0-bez zvjezdice,1-sa zvjezdicom
	
	/**
	 * Metoda koja stvara novu instancu razreda filter iz zadanog niza.
	 * @param filter iz kojeg se stvara razred.
	 */
	public LastNameFilter(String filter) {
		int position = filter.indexOf("*");
		if (position == -1) {
			mode = 0;
			prefix = filter;
			sufix = "";
		} else {
			mode = 1;
			if (position == filter.length() - 1) {
				prefix = filter.substring(0,position);
				sufix = "";
			} else if (position == 0) {

				prefix = "";
				sufix = filter.substring(1);
			} else {

				prefix = filter.substring(0, position);
				sufix = filter.substring(position + 1);
			}
		}
		
	}

	public boolean accepts(StudentRecord record) {
		if (mode == 0) {
			return record.lastName.compareTo(prefix)==0;
		} else {
			
			return record.lastName.startsWith(prefix)
					&& record.lastName.endsWith(sufix);

		}

	}
}
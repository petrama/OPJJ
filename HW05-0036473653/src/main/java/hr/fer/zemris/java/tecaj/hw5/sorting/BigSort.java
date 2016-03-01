package hr.fer.zemris.java.tecaj.hw5.sorting;

import java.io.File;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Razred koji vrši sortiranje podataka prema zadanim sortovima.
 * Broj sortova je proizvoljan, ali redosljed je bitan.
 * @author Petra Marče
 *
 */
public class BigSort {
	private List<String> givenSorts;
	private List<File> allFiles;
	private List<Integer> granice;
	
	/**
	 * Konstruktor.
	 * @param files prima podake koje mora sortirati.
	 * @param sorts prima polje stringova koje predstavljaju zapis sorta.
	 */

	public BigSort(List<File> files, List<String> sorts) {
		givenSorts=sorts;
		allFiles = new LinkedList<>(files);
		granice=new LinkedList<>( Arrays.asList(0, allFiles.size()));
	
	}
	
	/**
	 * Metoda koja vrši sva sortiranja.
	 * Stvara odgovarajući komparator i sortira podatke prema njemu.
	 */

	public void sortAllFiles() {
		for (String sort : givenSorts) {
			Comparator<File> sortComparator = new BothSort(sort);
			sortEm(sortComparator);
		}
	}
	/**
	 * Metoda koja provodi sortiranje po grupama koje su nastale prethodnim sortiranjima.
	 * @param sortComparator komparator prema kojemu treba izvršiti sortiranje.
	 */

	public void sortEm(Comparator<File> sortComparator) {
		for (int i = 0; i < granice.size() - 1; i++) {//broj internih sortiranja jednak je broju grupa
			if (granice.size() == 2) {//prvo sortiranje samo jedna grupa (dvije granice)
				Collections.sort(allFiles, sortComparator);
				break;
			} else {
				List<File> subList = allFiles.subList(granice.get(i),
						granice.get(i + 1));
				Collections.sort(subList, sortComparator);

			}
		}
		dodajNoveGranice(sortComparator);
		Collections.sort(granice); //granice se moraju sortirat!
		
	}

	public List<File> getAllFiles() {
		return allFiles;
	}

	/**
	 * Metoda koja služi za dodavanje novih granica nakon uspjesnog sortiranja po grupama.
	 * @param sortComparator komparator prema kojemu je prethodno izvrseno sortiranje
	 */
	public void dodajNoveGranice(Comparator<File> sortComparator) {
		for (int i = 0; i < allFiles.size() - 1; i++) {
			if (sortComparator.compare(allFiles.get(i), allFiles.get(i + 1)) != 0) {
				if (granice.contains(i + 1) == false) {
					granice.add((i + 1));
					
				}
			}

		}
	}
}

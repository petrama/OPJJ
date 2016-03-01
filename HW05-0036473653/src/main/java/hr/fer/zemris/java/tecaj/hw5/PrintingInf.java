package hr.fer.zemris.java.tecaj.hw5;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Razred koji služi ispisu prema zadanim specifikatorima.
 * @author Petra Marče
 *
 */

public class PrintingInf {
	private List<String> specificators;
	private List<File> allFiles;
	
	/**
	 * Konstruktor.
	 * @param spec ulazni specifikatori. Ako ih nema, defaultni je n.
	 * @param data Lista datoteka koju treba ispisati.
	 *        Konstruktor poziva metodu printAll() koja izvodi ispis.
	 */
	public PrintingInf(List<String> spec, List<File> data) {

		this.specificators = new ArrayList<>();
		if (spec.isEmpty()) {
			this.specificators.add("n");
		} else {
			this.specificators = spec;
		}
		this.allFiles = data;
	

	}
	
	/**
	 * Metoda koja izvodi ispis prema zadanim specifikatorima.
	 */
	public void printAll() {
		printHeader();
		for (File f : allFiles) {

			for (String s : specificators) {
				System.out.print("|");
				switch (s) {
				case "n":

					printName(f, getMaxName());
					break;
				case "t":
					printType(f);
					break;
				case "s":
					printSize(f, getMaxSize());
					break;
				case "m":
					printDateModified(f);
					break;
				case "h":
					printHidden(f);
					break;
				default:
					System.err.println("Unvalid column for print!");
					System.exit(1);
					break;
				}

			}
			System.out.println("|");

		}
		printHeader();
	}
	
	/**
	 * Metoda koja određuje prostor u ispisu koji treba predvidjeti za ime.
	 * vraca  duljinu najduljeg imena+2
	 * 
	 */
	public int getMaxName() {
		int getMax = allFiles.get(0).getName().length();

		for (File f : allFiles) {
			if (f.getName().length() > getMax) {
				getMax = f.getName().length();
			}
		}
		return getMax + 2;

	}
	/**
	 * Metoda koja određuje velicinu prostora u ispisu kojeg treba predvidjeti za velicinu.
	 * @return vraća broj znamenaka najduljeg broja+2
	 */

	public int getMaxSize() {
		Long getMax = allFiles.get(0).length();

		for (File f : allFiles) {
			if (f.length() > getMax) {
				getMax = f.length();
			}
		}

		return (getMax.toString().length()) + 2;

	}
	/**
	 * Metoda koja ispisuje zaglavlje i podnožje.
	 */

	public void printHeader() {
		StringBuilder head=new StringBuilder();

		for (String s : specificators) {
			head.append("+");
			switch (s) {
			case "n":

				for (int i = 0; i < getMaxName(); i++) {
					head.append("-");
				}

				break;
			case "t":
				head.append("---");

				break;
			case "s":

				for (int i = 0; i < getMaxSize(); i++) {
					head.append("-");
				}

				break;
			case "m":
				head.append("---------------------");

				break;
			case "h":
				head.append("---");

				break;
			default:
				break;
			}
		}
		System.out.println(head + "+");

	}
	
	/**
	 * Metoda koja ispisuje ime zadane datoteke.
	 * @param f datoteka cije ime zelimo ispisati.
	 * @param maxName format ispisa imena
	 */
	public static void printName(File f, int maxName) {
		System.out.printf("%-" + maxName + "s", " " + f.getName() + " ");
	}

	/**
	 * Metoda koja ispisuje tip datoteke.
	 * Ispisuje d za direktorij f za obicnu datoteku.
	 * @param f ulazna datoteka
	 */
	public static void printType(File f) {
		System.out.print(f.isDirectory() ? " d " : " f ");
	}
	/**
	 * Metoda koja ispisuje velicinu datoteke u oktetima.
	 * @param f datoteka cija se velicina treba ispisati.
	 * @param maxSize format ispisa za velicinu
	 */
	public static void printSize(File f, int maxSize) {
		System.out.printf("%" + maxSize + "s", " " + f.length() + " ");
	}
	/**
	 * Metoda koja sluzi ispisu datuma zadnje izmjene datoteke.
	 * @param f datoteka o kojoj se informacija ispisuje.
	 */

	public static void printDateModified(File f) {
		Date date = new Date(f.lastModified());
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		String formatiraniDatum = sdf.format(date);
		System.out.print(formatiraniDatum);
	}
	/**
	 * Metoda koja sluzi ispisu vidljivosti datoteke.
	 * Ako je file skriven ispisuje se h inače ništa.
	 * @param f datoteka o kojoj se informacija ispisuje.
	 */
	public static void printHidden(File f) {
		System.out.print(f.isHidden() ? " h " : "   ");
	}

}

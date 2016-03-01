package hr.fer.zemris.java.tecaj.hw4.db;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

/**
 * Program koji čita iz datoteke liniju po liniju.
 * Iz datoteke stvara bazu studenata.
 * @author Petra Marče
 *
 */
public class StudentDB {
	/**
	 * Metoda koja se poziva pri pokretanju programa.
	 * @param args argumenti iz komandne linije.
	 * @throws IOException baca iznimku ako ne može pronaći datoteku ili čitati iz datoteke.
	 */
	public static void main(String args[]) throws IOException {

		List<String> lines = Files.readAllLines(Paths.get("./database.txt"),
				StandardCharsets.UTF_8);

		StudentDatabase baza = new StudentDatabase(lines);

		Scanner scanInput = new Scanner(System.in);
		String ulaz;
		do {
			System.out.println("Command must start with a word \"query\"!");
			ulaz = scanInput.nextLine().trim();
		} while (ulaz.startsWith("query") == false);
		scanInput.close();
		ulaz = ulaz.substring(5).trim();

		if (ulaz.startsWith("lastName=")) {
			ulaz = ulaz.substring(10, ulaz.length() - 1).trim();

			LastNameFilter surnames = new LastNameFilter(ulaz);
			List<StudentRecord> nova = baza.filter(surnames);
			formattedPrint(nova);
		} else if (ulaz.startsWith("jmbag=")) {
			ulaz = ulaz.substring(7, ulaz.length() - 1);
			List<StudentRecord> nova = new ArrayList<>(1);
			nova.add(baza.forJMBAG(ulaz));
			formattedPrint(nova);
			
		}

	}
	/**
	 * Metoda koja formatirano ispisuje zapise studenata.
	 * @param zapis lista studenata koju treba ispisati.
	 */

	public static void formattedPrint(List<StudentRecord> zapis) {
		int maxIme = 0, maxPrezime = 0;
		for (StudentRecord record : zapis) {
			if (record.firstName.length() > maxIme) {
				maxIme = record.firstName.length();
			}
			if (record.lastName.length() > maxPrezime) {
				maxPrezime = record.lastName.length();
			}

		}
		if (zapis.isEmpty() == false) {

			printHeader(maxIme, maxPrezime);
			System.out.println();
			for (StudentRecord record : zapis) {
				System.out.println(String.format("| %10s | %-" + maxPrezime
						+ "s | %-" + maxIme + "s | %1d |", record.jmbag,
						record.lastName, record.firstName, record.finalGrade));
			}
			printHeader(maxIme, maxPrezime);
			
		}System.out.println("\nRecords selected: "+zapis.size());
	}

	public static void printHeader(int maxIme, int maxPrezime) {
		System.out.print("+============+=");
		for (int i = 0; i < maxPrezime; i++) {
			System.out.print("=");
		}
		System.out.print("=+=");
		for (int i = 0; i < maxIme; i++) {
			System.out.print("=");
		}
		System.out.print("=+===+");

	}
}

package hr.fer.zemris.java.tecaj.hw6.problem1a;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Razred predstavlja promatrača. Promatrač je objekt koji čeka na promjenu
 * statusa vrijednosti objekta kojeg promatra. Kad objekt koji se promatra
 * dojavi promjenu,promatrač poduzima neku akciju. Ovaj razred dovaća novu
 * vrijednost promatranog objekta, te je upisuje u zadanu datoteku.
 * 
 * @author Petra Marče
 * 
 */
public class LogValue implements IntegerStorageObserver {
	File file;

	/**
	 * Konstruktor.
	 *  @param pathToFile prima putanju do datoteke u koju treba upisati vrijednosti.
	 */
	public LogValue(Path pathToFile) {
		super();
		file = new File(pathToFile.toString());
		if (file.exists() == false) {
			try {
				file = Files.createFile(pathToFile).toFile();
			} catch (IOException e) {
				System.err.println("Cannot create new file");
			}
		}
	}
	/**
	 * Metoda koja se poziva kada se promjeni vrijednost promatranog objekta.
	 * Metoda upisuje novu vrijednost u datoteku.
	 */

	public void valueChanged(IntegerStorage istorage) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(file, true);

		} catch (FileNotFoundException | SecurityException e) {
			System.err.println("Cannot open file!");

		}
		if (os == null) {
			System.err.println("Output stream is null!");
		}
		Integer value = istorage.getValue();
		byte[] data = value.toString().getBytes(StandardCharsets.UTF_8);
		for (int i = 0; i < data.length; i++) {
			try {
				os.write(data[i]);
			} catch (IOException e) {
				System.err.println("Cannot write in file!");
			}
		}

		try {
			os.close();
		} catch (Exception e) {
			System.err.println("Cannot close file!");
		}
	}

}

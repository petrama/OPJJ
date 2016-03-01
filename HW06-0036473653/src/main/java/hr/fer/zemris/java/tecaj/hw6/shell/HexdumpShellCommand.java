package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
* Razred predstavlja jednu od komandi razreda MyShell.
* Ova komanda čita sadržaj zadane datoteke u grupama po 16 bajtova.
* Ispisuje redak po redak heksadekadske vrijednosti tih bajtova i tekstovnu reprezentaciju istih.
* @author Petra Marče.
*
*/
public class HexdumpShellCommand implements ShellCommand {

	/**
	 * Metoda koja daje traženi ispis.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) {
		if (arguments.length != 1) {
			MyShell.writeMessage("Hexdump command expects only one argument",
					out);
		}
		Path filePath = Paths.get(arguments[0]);

		try {
			BufferedInputStream input = new BufferedInputStream(
					new FileInputStream(filePath.toFile()));
			int lineNumber = 0;
			int read = 0;
			byte[] inputBuff = new byte[16];
			while ((read = input.read(inputBuff)) >= 1) {
				out.newLine();
				out.write(String.format("%08x", lineNumber) + ": "
						+ hexa(inputBuff, read, 16)
						+ textRepresent(inputBuff, read));
				lineNumber += 16;
			}
			out.newLine();
			out.flush();
			input.close();
		} catch (FileNotFoundException e) {
			return MyShell.writeMessage("File '" + arguments[0]
					+ "' not found.", out);
		} catch (IOException e) {
			System.out.println("Error with input/output stream.");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda koja od pola bajtova gradi string.
	 * 
	 * @param bytes
	 *            polje bajtova koja treba interpretirati kao string.
	 * @param size
	 *            duljina polja
	 * @return stringovna reprezentacija
	 */
	public static String textRepresent(byte[] bytes, int size) {
		StringBuilder rep = new StringBuilder();
		for (int i = 0; i < size; i++) {
			if (bytes[i] < 37 || bytes[i] > 127) {
				rep.append(".");
			} else {
				rep.append(String.format("%c", bytes[i]));
			}

		}
		return rep.toString();
	}

	/**
	 * Metoda koja iz polja bajtova vraća string koji predstavlja heksadekadski
	 * niz.
	 * 
	 * @param bytes
	 *            bajtovi koje treba heksadekadski zapisati.
	 * @param size
	 *            duljina polja.
	 * @param rowSize
	 *            duljina jednog retka u ispisu.
	 * @return
	 */

	private String hexa(byte[] bytes, int size, int rowSize) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			if (i < size) {
				buffer.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			} else {
				buffer.append("  ");
			}

			if (i == rowSize / 2 - 1)
				buffer.append("|");
			else
				buffer.append(" ");
		}

		return buffer.append("| ").toString();
	}

	}



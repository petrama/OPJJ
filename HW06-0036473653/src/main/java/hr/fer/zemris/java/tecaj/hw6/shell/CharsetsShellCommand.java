package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map.Entry;
/**
* Razred predstavlja jednu od komandi razreda MyShell.
* Ova komanda je komanda koja na standardni izlaz ispisuje sve trenutno podržane charsetove.
* @author Petra Marče.
*
*/
public class CharsetsShellCommand implements ShellCommand {

	/**
	 * Metoda cijim se pozivom komanda izvrsava.
	 * Metoda ne ocekuje ulazne argumente.
	 */

	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) {
		Iterator<Entry<String, Charset>> it = Charset.availableCharsets()
				.entrySet().iterator();
		while (it.hasNext()) {
			Charset charset = it.next().getValue();
			try {
				out.write(charset.toString());
				out.newLine();
			} catch (IOException e) {
				System.err.println("Problem with I/O operations!");
			}
		}

		try {
			out.flush();
		} catch (IOException e) {
			System.err.println("Problem with I/O operatinos!");
		}

		return ShellStatus.CONTINUE;
	}

}

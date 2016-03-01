package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
/**
 * Razred predstavlja jednu od komandi razreda MyShell.
 * Ova komanda je komanda ispisavanja sadržaja zadane datoteke na standardni izlaz.
 * @author Petra Marče.
 *
 */
public class CatShellCommand implements ShellCommand {

	/**
	 * Metoda koja čijim se pozivom komanda izvršava.
	 * Metoda kao ulazne argumente očekuje putanju do datoteke ciji sadrzaj treba ispisati.
	 * Dodatno podržano je zadavanje određenog charseta, no ako se ne zada komanda će koristiti defaultni.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) {

		if (arguments.length < 1 || arguments.length > 2) {
			String message = "'cat' command expects one or two arguments!";
			return MyShell.writeMessage(message, out);
		}
		Charset charset = Charset.defaultCharset();
		if (arguments.length == 2) {
			charset = Charset.availableCharsets().get(arguments[1]);
		}

		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(new FileInputStream(arguments[0])),
					charset));

			String line = null;
			while ((line = input.readLine()) != null) {
				out.write(line);
				out.newLine();
				out.flush();
			}

			out.newLine();
			out.flush();
			input.close();
		} catch (FileNotFoundException f) {

			return MyShell.writeMessage("File  " + arguments[0]
					+ " does not exist!", out);
		} catch (IOException io) {
			System.out.println("Error with input output operations!");
		}

		return ShellStatus.CONTINUE;
	}

}

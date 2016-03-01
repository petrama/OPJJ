package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Razred predstavlja jednu od komandi ljuske MyShell.
 * Komanda služi za stavaranje nove strukture direktorija.
 * Komanda kao argument očekuje putanju koja predstavlja novu strukturu direktorija.
 * @author Petra Marče.
 *
 */
public class MkdirShellCommand implements ShellCommand {

	/**
	 * Metoda čijim se pozivom stvara novi direktorij zadane strukture.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) {
		if (arguments.length != 1) {
			System.err.println("Command expects one argument");
		}
		try {
			Path p = Paths.get(arguments[0]);
			Files.createDirectories(p);
		} catch (IOException e) {
			System.err.println("Directories cannot be created!");
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.CONTINUE;
	}

}

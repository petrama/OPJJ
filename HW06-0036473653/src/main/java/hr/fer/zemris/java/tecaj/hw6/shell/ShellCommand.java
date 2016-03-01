package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
/**
 * Sučelje koje predstavlja komandu ljuske MyShell
 * @author Petra Marče
 *
 */
public interface ShellCommand {
	/**
	 * Metoda čijim se pozivom komanda izvršava.
	 * @param in ulazni stream preko kojeg se komunicira s korisnikom.
	 * @param out izlazni stream preko kojeg se komunicira s korisnikom.
	 * @param arguments argumenti za izvrsavanje zadane instrukcije.
	 * @return status izvršavanja može biti Continue ili Terminate
	 */
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments);
}

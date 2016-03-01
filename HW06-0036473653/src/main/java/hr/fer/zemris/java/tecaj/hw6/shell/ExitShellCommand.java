package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
/**
* Razred predstavlja jednu od komandi razreda MyShell.
* Ova komanda cijim se izvrsavanjem terminira program, tj zavrsava rad sa ljuskom.
* @author Petra Marče.
*
*/
public class ExitShellCommand implements ShellCommand {

	/**
	 * Metoda cijim se pozivom rad sa MyShell zavrsava.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) {
		return ShellStatus.TERMINATE;
	}

}

package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
* Razred predstavlja jednu od komandi razreda MyShell.
* Ova komanda je komanda koja kopira sadržaj iz jedne u drugu datoteku.
* Ako odredisna datoteka postoji, komanda trazi od korisnika dopustenje za nastavak rada.
* @author Petra Marče.
*
*/
public class CopyShellCommand implements ShellCommand {

	/**
	 * Metoda cijim se pozivom komanda izvršava.
	 * Metoda kao argument ocekuje dvije putanje, prvu do izvorisne i drugu do odredisne datoteke.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) {

		if (arguments.length != 2) {
			String message = "Command copy exepcts two aguments!";

			return MyShell.writeMessage(message, out);
		}
		File src = new File(arguments[0]);
		Path srcPath = Paths.get(arguments[0]);
		if (src.isFile() == false) {
			String message = "First argument must be file not directory!";

			return MyShell.writeMessage(message, out);
		}

		try {
			Path destPath = Paths.get(arguments[1]);
			if (Files.exists(destPath) == false
					&& Files.exists(destPath.getParent()) == false) {
				return MyShell.writeMessage(
						"Destinaton directory doesn't exist!", out);
			} else if (Files.isDirectory(destPath)) {
				String destString = arguments[1] + File.separator
						+ srcPath.getFileName();
				destPath = Paths.get(destString);
			}

			if (Files.exists(destPath) && Files.isRegularFile(destPath)) {
				out.write("Destination file already exists! Would you like to overwrite it?( Y-yes N-no ) :");
				out.newLine();
				out.flush();

				String messageFromUser = in.readLine();
				if (messageFromUser != null) {
					messageFromUser = messageFromUser.toUpperCase();
				}
				if (messageFromUser.equals("N")) {
					return MyShell.writeMessage(
							"Existing file is not overwritten!", out);
				} else if (messageFromUser.equals("Y") == false) {
					return MyShell.writeMessage(
							"Invalid command. Copying unsuccessful!", out);
				}

			}
			File dest = destPath.toFile();
			BufferedReader input = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(new FileInputStream(src)), "UTF-8"));

			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(
					new BufferedOutputStream(new FileOutputStream(dest)),
					"UTF-8"));

			String line = null;
			while ((line = input.readLine()) != null) {
				output.write(line);
				output.newLine();
				output.flush();
			}

			output.newLine();
			output.flush();
			input.close();
			output.close();
			return MyShell.writeMessage("Files copyied!", out);

		} catch (FileNotFoundException fe) {
			System.err.println("File not found!");
			return ShellStatus.CONTINUE;
		} catch (IOException io) {
			System.err.println("File cannot be opened!");
			return ShellStatus.CONTINUE;
		}

	}

}

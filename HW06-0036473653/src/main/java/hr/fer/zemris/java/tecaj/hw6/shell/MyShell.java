package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Razred koji predstavlja ljusku kroz koji se mogu izvrsavati neke naredbe.
 * Podrzane komande su cat,copy,charsets,exit,hexdump,mkdir,sybol i tree
 * Kommanda ima oblik nazivKomande "putanja" arg2, tj: prva riječ je ključna
 * riječ koja određuje komandu za izvršavanje. Ostatak u liniji tumačit će se
 * kao argumenti za komandu. Putanje do datoteka i direktorija pišu se unutar
 * običnih navodnika.
 * 
 * @author Petra Marče
 * 
 */
public class MyShell {

	private static Map<String, ShellCommand> commands = new HashMap<String, ShellCommand>();

	/**
	 * Metoda koja se poziva pri pokretanju programa.
	 * 
	 * @param args
	 *            argumenti iz komandne linije. Ljuska ne očekuje argumente.
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to MyShell v 1.0");
		ShellStatus status = ShellStatus.CONTINUE;

		commands.put("charsets", new CharsetsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in,
				StandardCharsets.UTF_8));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				System.out, StandardCharsets.UTF_8));

		do {
			try {
				out.write(ShellSymbol.prompt + " ");
				out.flush();

				String[] userInput = readLines(in);
				if (userInput == null) {
					MyShell.writeMessage("No arguments provided!", out);
				} else {
					String commandName = userInput[0];
					String[] commandArgs = Arrays.copyOfRange(userInput, 1,
							userInput.length);
					if (commandName != null) {
						ShellCommand command = commands.get(commandName);

						if (command == null) {
							out.write("Unknown command.");
							out.newLine();
							out.flush();
						} else {
							status = command.executeCommand(in, out,
									commandArgs);
						}
					}
				}
			} catch (IOException e) {
				System.out.println("Error with reading from/to input/output.");
			}
		} while (status == ShellStatus.CONTINUE);

	}

	/**
	 * Metoda koja služi za čitanje jedne ili više linija. Metoda obrađuje
	 * pročitanu liniju i izdvaja naziv komande i komandne argumente.
	 * 
	 * @param in
	 *            ulazni stream iz kojeg čita liniju,standardni ulaz
	 * @return vraća polje čiji je prvi element naziv komande, a ostali
	 *         argumenti za istu
	 */
	private static String[] readLines(BufferedReader in) {
		String line = "";

		try {
			line = in.readLine();
			if (line != null) {
				while (line.endsWith(" " + ShellSymbol.morelines)) {
					System.out.print(ShellSymbol.multilines + " ");
					String newLine = in.readLine();
					line = line.substring(0, line.length() - 1);
					line += newLine;
				}
			}
		} catch (IOException e) {
			System.out.println("Error with reading from input buffer.");
		}

		return splitCommandArguments(line);
	}

	/**
	 * Metoda koja služi ispisu određene poruke na zaslon korisniku.
	 * 
	 * @param message
	 *            poruka koja se treba ispisati.
	 * @param out
	 *            izlazni stream, standardni izlaz
	 * @return uvijek vraća konmandni status za nastavak rada komande
	 */
	public static ShellStatus writeMessage(String message, BufferedWriter out) {
		try {
			out.write(message);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			System.err.println("Error with output stream!");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda koja razdvaja argumente iz linije. Uzima u obzir da je sadržaj
	 * unutar navodnika jedan argument.
	 * 
	 * @param line
	 *            linija za obradu.
	 * @return polje argumenata
	 */
	public static String[] splitCommandArguments(String line) {
		String token = "";
		if (line != null) {
			line = line.trim();
			List<String> tokens = new ArrayList<>(3);

			while (line.compareTo("") != 0) {
				if (line.startsWith("\"")) {
					line = line.substring(1);
					int second = line.indexOf("\"");
					if (second == -1) {
						System.err.println("Unvalid arguments");
						System.exit(1);
					}
					token = line.substring(0, second).trim();
					tokens.add(token);
					if (second < line.length() - 1) {
						line = line.substring(second + 1).trim();

					} else {
						break;
					}
				} else {
					int second = line.indexOf(" ");
					if (second == -1) {
						token = line;
						tokens.add(token);
						System.out.println(token);
						break;
					} else {
						token = line.substring(0, second).trim();
						tokens.add(token);
						line = line.substring(second + 1).trim();
					}

				}
			}
			String[] arr = new String[tokens.size()];
			tokens.toArray(arr);
			return arr;
		}
		return null;
	}
}
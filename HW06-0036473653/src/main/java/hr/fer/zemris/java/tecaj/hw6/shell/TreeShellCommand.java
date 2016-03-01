package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Razred predtsavlja jednu od koamndi razreda MyShell.
 * Komanda očekuje jedan argument-putanju do direktorija.
 * Izvršenjem naredbe ispisuje se sadržaj direktorija i svih njegovih poddirektorija.
 * @author petra
 *
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * Razred koji pregledno ispisuje sve datoteke u direktoriju, i pod direktorijima.
	 * @author Petra Marče
	 *
	 */
	static class FileVisitorObilazak implements FileVisitor<Path> {

		private int indent = 0;
		private BufferedWriter bufOut = null;
		private String message = "";

		public void setBuffer(BufferedWriter buf) {
			this.bufOut = buf;
		}
		
		/**
		 * Metoda koja definira akcije koja treba poduzeti prije ulaska u novi direktorij.
		 * 
		 */
		@Override
		public FileVisitResult preVisitDirectory(Path file,
				BasicFileAttributes attrs) throws IOException {

			if (indent == 0) {
				System.out.println(file.toFile().getAbsolutePath());

			}

			else {
				message = String.format("%" + indent + "s%s%n", "",
						file.getName(file.getNameCount() - 1));
			}
			bufOut.write(message);
			indent += 2;
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Metoda koja definira posao koji treba obaviti u samom direktoriju.
		 */
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			message = String.format("%" + indent + "s%s%n", "",
					file.getName(file.getNameCount() - 1));
			bufOut.write(message);
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Metoda koja definira akcije koje treba poduzeti ako obilazak datoteke
		 * ne uspije.
		 */
		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Metoda koja definira koje akcije treba poduzeti pri izlasku iz
		 * direktorija.
		 */

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				throws IOException {
			indent -= 2;
			return FileVisitResult.CONTINUE;
		}
	}

	/**
	 * Metoda čijim se pozivom komanda izvršava. Metoda očekuje jedan argument,
	 * putanju do direktorija čiji sadržaj treba ispisati.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) {
		if (arguments.length != 1) {
			System.err.println("Path to directory expected");
			return ShellStatus.CONTINUE;
		}
		Path p = Paths.get(arguments[0]);
		if (!Files.isDirectory(p)) {

			System.err.println("Path to directory expected");
			return ShellStatus.CONTINUE;
		}
		try {
			FileVisitorObilazak obilazak = new FileVisitorObilazak();
			obilazak.setBuffer(out);
			Files.walkFileTree(p, obilazak);

			out.flush();
		} catch (IOException e) {
			System.err.println("Walking unsuccesful");
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.CONTINUE;
	}

}

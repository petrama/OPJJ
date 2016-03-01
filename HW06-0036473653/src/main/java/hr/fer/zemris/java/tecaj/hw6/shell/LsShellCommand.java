package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Razred koji predstavlja jednu od komandi ljuske MyShell.
 * Komanda ispisuje sadržaj zadanog direktorija, ne ulazeći u poddirektorije.
 * Komanda ispisuje sadržaj na standardni izlaz, ispis je formatiran.
 * Očekivani ulazni argument je putanja do direktorija ciji sadrzaj treba ispisati.
 * @author Petra Marče
 *
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * Metoda čijim se pozivom izvršava komanda.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) {
		if (arguments.length != 1) {
			return MyShell.writeMessage("Ls expects only one argument-Path!",
					out);
		}

		File[] allFiles = (new File(arguments[0])).listFiles();
		for (File f : allFiles) {
			StringBuilder sb = new StringBuilder();

			if (f.isDirectory()) {
				sb.append("d");
			} else {
				sb.append("-");
			}

			if (f.canRead()) {
				sb.append("r");
			} else {
				sb.append("-");
			}

			if (f.canWrite()) {
				sb.append("w");
			} else {
				sb.append("-");
			}

			if (f.canExecute()) {
				sb.append("x");
			} else {
				sb.append("-");
			}
			Long len = f.length();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Path path = Paths.get(arguments[0]);
			BasicFileAttributeView faView = Files.getFileAttributeView(path,
					BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
			String formattedDateTime = "";
			try {
				BasicFileAttributes attributes = faView.readAttributes();

				FileTime fileTime = attributes.creationTime();
				formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
			} catch (IOException e) {
				return MyShell.writeMessage(
						"File attribut date-tame cannot be read!", out);
			}

			String name = f.getName();
			String all = String.format("%4s %10d %s %s", sb, len,
					formattedDateTime, name);

			try {
				out.write(all);
				out.newLine();
				out.flush();
			} catch (IOException e) {
				System.out.println("Eror with input/output operations!");
			}

		}
		return ShellStatus.CONTINUE;
	}

}

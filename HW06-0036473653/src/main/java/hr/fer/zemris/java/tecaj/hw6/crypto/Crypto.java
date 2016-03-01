package hr.fer.zemris.java.tecaj.hw6.crypto;

import java.util.Scanner;
/**
 * Razred koji omogućuje enkripciju,dekripciju i računanje SHA-1 vrijednosti datoteke.
 * @author Petra Marče.
 *
 */
public class Crypto {

	/**
	 * Metoda koja se poziva pri pokretanju programa.
	 * @param args argumenti iz komandne linije.
	 * Program ocekuje prvi argument kao naziv komande,kljucne rijeci:chechsha,encrypt i decrypt.
	 * Kao drugi argument za komandu chechsha ocekuje putanju do datoteke kojoj treba odrediti SHA-1 checksum.
	 * Encrypt i Decrypt ocekuju jos dva argumenta, prvi je putanja do izvorisne a drugi putanja do odredisne datoteke.
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err
					.println("Please provide some of the following commands - 'checksha', 'encrypt' or 'decrypt'.");
			System.exit(1);
		}

		if (args[0].equals("checksha")) {
			if (args.length != 2) {
				System.err
						.println("Command should have aditional parameter - path of input file.");
				System.exit(1);
			}

			getSHAChecked(args[1]);

		} else if (args[0].equals("encrypt") || args[0].equals("decrypt")) {
			if (args.length != 3) {
				System.err
						.println("Command should have two aditional parameters - paths of input and output file.");
				System.exit(1);
			}

			getFilesEncripted(args[0], args[1], args[2]);
		} else {
			throw new IllegalArgumentException(
					"Available commands are 'checksha', 'encrypt' and 'decrypt'.");
		}
	}

	/**
	 * Metoda koja provodi enkripciju ili dekripciju datoteke.
	 * Traži od korisnika unos ključa za enkripciju ili dekripciju, te inicijalizacijskog vektora.
	 * Metoda ovisno o uspjesnosti ispisuje odgovarajucu poruku korisniku.
	 * @param mode varijabla koja određuje da li treba provesti enkripciju ili dekripciju.
	 * @param in putanja do izvorisne datoteke koji treba obraditi.
	 * @param out putanja do datoteke koja treba biti rezultat obrade.
	 */
	public static void getFilesEncripted(String mode, String in, String out) {
		int modeValue = 0;
		String message = "";
		if (mode.equals("encrypt")) {
			modeValue = FileCrypt.ENCRYPT;
			message = "Encrypting";
		} else {
			modeValue = FileCrypt.DECRYPT;
			message = "Decrypting";
		}
		System.out
				.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");

		Scanner scan = new Scanner(System.in, "UTF-8");
		String password = scan.nextLine().trim();
		System.out
				.println("Please provide initialization vector as hex-encoded text (32 hex-digits) :");
		String initVector = scan.nextLine().trim();
		scan.close();

	
		FileCrypt fc = new FileCrypt(in, out, password, initVector, modeValue);
		if (fc.criptFiles()) {
			System.out.println(message + " completed. Generated file " + out
					+ " based on file " + in + ".");
		} else {
			System.out.println(message+" failed!");
		}
	}
	/**
	 * Metoda koja određuje SHA-1 vrijednost datoteke.
	 * Traži od korisnika unos očekivane vrijednosti rezultata ove funkcije,
	 * kako bi mogao ispisati da li se očekivano slaže s rezultatom ili ne.
	 *  @param file putanja do datoteke.
	 */

	public static void getSHAChecked(String file) {
		System.out.println("Please provide expected sha signature for "+file);
		Scanner scan=new Scanner(System.in,"UTF-8");
		String expectedValue=scan.nextLine();
		scan.close();
		
		TestChecksum tc=new TestChecksum(file,expectedValue);
		if (tc.verifyChecksum()) {
			System.out.println("Digesting completed. Digest of " + file
					+ " matches expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of " + file
					+ " does not match the expected digest. Digest was: "
					+ tc.getFileDigest());
		}
			
		}
	}



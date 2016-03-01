package hr.fer.zemris.java.tecaj.hw6.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Razred koji omogućava enkripciju ili dekripciju podataka.
 * @author Petra Marče
 *
 */
public class FileCrypt {

	public static final int ENCRYPT = Cipher.ENCRYPT_MODE;
	public static final int DECRYPT = Cipher.DECRYPT_MODE;

	private Path inputPath;
	private Path outputPath;
	private Cipher cipher;

	/**
	 * Konstruktor.
	 * @param inputFileString putanja do izvorisne datoteke.
	 * @param outputFileString putanja do odredisne datoteke.
	 * @param passwordKey kljuc enkripcije/dekripcije.
	 * @param initVector inicijalizacijski vektor.
	 * @param mode varijabla koja određuje operaciju.
	 */
	public FileCrypt(String inputFileString, String outputFileString,
			String passwordKey, String initVector, int mode) {
		SecretKeySpec keySpec = new SecretKeySpec(hexToByte(passwordKey), "AES");
		AlgorithmParameterSpec parametarSpec = new IvParameterSpec(
				hexToByte(initVector));

		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(mode, keySpec, parametarSpec);
			inputPath = Paths.get(inputFileString);
			File outputFile = new File(outputFileString);

			outputPath = outputFile.toPath();
			if (outputFile.exists() == false) {
				Files.createFile(outputPath);
			}

		} catch (NoSuchAlgorithmException alg) {
			System.err.println("Specified algorithm does not exist!");
		} catch (NoSuchPaddingException pad) {
			System.err.println("Padding is invalid!");

		} catch (InvalidAlgorithmParameterException algp) {
			System.err.println("Algorithm parameter is invalid!");
		} catch (InvalidKeyException ke) {
			System.err.println("Key is invalid!");
		} catch (IOException e) {
			System.err.println("Cannot read file!");
		}

	}

	/**
	 * Metoda koja provodi kriptiranje podataka.
	 * Podatke čita iz izvorišne datoteke, kriptira ih
	 * te zapisuje u odredišnu datoteku.
	 * @return vraća true ako je proces uspio,false inače
	 */
	public boolean criptFiles() {
		try {

			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(inputPath.toFile()));
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(outputPath.toFile()));

			byte[] inputBuff = new byte[4096];
			int read = 0;
			while ((read = bis.read(inputBuff)) >= 1) {
				byte[] outputBuff = new byte[4096];
				int updated = cipher.update(inputBuff, 0, read, outputBuff);
				bos.write(outputBuff, 0, updated);
				bos.flush();
			}

			byte[] outputBuff = new byte[4096];
			int updated = cipher.doFinal(outputBuff, 0);
			bos.write(outputBuff, 0, updated);

			bis.close();
			bos.close();

		} catch (ShortBufferException sb) {
			return false;
		} catch (IllegalBlockSizeException ib) {
			return false;
		} catch (BadPaddingException bpe) {
			return false;
		} catch (IOException ioe) {
			return false;
		}

		return true;
	}

	/**
	 * Metoda koja heksadekadski stringovni zapis pretvara u polje okteta.
	 * @param hexString string koji treba pretvoriti u bajtove.
	 * @return vraća polje bajtova
	 */
	private byte[] hexToByte(String hexString) {
		String part;
		byte[] resultBytes = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length() / 2; i++) {
			part = hexString.substring(i * 2, i * 2 + 2);
			resultBytes[i] = (byte) (Integer.parseInt(part, 16) & 0xff);
		}
		return resultBytes;
	}

}

package hr.fer.zemris.java.tecaj.hw6.crypto;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Razred koji omogućava računanje SHA-1 checksume datoteke.
 * @author Petra Marče.
 *
 */
public class TestChecksum {
	private String file;
	private String fileDigest;
	private String expectedValue;
	
	/**
	 * Konstruktor.
	 * @param file prima datoteku čija se sha-1 vrijednost računa.
	 * @param check očekivana vrijednost operacije.
	 */
	public TestChecksum(String file, String check) {
		super();
		this.file = file;
		expectedValue = check;
	}

	/**
	 * Metoda koja računa traženu vrijednost.
	 * @return vraća true ako se vrijednosti podudaraju,false inače.
	 */
	public boolean verifyChecksum() {
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA1");
			FileInputStream is = new FileInputStream(file);
			BufferedInputStream bs = new BufferedInputStream(is);

			byte[] data = new byte[1024];
			int read = 0; // broj dosad procitanih
			while ((read = bs.read(data)) != -1) { // dok nismo dosli do kraja
													// napuni polje
				sha1.update(data, 0, read);// updetaj sha sa trenutnim
											// podaticma, ofset 0,duljina read
			}
			;
			bs.close();
			byte[] hashBytes = sha1.digest();
			StringBuilder sb = new StringBuilder();
			for (byte hashByte : hashBytes) {
				sb.append(Integer.toHexString((hashByte & 0xff) + 0x100)
						.substring(1));
			}
			String checkSum = sb.toString();
			this.setFileDigest(checkSum);
			return checkSum.contentEquals(expectedValue);
		} catch (NoSuchAlgorithmException na) {
			return false;
		} catch (FileNotFoundException fnf) {
			return false;
		} catch (IOException ioe) {
			return false;
		}

	}

	/**
	 * Metoda koja omogućuje postavljanje vrijednosti checksume.
	 * @param fileDigest
	 */
	private void setFileDigest(String fileDigest) {
		this.fileDigest = fileDigest;
	}
	/**
	 * Metoda koja omogućuje dohvat vrijednosti sume.
	 * @return vrijednost sume.
	 */

	public String getFileDigest() {
		return fileDigest;
	}

}

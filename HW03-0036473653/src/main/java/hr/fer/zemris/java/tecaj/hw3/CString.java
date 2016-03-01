package hr.fer.zemris.java.tecaj.hw3;

/**Razred koji nudi rad sa stringovima.
 * Stringovi su nepromjenjivi.
 * @author Petra Marče
 * @version 1.0
 */

public class CString {

	 int offset;
	 int length;
	 char[] data;
	
	/**
	 * Konstruktor.
	 * Kopira sadržaj predanog polja.
	 * @param data polje koje će biti kopirano;
	 * @param offset offset ulaznog polja
	 * @param length duljina ulazng polja
	 */

	public CString(char[] data, int offset, int length) {
		if (data == null)
			throw new IllegalArgumentException(
					"Poslano je polje sa null vrijednosti");

		this.data = new char[length];
		System.arraycopy(data, offset, this.data, 0, length);
		this.offset = 0;
		this.length = length;
	}

	/**
	 * Konstruktor.
	 * Kopira sadržaj predanog polja.
	 * @param data predano polje
	 */
	public CString(char[] data) {
		if (data == null)
			throw new IllegalArgumentException(
					"Poslano je polje sa null vrijednosti");

		this.data = new char[data.length];
		System.arraycopy(data, 0, this.data, 0, data.length);
		this.offset = 0;
		this.length = data.length;
	}
	
	/**
	 * Konstruktor prima sadrzaj u obliku stringa.
	 * @param s predani string
	 */
	public CString(String s) {
		char[] n = s.toCharArray();
		data = n;
		length = data.length;
		offset = 0;
	}
	
	/**
	 * Konstruktor.
	 * Stvara novi primjerak istog sadrzaja kao original.
	 * Ako je polje originala veće no što treba
	 * sadrzaj se kopira u novi niz. Inače ne.
	 * @param orginal originalni primjerak.
	 */

	public CString(CString orginal) {

		if (orginal.data.length > orginal.length) {

			char[] pomocnopolje = new char[orginal.length];
			System.arraycopy(orginal.data, orginal.offset, pomocnopolje, 0,
					orginal.length);

			this.data = pomocnopolje;
			this.offset = 0;
			this.length = orginal.length;

		} else {
			this.data = orginal.data;
			this.offset = orginal.offset;
			this.length = orginal.length;
		}
	}
	/**
	 * Privatni konstruktor.
	 * Slozenost O(1).
	 * @param data sadržaj koji pridruzujemo
	 * @param offset pomak u originalnom polju
	 * @param length duljina niza
	 * @param flag zastavica. Kad je true stvara se novi element.
	 */

	private CString(char[] data, int offset, int length, boolean flag) {
		if (data == null)
			throw new IllegalArgumentException(
					"Poslano je polje sa null vrijednosti");
		if (!flag)
			throw new IllegalArgumentException("Pogrešna zastavica.");
		this.data = data;
		this.offset = offset;
		this.length = length;
	}

	/**
	 * Metoda koja vraća duljinu stringa.
	 * @return vraća duljinu.
	 */
	public int length() {

		return this.length;
	}
	/**
	 * Vraća znak koji se nalazi na zadanoj poziciji.
	 * Ako pozicija nije valjana baca se iznimka.
	 * @param index pozicija
	 * @return nađeni znak
	 */

	public char charAt(int index) {
		if (index<0 || index>length){
			throw new IndexOutOfBoundsException("Invalid Index");
		}
		return this.data[offset + index];

	}

	/**
	 * Vraća sadržaj objekta kao polje znakova.
	 * @return polje znakova
	 */
	public char[] toCharArray() {
		char[] n = new char[this.length];
		for (int i = 0; i < this.length; i++) {
			n[i] = data[i + this.offset];
		}
		return n;

	}

	/**
	 * Metoda koja vraća sadržaj objekta.
	 * Vraća String
	 */
	@Override
	public String toString() {
		String n = new String(this.data, this.offset, this.length);
		return n;
	}
	
	/**
	 * Metoda koja traži prvo pojavljivanje znaka.
	 * @param c znak koji se traži
	 * @return indeks prvog pojavljivanja.
	 * Vraća -1 ako se znak ne pojavljuje
	 */

	public int indexOf(char c) {
		for (int i = 0, kraj = this.length; i < kraj; i++) {
			if (this.data[i + this.offset] == c)
				return i;

		}
		return -1;
	}
	/**
	 * Metoda koja provjerava početak niza.
	 * @param s zadani podniz.
	 * @return vraća true ako niz započinje podnizom,false inače
	 */
	
	public boolean startsWith(CString s) {
		for (int i = 0; i < s.length; i++) {
			if (this.data[this.offset + i] != s.data[s.offset + i])
				return false;
		}
		return true;
	}
	
	/**
	 * Metoda koja provjerava kraj niza.
	 * @param s zadani podniz.
	 * @return vraća true ako niz završava podnizom, false inače
	 */

	public boolean endsWith(CString s) {
		for (int i = 0; i < s.length; i++) {
			if (this.data[offset + length + -s.length + i] != s.data[s.offset
			                                                         + i])
				return false;
		}
		return true;
	}
	/**
	 * Metoda koja provjerava sadrži li niz zadani podniz.
	 * @param s podniz koji se traži
	 * @return
	 */

	public boolean contains(CString s) {
		int numberOfChecks = this.length - s.length + 1;// broj provjera koje
		if (numberOfChecks <= 0)
			return false; // treba napraviti
		for (int i = 0; i < numberOfChecks; i++) {
			boolean check = true;
			for (int j = 0; j < s.length; j++) {
				if (data[offset + i + j] != s.data[s.offset + j]) {
					check = false;

				}
			}
			if (check == true)
				return true;

		}
		return false;

	}
	/**
	 * Metoda koja vraća podniz niza.
	 * @param startIndex početna pozicija
	 * @param endIndex krajnja pozicija
	 * @return vraća podniz
	 */

	public CString substring(int startIndex, int endIndex) {
		return new CString(this.data, this.offset + startIndex, endIndex
				- startIndex, true);

	}

	/**
	 * Vraća CString koji je lijevi podniz niza
	 * @param n duljina podniza s lijeva
	 * @return podniz
	 */
	public CString left(int n) {
		if (n < 0 || n > this.length)
			throw new IllegalArgumentException(
					"Invalid length of left side od string!");
		CString t = new CString(this.data, this.offset, n, true);
		return t;
	}
	/**
	 * Vraća CString koji je desni podniz niza
	 * @param n duljina podniza s desna
	 * @return podniz
	 */

	public CString right(int n) {
		if (n < 0 || n > this.length)
			throw new IllegalArgumentException(
					"Invalid length of left side od string!");
		CString t = new CString(this.data, this.offset + this.length - n, n,
				true);
		return t;
	}
	/**
	 * Metoda obavlja konkatenaciju dva niza.
	 * @param s niz koji se dodaje
	 * @return vraća novu instancu objekta 
	 * ciji je sadrzaj rezultat konkatenacije
	 */

	public CString add(CString s) {
		int l = s.length + this.length;
		char[] n = new char[l];
		for (int i = 0; i < this.length; i++) {
			n[i] = this.data[offset + i];
		}
		for (int i = 0; i < s.length; i++) {
			n[i + this.length] = s.data[s.offset + i];

		}
		return new CString(n, 0, l);

	}
	
	/**
	 * Metoda u nizu zamjenjuje svaku pojavu jednog znaka drugim.
	 * @param oldChar Znak kojeg se mjenja.
	 * @param newChar znak koji će ga zamjeniti.
	 * @return vraća izmjenjeni niz
	 */
	public CString replaceAll(char oldChar, char newChar) {
		char[] n = new char[this.length];
		for (int i = 0; i < this.length; i++) {
			if (data[offset + i] == oldChar) {
				n[i] = newChar;
			} else {
				n[i] = data[offset + i];
			}
		}
		return new CString(n, 0, this.length);

	}
	
	/**
	 * Metoda koja u nizu svaku pojavu podniza mjenja drugim podnizom.
	 * @param oldStr podniz koji se mjenja
	 * @param newStr podniz koji će ga zamjeniti
	 * @return vraća izmjenjeni niz
	 */

	public CString replaceAll(CString oldStr, CString newStr) {
		CString pomocni = new CString(this);
		int poz = 0;
		char[] n = new char[(int)Math.ceil(((double)this.length)/oldStr.length*newStr.length)];
		while (pomocni.length > 0) {
			if (pomocni.startsWith(oldStr) == true) {
				for (int i = 0; i < newStr.length; i++) {
					n[poz] = newStr.data[newStr.offset + i];
					poz++;
				}
				pomocni.offset += oldStr.length;
				pomocni.length -= oldStr.length;

			} else {
				n[poz] = pomocni.data[pomocni.offset];
				pomocni.offset++;
				pomocni.length--;
				poz++;

			}
		}
		return new CString(n, 0, poz);

	}

}

package hr.fer.zemris.java.tecaj.hw3;

import java.text.DecimalFormat;

/**
 * Razred koji omogućava rad sa kompleksnim brojevima.
 * @author Petra Marče
 * @version 1.0
 */

public class ComplexNumber {
/**
 *  Jedna za realni i druga za imaginarni dio broja.
 */
	 double realPart;
	 double imagPart;

	/**
	 * Metoda koja se poziva pri stvaranju nove instance razreda.
	 * @param realPart realni dio broja
	 * @param imagPart imaginarni dio broja
	 */
	public ComplexNumber(double realPart, double imagPart) {
		this.realPart = realPart;
		this.imagPart = imagPart;

	}

	/**
	 * Metoda koja kreira novi primjerak razreda.
	 * Imaginarni dio broja je nula.
	 * @param real realni dio broja
	 * @return vraća stvoreni primjerak
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);

	}

	/**
	 * Metoda koja kreira novi primjerak razreda.
	 * Realni dio broja je nula.
	 * @param imag imaginarni dio broja
	 * @return vraća stvoreni primjerak
	 */
	public static ComplexNumber fromImaginary(double imag) {
		return new ComplexNumber(0, imag);

	}

	/**
	 * Metoda koja stvara novi primjerak razreda.
	 * @param magnitude modul kompleksnog broja
	 * @param angle kut kompleksnog broja
	 * @return vraća novi primjerak razreda
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude,
			double angle) {
		double real = magnitude * Math.cos(angle);
		double imag = magnitude * Math.sin(angle);
		return new ComplexNumber(real, imag);

	}

	/**
	 * Metoda koja stvara novi primjerak razreda parsiranjem ulaznog niza.
	 * @param s  ulazni niz koji treba parsirati
	 * @return vraća novi primjerak razreda
	 */

	public static ComplexNumber parse(String mojNiz) {
		ComplexNumber novi = new ComplexNumber(1, 1);
		char znak = mojNiz.charAt(0);
		if (znak == 'i' && mojNiz.length()==1) { // slučaj i
			novi.realPart = 0;

		} else if (znak>= '0' && znak <= '9') { // prvi član je pozitivan
			novi.parseFromSecond(mojNiz);
			
		} else if (znak == '-') { // prvi član je negativan
			if (mojNiz.charAt(1) == 'i' && mojNiz.length() == 2) {
				novi.realPart = 0;
				novi.imagPart = -1;

			} else {
				novi.parseFromSecond(mojNiz.substring(1));
				if (novi.realPart == 0) {
					novi.imagPart *= -1;
				} else {
					novi.realPart *= -1;
				}

			}

		}
		else{
			throw new IllegalArgumentException("Unable to parse");
		}

		return novi;
	}

	/**
	 * Metoda koja provjerava da li je zadnji znak ulaznog niza jednak i. 
	 * @param s ulazni niz nad kojim se vrši provjera
	 */
	
	public static void isLastChari(String ulNiz) {
		if (ulNiz.charAt(ulNiz.length() - 1) != 'i') {
			throw new IllegalArgumentException(
					"Second number must be imaginary!");
		}

	}

	/**
	 * Metoda koja dijeli ulazni niz na dva podniza.
	 * Na dio od početka do zadanog indeksa, 
	 * te od zadanog indeksa do predzadnjeg znaka. 
	 * Ta dva podniza pretvara u realan broj.
	 * Vrijednosti pridružuje članskim varijablama
	 * objekta
	 * @param sub ulazni niz koji teba podijeliti na dva dijela
	 * @param middle indeks koji predstavlja granicu
	 */

	public void divideTwoParts(String sub, int middle) {
		try {
			this.realPart = 
					Double.parseDouble(sub.substring(0, middle));
			this.imagPart =
					Double.parseDouble(sub.substring(middle,
					sub.length() - 1));

		} catch (IllegalArgumentException | IndexOutOfBoundsException pe) {
			throw new IllegalArgumentException("Cannot parse,invalid argument!");
		}

	}

	/**
	 * Metoda koja provjerava je li ulazni niz dvodijelan.
	 * Ako jest,utvrđuje koji znak je granica, te vrši podjelu.
	 * @param s niz koji se treba obraditi
	 */
	public void parseFromSecond(String s) {
		int middle = s.indexOf('+'); // indeks sredine
		if (middle != -1) { // ako smo nasli plus,broj je dvodijelni
			isLastChari(s);
			divideTwoParts(s, middle);
			return;

		} else {
			middle = s.indexOf('-');
			if (middle != -1) { // nasli smo minus,
				isLastChari(s);  //broj je dvodijelni
				divideTwoParts(s, middle);
				return;

			} else { // broj ima samo jedan dio
				char lastChar = s.charAt(s.length() - 1);
				if (lastChar == 'i') {
					try {
						this.imagPart =
								Double.parseDouble(s.substring(0, s.length() - 1));

					} catch (RuntimeException e) {
						throw new IllegalArgumentException("Cannot parse,invalid argument!");
					}
					this.realPart = 0;

				} else {
					try {
						this.realPart = Double.parseDouble(s);
					} catch (RuntimeException e) {
						throw new IllegalArgumentException("Cannot parse,invalid argument!");
					}
					this.imagPart = 0;

				}

			}

		}

	}

	/**
	 * Metoda koja vraća realni dio broja.
	 * @return realni dio broja
	 */
	public Double getReal() {
		return this.realPart;
	}

	/**
	 * Metoda koja vraća imaginarni dio broja.
	 * @return imaginarni dio
	 */
	public Double getImaginary() {
		return this.imagPart;
	}

	/**
	 * Metoda koja racuna  apsolutnu vrijednost broja.
	 * @return apsolutna vrijednost kompleksnog broja
	 */
	public Double getMagnitude() {
		return Math.sqrt(realPart * realPart + imagPart * imagPart);
	}

	/**
	 * Metoda koja vraća kut kompleksnog broja.
	 * @return kut
	 */

	public Double getAngle() {
		return Math.atan2(realPart,imagPart);
	}

	/**
	 * Metoda koja kompleksnom broju pridodaje zadani broj.
	 * @param c broj koji treba dodati
	 * @return vraća zbroj
	 * 
	 */

	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.realPart + c.realPart, this.imagPart
				+ c.imagPart);

	}

	/**
	 * Metoda koja od kompleksnog broja oduzima zadani broj.
	 * @param c broj koji treba oduzeti
	 * @return vraća razliku
	 * 
	 */

	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber (this.realPart - c.realPart, this.imagPart
				- c.imagPart);

	}

	/**
	 * Metoda koja kompleksni broj množi zadanim brojem.
	 * @param c broj s kojim treba pomnožiti
	 * @return umnožak
	 * 
	 */

	public ComplexNumber mul(ComplexNumber c) {
		double mag = this.getMagnitude() * c.getMagnitude();
		double an = this.getAngle() + c.getAngle();
		return ComplexNumber.fromMagnitudeAndAngle(mag, an);
	}

	/**
	 * Metoda koja kompleksni broj dijeli zadanim brojem.
	 * @param c broj s kojim treba dijeliti
	 * @return kvocijent
	 * 
	 */

	public ComplexNumber div(ComplexNumber c) {
		double mag = this.getMagnitude() / c.getMagnitude();
		double an = this.getAngle() - c.getAngle();
		return ComplexNumber.fromMagnitudeAndAngle(mag, an);

	}

	/**
	 * Metoda koja računa n-tu potenciju kompleksnog broja.
	 * @param n potencija, ne smije biti manja od nule
	 * @return nta potencija
	 */
	public ComplexNumber power(int n) {
		if (n < 0)
			throw new IllegalArgumentException(
					"n must be zero or grater than zero");

		double mag = Math.pow(this.getMagnitude(), n);
		double an = this.getAngle() * n;
		return ComplexNumber.fromMagnitudeAndAngle(mag, an);

	}

	/**
	 * Metoda koja računa n n-tih korijena kompleksnog broja.
	 * @param n -ti korijen, mora biti veći od nule
	 * @return vraća polje n-tih korijena
	 */

	public ComplexNumber[] root(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("n must grater than zero");
		ComplexNumber[] roots = new ComplexNumber[n];

		double absRoot = Math.pow(this.getMagnitude(), 1 / (double)n);
		double realRoot = 0;
		double imagRoot = 0;
		double phi = 0;
		for (int i = 0; i < n; i++) {
			phi = (this.getAngle() + 2 * i * Math.PI) / n;
			realRoot = absRoot * Math.cos(phi);
			imagRoot = absRoot * Math.sin(phi);
			roots[i] = new ComplexNumber(realRoot, imagRoot);

		}
		return roots;

	}

	/**
	 * Metoda koja vraća znakovnu reprezentaciju kompleksnog broja.
	 * @return vraca broj u obliku niza.
	 */
	
	@Override
	public String toString() {
		String novi="";
		String format1 = "+0;-0";
		String format2 = "0;-0";
		final int precision = -6;
		final int base = 10;
		if (Math.abs(realPart - Math.round(realPart)) > Math.pow(base,
				precision)
				|| Math.abs(imagPart - Math.round(imagPart)) > Math.pow(base,
						precision)) {
			format1 = "+0.000;-0.000";
			format2 = "0.000;-0.000";
		}

		DecimalFormat formatterImag = new DecimalFormat(format1); 
		// *format za imaginarni dio
		DecimalFormat formatterReal = new DecimalFormat(format2); 
		// *format za realni dio
		if(Math.abs(realPart)>Math.pow(10,-6)){
			novi+=formatterReal.format(realPart) ;
		}
		if (Math.abs(imagPart)>Math.pow(10,-6)) {
			novi+= formatterImag.format(imagPart)+"i";
		}
				
		return novi;
	}
}

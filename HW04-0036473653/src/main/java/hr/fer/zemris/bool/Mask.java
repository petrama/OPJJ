package hr.fer.zemris.bool;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
/**
 * Razred koji omogućuje rad sa maskama u Boolovoj algebri.
 * @author Petra Marče
 *
 */
public class Mask {
	private List<MaskValue> values;

	/**
	 * Konstruktor.
	 * Kopira ulazne vrijednosti i stavlja ih u privatnu listu.
	 * @param val ulazne vrijednosti
	 */
	public Mask(MaskValue[] val) {
		values = Arrays.asList(val);
	}

	/**
	 * Metoda koja vraća vrijednost maske na zadanoj poziciji.
	 * @param index pozicija na kojoj nas zanima vrijednost
	 * @return vrijednost maske
	 */
	public MaskValue getValue(int index) {
		return values.get(index);
	}
	/**
	 * Metoda koja iz Stringa stvara novu masku.
	 * String se smije sastojati od 0,1,x ili X
	 * Ako string ne zadovoljava pravila metoda baca iznimku
	 * @param homeString niz iz kojeg treba stvoriti masku
	 * @return novostvorena instanca razreda Mask
	 */

	public static Mask parse(String homeString) {
		MaskValue[] pomPolje = new MaskValue[homeString.length()];

		for (int i = 0; i < homeString.length(); i++) {
			switch (homeString.charAt(i)) {
			case '1':
				pomPolje[i] = MaskValue.ONE;
				break;
			case '0':
				pomPolje[i] = MaskValue.ZERO;
				break;

			case 'x':
				pomPolje[i] = MaskValue.DONT_CARE;
				break;

			case 'X':
				pomPolje[i] = MaskValue.DONT_CARE;
				break;

			default:
				throw new IllegalArgumentException("Unable to parse mask");
			}
		}
		return new Mask(pomPolje);
	}

	/**
	 * Metoda koja vraća broj dont-careova u maski.
	 * @return broj dont-careova
	 */
	public int numberOfDontCares() {
		int brojac = 0;
		for (MaskValue v : values) {
			if (v == MaskValue.DONT_CARE) {
				brojac++;
			}
		}
		return brojac;
	}
	/**
	 * Metoda koja utvrđuje da li je maska nad kojom je pozvana metoda poopćenje zadane maske.
	 * @param secondMask maska za koju se pita da li je obuhvaćena trenutnom maskom
	 * @return vraća true ako je maska nad kojom smo pozvali metodu općenitija od zadane, false inače
	 */
	public boolean isMoreGeneralThan(Mask secondMask) {
		if (values.size() != secondMask.values.size()
				|| this.numberOfDontCares() <= secondMask.numberOfDontCares()) {
			return false; // ako su nizovi razlicite duljine ili prvi ima manje
			// x od drugog , prvi ne može biti generalniji
		}
		int i = 0;
		for (MaskValue value : secondMask.values) {
			if (value != values.get(i) && values.get(i) != MaskValue.DONT_CARE) {
				return false;
			}
			// ako su znamenke različite i viša maska na tom mjestu nema x onda
			i++; // nije generalnija
		}
		return true;

	}


	/**
	 * Metoda koja ako je to moguće stvara masku koja je općenitja od zadanih maski.
	 * @param secondMask maska koju želimo obuhvatiti novom maskom
	 * @return vraća općenitiju masku
	 */

	public static Mask combine(Mask prvaMaska, Mask drugaMaska) {
		  if (prvaMaska == null || drugaMaska == null
		    || prvaMaska.values.size() != drugaMaska.values.size() ) {
		   throw new IllegalArgumentException("Unesene su pogresne maske!");
		  }

		  int pozicijaDontCare = -1;
		  boolean kompatibilni = false;

		  for (int i = 0, duljina = prvaMaska.values.size(); i < duljina; i++) {

		   if (!prvaMaska.values.get(i).equals(drugaMaska.values.get(i))) {

		    if (pozicijaDontCare < 0) {
		     kompatibilni = true;
		     pozicijaDontCare = i;

		    } else {
		     kompatibilni = false;
		     break;

		    }
		   }
		  }

		  if (!kompatibilni) {
		   return null;

		  } else {

		   MaskValue[] pomocna = new MaskValue[prvaMaska.getSize()];

		   for (int i = 0 ,duljina = prvaMaska.getSize() ; i < duljina ;i++) {
		   
		    if ( i == pozicijaDontCare){
		     pomocna[i] = MaskValue.DONT_CARE;
		    } else {
		     pomocna[i] = prvaMaska.getValue(i);
		    }
		     
		   }
		   return new Mask(pomocna);

		  }
		 }
	/**
	 * Metoda koja stvara novu masku zadane duljine, koja predstavlja zadani broj.
	 * @param numOfVariables željena duljina maske
	 * @param number broj koji maska mora reprezentirati
	 * @return vraća novostvorenu masku
	 */

	public static Mask fromIndex(int numOfVariables, int number) {
		String binaryNumber = Integer.toBinaryString(number);
		int zerosBefore = numOfVariables - binaryNumber.length();
		if (zerosBefore < 0) {
			throw new IllegalArgumentException("Invalid arguments!");
		}
		String addZerosBefore = "";
		for (int i = 0; i < zerosBefore; i++) {
			addZerosBefore += "0";
		}
		return parse(addZerosBefore + binaryNumber);
	}
	
	/**
	 * Metoda koja vraća broj nula u maski.
	 * @return broj nula.
	 */

	int getNumberOfZeros() {
		int numOfZeros = 0;
		for (MaskValue v : this.values) {
			if (v == MaskValue.ZERO) {
				numOfZeros++;
			}
		}
		return numOfZeros;
	}
	/**
	 * Metoda koja vraća broj jedinica u maski.
	 * @return broj jedinica u maski.
	 */

	int getNumberOfOnes() {
		int numOfOnes = 0;
		for (MaskValue v : this.values) {
			if (v == MaskValue.ONE) {
				numOfOnes++;
			}
		}
		return numOfOnes;
	}
	
	/**
	 * Metoda vraća duljinu maske.
	 * @return duljina maske
	 */

	int getSize() {
		return values.size();
	}
	
	/**
	 * Metoda računa hash vrijednost objekta maske.
	 * Za računanje uzima u obzir polje vrijednosti maske.
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		return result;
	}
	
	/**
	 * Metoda vraća true ako su dvije maske iste, false inače.
	 * Dvije maske su iste ako imaju sve iste vrijednosti.
	 * 
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mask other = (Mask) obj;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String s = "";
		for (MaskValue v : values) {
			switch (v) {
			case ONE:
				s += "1";
				break;
			case ZERO:
				s += "0";
				break;
			case DONT_CARE:
				s += "x";
				break;
			default:
				break;
			}
		}

		return s;

	}
}

package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * Razred koji predstavlja realizaciju stoga.
 * Taj stog zapravo sadrži više stogova kojima se pristupa preko njihovih imena.
 * Ime pojedinog podstoga bira korisnik.
 * Elementi stoga su instance razreda ValueWrapper.
 * @author Petra Marče
 *
 */
public class ObjectMultistack {

	/**
	 * Razred koji predstavlja jedan element podstoga.
	 * Taj element čuva svoju vrijednost, i referencu na sljedeći element stoga.
	 */
	static class MultistackEntry {
		ValueWrapper value;
		MultistackEntry next;

		/**
		 * Konstruktor elementa stoga.
		 * @param value vrijednost koju čuva.
		 * @param next referenca na sljedećeg na stogu.
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			super();
			this.value = value;
			this.next = next;
		}

	}

	
	private Map<String, MultistackEntry> multistack;

	/**
	 * Konstruktor.
	 * Stvara novu instancu razreda ObjectMultistack.
	 * Ne očekuje argumente.
	 */
	public ObjectMultistack() {
		multistack = new HashMap<>();

	}
	/**
	 * Metoda koja određuje da li je podstog određenog imena prazan.
	 * @param name ime podstoga za kojeg nas zanima je li prazan.
	 * @return vraća true ako je stog prazan,false inače
	 */
	public boolean isEmpty(String name) {
		if(multistack.containsKey(name)){
			return false;
		}
		
		return true;
	}
	/**
	 * Metoda koja stavlja predani element na stog zadanog imena.
	 * @param name ime stoga na kojeg stavljamo novi element.
	 * @param value vrijednost koji želimo staviti na stog.
	 */
	public void push(String name, ValueWrapper value) {
		MultistackEntry novi = null;
		if (this.isEmpty(name) == false) {
			novi = new MultistackEntry(value, multistack.get(name));
		} else {
			novi = new MultistackEntry(value, null);
		}
		multistack.put(name, novi);
	}
	/**
	 * Metoda kojom uzimamo element sa vrha stoga.
	 * Nakon operacije izbaceni element vise nije na stogu.
	 * Pokušaj dohvaćanja elementa sa vrha stoga ako je isti prazan,
	 * rezultirat će sa RuntimeException.
	 * @param name ime stoga sa kojeg zelimo skinuti element.
	 * @return vraća skinuti element.
	 */
	public ValueWrapper pop(String name) {
		if (this.isEmpty(name)) {
			throw new RuntimeException("Stack is empty!");
		} else {
			MultistackEntry stari = multistack.get(name);
			MultistackEntry sljedeci = stari.next;
			multistack.put(name, sljedeci);

			if (multistack.get(name) == null) {
				multistack.remove(name);
			}
			return stari.value;
		}
	}

	
	/**
	 * /**
	 * Metoda kojom čitamo element sa vrha stoga.
	 * Nakon operacije pročitani element ostaje na stogu.
	 * Pokušaj dohvaćanja elementa sa vrha stoga ako je isti prazan,
	 * rezultirat će sa RuntimeException.
	 * @param name ime stoga sa kojeg zelimo skinuti element.
	 * @return vraća pročitani element.
	 */
	public ValueWrapper peek(String name) {
		if (this.isEmpty(name)) {
			throw new RuntimeException("Stack is empty!");
		} else {
			MultistackEntry trazeni = multistack.get(name);
			return trazeni.value;
		}
	}
}


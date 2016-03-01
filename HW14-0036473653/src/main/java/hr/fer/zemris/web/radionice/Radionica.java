package hr.fer.zemris.web.radionice;

import hr.fer.zemris.web.radionice.RadioniceBaza.Opcija;
import java.util.Set;
/**
 * Razred predstavlja jednu radionicu.
 * Radionicu karakterizira njen jedinstveni id, naziv, datum održavanja,<br>
 * oprema potrebna za održavanje radionice koja je opcionalna, trajanje, ciljana publika, maksimalni broj polaznika,<br>
 * te email kontakt organizatora i opcionalnu dodatnu napomenu.

 * @author Petra Marče
 *
 */
public class Radionica {
	Long id;
	String naziv;
	String datum;
	Set<Opcija> oprema;
	Opcija trajanje;
	Set<Opcija> publika;
	Integer maksPolaznika;
	String email;
	String dopuna;
	
	
	/**
	 * Konstruktor, potrebno je zadati sve parametre jedne radionice.
	 * @param id id radionice
	 * @param naziv naziv radionice.
	 * @param datum datum odrzavanja.
	 * @param oprema oprema potrebna za odrzavanje radionice, lista moze biti prazna.
	 * @param trajanje trajanje radinice.
	 * @param publika ciljana publika radionice.
	 * @param maksPolaznika maksimalan broj polaznika.
	 * @param email email organizatora.
	 * @param dopuna opcionalna dodatna napuna, može biti prazan string
	 */
	public Radionica(Long id, String naziv, String datum, Set<Opcija> oprema,
			Opcija trajanje, Set<Opcija> publika, Integer maksPolaznika,
			String email, String dopuna) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.datum = datum;
		this.oprema = oprema;
		this.trajanje = trajanje;
		this.publika = publika;
		this.maksPolaznika = maksPolaznika;
		this.email = email;
		this.dopuna = dopuna;
	}
	
	/**
	 * Defaultni konstruktor.
	 * Stvara novi primjerak razreda.
	 */
	public Radionica(){

	}

	
	/**
	 * Metoda za dohvat id-a radionice.
	 * @return id radionice.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Metoda za dohvat naziva radionice.
	 * @return naziv radionice.
	 */
	public String getNaziv() {
		return naziv;
	}

	/**
	 * Metoda za dohvat datuma održavanja radionice.
	 * @return datum radionice.
	 */
	public String getDatum() {
		return datum;
	}

	/**
	 * Metoda za dohvat skupa opreme potrebne za odrzavanje radionice.
	 * @return skup opreme koji moze biti prazan.
	 */
	public Set<Opcija> getOprema() {
		return oprema;
	}

	/**
	 * Metoda za dohvat trajanja radionice.
	 * @return trajanje radionice.
	 */
	public Opcija getTrajanje() {
		return trajanje;
	}

	/**
	 * Metoda za dohvat ciljane publke radionice.
	 * @return ciljana publika radionice.
	 */
	public Set<Opcija> getPublika() {
		return publika;
	}

	/**
	 * Metoda za dohvat maksimalnog broja polaznika radionice.
	 * @return maksimalan broj polaznika radionice.
	 */
	public Integer getMaksPolaznika() {
		return maksPolaznika;
	}

	/**
	 * Metoda za dohvat emaila organizatora radionice.
	 * @return email organizatora radionice.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Metoda za dohvat dodatne napomene organizatora radionice.
	 * Može biti i prazan string.
	 * @return dodatna napomena organizatora radionice.
	 */
	public String getDopuna() {
		return dopuna;
	}
	
	/**
	 * Metoda za postavljanje id-a radionice.
	 * @param id novi id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Metoda za postavljanje naziva radionice.
	 * @param naziv naziv radionice.
	 */

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	
	/**
	 * Metoda za postavljanje datuma radionice.
	 * @param datum datum radionice.
	 */
	public void setDatum(String datum) {
		this.datum = datum;
	}


	/**
	 * Metoda za postavljanje opreme radionice.
	 * @param oprema oprema radionice.
	 */
	public void setOprema(Set<Opcija> oprema) {
		this.oprema = oprema;
	}


	/**
	 * Metoda za postavljanje trajanja radionice.
	 * @param trajanje trajanje radionice.
	 */
	public void setTrajanje(Opcija trajanje) {
		this.trajanje = trajanje;
	}


	/**
	 * Metoda za postavljanje ciljane publike.
	 * @param publika ciljan publika radionice.
	 */
	public void setPublika(Set<Opcija> publika) {
		this.publika = publika;
	}


	/**
	 * Metoda za postavljanje maksimalnog broja polaznika.
	 * @param maksPolaznika maks broj polaznika radionice.
	 */
	public void setMaksPolaznika(Integer maksPolaznika) {
		this.maksPolaznika = maksPolaznika;
	}


	/**
	 * Metoda za postavljanje email-a organizatora radionice.
	 * @param email email organizatora
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Metoda za postavljanje dodatne napomene organizatora radionice.
	 * @param dopuna dodatna napomena.
	 */

	public void setDopuna(String dopuna) {
		this.dopuna = dopuna;
	}


	/**
	 * Metoda koja vraća stringovnu reprezentaciju radionice.
	 */
	@Override
	public String toString() {
		return "[id=" + id + ", naziv=" + naziv + ", datum=" + datum
				+ ", oprema=" + oprema + ", trajanje=" + trajanje
				+ ", publika=" + publika + ", maksPolaznika=" + maksPolaznika
				+ ", email=" + email + ", dopuna=" + dopuna + "]";
	}


	

	

}

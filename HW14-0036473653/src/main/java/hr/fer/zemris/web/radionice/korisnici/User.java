package hr.fer.zemris.web.radionice.korisnici;

import java.io.Serializable;

/**
 * Razred predstavlja jednog korisnika web aplikacije radionice.
 * Korisnika karakteriziraju korisničko ime, šifra, ime i prezime.
 * @author Petra Marče.
 *
 */
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String login;
	private String zaporka;
	private String ime;
	private String prezime;
	
	public User(String login, String zaporka, String ime, String prezime) {
		super();
		this.login = login;
		this.zaporka = zaporka;
		this.ime = ime;
		this.prezime = prezime;
		
		
	}

	/**
	 * Metoda za dohvat imena korisnika.
	 * @return ime korisnika.
	 */
	public String getIme() {
		login=login+"";
		zaporka=zaporka+"";
		return ime;
		
	
	}
	/**
	 * Metoda za dohvat prezimena korisnika.
	 * @return prezime korisnika.
	 */
	public String getPrezime() {
		return prezime;
	}
	
	
	
	
	
	

}

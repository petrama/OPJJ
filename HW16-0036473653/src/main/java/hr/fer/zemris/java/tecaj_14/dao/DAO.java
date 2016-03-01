package hr.fer.zemris.java.tecaj_14.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;

public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Vraća listu Usera sa zadanim nickom.
	 * @param nick nick koji se traži.
	 * @return lista usera sa traženim nickom.
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public List<BlogUser> getUserByNick(String nick) throws DAOException;
	
	/**
	 * Metoda dohvaća listu svih Usera iz baze.
	 * @return lista sbih usera
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public List<BlogUser> getAllUsers() throws DAOException;
	
	/**
	 * Metoda ubacuje novog korisnika u bazu.
	 * @param firstName ime novog korisnika.
	 * @param lastName prezime novog korisnika.
	 * @param email email adresa novog korisnika.
	 * @param nick nadimak novog korisnika.
	 * @param passwordHash SHA-1 vrijednost lozinke novog korisnika.
	 */
	public void insertNewBlogUser(String firstName,String lastName,String email,String nick,String passwordHash);

//	/**
//	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
//	 * vraća <code>null</code>.
//	 * 
//	 * @param id ključ zapisa
//	 * @return entry ili <code>null</code> ako entry ne postoji
//	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
//	 */
//	public BlogEntry getBlogEntry(BlogUser creator,Long id);
	
	/**
	 * Metoda dodaje novi entry u bazu.
	 * @param title naslov entry-a
	 * @param text tekst entry-a
	 * @param creatorNick nadimak kreatora entry-a
	 */
	public void addNewEntry(String title,String text,String creatorNick);
	
	/**
	 * Metoda updatea postojeći entry u bazi.
	 * @param title naslov entry-a
	 * @param text tekst entry-a
	 * @param creatorNick nadimak kreatora entry-a
	 * @param id id entry-a
	 */
	public void updateEntry(String creatorNick,Long id,String title,String text);
	/**
	 * Metoda dodaje novi komentar u bazu.
	 * @param entry entry čiji se komentar dodaje.
	 * @param text tekst komentara.
	 * @param email email osobe koja je komentirala.
	 */
	public void addNewComment(String entry,String text,String email);
}
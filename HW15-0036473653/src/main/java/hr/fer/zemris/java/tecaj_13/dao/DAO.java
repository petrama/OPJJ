package hr.fer.zemris.java.tecaj_13.dao;


import hr.fer.zemris.java.tecaj_13.model.Glasanje;


import hr.fer.zemris.java.tecaj_13.model.Result;

import java.util.List;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author Petra Marče
 *
 */
public interface DAO {
	/**
	 * Metoda koja zapisu koja odgovara predanoj opciji glasanja povećava broj glasova za jedan.
	 * @param idOpcije id opcije kojoj se broj glasova povećava za jedan.
	 * @param pollId glasovanje u sklopu kojeg se opcija nalazi.
	 * @throws DAOException u slučaju pogreške u komuniciranju s bazom 
	 */
	public void glasaj(long idOpcije,long pollId) throws DAOException;
	/**
	 * Metoda dohvaća rezultate glasovanja s predanim id-om.
	 * @param pollId id glasanja čiji se rezultati traže.
	 * @return vraća listu rezultata zadanog glasovanja.
	 * @throws DAOException u slučaju pogreške u komuniciranju s bazom 
	 */
	public List<Result> dohvatiRezultate(long pollId) throws DAOException;
	/**
	 * Metoda koja dohvaća podatke o pojedinom glasovanju.
	 * @param pollId id glasovanja čiji se podatci traže.
	 * @return podatci o traženom glasovanju.
	 * @throws DAOException u slučaju pogreške u komuniciranju s bazom 
	 */
	public Glasanje dohvatiPodatkeOGlasanju(long pollId) throws DAOException;
	
	/**
	 * Metoda koja dohvaća sva glasanja iz baze.
	 * @return sva glasanja uz baze,
	 * @throws DAOException u slučaju pogreške u komuniciranju s bazom 
	 */
	public List<Glasanje> dohvatiSvaGlasanja() throws DAOException;
	/**
	 * Metoda koja ubacuje novo glasanje u bazu.
	 * @param glasanje novo glasanje koje treba ubaciti u bazu
	 */
	public void ubaciGlasanje(Glasanje glasanje);
	/**
	 * Metoda koja ubacije nove zapise o  glasanju u bazu.
	 * @param zapisi lista zapisa čiji sadržaj treba ubaciti u bazu.
	 */
	public void ubaciRezultate(List<Result> zapisi);
	/**
	 * Metoda za dohvat glasanja iz baze sa predanim imenom.
	 * @param name ime glasanja koje treba dohvatiti iz baze.
	 * @return traženo glasanje.
	 */
	public Glasanje dohvatiGlasanje(String name);
	/**
	 * Metoda za dohvat glasanja iz baze sa predanim id-om.
	 * @param id id glasanja koje treba dohvatiti.
	 * @return traženo glasanje.
	 */
	public Glasanje dohvatiGlasanje(long id);
		
	

	
}
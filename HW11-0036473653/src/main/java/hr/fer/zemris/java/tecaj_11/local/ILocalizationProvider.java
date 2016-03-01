package hr.fer.zemris.java.tecaj_11.local;
/**
 * Sučelje koje predstavlja subjekt oblikovnog obrasca Promatrač.
 * Kad se dogodi promjena jezika, on obavještava svoje promatrače.
 * @author Petra Marče
 *
 */
public interface ILocalizationProvider {
	/**
	 * Metoda za dodavanje novog promatrača subjekta.
	 * @param listener novi promatrač koji se dodaje u red promatrača.
	 */
	public void addLocalizationListener(ILocalizationListener listener);
	/**
	 * Metoda za uklanjanje postojećeg promatrača iz reda promatrača.
	 * @param listener promatrač koji se uklanja.
	 */
	public void removeLocalizationListener(ILocalizationListener listener);
	/**
	 * Metoda za dohvat riječi na trenutnom stranom jeziku pod ključem key.
	 * @param key ključ koji mapira traženi string
	 * @return tražena riječ.
	 */
	public String getString(String key);
}

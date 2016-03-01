package hr.fer.zemris.java.tecaj_11.local;
/**
 * Sučelje koje predstavlja promatrača u oblikovnom obrascu Promatrač.
 * Instanca ovog razreda promatra objekt koji implementira sučenje ILocalizationProvider.
 * Kada se stanje subjekta promjeni, promatrač poduzima odgovarajuću akciju.
 * @author Petra Marče
 *
 */
public interface ILocalizationListener {
	/**
	 * Metoda predstavlja akciju koju je potrebno poduzeti kad se stanje subjekta kojeg promatrač promatra promjeni.
	 */
	public void localizationChanged();
}

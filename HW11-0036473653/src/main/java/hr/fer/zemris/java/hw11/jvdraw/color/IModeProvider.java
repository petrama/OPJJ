package hr.fer.zemris.java.hw11.jvdraw.color;
/**
 * Sučelje koje predstavlja subjekt oblikovnog obrasca Promatrač.
 * Kad se dogodi promjena vrste objekta kojeg treba crtati , on obavještava svoje promatrače.
 * @author Petra Marče
 *
 */
public interface IModeProvider {
	
	/**
	 * Metoda za dodavanje novog promatrača subjekta.
	 * @param listener novi promatrač koji se dodaje u red promatrača.
	 */
	public void addModeChangedListener(ModeChangedListener listener);
	/**
	 * Metoda za uklanjanje postojećeg promatrača iz reda promatrača.
	 * @param listener promatrač koji se uklanja.
	 */
	public void removeModeChangedListener(ModeChangedListener listener);

		
	
}

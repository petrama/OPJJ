package hr.fer.zemris.java.hw11.jvdraw.color;
/**
 * Sučelje koje predstavlja subjekt oblikovnog obrasca Promatrač.
 * Kad se dogodi promjena boje, on obavještava svoje promatrače.
 * @author Petra Marče
 *
 */


import java.awt.Color;

public interface IColorProvider {
	/**
	 * Metoda koja dohvaća trenutnu boju.
	 * @return trenutna boja.
	 */
	public Color getCurrentColor();
	/**
	 * Metoda za dodavanje novog promatrača subjekta.
	 * @param listener novi promatrač koji se dodaje u red promatrača.
	 */
	public void addColorChangeListener(ColorChangeListener listener);
	/**
	 * Metoda za uklanjanje postojećeg promatrača iz reda promatrača.
	 * @param listener promatrač koji se uklanja.
	 */
	public void removeColorChangeListener(ColorChangeListener listener);

}

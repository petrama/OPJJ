package hr.fer.zemris.java.hw11.jvdraw.color;

import hr.fer.zemris.java.hw11.jvdraw.geometrical.GeometricalObject;
/**
 * Sučelje koje predstavlja promatrača promjene vrste objekta koji se crta.
 * @author Petra Marče
 *
 */
public interface ModeChangedListener {
	/**
	 * Metoda koja se poziva kada se promjeni vrsta objekta koja se treba crtati
	 * @param defaultG nova vrsta objekta.
	 */
	public void newStatusSelected(GeometricalObject defaultG);
}

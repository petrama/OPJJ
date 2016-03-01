package hr.fer.zemris.java.hw11.jvdraw.color;

import java.awt.Color;
/**
 * Razred koji predstavlja promatrača promjene boje.
 * @author Petra Marče
 *
 */
public interface ColorChangeListener {
	/**
	 * Metoda koja se poziva kad se promjeni boja subjekta.
	 * @param source referenca na subjekt koji je promjenio boju.
	 * @param oldColor stara boja.
	 * @param newColor nova boja.
	 */
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor);
}

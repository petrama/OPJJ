package hr.fer.zemris.java.hw11.jvdraw.geometrical;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JFrame;
/**
 * Sučelje koje predstavlja model Geometrijskog Lika.
 * @author Petra Marče
 *
 */
public interface GeometricalObject {
	/**
	 * Metoda koja iscratava objekt pomoću zadane instance razreda Graphic translatirane za zadanu veličinu.
	 * @param g crtač
	 * @param gornji lijevi kut "ekrana"
	 */
	public void nacrtajSe(Graphics g, Point leftUpCorner);
	/**
	 * Metoda koja postavlja trenutne parametre objekta na nove ulazne parametre.
	 * Nakon ove metode parametri početnog objekta su izmjenjeni
	 * @param first početna točka
	 * @param second krajnja točka
	 * @param out boja crtanja ruba
	 * @param in boja ispune
	 */
	public void update(Point first, Point second, Color out, Color in);
	/**
	 * Metoda koja stvara novi primjerak Geometrijskog lika identičnog predanome.
	 * @return nova instanca razreda GeometrijskiLik
	 */
	public GeometricalObject copy();
	/**
	 * Metoda koja prikazuje opcije izmjene za GeometrijskiLik
	 * @param f instanca razreda JFrame na kojoj ce se dijalog prikazati.
	 */
	public void showOptions(JFrame f);
	/**
	 * Najmanja x koordinata u kojoj je objekt prisutan.
	 * @return minimalna x koordinata
	 */
	public int getMinX();
	/**
	 * Najmanja y koordinata u kojoj je objekt prisutan.
	 * @return minimalna y koordinata
	 */
	public int getMinY();
	/**
	 * Najveca x koordinata u kojoj je objekt prisutan.
	 * @return maksimalna x koordinata
	 */
	public int getMaxX();

	/**
	 * Najveca y koordinata u kojoj je objekt prisutan.
	 * @return maksimalna y koordinata
	 */
	public int getMaxY();
}

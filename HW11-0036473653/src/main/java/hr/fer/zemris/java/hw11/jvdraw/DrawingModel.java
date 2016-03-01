package hr.fer.zemris.java.hw11.jvdraw;


import hr.fer.zemris.java.hw11.jvdraw.geometrical.GeometricalObject;
/**
 * Sučelje koje predstavlja jedan model crtanja.
 * Sadrži popis geometrijskih likova i promatraca koji reagiraju kad se sadrzaj te liste promjeni.
 * @author Petra Marče.
 *
 */
public interface DrawingModel {
	/**
	 * Metoda koja vraća prob elemenata u listi geometrijskih likova.
	 * @return broj elemenata.
	 */
	public int getSize();
	/**
	 * Metoda koja vraća geometrijski lik na i-toj poziciji.
	 * @param index index sa kojeg se dohvaća element iz liste.
	 * @return traženi element.
	 */
	public GeometricalObject getObject(int index);
	/**
	 * Metoda za dodavanje novom geometrijskog objekta u listu objekata.
	 * @param object novi objekt koji se dodaje u listu.
	 */
	public void add(GeometricalObject object);
	/**
	 * Metoda za dodavanje novog promatrača modela.
	 * @param l novi promatrač koji se dodaje u listu promatrača.
	 */
	public void addDrawingModelListener(DrawingModelListener l);
	/**
	 * Metoda za uklanjanje promatrača iz liste promatrača.
	 * @param  l promatrač koji se uklanja
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
	
	
}

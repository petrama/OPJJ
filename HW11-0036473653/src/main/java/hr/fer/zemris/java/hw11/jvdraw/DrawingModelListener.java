package hr.fer.zemris.java.hw11.jvdraw;
/**
 * Razred koji predstavlja promatraca DrawingModela.
 * Na promjenu sadržaja liste promatrač poduzima odgovarajucu akciju.
 * @author petra Marče.
 *
 */
public interface DrawingModelListener {
	/**
	 * Metoda koja se poziva kada se u listu subjekta dodaju elementi.
	 * @param source subjekt nad kojim se desila promjena.
	 * @param index0 početni index promjene.
	 * @param index1 krajnji indeks promjene.
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);
	/**
	 * Metoda koja se poziva kada se iz liste subjekta uklanjaju elementi. 
	 * @param source subjekt nad kojim se desila promjena.
	 * @param index0 početni index promjene.
	 * @param index1 krajnji indeks promjene.
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
	/**
	 * Metoda koja se poziva kada se u listi subjekta mjenjaju elementi.
	 * @param source subjekt nad kojim se desila promjena.
	 * @param index0 početni index promjene.
	 * @param index1 krajnji indeks promjene.
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);

}

package hr.fer.zemris.java.tecaj.hw4.db;

/**
 * Sučelje koje prestavlja filter za StudentRecord.
 * @author petra
 *
 */
public interface IFilter {
	/**
	 * Metoda koja određuje da li se zapis prihvaća na osnovi zadanog filtera.
	 * @param record zapis koji se provjerava.
	 * @return vraća true ako zapis zadovoljava uvjete, false inače
	 */
	public boolean accepts(StudentRecord record);
	

}

package hr.fer.zemris.java.filechecking.fmagician.syntax.nodes;

import hr.fer.zemris.java.filechecking.fmagician.syntax.FMagicianNodeVisitor;

/**
 * Razred predstavlja instrukciju koja provjerava postojanje datoteke ili direktorija,
 * koji se nalazi na predanoj putanji. 
 * Instrukcija može sadržavati poruku o pogrešci.
 * @author Petra Marče
 *
 */
public class ExistsStatement extends FMagicianNode {
	
	/** ako je true znaci da provjerava direktorij**/
	private boolean dir;
	/** putanja do objekta cije se postojanje utvrđuje**/
	private PathNode path;
	/** poruka o pogrešci **/
	private MessageNode message;
	/** Zastavica koja ako je true označava da je instrukcija invertirana **/
	private boolean inverted;
	
	/**
	 * Konstruktor.
	 * @param dir prima oznaku za direktorij ili datoteku.
	 * @param path putanja do trazenog.
	 * @param m opcionalna poruka o pogrešci.
	 * @param i oznaka invertiranja.
	 */
	public ExistsStatement(boolean dir,PathNode path, MessageNode m,boolean i){
		this.dir=dir;
		this.path=path;
		message=m;
		inverted=i;
	}

	/**
	 * Dohvat zastavice dir.
	 * Metoda vraća true ako instanca razreda ispituje postojanje direktorija, false inače.
	 * @return true ako se ispituje direktorij, false inače.
	 */
	public boolean isDir() {
		return dir;
	}

	/**
	 * Metoda vraća poruku u slučaju pogreške.
	 */
	public MessageNode getMessage() {
		return message;
	}

	/**
	 * Metoda za dohvat zastavice invertiranja.
	 * @return true ako je instrukcija invertirana, false inače.
	 */
	public boolean isInverted() {
		return inverted;
	}
	
	/**
	 * Metoda za dohvat putanje do objekta čije se postojanje ispituje.
	 * @return putanja do objekta.
	 */
	public PathNode getPath(){
		return path;
	}
	
	public void accept(FMagicianNodeVisitor visitor){
		visitor.visit(this);
	}
}

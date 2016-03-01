package hr.fer.zemris.java.filechecking.fmagician.syntax.nodes;


import hr.fer.zemris.java.filechecking.fmagician.syntax.FMagicianNodeVisitor;

import java.util.ArrayList;
import java.util.List;



/**
 * Općeniti čvor stabla programa napisanog jezikom <i>fmagician</i>
 * pri čemu konkretne implementacije ovog čvora predstavljaju
 * pojedine naredbe (definicije, dodjeljivanja, ispis).
 * 
 * @author Petra Marče
 */

	
public abstract class FMagicianNode {
	
	private List<FMagicianNode> children;
	/**
	 * Konstruktor.
	 */
		public FMagicianNode() {
		children=null;
		}
		
		/**
		 * Metoda koja dodaje dijete u niz children.
		 * @param child Čvor djeteta.
		 */

		public void addChildNode(FMagicianNode child) {
			if (children == null){
				children = new ArrayList<FMagicianNode>();
			}
			
			children.add(child);
		}

		/**
		 * Metoda koja vraća broj djece ovog čvora.
		 * @return Broj djece čvora.
		 */
		
		public int numberOfChildren() {
			if (children == null){
				return 0;
			}
			return children.size();
		}

		/**
		 * Metoda koja vraća dijete na poziciji index (od 0 do brojDjece - 1).
		 * @param index Indeks djeteta kojeg želimo vratiti.
		 * @return Čvor djeteta.
		 */
		
		public FMagicianNode getChild(int index) {
			if  (children == null){
				throw new IndexOutOfBoundsException("Čvor nema djece");
			}
			return (FMagicianNode) children.get(index);
		}
		
		/**
		 * Prihvat posjetitelja.
		 * @param visitor posjetitelj
		 */
		public abstract void accept(FMagicianNodeVisitor visitor);
		
		public abstract MessageNode getMessage();
	}


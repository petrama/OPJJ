package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Matrix;
/**
 * Program koji služi za racunanje inverza matrice.
 * @author Petra Marče
 *
 */
public class Prog4 {

	/**
	 * Metoda koja se poziva pri pokretanju programa.
	 * @param args argumenti iz komandne linije.
	 * Program ne očekuje argumente.
	 */
 public static void main(String[] args) {
  IMatrix m1 = Matrix.parseSimple("1 5 3 | 0 0 8 | 1 1 1");
  IMatrix m2 = Matrix.parseSimple("3 | 4 | 1");
  IMatrix r = m1.nInvert().nMultiply(m2);
  System.out.println(r);

 }
}

package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Matrix;

/**
 * Program koji služi za rjesavanje sustava jednadzbi.
 * @author Petra Marče
 *
 */
public class Prog3 {
	/**
	 * Metoda koja se poziva pri pokretanju programa.
	 * @param args argumenti iz komandne linije.
	 * Program ne očekuje argumente.
	 */
 public static void main(String[] args) {
  IMatrix a = Matrix.parseSimple("3 5 | 2 10");
  IMatrix r = Matrix.parseSimple("2 | 8");
  IMatrix v = a.nInvert().nMultiply(r);
  System.out.println("Rjesenje sustava je: ");
  System.out.println(v);
  
 }
}
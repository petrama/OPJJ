package hr.fer.zemris.linearna;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;

import org.junit.Assert;
import org.junit.Test;

public class AbstractVectorTest {

	@Test
	public void testAdd() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(polje);
		IVector vektor1 = new Vector(polje);
		vektor.add(vektor1);
		for (int i = 0; i < 10000; i++) {
			Assert.assertEquals(2 * i, vektor.get(i), 0.0000000000001);
		}
	}
	@Test(expected=IncompatibleOperandException.class)
	public void testAddErr(){
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		
		IVector vektor1 = new Vector(polje);
			IVector vektor3 = new Vector(1, 2, 3, 4);
			vektor1.add(vektor3);
		

		
	}

	@Test
	public void testNAdd() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(polje);
		IVector vektor1 = new Vector(polje);
		vektor.nAdd(vektor1);

		for (int i = 0; i < 10000; i++) {
			Assert.assertEquals(i, vektor.get(i), 0.0000000000001);
		}
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void testNaddErr(){
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor1 = new Vector(polje);
			IVector vektor3 = new Vector(1, 2, 3, 4);
			vektor1.nAdd(vektor3);
		

		}
	

	@Test
	public void testSub() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(polje);
		IVector vektor1 = new Vector(polje);
		vektor.sub(vektor1);
		for (int i = 0; i < 10000; i++) {
			Assert.assertEquals(0, vektor.get(i), 0.0000000000001);
		}
	}
		
	@Test(expected=IncompatibleOperandException.class)
	public void testSubErr(){
			IVector vektor1=Vector.parseSimple("1");
			IVector vektor3 = new Vector(1, 2, 3, 4);
			vektor1.sub(vektor3);
		

	}

	@Test
	public void testNSubb() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(polje);
		IVector vektor1 = new Vector(polje);
		vektor.nSub(vektor1);

		for (int i = 0; i < 10000; i++) {
			Assert.assertEquals(i, vektor.get(i), 0.0000000000001);
		}
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void testNSubbErr() {
			IVector vektor3 = new Vector(1, 2, 3, 4);
			IVector v=Vector.parseSimple("1 2");
			v.nSub(vektor3);
		
	
	}

	@Test
	public void testScalarMultiply() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(polje);

		vektor.scalarMultiply(5);
		for (int i = 0; i < 10000; i++) {
			Assert.assertEquals(5 * i, vektor.get(i), 0.0000000000001);
		}
	}

	
	@Test
	public void testNScalarMultiply() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(polje);
		vektor.nScalarMultiply(5);
		for (int i = 0; i < 10000; i++) {
			Assert.assertEquals(i, vektor.get(i), 0.0000000000001);
		}
	}

	
	@Test
	public void testNorm() {
		double[] polje = new double[100];
		for (int i = 0; i < 100; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(polje);
		Assert.assertEquals(573.018, vektor.norm(), 0.01);
	}

	@Test
	public void testNormalize() {
		double[] polje = new double[100];
		for (int i = 0; i < 100; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(polje);
		vektor.normalize();
		Assert.assertEquals(1, vektor.norm(), 0.00000001);
	}

	@Test
	public void testNNormalize() {
		double[] polje = new double[100];
		for (int i = 0; i < 100; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(polje);
		IVector vektor1 = vektor.nNormalize();

		for (int i = 0; i < 100; i++) {
			Assert.assertEquals(i / vektor.norm(), vektor1.get(i), 0.000);
		}
	}

	@Test
	public void testCosine() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(polje);
		Assert.assertEquals(1, vektor.cosine(vektor), 0.0);
	}

	@Test
	public void testScalarProduct() {
		double[] polje = new double[100];
		for (int i = 0; i < 100; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(polje);
		Assert.assertEquals(328350, vektor.scalarProduct(vektor), 0.0);
	}
	
	@Test (expected=IncompatibleOperandException.class)
	public void testScalarProductErr(){
		IVector vektor1 = new Vector(1, 2, 3);
		 	IVector vektor=Vector.parseSimple("5");
			vektor.scalarProduct(vektor1);
		
	}

	@Test
	public void testnVectorProduct() {
		IVector vektor = new Vector(1, 2, 3);
		IVector vektorRez = new Vector(0, 0, 0);
		Assert.assertArrayEquals(vektorRez.toArray(),
				vektor.nVectorProduct(vektor).toArray(), 0.0001);

	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void testNVectorProduct(){
		IVector vektor = new Vector(1, 2, 3);
		IVector vektor1 = new Vector(1, 2, 3, 4);
		 vektor.nVectorProduct(vektor1);
		
		
		
	}

	@Test
	public void nFromHomogeneus() {
		double[] polje = new double[100];
		for (int i = 0; i < 100; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(polje);
		IVector vektor1 = vektor.nFromHomogeneus();
		for (int i = 0; i < 99; i++) {
			Assert.assertEquals(vektor.get(i) / 99, vektor1.get(i), 0.00001);
		}
	}

	@Test
	public void testToArray() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(polje);
		Assert.assertArrayEquals(polje, vektor.toArray(), 0.0000);
	}

	@Test
	public void testCopyPart() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(polje);
		IVector vektor1 = vektor.copyPart(20000);
		for (int i = 0; i < 20000; i++) {
			if (i < 10000) {
				Assert.assertEquals(i, vektor1.get(i), 0.0);
			} else {
				Assert.assertEquals(0, vektor1.get(i), 0.0);
			}
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCopyPartErr() {
		IVector v = Vector.parseSimple("1 2");
		v.copyPart(-5);
		}

	
	@Test
	public void testToRowMatrix() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(polje);

		IMatrix matrica = vektor.toRowMatrix(false);
		for (int i = 0; i < 10000; i++) {
			Assert.assertEquals(i, matrica.get(0, i), 0.0);
		}

		IMatrix matrica1 = vektor.toRowMatrix(true);
		for (int i = 0; i < 10000; i++) {
			matrica1.set(0, i, 2 * i);
		}
		for (int i = 0; i < 10000; i++) {
			Assert.assertEquals(2 * i, vektor.get(i), 0.0);
		}
	}

	@Test
	public void testToColumnMatrix() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(polje);

		IMatrix matrica = vektor.toColumnMatrix(false);
		for (int i = 0; i < 10000; i++) {
			Assert.assertEquals(i, matrica.get(i, 0), 0.0);
		}

		IMatrix matrica1 = vektor.toColumnMatrix(true);
		for (int i = 0; i < 10000; i++) {
			matrica1.set(i, 0, 2 * i);
		}
		for (int i = 0; i < 10000; i++) {
			Assert.assertEquals(2 * i, vektor.get(i), 0.0);
		}
	}

	@Test
	public void testToString() {
		IVector vektor = new Vector(1.5245, 4.55645, 7.12356, 14.5651);
		String ispis = vektor.toString();
		String usporedi = "[1.5245, 4.55645, 7.12356, 14.5651]";
		Assert.assertEquals(ispis, usporedi);
	}
}
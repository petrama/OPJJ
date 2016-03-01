package hr.fer.zemris.linearna;

import org.junit.Assert;
import org.junit.Test;

public class VectorTest {

	@Test
	public void getConstructorsTested() {
		double[] pom = new double[3];
		pom[0] = -2;
		pom[1] = 4;
		pom[2] = 1;
		IVector a = new Vector(false, false, pom);
		IVector b = new Vector(pom);

		Assert.assertTrue("Vector a and b should be equal!", a.equals(b));

		IVector c = new Vector(true, true, pom);
		Assert.assertTrue("Vector a and b should be equal!",
				a.equals(c) == false);
	}

	@Test
	public void getParseSimpleTested() {
		double[] pom = new double[3];
		pom[0] = -2;
		pom[1] = 4;
		pom[2] = 1;
		IVector a = new Vector(false, false, pom);
		IVector ab = Vector.parseSimple("     -2       4        1    ");
		Assert.assertTrue("Vector a and ab should be equal!", a.equals(ab));

		IVector ac = Vector.parseSimple("     -2       40       1    ");
		Assert.assertTrue("Vector a and ac should not be equal!",
				a.equals(ac) == false);

	}

	@Test(expected = IllegalArgumentException.class)
	public void getParseSimpleTestedError() {
		double[] pom = new double[3];
		pom[0] = -2;
		pom[1] = 4;
		pom[2] = 1;
		IVector a = new Vector(false, false, pom);
		IVector ab = Vector.parseSimple("     -2ekd      4        1    ");
		Assert.assertTrue("Vector a and ab should be equal!", a.equals(ab));

		IVector ac = Vector.parseSimple("     -2       40       1    ");
		Assert.assertTrue("Vector a and ac should not be equal!",
				a.equals(ac) == false);

	}

	@Test
	public void getCheckIndexTested() {
		Vector.checkIndex(5, 0, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getCheckIndexTestedErr() {
		Vector.checkIndex(10, 0, 5);
	}

	@Test
	public void getNSetTest() {
		IVector ac = Vector.parseSimple("     -2       40       1    ");
		Assert.assertTrue("Element on index 1 should be 40!", ac.get(1) == 40);
		ac.set(1, 70);
		Assert.assertTrue("Element on index 1 should be 70!", ac.get(1) == 70);
	}

	@Test(expected = UnmodifiableObjectException.class)
	public void getNSetTestErr() {
		double[] pom = new double[3];
		pom[0] = -2;
		pom[1] = 4;
		pom[2] = 1;
		IVector a = new Vector(true, true, pom);
		Assert.assertTrue("Element on index 1 should be 4!", a.get(1) == 4);
		a.set(1, 70);

	}

	@Test
	public void getDimensionTest() {
		IVector ac = Vector.parseSimple("     -2       40       1  4 3 5 6   ");
		Assert.assertTrue("Dimension of vector should be 7!",
				ac.getDimension() == 7);
	}

	@Test
	public void getCopyTested() {
		IVector ac = Vector.parseSimple("     -2       40       1  4 3 5 6   ");
		IVector cop = ac.copy();
		Assert.assertTrue("Copy is not working!", ac.equals(cop));

	}

	@Test(expected=IllegalArgumentException.class)
	public void getNewInstanceTested() {
		double[] pom = new double[3];
		pom[0] = -2;
		pom[1] = 4;
		pom[2] = 1;
		IVector a = new Vector(true, true, pom);
		IVector b = a.newInstance(5);
		Assert.assertTrue("Dimension of new vector should be 5!",
				b.getDimension() == 5);
		a.newInstance(-5);
		
	}
	@Test
	public void hashEqTest() {
		double[] pom = new double[3];
		pom[0] = -2;
		pom[1] = 4;
		pom[2] = 1;
		IVector a = new Vector(false, false, pom);
		IVector b = new Vector(pom);

		Assert.assertTrue("Vector a and b should be equal!", a.equals(b));
		Assert.assertTrue("HashCode of a and b should be equal!",
				a.hashCode() == b.hashCode());

	}
	

	@Test
	public void getCopyPartTested() {
		IVector p = Vector.parseSimple(" 1 2 3 ");
		IVector d = Vector.parseSimple("1 2 3 0 0 0 0");
		IVector e = Vector.parseSimple("1 2");

		IVector kopi7 = p.copyPart(7);
		IVector kopi2 = p.copyPart(2);
		Assert.assertTrue("Vector d and kopi7 should be equal!",
				d.equals(kopi7));
		Assert.assertTrue("Vector e and kopi2 should be equal!",
				e.equals(kopi2));

	}

	@Test(expected = IllegalArgumentException.class)
	public void getCopyPartTestedEx() {
		IVector p = Vector.parseSimple(" 1 2 3 ");
		p.copyPart(0);
	}

	@Test
	public void getAddTested() {
		IVector p = Vector.parseSimple(" 1 2 3 ");
		IVector d = Vector.parseSimple("1 2 3 ");
		IVector e = p.add(d);

		Assert.assertTrue("P should be 2 4 6!", e.equals(p));

	}
	
	@Test 
	public void getNAddTested(){
		IVector p = Vector.parseSimple(" 1 2 3 ");
		IVector d = Vector.parseSimple("1 2 3 ");
		

		Assert.assertTrue("P should be 2 4 6!", p.equals(d));
	}

	@Test
	public void testGetDimension() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(true, true, polje);
		Assert.assertEquals(10000, vektor.getDimension());
	}

	@Test
	public void testCopy() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new Vector(true, true, polje);
		IVector vektor1 = vektor.copy();
		Assert.assertArrayEquals(vektor.toArray(), vektor1.toArray(), 0.0);
		
	}

	@Test
	public void testParseSimple() {
		StringBuilder builder = new StringBuilder();
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		for (int i = 0; i < 10000; i++) {
			builder.append(Integer.toString(i));
			builder.append(" ");
		}
		IVector vektor = Vector.parseSimple(builder.toString());
		Assert.assertArrayEquals(polje, vektor.toArray(), 0.0);
	}
	
	@Test 
	public void equalsHashTest(){
		IVector p = Vector.parseSimple(" 1 2 3 ");
		IVector d = Vector.parseSimple(" 1 2 3 4");
		IMatrix m=Matrix.parseSimple("1 2 3");
		Assert.assertFalse("Sholud not be equal!",p.equals(null));
		Assert.assertFalse("Sholud not be equal!",p.equals(m));
		Assert.assertFalse("Sholud not be equal!",p.equals(d));
		
		Assert.assertFalse("Sholud not be equal!",p.hashCode()==d.hashCode());
		Assert.assertFalse("Sholud not be equal!",p.hashCode()==m.hashCode());
	
		
		
	}
	
	

	
}

package hr.fer.zemris.java.tecaj.hw3;

import org.junit.Test;

import org.junit.Assert;

public class ComplexNumberTest {

	@Test
	public void konstruktor() {
		ComplexNumber c = new ComplexNumber(-17.2, 18.9);
		{
			Assert.assertTrue(c.realPart == -17.2 && c.imagPart == 18.9);
		}
	}

	@Test
	public void konsRealni() {
		ComplexNumber c = ComplexNumber.fromReal(18.9);
		{
			Assert.assertTrue(c.getReal() == 18.9 && c.getImaginary() == 0);
		}
	}

	@Test
	public void konsImaginarni() {
		ComplexNumber c = ComplexNumber.fromImaginary(18.9);
		{
			Assert.assertTrue(c.realPart == 0 && c.imagPart == 18.9);
		}
	}

	@Test
	public void konsKut() {
		double eksp = -6;
		double dozvOdstupanje = Math.pow(10, eksp);

		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(5, -Math.PI);

		double greskaReal = Math.abs(Math.abs(c.realPart) - 5);
		double greskaImag = c.imagPart - 0;
		System.out.println(c);

		Assert.assertTrue(greskaReal < dozvOdstupanje
				&& greskaImag < dozvOdstupanje);

	}

	@Test
	public void parserRealni() {
		ComplexNumber c = ComplexNumber.parse("-5.17");
		{
			Assert.assertTrue(c.realPart == -5.17);
		}

	}

	@Test
	public void parserI1() {
		ComplexNumber c = ComplexNumber.parse("-i");
		Assert.assertTrue(c.imagPart == -1 && c.realPart == 0);
	}
	
	@Test
	public void parserI2() {
		ComplexNumber c = ComplexNumber.parse("i");
		Assert.assertTrue(c.imagPart == 1 && c.realPart == 0);
	}

	@Test
	public void parserI3() {
		ComplexNumber c = ComplexNumber.parse("5+6i");
		Assert.assertTrue(c.imagPart == 6 && c.realPart == 5);
	}

	@Test
	public void parserI4() {
		ComplexNumber c = ComplexNumber.parse("-5-6i");
		Assert.assertTrue(c.imagPart ==- 6 && c.realPart == -5);
	}
	@Test
	public void parserImag() {
		ComplexNumber c = ComplexNumber.parse("-7.4i");
		Assert.assertTrue(c.imagPart == -7.4 && c.realPart == 0);
	}

	@Test
	public void getParserTested1() {
		ComplexNumber c = ComplexNumber.parse("1");
		Assert.assertTrue(c.imagPart == 0 && c.realPart == 1);
		System.out.println(c);
	}
		
	@Test (expected=IllegalArgumentException.class)
	public void getParserTested(){
		ComplexNumber.parse("5+78");

	}
	@Test(expected=IllegalArgumentException.class)
	public void getDivideTested(){
		(new ComplexNumber(1,1)).divideTwoParts("petra", 7);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getParseTest3(){
		ComplexNumber.parse("i57ert8i");

	}
	

	@Test
	public void kutIMod() {
		double eksp = -6;
		double dozvOdstupanje = Math.pow(10, eksp);

		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(1, Math.PI / 2);

		double greskaModul = Math.abs(Math.abs(c.getMagnitude() - 1));

		double greskaKut = Math.abs(Math.abs(c.getAngle() - Math.atan2(0, 1)));

		Assert.assertTrue(greskaModul < dozvOdstupanje
				&& greskaKut < dozvOdstupanje);

	}

	@Test
	public void getMagnitudeTest() {
		Assert.assertEquals(
				Math.abs(new ComplexNumber(1, 1).getMagnitude() - Math.sqrt(2)) < 1E-6,
				true);
	}

	@Test
	public void getAngleTest() {
		Assert.assertTrue(Math.abs(new ComplexNumber(1, 1).getAngle() - Math.PI
				/ 4) < 1E-6);

	}

	@Test
	public void getAddTest() {
		ComplexNumber c = new ComplexNumber(5, 7);
		ComplexNumber zbroj = c.add(new ComplexNumber(8, 9));
		Assert.assertTrue(zbroj.realPart == 13 && zbroj.imagPart == 16);
	}

	@Test
	public void getSubTest() {
		ComplexNumber c = new ComplexNumber(5, 7);
		ComplexNumber zbroj = c.sub(new ComplexNumber(8, 9));
		Assert.assertTrue(zbroj.realPart == -3 && zbroj.imagPart == -2);
	}

	@Test
	public void getDivTest() {
		ComplexNumber c = new ComplexNumber(2, 2);
		ComplexNumber rez = c.div(new ComplexNumber(1, 1));
		Assert.assertTrue(Math.abs(rez.getImaginary()) < 1E-6
				&& Math.abs(rez.getReal() - 2) < 1E-6);
	}

	@Test
	public void getMulTest() {
		ComplexNumber c = new ComplexNumber(2, 2);
		ComplexNumber rez = c.mul(new ComplexNumber(1, 0));
		Assert.assertTrue(Math.abs(Math.abs(rez.getReal()) - 2) < 1E-6
				&& Math.abs(Math.abs(rez.getImaginary()) - 2) < 1E-6);

	}

	@Test
	public void getPowTest(){
		ComplexNumber c = (new ComplexNumber(2, 2)).power(4);
		Assert.assertTrue(c.getImaginary()<1E-6
				&& c.getReal()+2*2*2*2<1E-6);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void getPowExTest(){
		new ComplexNumber(2, 2).power(-5);
	}
	
	@Test
	public void ispis(){
		ComplexNumber[] polje=(new ComplexNumber(4,3)).root(2);
		for(int i=0;i<2;i++){
			System.out.println(polje[i]);
		}
	}
	
	@Test
	public void getRootTest(){
		ComplexNumber[] polje=(new ComplexNumber(16,0)).root(8);
		Assert.assertTrue(Math.abs(polje[0].getMagnitude()-Math.sqrt(2))<1E-6);
				
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void getRootExTest(){
		(new ComplexNumber(3,4)).root(-2);
		
	}
	
}

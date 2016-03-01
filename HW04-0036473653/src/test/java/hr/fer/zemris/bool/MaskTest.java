package hr.fer.zemris.bool;

import org.junit.Assert;
import org.junit.Test;

public class MaskTest {
	
	
	@Test
	public void testConstruct(){
		MaskValue[] polje=new MaskValue[] {MaskValue.ONE,MaskValue.ZERO,MaskValue.DONT_CARE};
		Mask novaMaska=new Mask(polje);
		Assert.assertTrue(novaMaska.toString().compareTo("10x")==0);
	}
	
	@Test
	public void testGetValue(){
		MaskValue[] polje=new MaskValue[] {MaskValue.ONE,MaskValue.ZERO,MaskValue.DONT_CARE};
		Mask novaMaska=new Mask(polje);
		Assert.assertTrue(novaMaska.getValue(2)==MaskValue.DONT_CARE);
	}
	
	@Test
	public void equalsHashTest(){
		Mask m1=Mask.parse("111");
		Mask m2=Mask.parse("111");
		Assert.assertTrue(m1.equals(m2));
		Assert.assertTrue(m1.hashCode()==m2.hashCode());
		
	}
	
	@Test
	public void parseTest(){
		String ulazna="0010xX10";
		Mask parsirana=Mask.parse(ulazna);
		Assert.assertTrue(parsirana.toString().equalsIgnoreCase(ulazna));
		
	}
	@Test 
	public void fromIndexTest() {
		Mask nova = Mask.fromIndex(4, 0);
		Assert.assertTrue(nova.equals(Mask.parse("0000")));
	}

	@Test
	public void numOfDontsTest(){
		String ulazna="0010xX10";
		Mask parsirana=Mask.parse(ulazna);
		Assert.assertTrue(parsirana.numberOfDontCares()==2);
		
	}
	@Test
	public void getNumOfOnesTest(){
		String ulazna="0010xX10";
		Mask parsirana=Mask.parse(ulazna);
		Assert.assertTrue(parsirana.getNumberOfOnes()==2);
		
	}
	@Test
	public void getNumOfZerosTest(){
		String ulazna="0010xX10";
		Mask parsirana=Mask.parse(ulazna);
		Assert.assertTrue(parsirana.getNumberOfZeros()==4);
		
	}
	
	@Test
	public void isMoreGeneralThanTest() {
		Mask visa = Mask.parse("x011x0");
		Mask niza = Mask.parse("1011x0");
		Assert.assertTrue(visa.isMoreGeneralThan(niza));
		Assert.assertFalse(niza.isMoreGeneralThan(visa));

		visa = Mask.parse("11x00");
		niza = Mask.parse("11x11");
		Assert.assertFalse(niza.isMoreGeneralThan(visa));

		visa = Mask.parse("11xx0");
		niza = Mask.parse("11x11");
		Assert.assertFalse(visa.isMoreGeneralThan(niza));

		visa = Mask.parse("11xx00");
		niza = Mask.parse("11x11");
		Assert.assertFalse(visa.isMoreGeneralThan(niza));

		visa = Mask.parse("1011x0000x");
		niza = Mask.parse("1011x00001");
		Assert.assertTrue(visa.isMoreGeneralThan(niza));

		visa = Mask.parse("1011x0000x");
		niza = Mask.parse("1011x0000x");
		Assert.assertFalse(visa.isMoreGeneralThan(niza));
	}
	
	@Test
	public void combineTest(){
		Mask prva=Mask.parse("1110");
		Mask druga=Mask.parse("1111");
		Mask treca=Mask.combine(druga,prva);
		Assert.assertTrue(treca.equals(Mask.parse("111x")));
		
		prva=Mask.parse("1010");
		druga=Mask.parse("0100");
		treca=Mask.combine(druga,prva);
		System.out.println(treca);

		Assert.assertTrue(treca==null);
		
	}

}

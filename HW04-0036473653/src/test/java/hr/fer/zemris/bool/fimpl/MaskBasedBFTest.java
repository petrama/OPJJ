package hr.fer.zemris.bool.fimpl;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Masks;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class MaskBasedBFTest {
	
	@Test
	public void test2(){
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		BooleanFunction f1 = new MaskBasedBF("f1", Arrays.asList(varA, varB,
				varC, varD), true, Masks.fromStrings("00x0", "1xx1"),
				Masks.fromStrings("10x0"));
		for (Integer i : f1.mintermIterable()) { // Ispis: 0, 2, 9, 11, 13, 15
			System.out.println("Imam minterm: " + i);
		}
		for (Integer i : f1.maxtermIterable()) { // Ispis: 1, 3, 4, 5, 6, 7, 12,
													// 14
			System.out.println("Imam maxterm: " + i);
		}
		for (Integer i : f1.dontcareIterable()) { // Ispis: 8, 10
			System.out.println("Imam dontcare: " + i);
		}
		varA.setValue(BooleanValue.DONT_CARE);
		varB.setValue(BooleanValue.TRUE);
		varC.setValue(BooleanValue.TRUE);
		System.out.println(f1.getValue());
		
		if ( f1.hasMaxterm(1) != true ) {
			Assert.fail("Metoda hasMaxterm ne radi!");
		}
		
		if ( f1.hasMinterm(0) != true ) {
			Assert.fail("Metoda hasMinterm ne radi!");
		}
		
		if ( f1.hasDontCare(8) != true ) {
			Assert.fail("Metoda hasDontCare ne radi!");
		}
		
		if ( f1.getDomain().size() != 4 ) {
			Assert.fail("Metoda getDomain ne radi!");
		}
		
		
	}
	
	@Test
	public void getValueTest(){
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		
		BooleanFunction f1 = new MaskBasedBF("f1", Arrays.asList(varA, varB,
				varC ), true, Masks.fromStrings("00x", "1x0"),
				Masks.fromStrings("010"));
		for (Integer i : f1.mintermIterable()) { 
			System.out.println("Imam minterm: " + i);//0 1 6 4 
		}
		for (Integer i : f1.maxtermIterable()) { 
			System.out.println("Imam maxterm: " + i);//3 5 7
		}
		for (Integer i : f1.dontcareIterable()) { 
			System.out.println("Imam dontcare: " + i);//2
		}
		
		int[] mintermi=new int[3];
		
		mintermi[0]=3;
		mintermi[1]=5;
		mintermi[2]=7;
		
		int j=0;
		
		for(Integer i : f1.maxtermIterable()) { // Ispis: 3, 5, 7
			if(i!=mintermi[j]){
				Assert.fail("Netočan izračun minterma!");
			}
			j++;
		}
		varA.setValue(BooleanValue.DONT_CARE);
		varB.setValue(BooleanValue.TRUE);
		varC.setValue(BooleanValue.TRUE);
		Assert.assertTrue(f1.getValue()==BooleanValue.FALSE);
		
		
		
		
		
		
		
		
	}
}

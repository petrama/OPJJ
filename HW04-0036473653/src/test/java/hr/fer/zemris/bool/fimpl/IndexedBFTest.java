package hr.fer.zemris.bool.fimpl;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;

import java.util.Arrays;

import org.junit.Assert;

import org.junit.Test;

public class IndexedBFTest {
	@Test
	public void test1(){
	
			BooleanVariable varA = new BooleanVariable("A");
			BooleanVariable varB = new BooleanVariable("B");
			BooleanVariable varC = new BooleanVariable("C");
			BooleanFunction f1 = new IndexedBF("f1",
					Arrays.asList(varA, varB, varC), true,
					Arrays.asList(0,1,5,7), Arrays.asList(2,3));
			for (Integer i : f1.mintermIterable()) { // Ispis: 0, 1, 5, 7
				System.out.println("Imam minterm: " + i);
			}
			for (Integer i : f1.maxtermIterable()) { // Ispis: 4, 6
				System.out.println("Imam maxterm: " + i);
			}
			for (Integer i : f1.dontcareIterable()) { // 2, 3
				System.out.println("Imam dontcare: " + i);
			}
			
			int[] mintermi=new int[4];
			
			mintermi[0]=0;
			mintermi[1]=1;
			mintermi[2]=5;
			mintermi[3]=7;

			int j=0;
			
			for(Integer i : f1.mintermIterable()) { // Ispis: 0, 1, 5, 7
				if(i!=mintermi[j]){
					Assert.fail("Netočan izračun minterma!");
				}
				j++;
			}

			if ( f1.hasMaxterm(4) != true ) {
				Assert.fail("Metoda hasMaxterm ne radi!");
			}
			
			if ( f1.hasMinterm(0) != true ) {
				Assert.fail("Metoda hasMinterm ne radi!");
			}
			
			if ( f1.hasDontCare(2) != true ) {
				Assert.fail("Metoda hasDontCare ne radi!");
			}
			
			if ( f1.getDomain().size() != 3 ) {
				Assert.fail("Metoda getDomain ne radi!");
			}
			
			if ( f1.getValue()!= BooleanValue.TRUE ) {
				Assert.fail("Metoda getValue ne radi!");
			}
			
			}
	@Test 
	public void getValueTest(){
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanFunction f1 = new IndexedBF("f1",
				Arrays.asList(varA, varB, varC), true,
				Arrays.asList(0,1,5,7), Arrays.asList(2,3));
		
		varA.setValue(BooleanValue.FALSE);
		varB.setValue(BooleanValue.DONT_CARE);
		varC.setValue(BooleanValue.FALSE);
		Assert.assertTrue(f1.getValue()==BooleanValue.DONT_CARE);
		
	}
}

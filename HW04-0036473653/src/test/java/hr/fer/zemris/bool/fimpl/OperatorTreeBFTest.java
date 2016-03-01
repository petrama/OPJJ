package hr.fer.zemris.bool.fimpl;

import hr.fer.zemris.bool.BooleanConstant;
import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.opimpl.BooleanOperators;
import hr.fer.zemris.bool.fimpl.*;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;


public class OperatorTreeBFTest{
	@Test
	public void test1() {
		
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		
		BooleanOperator izraz1 = BooleanOperators.or(BooleanConstant.FALSE,
				varC, BooleanOperators.and(varA, BooleanOperators.not(varB)));
		BooleanFunction f1 = new OperatorTreeBF("f1", Arrays.asList(varA, varB,
				varC), izraz1);
	
		for (Integer i : f1.mintermIterable()) { // Ispis: 1, 3, 4, 5, 7
			System.out.println("Imam minterm: " + i);
		}
		for (Integer i : f1.maxtermIterable()) { // Ispis: 0, 2, 6
			System.out.println("Imam maxterm: " + i);
		}
		for (Integer i : f1.dontcareIterable()) { // Ispis:
			System.out.println("Imam dontcare: " + i);
		}
		if ( f1.hasMaxterm(0) != true ) {
			Assert.fail("Metoda hasMaxterm ne radi!");
		}
		
		if ( f1.hasMinterm(1) != true ) {
			Assert.fail("Metoda hasMinterm ne radi!");
		}
		
		if ( f1.hasDontCare(2) != false ) {
			Assert.fail("Metoda hasDontCare ne radi!");
		}
		
		if ( f1.getDomain().size() != 3 ) {
			Assert.fail("Metoda getDomain ne radi!");
		}
		
		varA.setValue(BooleanValue.TRUE);
		varB.setValue(BooleanValue.FALSE);
		varC.setValue(BooleanValue.TRUE);
		Assert.assertTrue(f1.getValue()==BooleanValue.TRUE);
		if ( f1.getValue()!= BooleanValue.TRUE ) {
			Assert.fail("Metoda getValue ne radi!");
		}
		
		 Assert.assertTrue(((OperatorTreeBF)f1).setAndCalculate().equals("01011101"));
	}

	@Test
	public void testgetValue(){
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanOperator izraz1 = BooleanOperators.or(BooleanConstant.FALSE,
				varC, BooleanOperators.and(varA, BooleanOperators.not(varB)));
		BooleanFunction f1 = new OperatorTreeBF("f1", Arrays.asList(varA, varB,
				varC), izraz1);
		
		varA.setValue(BooleanValue.TRUE);
		varB.setValue(BooleanValue.FALSE);
		varC.setValue(BooleanValue.DONT_CARE);
		Assert.assertTrue(f1.getValue()==BooleanValue.TRUE);
		
		}

}


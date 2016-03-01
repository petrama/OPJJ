package hr.fer.zemris.bool.opimpl;



import hr.fer.zemris.bool.BooleanOperator;

import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;

import org.junit.Assert;
import org.junit.Test;

public class BooleanOperatorANDTest {
	@Test
	public void andTest(){
		BooleanVariable var1=new BooleanVariable("A");
		BooleanVariable var2=new BooleanVariable("B");
		BooleanVariable var3=new BooleanVariable("C");
		var1.setValue(BooleanValue.TRUE);
		var2.setValue(BooleanValue.TRUE);
		var3.setValue(BooleanValue.TRUE);
		
	
		BooleanOperator and=  BooleanOperators.and(var1,var2,var3);
		Assert.assertTrue(and.getValue()==BooleanValue.TRUE);
		
	}
	

}

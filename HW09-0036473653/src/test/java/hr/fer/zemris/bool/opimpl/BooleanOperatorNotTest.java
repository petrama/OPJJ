package hr.fer.zemris.bool.opimpl;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;

public class BooleanOperatorNotTest{
	
	@Test
	public void notTest(){
		BooleanVariable val=new BooleanVariable("A");
		BooleanOperator not= new BooleanOperatorNOT(val);
		Assert.assertTrue(not.getValue()==BooleanValue.TRUE);
		
	}
	
}


		
	
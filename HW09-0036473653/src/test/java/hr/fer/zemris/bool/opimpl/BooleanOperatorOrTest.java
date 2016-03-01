package hr.fer.zemris.bool.opimpl;


	

	import hr.fer.zemris.bool.BooleanOperator;

	import hr.fer.zemris.bool.BooleanValue;
	import hr.fer.zemris.bool.BooleanVariable;

	import org.junit.Assert;
	import org.junit.Test;

	public class BooleanOperatorOrTest {
		@Test
		public void andTest(){
			BooleanVariable var1=new BooleanVariable("A");
			BooleanVariable var2=new BooleanVariable("B");
			BooleanVariable var3=new BooleanVariable("C");
			var1.setValue(BooleanValue.TRUE);
			
			
			BooleanOperator or=  BooleanOperators.or(var1,var2,var3);
			Assert.assertTrue(or.getValue()==BooleanValue.TRUE);
			
		}
		

	}




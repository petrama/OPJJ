package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Assert;
import org.junit.Test;

public class ObjectMultistackTest {
	
	@Test
	public void getPushTested(){
		ObjectMultistack stack=new ObjectMultistack();
		ValueWrapper ulazna=new ValueWrapper(2014);
		stack.push("year",ulazna);
		ValueWrapper vrh=stack.peek("year");
		if(vrh.equals(ulazna)==false){
			Assert.fail("Expected equality!");
		}
	}

	@Test
	public void getPushTested2(){
		ObjectMultistack stack=new ObjectMultistack();
		ValueWrapper ulazna1=new ValueWrapper(2014);
		stack.push("year",ulazna1);
		
		ValueWrapper ulazna2=new ValueWrapper(2050);
		stack.push("year",ulazna2);
		
		ValueWrapper vrh=stack.peek("year");
		if(vrh.equals(ulazna2)==false){
			Assert.fail("Expected equality!");
		}
	}

	@Test (expected=RuntimeException.class)
	public void getPopTestedStack(){
		ObjectMultistack stack=new ObjectMultistack();
		ValueWrapper ulazna1=new ValueWrapper(2014);
		stack.push("year",ulazna1);
		
		ValueWrapper ulazna2=new ValueWrapper(2050);
		stack.push("year",ulazna2);
		
		stack.pop("year");
		ValueWrapper vrh=stack.pop("year");
		
		if(vrh.equals(ulazna1)==false){
			Assert.fail("Expected equality!");
		}
		
		stack.pop("year");
		


		}
	
	@Test (expected=RuntimeException.class)
	public void getPopTestedStackEr(){
		ObjectMultistack stack=new ObjectMultistack();
		ValueWrapper ulazna1=new ValueWrapper(2014);
		stack.push("year",ulazna1);
		
		ValueWrapper ulazna2=new ValueWrapper(2050);
		stack.push("year",ulazna2);
		
		stack.pop("petra");
		ValueWrapper vrh=stack.pop("year");
		
		if(vrh.equals(ulazna1)==false){
			Assert.fail("Expected equality!");
		}


		}
	
	@Test (expected=RuntimeException.class)
	public void getPeekTestedStackEr(){
		ObjectMultistack stack=new ObjectMultistack();
		ValueWrapper ulazna1=new ValueWrapper(2014);
		stack.push("year",ulazna1);
		
		ValueWrapper ulazna2=new ValueWrapper(2050);
		stack.push("year",ulazna2);
		
		stack.peek("petra");
		ValueWrapper vrh=stack.pop("year");
		
		if(vrh.equals(ulazna1)==false){
			Assert.fail("Expected equality!");
		
			stack.pop("year");
			stack.peek("year");
		}


		}
	
	

	@Test (expected=RuntimeException.class)
	public void getPeekTestedEmptyStack(){
		ObjectMultistack stack=new ObjectMultistack();
		ValueWrapper ulazna1=new ValueWrapper(2014);
		stack.push("year",ulazna1);
		
		ValueWrapper ulazna2=new ValueWrapper(2050);
		stack.push("year",ulazna2);
		
		stack.pop("year");
		stack.pop("year");
		stack.peek("year");



		}
	

	@Test 
	public void getPeekTestedStack(){
		ObjectMultistack stack=new ObjectMultistack();
		ValueWrapper ulazna1=new ValueWrapper(2014);
		stack.push("year",ulazna1);
		
		ValueWrapper ulazna2=new ValueWrapper(2050);
		stack.push("year",ulazna2);
		
		stack.peek("year");
		ValueWrapper vrh=stack.peek("year");
		
		if(vrh.equals(ulazna2)==false){
			Assert.fail("Expected equality!");
		}


		}
	

	@Test (expected=RuntimeException.class)
	public void getPopTestedEmptyStack(){
		ObjectMultistack stack=new ObjectMultistack();
		ValueWrapper ulazna1=new ValueWrapper(2014);
		stack.push("year",ulazna1);
		
		ValueWrapper ulazna2=new ValueWrapper(2050);
		stack.push("year",ulazna2);
		
		stack.pop("year");
		stack.pop("year");
		stack.pop("year");



		}
	
	@Test 
	public void getIsEmptyTestedFalse(){
		ObjectMultistack stack=new ObjectMultistack();
		ValueWrapper ulazna1=new ValueWrapper(2014);
		stack.push("year",ulazna1);
		
		if(stack.isEmpty("year")){
			Assert.fail("Stack is not empty!");
		}


		}
	
	@Test 
	public void getIsEmptyTestedTrue(){
		ObjectMultistack stack=new ObjectMultistack();
		ValueWrapper ulazna1=new ValueWrapper(2014);
		stack.push("year",ulazna1);
		stack.pop("year");
		if(stack.isEmpty("year")==false){
			Assert.fail("Stack should be empty!");
		}


		}
	





	
}



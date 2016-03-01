package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Assert;
import org.junit.Test;

public class ValueWrapperTest {

	@Test
	public void getConstructorTested() {
		ValueWrapper nova = new ValueWrapper(null);
		if (nova.getValue() != null) {
			Assert.fail("Value expected to be null, but it is not!");
		}

		nova = new ValueWrapper(5);
		int razlika = (Integer) nova.getValue() - 5;
		Assert.assertTrue("Difference expected to be zero!", razlika == 0);

	}

	@Test
	public void getGettersNSettersTested() {
		ValueWrapper nova = new ValueWrapper("5.81");
		
		Assert.assertTrue("String expected to be 5.81",
				((String) nova.getValue()).compareTo("5.81") == 0);

	}

	@Test
	public void getParseToNumberTestedDouble() {
		ValueWrapper nova = new ValueWrapper("5.81");
		

		Object isparsirani = ValueWrapper.parseToNumber(nova.getValue());
		Assert.assertTrue("Double expected to be 5.81",
				((Double) isparsirani) - 5.81 == 0);
	}

	@Test
	public void getParseToNumberTestedDoubleScientific() {
		ValueWrapper nova = new ValueWrapper("5E2");


		Object isparsirani = ValueWrapper.parseToNumber(nova.getValue());
		Assert.assertTrue("Double expected to be 500",
				((Double) isparsirani) - 500 == 0);
	}

	@Test
	public void getParseToNumberTestedInteger() {
		ValueWrapper nova = new ValueWrapper("10");
	

		Object isparsirani = ValueWrapper.parseToNumber(nova.getValue());
		Assert.assertTrue("Integer expected to be 10",
				((Integer) isparsirani) - 10 == 0);
	}

	@Test(expected = RuntimeException.class)
	public void getParseToNumberTestedError() {
		ValueWrapper nova = new ValueWrapper("petra");
		ValueWrapper.parseToNumber(nova.getValue());

	}

	@Test
	public void getIncrementTestedNulls() {
		ValueWrapper nova = new ValueWrapper(null);

		nova.increment(null);

		if ((Integer) nova.getValue() != 0) {
			Assert.fail("Adding integer and integer fails!");
		}

	}

	@Test
	public void getIncrementTestedNullInt() {
		ValueWrapper nova = new ValueWrapper(null);

		nova.increment(5);

		if ((Integer) nova.getValue() != 5) {
			Assert.fail("Adding integer and integer fails!");
		}

	}

	@Test
	public void getIncrementTestedIntInt() {
		ValueWrapper nova = new ValueWrapper(5);
		nova.increment(5);
		if (Math.abs((Integer) nova.getValue() - 10) > 1E-6) {
			Assert.fail("Adding integer and integer fails!");
		}

	}

	@Test
	public void getIncrementTestedDoubDoub() {
		ValueWrapper nova = new ValueWrapper(15.0);
		nova.increment(15.0);

		if (Math.abs((Double) nova.getValue() - 30) > 1E-6) {
			Assert.fail("Adding double and double fails!");
		}

	}

	@Test
	public void getIncrementTestedIntDoub() {
		ValueWrapper nova = new ValueWrapper("10");
		nova.increment(5.78);

		if (Math.abs((Double) nova.getValue() - 15.78) > 1E-6) {
			Assert.fail("Adding integer and double fails!");
		}

	}

	@Test
	public void getIncrementTestedDoubInt() {
		ValueWrapper nova = new ValueWrapper("5.78");
		
		nova.increment(10);

		if (Math.abs((Double) nova.getValue() - 15.78) > 1E-6) {
			Assert.fail("Adding integer and double fails!");
		}

	}

	@Test
	public void getIncrementTestedStrings1() {
		ValueWrapper nova = new ValueWrapper("5.78");
		nova.increment("4");

		if (Math.abs((Double) nova.getValue() - 9.78) > 1E-6) {
			Assert.fail("Adding string and string fails!");
		}

	}

	@Test
	public void getIncrementTestedStrings2() {
		ValueWrapper nova = new ValueWrapper("5");
		nova.increment("4");

		if (Math.abs((Integer) nova.getValue() - 9) > 1E-6) {
			Assert.fail("Adding string and string fails!");
		}

	}

	@Test(expected = RuntimeException.class)
	public void getIncrementTestedError() {
		ValueWrapper nova = new ValueWrapper("5");
		nova.increment("ana");

	}

	@Test
	public void getDecrementTestedIntInt() {
		ValueWrapper nova = new ValueWrapper(5);
		nova.decrement(5);

		if (Math.abs((Integer) nova.getValue()) > 1E-6) {
			Assert.fail("Substraction of integer and integer fails!");
		}

	}

	@Test
	public void getDecrementTestedDoubDoub() {
		ValueWrapper nova = new ValueWrapper(15.0);
		nova.decrement(14.0);

		if (Math.abs((Double) nova.getValue() - 1) > 1E-6) {
			Assert.fail("Substraction of double and double fails!");
		}

	}

	@Test
	public void getDecrementTestedIntDoub() {
		ValueWrapper nova = new ValueWrapper(10);
		nova.decrement(5.5);

		if (Math.abs((Double) nova.getValue() - 4.5) > 1E-6) {
			Assert.fail("Substraction of integer and double fails!");
		}

	}

	@Test
	public void getDecrementTestedDoubInt() {
		ValueWrapper nova = new ValueWrapper("10.1");

		nova.decrement(10);

		if (Math.abs((Double) nova.getValue() - 0.1) > 1E-6) {
			Assert.fail("Substraction of integer and double fails!");
		}

	}

	@Test
	public void getDecrementTestedStrings1() {
		ValueWrapper nova = new ValueWrapper("5.78");
		nova.decrement("4");

		if (Math.abs((Double) nova.getValue() - 1.78) > 1E-6) {
			Assert.fail("Substraction of string and string fails!");
		}

	}

	@Test
	public void getDecrementTestedStrings2() {
		ValueWrapper nova = new ValueWrapper("5");
		
		nova.decrement("4");

		if (Math.abs((Integer) nova.getValue() - 1) > 1E-6) {
			Assert.fail("Substraction of string and string fails!");
		}

	}

	@Test(expected = RuntimeException.class)
	public void getDecrementTestedError() {
		ValueWrapper nova = new ValueWrapper("5");
	
		nova.decrement("ana");

	}

	@Test
	public void getDecrementTestedNull() {
		ValueWrapper nova = new ValueWrapper("5");

		nova.decrement(null);

		if ((Integer) nova.getValue() != 5) {
			Assert.fail("Substraction of string and string fails!");
		}

	}

	@Test
	public void getMulTestedIntInt() {
		ValueWrapper nova = new ValueWrapper(5);
		
		nova.multiply(5);

		if ((Integer) nova.getValue() != 25) {
			Assert.fail("Multiplication of integer and integer fails!");
		}

	}

	@Test
	public void getMulTestedIntNull() {
		ValueWrapper nova = new ValueWrapper(5);
		
		nova.multiply(null);

		if ((Integer) nova.getValue() != 0) {
			Assert.fail("Multiplication of integer and null fails!");
		}

	}

	@Test
	public void getMulTestedDoubDoub() {
		ValueWrapper nova = new ValueWrapper(15.0);
		
		nova.multiply(10.0);

		if (Math.abs((Double) nova.getValue() - 150) > 1E-6) {
			Assert.fail("Multiplication of double and double fails!");
		}

	}

	@Test
	public void getMulTestedIntDoub() {
		ValueWrapper nova = new ValueWrapper(10);
		nova.multiply(5.5);

		if (Math.abs((Double) nova.getValue() - 55) > 1E-6) {
			Assert.fail("Multiplication of integer and double fails!");
		}

	}

	@Test
	public void getMulTestedDoubInt() {
		ValueWrapper nova = new ValueWrapper("13.0");
		nova.multiply(10);

		if (Math.abs((Double) nova.getValue() - 130) > 1E-6) {
			Assert.fail("Multiplication of integer and double fails!");
		}

	}

	@Test
	public void getMulTestedStrings1() {
		ValueWrapper nova = new ValueWrapper("5.0");
		nova.multiply("4");

		if (Math.abs((Double) nova.getValue() - 20) > 1E-6) {
			Assert.fail("Multiplication of string and string fails!");
		}

	}

	@Test(expected = RuntimeException.class)
	public void getMulTestedError() {
		ValueWrapper nova = new ValueWrapper("5");
		nova.multiply("ana");

	}

	@Test
	public void getMulTestedNull() {
		ValueWrapper nova = new ValueWrapper("5");
		nova.multiply(null);

		if ((Integer) nova.getValue() != 0) {
			Assert.fail("Multiplication of string and null fails!");
		}

	}

	@Test
	public void getDivTestedIntInt() {
		ValueWrapper nova = new ValueWrapper("5");
		nova.divide(2);

		if ((Integer) nova.getValue() != 2) {
			Assert.fail("Division of integer and integer fails!");
		}

	}

	@Test(expected = ArithmeticException.class)
	public void getDivTestedIntNull() {
		ValueWrapper nova = new ValueWrapper("5");
		nova.divide(null);
		System.out.println(nova.getValue());
		if ((Integer) nova.getValue() == 0) {
			Assert.fail("Division of integer and null fails!");
		}

	}

	@Test
	public void getDivTestedDoubDoub() {
		ValueWrapper nova = new ValueWrapper(15.0);
		nova.divide(10.0);

		if (Math.abs((Double) nova.getValue() - 1.5) > 1E-6) {
			Assert.fail("Division of double and double fails!");
		}

	}

	@Test
	public void getDivTestedIntDoub() {
		ValueWrapper nova = new ValueWrapper(10);
		nova.divide(3.0);

		if (Math.abs((Double) nova.getValue() - 3.3333333) > 1E-6) {
			Assert.fail("Division of integer and double fails!");
		}

	}

	@Test
	public void getDivTestedDoubInt() {
		ValueWrapper nova = new ValueWrapper(13.0);
		nova.divide(2);

		if (Math.abs((Double) nova.getValue() - 6.5) > 1E-6) {
			Assert.fail("Division of integer and double fails!");
		}

	}

	@Test
	public void getDivTestedStrings1() {
		ValueWrapper nova = new ValueWrapper("5.0");
		nova.divide("4");

		if (Math.abs((Double) nova.getValue() - 1.25) > 1E-6) {
			Assert.fail("Multiplication of string and string fails!");
		}

	}

	@Test(expected = RuntimeException.class)
	public void getDivTestedError() {
		ValueWrapper nova = new ValueWrapper(null);
		nova.setValue("5");
		nova.divide("ana");

	}

	@Test
	public void getDivTestedNull() {
		ValueWrapper nova = new ValueWrapper(null);
		nova.setValue(null);
		nova.divide(5);

		if ((Integer) nova.getValue() != 0) {
			Assert.fail("Division of null and String fails!");
		}

	}

	@Test
	public void getNumCompareTestedNulls() {
		ValueWrapper nova = new ValueWrapper(null);

		int rez = nova.numCompare(null);
		if (rez != 0) {
			Assert.fail("Two nulls should be equal!");
		}
	}

	@Test
	public void getNumCompareTestedZeroNull() {
		ValueWrapper nova = new ValueWrapper(0.00);

		int rez = nova.numCompare(null);
		if (rez != 0) {
			Assert.fail("Two null and zero should be equal!");
		}
	}

	@Test
	public void getNumCompareTestedIntInt() {
		ValueWrapper nova = new ValueWrapper(5);

		int rez = nova.numCompare(5);
		if (rez != 0) {
			Assert.fail("5 and 5 should be equal!");
		}
	}

	@Test
	public void getNumCompareTestedIntDouble() {
		ValueWrapper nova = new ValueWrapper(5);

		int rez = nova.numCompare(5.00);
		if (rez != 0) {
			Assert.fail("5 and 5.00 should be equal!");
		}
	}

	@Test
	public void getNumCompareTestedSci() {
		ValueWrapper nova = new ValueWrapper(5);

		int rez = nova.numCompare("5E0");
		if (rez != 0) {
			Assert.fail("5 and 5E0 should be equal!");
		}
	}

	@Test
	public void getNumCompareTestedNegative() {
		ValueWrapper nova = new ValueWrapper(5);

		int rez = nova.numCompare("7.8");
		if (rez >= 0) {
			Assert.fail("5 is smaller than 7.8!");
		}
	}

	@Test
	public void getNumCompareTestedNegative2() {
		ValueWrapper nova = new ValueWrapper(5);

		int rez = nova.numCompare(6);
		if (rez >= 0) {
			Assert.fail("5 is smaller than 6!");
		}
	}

	@Test
	public void getNumCompareTestedDoub() {
		ValueWrapper nova = new ValueWrapper(5.0);

		int rez = nova.numCompare(6.0);
		if (rez != -1) {
			Assert.fail("5.00 is smaller than 6.00!");
		}
	}

	@Test
	public void getNumCompareTestedDoubBigger() {
		ValueWrapper nova = new ValueWrapper(6.0);

		int rez = nova.numCompare(4.0);
		if (rez != 1) {
			Assert.fail("4.00 is smaller than 6.00!");
		}
	}

	@Test
	public void getNumCompareTestedIntBigger() {
		ValueWrapper nova = new ValueWrapper(25);

		int rez = nova.numCompare(4);
		if (rez <= 0) {
			Assert.fail("25 is bigger than 4!");
		}
	}

	@Test
	public void getNumCompareTestedEqualDouble() {
		ValueWrapper nova = new ValueWrapper(25.0);

		int rez = nova.numCompare(25.0);
		if (rez != 0) {
			Assert.fail("25 is same as 25!");
		}
	}

	@Test
	public void getNumCompareTestedNotEqual() {
		ValueWrapper prva = new ValueWrapper(25.00);
		ValueWrapper druga = new ValueWrapper("5.00");

		if (prva.equals(druga)) {
			Assert.fail("25 is bigger than 5!");
		}
	}

}

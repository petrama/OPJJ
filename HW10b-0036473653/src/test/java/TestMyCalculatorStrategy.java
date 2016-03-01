import javax.swing.JLabel;

import hr.fer.zemris.java.gui.calc.strategy.BinaryOperationListener;
import hr.fer.zemris.java.gui.calc.strategy.Calculator;
import hr.fer.zemris.java.gui.calc.strategy.ClrListener;
import hr.fer.zemris.java.gui.calc.strategy.DecimalDotListener;
import hr.fer.zemris.java.gui.calc.strategy.EqualsListener;
import hr.fer.zemris.java.gui.calc.strategy.MyCalculatorStrategy;
import hr.fer.zemris.java.gui.calc.strategy.NegateListener;
import hr.fer.zemris.java.gui.calc.strategy.NumberButtonListener;
import hr.fer.zemris.java.gui.calc.strategy.PopListener;
import hr.fer.zemris.java.gui.calc.strategy.PushListener;
import hr.fer.zemris.java.gui.calc.strategy.ResListener;
import hr.fer.zemris.java.gui.calc.strategy.UnaryOperationListener;
import org.junit.Assert;
import org.junit.Test;


public class TestMyCalculatorStrategy {
	
	@Test
	public void testNumberTyping(){
		Calculator calc=new Calculator();
		JLabel disp=new JLabel("0");
		MyCalculatorStrategy strat=new MyCalculatorStrategy(calc, disp);
		strat.execute(new NumberButtonListener(strat, "5"));
		
		Assert.assertTrue("Number on display should be 5!",disp.getText().equals("5"));
		
		strat.execute(new NumberButtonListener(strat, "7"));
		Assert.assertTrue("Number on display should be 57!",disp.getText().equals("57"));
		
		//postavljam zastavicu kao rezltat na true
		strat.setResult(true);
		
		strat.execute(new NumberButtonListener(strat, "9"));
		Assert.assertTrue("Number on display should be 9!",disp.getText().equals("9"));
		//sljedeći unos se ne smije nadovezivati na prethodni
	}
	
	@Test
	public void testDecimalDot(){
		Calculator calc=new Calculator();
		JLabel disp=new JLabel("2");
		MyCalculatorStrategy strat=new MyCalculatorStrategy(calc, disp);
		strat.execute(new DecimalDotListener(strat));
		Assert.assertTrue("Number on display should be 2.!",disp.getText().equals("2."));
		disp.setText("5.0");
		strat.execute(new DecimalDotListener(strat));
		Assert.assertTrue("Number on display should be 5.0!",disp.getText().equals("5.0"));
		strat.setResult(true);
		strat.execute(new DecimalDotListener(strat));
		Assert.assertTrue("Number on display should be 0.!",disp.getText().equals("0."));
	}
	
	@Test
	public void testNegation(){
		Calculator calc=new Calculator();
		JLabel disp=new JLabel("2");
		MyCalculatorStrategy strat=new MyCalculatorStrategy(calc, disp);
		strat.execute(new NegateListener(strat));
		
		Assert.assertTrue("Number on display should be -2!",disp.getText().equals("-2"));
		disp.setText("5.0");
		strat.execute(new NegateListener(strat));
	
		Assert.assertTrue("Number on display should be -5.0!",disp.getText().equals("-5.0"));
		strat.setResult(true);
		strat.execute(new NegateListener(strat));
		disp.setText("0.0");
		strat.execute(new NegateListener(strat));
		System.out.println(disp.getText());
		Assert.assertTrue("Number on display should be 0.0!",disp.getText().equals("0.0"));
	}
	
	@Test
	public void testBinaryOperation(){
		Calculator calc=new Calculator();
		JLabel disp=new JLabel("2");
		MyCalculatorStrategy strat=new MyCalculatorStrategy(calc, disp);
		strat.execute(new BinaryOperationListener(strat, "-"));
		strat.execute(new BinaryOperationListener(strat, "+"));
		//kliknut je minus zatim se korisnik predomislio i kliknuo +
		//na pomoćnom stogu mora biti 0 i +
		Assert.assertTrue("On stack should be number 2 and operator +!",strat.getStack().toString().equals("[2, +]"));
		//korisnik nakon plusa unosi pet
		disp.setText("5");
		strat.setLastClickOperator(false);//potrebno je ovo odsimulirati da test ne bi mislio da smo kliknuli + a zatim odmah minu
		//pa zatim minus
		strat.execute(new BinaryOperationListener(strat, "-"));
		//na stogu mora biti 7 i minus
		Assert.assertTrue("On stack should be number 7 and operator -!",strat.getStack().toString().equals("[7.0, -]"));
	}
	
	@Test
	public void testUnaryOperation(){
		Calculator calc=new Calculator();
		JLabel disp=new JLabel("2");
		MyCalculatorStrategy strat=new MyCalculatorStrategy(calc, disp);
		strat.execute(new UnaryOperationListener(strat, "sin"));
		System.out.println(disp.getText());
		Assert.assertEquals("Number sin(2) should be displayed!","0.9092974268256817",disp.getText());
		Assert.assertTrue("Stack should be empty",strat.getStack().isEmpty());
		disp.setText("50");
		strat.setInverted(true);//postavljamo na arcsin
		strat.execute(new UnaryOperationListener(strat, "sin"));
		Assert.assertTrue("Error message should be displayed!",disp.getText().equals("Invalid input"));
		
	}
	
	@Test
	public void testEqualsOperation(){
		Calculator calc=new Calculator();
		JLabel disp=new JLabel("2");
		MyCalculatorStrategy strat=new MyCalculatorStrategy(calc, disp);
		strat.execute(new BinaryOperationListener(strat, "*"));
		strat.execute(new EqualsListener(strat));
		Assert.assertTrue("Click on = should give 4.0",disp.getText().equals("4.0"));
		strat.execute(new BinaryOperationListener(strat, "*"));
		disp.setText("2.5");
		strat.execute(new BinaryOperationListener(strat, "*"));
		strat.execute(new EqualsListener(strat));
		System.out.println(disp.getText());
		Assert.assertTrue("Click on = should give 10.0",disp.getText().equals("10.0"));
		strat.execute(new UnaryOperationListener(strat, "log"));
		strat.execute(new EqualsListener(strat));
		System.out.println(disp.getText());
		Assert.assertTrue("Click on = should give 10.0",disp.getText().equals("1.0"));

	}
	
	@Test
	public void testPushPop(){
		Calculator calc=new Calculator();
		
		JLabel disp=new JLabel("2");
		MyCalculatorStrategy strat=new MyCalculatorStrategy(calc, disp);
		strat.execute(new PushListener(strat));
		disp.setText("Invalid input");
		strat.execute(new PushListener(strat));
		disp.setText("5");
		strat.execute(new PopListener(strat));
		Assert.assertTrue("2 should be displayed!",disp.getText().equals("2"));
	}
	
	@Test
	public void testClrRes(){
		Calculator calc=new Calculator();
		
		JLabel disp=new JLabel("2");
		MyCalculatorStrategy strat=new MyCalculatorStrategy(calc, disp);
		strat.execute(new ClrListener(strat));
		Assert.assertTrue("0 should be on display",disp.getText().equals("0"));
		strat.execute(new ResListener(strat));
	}
}

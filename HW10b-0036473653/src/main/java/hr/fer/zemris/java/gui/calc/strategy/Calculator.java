package hr.fer.zemris.java.gui.calc.strategy;

import hr.fer.zemris.java.gui.layouts.CalcLayout;


import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
/**
 * Razred koji predstavlja kalkulator.
 * Ovaj kalkulator podržava standardne operacije zbrajanja,dijeljenja,mnozenja i oduzimanja,
 * te trigonometrijske funkcije,prirodni logaritam, dekadski logaritam, potenciranje te njihove inverze.
 * Kalkulator ima pomoćne tipke koje sluze za brisanje trenutnog unosa, resetiranje ili baratanje s memorijom kalkulatora.
 * Ovaj kalkulator ne pazi na prioritete matematickih operacija vec sve racuna s lijeva na desno.
 * @author Petra Marče.
 *
 */
public class Calculator extends JFrame {
	private static final long serialVersionUID = 1L;
	
	/** korisnicki stog koji sluzi kao memorija kalkulatora **/
	protected Stack<String> userStack;
	/** izvrsitelj naredaba **/
	protected MyCalculatorStrategy strategy;

	public Calculator() {
	
		userStack = new Stack<>();
		initGUI();
		pack();
	}

	/**
	 * Pomoćna metoda koja inicijalizira grafičko korisnicko sučelje kalkulatora.
	 */
	private void initGUI() {
		
		
		this.setLocation(200, 100);
		this.setSize(300, 250);
		this.setTitle("Calculator");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLayout(new CalcLayout(3));

		JLabel type = new JLabel();
		type.setHorizontalAlignment(SwingConstants.RIGHT);
		add(type, "1,1");
		type.setText("0");
		type.setVisible(true);
		strategy = new MyCalculatorStrategy(this, type);

		JButton evaluate = new JButton("=");
		evaluate.addActionListener(new EqualsListener(strategy));
		add(evaluate, "1,6");
		JButton clr = new JButton("clr");
		clr.addActionListener(new ClrListener(strategy));
		add(clr, "1,7");
		JButton recip = new JButton("1/x");
		recip.addActionListener(new UnaryOperationListener(strategy, "1/x"));
		add(recip, "2,1");
		JButton sin = new JButton("sin");
		sin.addActionListener(new UnaryOperationListener(strategy, "sin"));
		add(sin, "2,2");
		JButton seven = new JButton("7");
		seven.addActionListener(new NumberButtonListener(strategy, "7"));
		add(seven, "2,3");
		JButton eight = new JButton("8");
		eight.addActionListener(new NumberButtonListener(strategy, "8"));

		add(eight, "2,4");
		JButton nine = new JButton("9");
		nine.addActionListener(new NumberButtonListener(strategy, "9"));
		add(nine, "2,5");
		JButton divide = new JButton("/");
		divide.addActionListener(new BinaryOperationListener(strategy, "/"));
		add(divide, "2,6");
		JButton res = new JButton("res");
		res.addActionListener(new ResListener(strategy));
		add(res, "2,7");

		JButton log = new JButton("log");
		log.addActionListener(new UnaryOperationListener(strategy, "log"));
		add(log, "3,1");
		JButton cos = new JButton("cos");
		cos.addActionListener(new UnaryOperationListener(strategy, "cos"));
		add(cos, "3,2");
		JButton four = new JButton("4");
		add(four, "3,3");
		four.addActionListener(new NumberButtonListener(strategy, "4"));
		JButton five = new JButton("5");
		five.addActionListener(new NumberButtonListener(strategy, "5"));
		add(five, "3,4");
		JButton six = new JButton("6");
		six.addActionListener(new NumberButtonListener(strategy, "6"));
		add(six, "3,5");
		JButton mul = new JButton("*");
		mul.addActionListener(new BinaryOperationListener(strategy, "*"));
		add(mul, "3,6");
		JButton push = new JButton("push");
		push.addActionListener(new PushListener(strategy));
		add(push, "3,7");

		JButton ln = new JButton("ln");
		ln.addActionListener(new UnaryOperationListener(strategy, "ln"));
		add(ln, "4,1");
		JButton tan = new JButton("tan");
		tan.addActionListener(new UnaryOperationListener(strategy, "tan"));
		add(tan, "4,2");
		JButton one = new JButton("1");
		one.addActionListener(new NumberButtonListener(strategy, "1"));
		add(one, "4,3");
		JButton two = new JButton("2");
		two.addActionListener(new NumberButtonListener(strategy, "2"));
		add(two, "4,4");
		JButton three = new JButton("3");
		three.addActionListener(new NumberButtonListener(strategy, "3"));
		add(three, "4,5");

		JButton minus = new JButton("-");
		minus.addActionListener(new BinaryOperationListener(strategy, "-"));
		add(minus, "4,6");
		JButton pop = new JButton("pop");
		pop.addActionListener(new PopListener(strategy));
		add(pop, "4,7");

		JButton potention = new JButton("x^n");
		potention.addActionListener(new BinaryOperationListener(strategy, "x^n"));
		add(potention, "5,1");
		JButton ctg = new JButton("ctg");
		ctg.addActionListener(new UnaryOperationListener(strategy, "ctg"));
		add(ctg, "5,2");
		JButton zero = new JButton("0");
		zero.addActionListener(new NumberButtonListener(strategy, "0"));
		add(zero, "5,3");
		JButton plusMinus = new JButton("+/-");
		plusMinus.addActionListener(new NegateListener(strategy));
		add(plusMinus, "5,4");
		JButton decimal = new JButton(".");
		decimal.addActionListener(new DecimalDotListener(strategy));
		add(decimal, "5,5");

		JButton plus = new JButton("+");
		plus.addActionListener(new BinaryOperationListener(strategy, "+"));
		add(plus, "5,6");
		final JCheckBox inv = new JCheckBox("Inv");
		inv.addActionListener(new InvListener(strategy));	
		add(inv, "5,7");

	}

	/**
	 * Metoda koja se poziva pri pokretanju programa.
	 * @param args argumenti iz komandne linije, ne koriste se.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Calculator().setVisible(true);
			}
		});

	}

}
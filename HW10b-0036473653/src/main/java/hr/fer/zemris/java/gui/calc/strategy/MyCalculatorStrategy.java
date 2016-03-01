package hr.fer.zemris.java.gui.calc.strategy;

import java.util.Stack;

import javax.swing.JLabel;

/**
 * Razred predstavlja konkretnu implementaciju izvrsitelja naredbi kalkulatora.
 * 
 * @author Petra Marče.
 * 
 */
public class MyCalculatorStrategy implements CalculatorStrategy {
	/** instanca kalkulatora **/
	private Calculator calc;
	/** display kalkulatora **/
	private JLabel display;
	/**
	 * pomoćna zastavica koja govori da li je zadnji prikaz rezultat neke
	 * operacije
	 **/
	private boolean isResult;
	/**
	 * pomoćna zastavica koja je true ako je neposredno prethodno kliknut neki
	 * od binarnih operatora
	 **/
	private boolean lastClickOperator;
	/** pomoćna zastavica koja je true ako je gumb Inv označen **/
	private boolean inverted;
	/** sredisnji pomocni stog za izvrsavanje operacija **/
	private Stack<String> stack;

	/**
	 * Konstruktor, prima referencu na kalkulator i display kojeg će koristiti.
	 * Sve zastavice postavlja na false, i inicijalizira pomoćni stog.
	 * 
	 * @param calc
	 *            kalkulator.
	 * @param display
	 *            display.
	 */
	public MyCalculatorStrategy(Calculator calc, JLabel display) {
		super();
		this.calc = calc;
		this.display = display;
		isResult = false;
		lastClickOperator = false;
		inverted = false;
		stack = new Stack<>();
	}

	/**
	 * Izvršavanje binarnih operacija. Izvršavanje zbrajanja, oduzimanja,
	 * dijeljenja, množenja i potenciranja.
	 */
	@Override
	public void execute(BinaryOperationListener listener) {
		if (lastClickOperator) {// ako se korisnik predomislja
			stack.pop();// ako se korisnik predomislja, tj neposredno prije je
						// vec odabrao operaciju
			// ignoriraj prijasnje i stavi ovu operaciju na stog
			stack.push(listener.name);

		} else {
			stack.push(display.getText());
			if (stack.size() == 3) {
				String result = ""
						+ calculate(stack.pop(), stack.pop(), stack.pop());
				stack.push(result);
				display.setText(result);
				stack.push(listener.name);
			} else {
				stack.push(listener.name);
			}
			lastClickOperator = true;
			isResult = true;

		}
//		System.out.println(stack);
	}

	/**
	 * Izvršavanje Clr naredbe. Briše samo trenutni unos ali ne i memoriju
	 * kalkulatora.
	 */
	@Override
	public void execute(ClrListener listener) {
		display.setText("0");
		lastClickOperator = false;
//		System.out.println(stack);
	}

	/**
	 * Reakcija na klik decimalne točke. Dodaje decimalnu točku na kraj samo ako
	 * ju broj već ne sadrži.
	 */
	@Override
	public void execute(DecimalDotListener listener) {
		if (isResult == false) {

			String temp = display.getText();
			if (temp.contains(".")) {
				return;
			}
			display.setText(display.getText().concat("."));

		} else {
			isResult = false;
			display.setText("0.");
		}
		lastClickOperator = false;
//		System.out.println(stack);
	}

	/**
	 * Izvođenje operacije jednakosti.
	 */
	@Override
	public void execute(EqualsListener eq) {
		if (display.getText().equals("Stack is empty!")
				|| display.getText().equals("Invalid input")) {
			return;
		}
		String result = "";
		stack.push(display.getText());
		if (stack.size() == 1) {
			result = "" + stack.pop();
		} else {
			if (stack.size() == 3) {
				result = "" + calculate(stack.pop(), stack.pop(), stack.pop());
			}
		}

		if (result.isEmpty() == false) {
			Double d = Double.parseDouble(result) * 1.0;
			result = d.toString();
			display.setText(result);
		}

		lastClickOperator = false;
		isResult = true;
//		System.out.println(stack);
	}

	/**
	 * Unos brojki. Ako je prethodno na displayu bio prikazan nekakav rezultat
	 * prethodne operacije, taj rezultat se brise.
	 */
	@Override
	public void execute(NumberButtonListener listener) {
		String name = listener.name;
		if (isResult || display.getText().equals("0")) {
			display.setText(name);
			isResult = false;

		} else {
			display.setText(display.getText().concat(name));
		}
		lastClickOperator = false;
//		System.out.println(stack);

	}

	/**
	 * Izvođenje naredbe push. Ta naredba stavlja broj trenutno prikazan na
	 * displayu na vrh korisnickog stoga.
	 */
	@Override
	public void execute(PushListener push) {
		if (display.getText().equals("Invalid input")
				|| display.getText().equals("Stack is empty!")) {
			return;
		}
		calc.userStack.push(display.getText());
		lastClickOperator = false;
		isResult = true;
//		System.out.print(stack);
//		System.out.println(calc.userStack);

	}

	/**
	 * Izvođenje naredbe pop. Ta naredba stavlja na display broj sa vrha stoga,
	 * ili poruku da je stog prazan.
	 */
	@Override
	public void execute(PopListener p) {

		if (calc.userStack.isEmpty()) {
			display.setText("Stack is empty!");
		} else {
			String peek = calc.userStack.pop();
			display.setText(peek);
		}
		isResult = true;// //???
		lastClickOperator = false;
//		System.out.print(stack);
//		System.out.println(calc.userStack);
	}

	/**
	 * Reakcija na klik inverted. Invertira zastavicu isInverted.
	 */
	public void execute(InvListener listener) {
		inverted = !inverted;
	}

	/**
	 * Resetiranje kalkulatora. Briše memoriju i display postavlja na nulu.
	 */
	@Override
	public void execute(ResListener res) {
		stack.clear();
		calc.userStack.clear();
		isResult = false;
		lastClickOperator = false;
		display.setText("0");
//		System.out.println(stack);

	}

	/**
	 * Izvođenje operacija 1/x, kosinus, sinus, tangens, kotangens, logaritam,
	 * prirodni logaritam te njihovih inverza.
	 */
	@Override
	public void execute(UnaryOperationListener un) {
		String name = un.name;
		String temp = display.getText();
		String result = "" + evaluate(name, temp, inverted);
		// stack.push(result);
		if (result.isEmpty() == false) {
			display.setText(result);
		}
		isResult = true;
		lastClickOperator = false;
//		System.out.println(stack);
	}

	/**
	 * Izvođenje operacije negiranja. Množi trenutno prikazani broj s -1 i
	 * prikazuje ga.
	 */
	@Override
	public void execute(NegateListener lis) {
		if (display.getText().equals("0.0")) {
			return;
		}
		boolean haveInt = false;
		try {
			Integer n = -1 * Integer.parseInt(display.getText());
			display.setText(n.toString());
			haveInt = true;
		} catch (NumberFormatException n) {
		}
		if (haveInt == false) {
			Double num = Double.parseDouble(display.getText());
			num *= -1;
			display.setText(num.toString());
		}

		isResult = false;
		lastClickOperator = false;
//		System.out.println(stack);
	}

	/**
	 * Pomoćna metoda koja izvodi neku matematicku binarnu operaciju i vraća
	 * strignovnu reprezentaciju rezultata. Podrzane operacije su
	 * zbrajanje,oduzimanje, mnozenje,dijeljenje i potenciranje.
	 * 
	 * @param drugi
	 *            drugi operand.
	 * @param op
	 *            oznaka za operaciju.
	 * @param prvi
	 *            prvi operand
	 * @return vraća strignovnu reprezentaciju rezultata ili prazan string ako
	 *         je doslo do greske.
	 */

	public String calculate(String drugi, String op, String prvi) {
		Double p = Double.parseDouble(prvi);
		Double d = Double.parseDouble(drugi);
		switch (op) {
		case "+":
			return "" + (p + d);
		case "-":
			return "" + (p - d);
		case "*":
			return "" + (p * d);
		case "/":
			if (d == 0) {

				display.setText("Invalid input");
				return "";

			}
			return "" + (p / d);
		case "x^n":
			if (inverted) {
				if (p < 0) {
					display.setText("Invalid input");
					return "";
				}
				return "" + Math.pow(p, 1 / d);
			}
			return "" + Math.pow(p, d);
		}
		return "";
	}

	/**
	 * Pomoćna metoda koja vraća rezultat neke matematičke operacije u obliku
	 * stringa. Podržane su operacije: 1/x, kosinus, sinus, tangens, kotangens,
	 * logaritam, prirodni logaritam te njihovi inverzi.
	 * 
	 * @param op
	 *            operator, oznaka željene funkcije.
	 * @param prvi
	 *            argument funkcije.
	 * @param inv
	 *            zastavica koja ako je true, označava da želimo inverz navedene
	 *            funkcije.
	 * @return stringovna reprezentacija rezultata funkcije ili prazan string
	 *         ako ulaz u funkciju nije valjan.
	 */
	public String evaluate(String op, String prvi, boolean inv) {
		Double arg = Double.parseDouble(prvi);
		if (inv) {
			switch (op) {
			case "1/x":

				return "" + (1 / arg);
			case "log":

				return "" + Math.pow(10, arg);
			case "ln":
				return "" + Math.exp(arg);
			case "sin":
				if (arg < -1 || arg > 1) {
					display.setText("Invalid input");
					return "";
				}
				return "" + Math.asin(arg);
			case "cos":
				if (arg < -1 || arg > 1) {
					display.setText("Invalid input");
					return "";
				}
				return "" + Math.acos(arg);
			case "tan":
				return "" + Math.atan(arg);
			case "ctg":
				return "" + Math.atan(1 / arg);
			}
		} else {
			switch (op) {
			case "1/x":
				if (arg == 0) {
					display.setText("Invalid input");
					return "";
				}
				return "" + (1 / arg);
			case "log":
				if (arg <= 0) {
					display.setText("Invalid input");
					return "";
				}
				return "" + Math.log10(arg);
			case "ln":
				if (arg <= 0) {
					display.setText("Invalid input");
					return "";
				}
				return "" + Math.log(arg);
			case "sin":
				return "" + Math.sin(arg);
			case "cos":
				return "" + Math.cos(arg);
			case "tan":
				return "" + Math.tan(arg);
			case "ctg":
				return "" + 1 / Math.tan(arg);

			}
		}
		return "";
	}

	// ovi geteri i seteri su samo pomoć testiranju.

	/**
	 * Metoda za postavljanje zastavice lastClickOperator
	 * 
	 * @param lastClickOperator
	 */
	public void setLastClickOperator(boolean lastClickOperator) {
		this.lastClickOperator = lastClickOperator;
	}

	/**
	 * /** Metoda za postavljanje zastavice isResult
	 * 
	 * @param isResult
	 */
	public void setResult(boolean isResult) {
		this.isResult = isResult;
	}

	/**
	 * /** Metoda za postavljanje zastavice isInverted
	 * 
	 * @param isInverted
	 */
	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}

	/**
	 * Metoda za dohvat stoga
	 * 
	 * @return
	 */
	public Stack<String> getStack() {
		return stack;
	}

}

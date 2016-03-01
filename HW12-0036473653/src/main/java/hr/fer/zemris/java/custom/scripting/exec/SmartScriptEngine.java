package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;
import hr.fer.zemris.java.webserver.RequestContext;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
/**
 * Razred koji predstavlja izvoditelja isparsirane smart-skripte.
 * @author Petra Marče
 *
 */
public class SmartScriptEngine {
	private DocumentNode documentNode;
	private RequestContext requestContext;
	private ObjectMultistack multistack;

	/**
	 * Implementacija Visitora čvorova koja izvodi operacije.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		/**
		 * Izvođenje tekstualnog čvora svodi se na njegov ispis u stream instance razreda RequestContext.
		 */
		@Override
		public void visitTextNode(TextNode node) {
			requestContext.write(node.getText());

		}

		/**
		 * Izvođenje for-noda se svodi na višestruko izvođenje djece čvora.
		 * 
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String varijabla = node.getVariable().asText();
			multistack.push(varijabla, new ValueWrapper(node
					.getStartExpression().asText()));
			ValueWrapper end = new ValueWrapper(node.endExpression.asText());
			// System.out.println(node.endExpression.asText());
			ValueWrapper step;
			if (node.getStepExpression() == null) {
				step = new ValueWrapper(1);
			} else {
				step = new ValueWrapper(node.getStepExpression().asText());
			}
			ValueWrapper temp = multistack.peek(varijabla);

			while (temp.numCompare(end) <= 0.0) {
				for (int i = 0; i < node.numberOfChildren(); i++) {// jedan
																	// obilazak
																	// djece
					node.getChild(i).accept(this);
				}

				multistack.peek(varijabla).increment(step);// increment mjenja
															// direktno

			}
			multistack.pop(varijabla);// gotovi smo mičemo varijablu sa stoga

		}

		/**
		 * Izvođenje čvora pridruživanja ispisuje konačne rezultate u outputStream.
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			ObjectMultistack tempStack = new ObjectMultistack();
			String name = "temp";
			for (Token token : node.getTokens()) {
				if (token instanceof TokenConstantDouble
						|| token instanceof TokenConstantInteger) {
					tempStack.push(name, new ValueWrapper(token.asText()));
				} else {
					if (token instanceof TokenString) {
						tempStack.push(name, new ValueWrapper(
								((TokenString) token).getValue()));
					} else {
						if (token instanceof TokenVariable) {
							ValueWrapper value = multistack
									.peek(token.asText());
							tempStack.push(name, value);
						} else {
							if (token instanceof TokenOperator) {
								ValueWrapper drugi = tempStack.pop(name);
								ValueWrapper prvi = tempStack.pop(name);
								ValueWrapper rez = new ValueWrapper(
										prvi.aritmetic(drugi, token.asText()
												.charAt(0)));// rezultat

								tempStack.push(name, rez);
							} else {
								tokenFunctionProcess(tempStack, name,
										(TokenFunction) token);
							}
						}
					}
				}

			}

			if (!tempStack.isEmpty(name)) {
				List<ValueWrapper> pom = new ArrayList<>();
				do {
					pom.add(tempStack.pop(name));
				} while (!tempStack.isEmpty(name));
				for (int i = pom.size() - 1; i >= 0; i--) {
					requestContext.write(pom.get(i).getValue().toString().getBytes(Charset.forName("UTF-8")));
				}
			}

		}

		/**
		 * Izvođenje čvora documentNode svodi se na izvođenje njegove djece.
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}

		}

	};

	/**
	 * Konstruktor.
	 * Potrebno je kao argument predati isparsirano stablo skripte i instancu razreda RequestContext.
	 * @param documentNode stablo čvorova
	 * @param requestContext  instanca razeda RequestContext.
	 */
	public SmartScriptEngine(DocumentNode documentNode,
			RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
		this.multistack = new ObjectMultistack();

	}

	/**
	 * Metoda čijim pozivom započinje izvođenje skripte.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

	/**
	 * Pomoćna metoda koja radi procesiranje pojedinih funkcija skripte.
	 * @param stack stog sa kojeg treba skidat i stavljati podatke.
	 * @param name ime stoga.
	 * @param fun ime funkcije koja se treba izvesti;
	 */
	public void tokenFunctionProcess(ObjectMultistack stack, String name,
			TokenFunction fun) {
		ValueWrapper value;
		switch (fun.getName()) {
		case "sin":
			value = stack.pop(name);

			value.multiply(new ValueWrapper(1));

			Double arg;
			if (value.getValue() instanceof Integer) {
				arg = 1.0 * (Integer) value.getValue();
			} else {
				arg = (Double) value.getValue();
			}

			stack.push(name, new ValueWrapper(Math.sin(arg)));
			return;
		case "decfmt":

			ValueWrapper format = stack.pop(name);
			if (!(format.getValue() instanceof String)) {

			}
			DecimalFormat f = new DecimalFormat((String) format.getValue());

			value = stack.pop(name);

			value.multiply(new ValueWrapper(1));

			String formatirana = (f.format(value.getValue()));
			stack.push(name, new ValueWrapper(formatirana));
			return;
		case "dup":
			// stack.push(name, stack.peek(name));
			ValueWrapper v = stack.pop(name);
			// System.out.println(" vrijednost i stavljam na vrh dvija "+v.getValue());
			stack.push(name, v);
			stack.push(name, v);
			return;
		case "swap":
			ValueWrapper p = stack.pop(name);
			ValueWrapper d = stack.pop(name);
			stack.push(name, p);
			stack.push(name, d);
			return;
		case "setMimeType":
			value = stack.pop(name);
			if (value.getValue() instanceof String) {

				requestContext.setMimeType((String) value.getValue());
			} else {
				throw new IllegalArgumentException(
						"Argument of function @setMimeType must be String!");
			}
			return;

		case "paramGet":
			 new AbstractParameterGet(stack,
					name) {

				@Override
				protected String requiredString() {
					if (requestContext.getParameterNames().contains(
							this.getKeyName())) {
						return requestContext.getParameter(this.getKeyName());
					}
					return null;
				}

				@Override
				protected String functionName() {
					return "paramGet";
				}
			};

			return;
		case "pparamGet":
		 new AbstractParameterGet(stack,
					name) {

				@Override
				protected String requiredString() {
					if (requestContext.getPersistentParameterNames().contains(
							this.getKeyName())) {
						return requestContext.getPersistentParameter(this
								.getKeyName());
					}
					return null;
				}

				@Override
				protected String functionName() {
					return "pparamGet";
				}
			};

			return;
		case "tparamGet":
			new AbstractParameterGet(stack,
					name) {

				@Override
				protected String requiredString() {
					if (requestContext.getTemporaryParameterNames().contains(
							this.getKeyName())) {
						return requestContext.getTemporaryParameter(this
								.getKeyName());
					}
					return null;
				}

				@Override
				protected String functionName() {
					return "tparamGet";
				}

			};
			// System.out.println(requestContext.getTemporaryParameterNames());
			return;
		case "pparamSet":
		new AbstractParameterSet(stack,
					name) {

				@Override
				protected void set() {
					requestContext.setPersistentParameter(getKeyName(),
							getValue());

				}

				@Override
				protected String functionName() {
					return "pparamSet";
				}
			};
			return;

		case "tparamSet":
			new AbstractParameterSet(stack,
					name) {

				@Override
				protected void set() {
					requestContext.setTemporaryParameter(getKeyName(),
							getValue());

				}

				@Override
				protected String functionName() {
					return "tparamSet";
				}
			};

			// System.out.println("Sadržaj mape temp: "+requestContext.getTemporaryParameters());
			return;

		case "pparamDel":
			 new AbstractParameterDel(stack,
					name) {

				@Override
				protected void del() {
					requestContext.removePersistentParameter(getKeyName());

				}

				@Override
				protected String functionName() {
					return "pparamDel";
				}
			};
			return;
		case "tparamDel":
			new AbstractParameterDel(stack,
					name) {

				@Override
				protected void del() {
					requestContext.removeTemporaryParameter(getKeyName());

				}

				@Override
				protected String functionName() {
					return "tparamDel";
				}
			};
			return;
		}
	}

}

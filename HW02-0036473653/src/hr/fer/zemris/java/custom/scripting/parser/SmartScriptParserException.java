package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Razred koji simbolizira pogresku pri parsiranju
 * 
 * @author Petra Marče
 * 
 */
public class SmartScriptParserException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SmartScriptParserException() {
		super();

	}

	public SmartScriptParserException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);

	}

	public SmartScriptParserException(String message) {
		super(message);

	}

	public SmartScriptParserException(Throwable cause) {
		super(cause);

	}

}
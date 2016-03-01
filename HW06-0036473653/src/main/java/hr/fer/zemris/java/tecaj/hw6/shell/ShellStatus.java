package hr.fer.zemris.java.tecaj.hw6.shell;

/**
 * Enum koji predstavlja status komandne linije.
 * Taj će status biti tip kojeg će vraćati sve komande ljuske.
 * Status CONTINUE ljuska će tumačiti kao nastavak rada, a TERMINATE kao kraj.
 * @author petra
 *
 */
public enum ShellStatus {
	CONTINUE, TERMINATE;
}

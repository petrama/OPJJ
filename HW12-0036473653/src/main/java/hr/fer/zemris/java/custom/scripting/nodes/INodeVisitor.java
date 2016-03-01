package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Sučelje koje predstavlja apstraktnog posjetitelja nodeova.
 * 
 * @author Petra Marče
 * 
 */
public interface INodeVisitor {
	/**
	 * Metoda koja predstavlja obilazak tekstualnog čvora.
	 * @param node čvor koji se obilazi.
	 */
	public void visitTextNode(TextNode node);
	/**
	 * Metoda koja predstavlja obilazak  čvora for petlje.
	 * @param node čvor koji se obilazi.
	 */
	public void visitForLoopNode(ForLoopNode node);
	/**
	 * Metoda koja predstavlja obilazak čvora jednakosti.
	 * @param node čvor koji se obilazi.
	 */
	public void visitEchoNode(EchoNode node);
	/**
	 * Metoda koja predstavlja obilazak čvora koji predstavlja dokument.
	 * Obilaženje će se svesti na obilazak njegove djece.
	 * @param node čvor koji se obilazi.
	 */
	public void visitDocumentNode(DocumentNode node);
}
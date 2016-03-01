package hr.fer.zemris.java.webserver;
/**
 * Sučelje predstavlja radnika koji obavlja neki posao.
 * Radnik vraća neki odgovor klijentu servera SmartHttpServer.
 * @author Petra Marče.
 *
 */
public interface IWebWorker {
	/**
	 * Na poziv ove metode worker odrađuje konkretan posao preko parametra context čiju metodu write koristi.
	 * @param context parametar koji izgenereira ispravan odgovor oblika zaglavlje-sadržaj.
	 */
	public void processRequest(RequestContext context);

}

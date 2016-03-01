package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
/**
 * Radnik koji na zahtjev klijenta kao odgovor vraća ispis parametara koje je korisnik predao u zahtjevu.
 * @author Petra Marče.
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		for (String param : context.getParameterNames()) {
			context.write(param + "\r\n");

		}

	}

}

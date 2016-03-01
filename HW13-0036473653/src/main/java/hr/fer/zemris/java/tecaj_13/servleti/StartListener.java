package hr.fer.zemris.java.tecaj_13.servleti;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * Listener koji bilježi trenutak inicijalizacije aplikacije.
 * Kada se aplikacija inicijalizira trenutno vrijeme postavlja se kao servetContext parametar pod ključem 'start'.
 * @author Petra Marče
 *
 */
public class StartListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		arg0.getServletContext().setAttribute("start", System.currentTimeMillis());

	}

}

package hr.fer.zemris.java.tecaj_11.local.swing;

import hr.fer.zemris.java.tecaj_11.local.FormLocalizationProvider;
import hr.fer.zemris.java.tecaj_11.local.LocalizationProvider;

import javax.swing.JFrame;

/**
 * Razred koji i18n verziju prozora.
 * S promjenom jezika mjenja se izgled prozora.
 * @author Petra Marče
 * 
 */
public class SomeFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8616599481282850063L;
	public FormLocalizationProvider flp;

	public SomeFrame() {
		super();
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(),
				this);
	}
}

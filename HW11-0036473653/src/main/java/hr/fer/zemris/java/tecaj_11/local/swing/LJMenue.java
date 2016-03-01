package hr.fer.zemris.java.tecaj_11.local.swing;

import hr.fer.zemris.java.tecaj_11.local.ILocalizationListener;
import hr.fer.zemris.java.tecaj_11.local.ILocalizationProvider;

import javax.swing.JMenu;
/**
 * Razred koji predstavlja i18n verziju menua.
 * Sa promjenom jezika mjenja se i izgled menua.
 * @author Petra Marče
 *
 */
public class LJMenue extends JMenu {
	
	private static final long serialVersionUID = 1L;

	public LJMenue(String key,ILocalizationProvider provider){
		super();
		this.setText(provider.getString(key));
		final String kljuc=key;
		final ILocalizationProvider pro=provider;
		provider.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				LJMenue.this.setText(pro.getString(kljuc));
				
			}
		});
}
}

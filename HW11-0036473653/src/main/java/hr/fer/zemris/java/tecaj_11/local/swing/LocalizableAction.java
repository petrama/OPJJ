package hr.fer.zemris.java.tecaj_11.local.swing;

import java.io.UnsupportedEncodingException;

import hr.fer.zemris.java.tecaj_11.local.ILocalizationListener;
import hr.fer.zemris.java.tecaj_11.local.ILocalizationProvider;

import javax.swing.AbstractAction;

/**
 * Razred koji predstavlja i18n verziju akcije. Sa promjenom jezika mjenja se
 * naziv i opis akcije.
 * 
 * @author Petra Marče
 * 
 */
public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 5456359407499415292L;

	public LocalizableAction(String key, String desc,
			ILocalizationProvider provider) {
		super();

		String value = provider.getString(key);

		try {
			value = new String(key.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {

		}

		AbstractAction ac = this;
		ac.putValue(NAME, value);
		System.out.println(desc);

		String valueDesc = provider.getString(desc);

		try {
			valueDesc = new String(valueDesc.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {

		}

		ac.putValue(SHORT_DESCRIPTION, valueDesc);
		final String kljuc = key;
		final String decr = desc;
		final ILocalizationProvider pro = provider;
		provider.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				String v = pro.getString(kljuc);
				try {
					v = new String(v.getBytes("ISO-8859-1"), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LocalizableAction.this.putValue(NAME, v);

				String vD = pro.getString(decr);
				try {
					vD = new String(v.getBytes("ISO-8859-1"), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LocalizableAction.this.putValue(SHORT_DESCRIPTION, vD);

			}
		});

	}

}

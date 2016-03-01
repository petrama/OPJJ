package hr.fer.zemris.java.tecaj_11.local;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Razred koji predstavlja konkretnu implementaciju providera koji omogućava internacionalizaciju.
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	private ResourceBundle boundle;

	private static LocalizationProvider instance = null;

	private LocalizationProvider() {

	}

	public static LocalizationProvider getInstance() {
		if (instance == null) {
			instance = new LocalizationProvider();
		}
		return instance;
	}

	@Override
	public String getString(String key) {
		return boundle.getString(key);
	}

	public void setLanguage(String language) {
		this.boundle = ResourceBundle.getBundle(
				"hr.fer.zemris.java.tecaj_11.notepad.prijevodi",
				Locale.forLanguageTag(language));
		fire();
	
	}

}

package hr.fer.zemris.java.tecaj_11.local;
/**
 * Razred koji predstavlja most prema konkretnom provideru.
 * @author Petra Marče
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	private boolean connected;
	ILocalizationProvider provider;
	private ILocalizationListener listener;

	public LocalizationProviderBridge(ILocalizationProvider provider) {
		super();
		this.provider = provider;
		connected = false;
	}

	/**
	 * Metoda kojom se most spaja na konkretan provider.
	 */
	public void connect() {
		if (connected)
			return;
		ILocalizationListener listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				fire();

			}
		};
		connected = true;
		provider.addLocalizationListener(listener);

	}
	/**
	 * Metoda kojoj se most odspaja od providera.
	 * Odspaja se kada se prozor zatvori.
	 */

	public void disconnect() {
		if (connected) {
			provider.removeLocalizationListener(listener);
		}
	}

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		provider.addLocalizationListener(listener);

	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		provider.removeLocalizationListener(listener);

	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}

}

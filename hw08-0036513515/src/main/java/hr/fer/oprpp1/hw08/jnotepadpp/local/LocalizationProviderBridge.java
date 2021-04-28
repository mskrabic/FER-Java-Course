package hr.fer.oprpp1.hw08.jnotepadpp.local;

/*First step is to add a new class: LocalizationProviderBridge which is a decorator for some other
IlocalizationProvider. This class offers two additional methods: connect() and disconnect(), and it
manages a connection status (so that you can not connect if you are already connected). Here is the idea: this
class is ILocalizationProvider which, when asked to resolve a key delegates this request to wrapped
(decorated) ILocalizationProvider object. When user calls connect() on it, the method will register an
instance of anonimous ILocalizationListener on the decorated object. When user calls disconnect(),
this object will be deregistered from decorated object.*/

/**
 * Razred koji je dekorator za neki drugi {@link ILocalizationProvider}. Dodaje mu metode za upravljanje konekcijom.
 * 
 * @author mskrabic
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Status konekcije.
	 */
	private boolean connected;
	
	/**
	 * {@link ILocalizationProvider} koji se interno koristi za lokalizaciju.
	 */
	ILocalizationProvider provider;
	
	/**
	 * Konstruktor.
	 * 
	 * @param provider provider lokalizacije.
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
	}

	@Override
	public String getString(String key) {
		
		return provider.getString(key);
	}
	
	/**
	 * Metoda za spajanje na providera.
	 */
	public void connect() {
		if (connected)
			throw new IllegalStateException("Already connected!");
		
		connected = true;
		for (ILocalizationListener l : this.listeners)
			provider.addLocalizationListener(l);
		
	}
	
	/**
	 * Metoda za odspajanje s providera.
	 */
	public void disconnect() {
		if (!connected)
			throw new IllegalStateException("Not connected!");
		
		connected = false;
		for (ILocalizationListener l : this.listeners)
			provider.removeLocalizationListener(l);
		
	}
	
	@Override
	public String getCurrentLanguage() {
		return provider.getCurrentLanguage();
	}
	
}

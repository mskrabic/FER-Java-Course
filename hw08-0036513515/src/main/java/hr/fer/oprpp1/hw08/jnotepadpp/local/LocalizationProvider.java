package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Razred koji nasljeđuje {@link AbstractLocalizationProvider} i implementira oblikovni obrazac Singleton.
 * @author mskrabic
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Instanca ovog razreda.
	 */
	private static LocalizationProvider provider = new LocalizationProvider();
	/**
	 * Trenutni jezik.
	 */
	private String language;
	
	/**
	 * Prijevodi za trenutni jezik.
	 */
	private ResourceBundle bundle;
	
	/**
	 * Privatni konstruktor koji osigurava da se koristi samo jedna instanca razreda.
	 * Postavlja jezik na engleski.
	 */
	private LocalizationProvider() {
		this.language = "en";
		this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", Locale.forLanguageTag(language));
	};
	
	/**
	 * Metoda za dohvaćanje instance razreda.
	 * 
	 * @return instanca razreda.
	 */
	public static LocalizationProvider getInstance() {
		return provider;
	}
	
	/**
	 * Metoda za postavljanje jezika.
	 * 
	 * @param language jezik koji se želi postaviti.
	 */
	public void setLanguage(String language) {
		this.language = language;
		this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", Locale.forLanguageTag(language));
		fire();
	}
	
	public String getString(String key) {
		return bundle.getString(key);
	}	
	
	@Override
	public String getCurrentLanguage() {
		return language;
	}
	
}

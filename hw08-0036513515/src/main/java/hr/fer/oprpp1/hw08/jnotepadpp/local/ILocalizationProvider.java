package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Sučelje koje pruža funkcionalnost lokalizacije.
 * 
 * @author mskrabic
 *
 */
public interface ILocalizationProvider {

	/**
	 * Metoda za dodavanje promatrača.
	 * 
	 * @param l novi promatrač.
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Metoda za uklanjanje promatrača.
	 * 
	 * @param l promatrač kojeg se želi ukloniti.
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * Metoda za dohvat trenutnog jezika.
	 * 
	 * @return trenutni jezik.
	 */
	String getCurrentLanguage();
	
	/**
	 * Metoda za dohvat prijevoda za predani ključ.
	 * 
	 * @param key ključ.
	 * @return prijevod.
	 */
	String getString(String key);
}

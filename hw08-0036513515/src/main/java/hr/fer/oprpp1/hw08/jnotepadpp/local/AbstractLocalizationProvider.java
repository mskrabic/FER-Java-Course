package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred implementira sučelje {@link ILocalizationProvider} i dodaje mu mogućnost dodavanja, uklanjanja i informiranja promatrača.
 * Ovo je apstraktni razred - ne implementira getString(...) metodu.
 * 
 * @author mskrabic
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{
	
	/**
	 * Promatrači.
	 */
	List<ILocalizationListener> listeners = new ArrayList<>();
	
	/**
	 * Konstruktor.
	 */
	public AbstractLocalizationProvider() {}
	

	/**
	 * Metoda za informiranje promatrača o promjeni.
	 */
	public void fire() {
		for (ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}

	/**
	 * Metoda za dodavanje promatrača.
	 */
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	/**
	 * Metoda za uklanjanje promatrača.
	 */
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);	
	}

}

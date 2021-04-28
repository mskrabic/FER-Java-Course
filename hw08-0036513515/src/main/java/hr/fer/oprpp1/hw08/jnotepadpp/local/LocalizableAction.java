package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.AbstractAction;

/**
 * Razred nasljeđuje {@link AbstractAction} i dodaje mogućnost lokalizacije.
 * 
 * @author mskrabic
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * Serijski broj.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 * 
	 * @param key ključ akcije koji se koristi pri lokalizaciji.
	 * 
	 * @param lp {@link ILocalizationProvider} koji se koristi za lokalizaciju.
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {		
		this.putValue(NAME, lp.getString(key));
		
		lp.addLocalizationListener(new ILocalizationListener() {		
			@Override
			public void localizationChanged() {
				Object oldValue = LocalizableAction.this.getValue(NAME);
				Object newValue = lp.getString(key);
				LocalizableAction.this.putValue(NAME, newValue);
				LocalizableAction.this.firePropertyChange(NAME, oldValue, newValue);
			}
		});	
	}

}

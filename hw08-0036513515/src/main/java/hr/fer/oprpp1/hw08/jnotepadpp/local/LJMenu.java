package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * Razred nasljeđuje {@link JMenu} i dodaje mu potporu za lokalizaciju.
 * 
 * @author mskrabic
 */
public class LJMenu extends JMenu {
	
	/**
	 * Serijski broj.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 * 
	 * @param key ključ izbornika koji se koristi pri lokalizaciji.
	 * 
	 * @param lp {@link ILocalizationProvider} koji se koristi za lokalizaciju.
	 */
	public LJMenu(String key, ILocalizationProvider lp) {
		super(lp.getString(key));
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				LJMenu.this.setText(lp.getString(key));
			}
		});
	}

}

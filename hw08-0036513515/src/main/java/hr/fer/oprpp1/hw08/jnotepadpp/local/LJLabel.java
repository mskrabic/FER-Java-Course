package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JLabel;

/**
 * Razred nasljeđuje {@link JLabel} i dodaje joj potporu za lokalizaciju.
 * 
 * @author mskrabic
 */
public class LJLabel extends JLabel {

	/**
	 * Serijski broj.
	 */
	private static final long serialVersionUID = 1L;	
	
	/**
	 * Konstruktor.
	 * 
	 * @param key ključ labele koji se koristi pri lokalizaciji.
	 * 
	 * @param lp {@link ILocalizationProvider} koji se koristi za lokalizaciju.
	 */
	public LJLabel(String key, ILocalizationProvider lp) {
		super(lp.getString(key));
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				LJLabel.this.setText(lp.getString(key));
			}
		});
	}

}

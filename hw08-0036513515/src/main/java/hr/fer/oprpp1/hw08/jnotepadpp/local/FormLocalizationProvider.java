package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * Razred izveden iz {@link LocalizationProviderBridge}; u konstruktoru se prijavljuje kao {@link WindowListener} na prozor.
 * Kada je prozor otvoren, poziva se metoda <code>connect()</code>, a kada je prozor zatvoren poziva se metoda <code>disconnect()</code>.
 * 
 * @param provider {@link ILocalizationProvider} kojeg razred interno koristi za lokalizaciju.
 * @param frame prozor.
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				FormLocalizationProvider.this.connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				FormLocalizationProvider.this.disconnect();
			}
		});
	}

}

package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Apstraktni razred koji modelira gumb kalkulatora {@link Calculator} koji obavlja neku matematičku operaciju.
 * 
 * @author mskrabic
 *
 */
public abstract class OperationButton extends JButton {
	
	/**
	 * Tekst gumba.
	 */
	protected String text;
	
	/**
	 * Tekst gumba kada je invertiran.
	 */
	protected String invText;
	
	/**
	 * Zastavica koja određuje je li gumb invertiran.
	 */
	protected boolean inverted;
	
	/**
	 * Promatrač za operaciju gumba.
	 */
	protected ActionListener listener;
	
	/**
	 * Promatrač za invertiranu operaciju gumba.
	 */
	protected ActionListener invListener;
	
	/**
	 * Model kalkulatora.
	 */
	protected CalcModel model;
	
	
	/**
	 * Serijski broj.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Metoda koja invertira gumb.
	 */
	public void invert() {
		this.inverted = !this.inverted;
		
		if (inverted) {
			this.removeActionListener(listener);
			this.addActionListener(invListener);
			this.setText(invText);
		} else {
			this.removeActionListener(invListener);
			this.addActionListener(listener);
			this.setText(text);
		}
	};
}

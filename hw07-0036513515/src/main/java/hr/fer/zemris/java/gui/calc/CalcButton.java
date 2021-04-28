package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionListener;
import java.util.EmptyStackException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;


/**
 * Razred predstavlja implementaciju gumba na kalkulatoru {@link Calculator}.
 * 
 * @author mskrabic
 */
public class CalcButton extends JButton {

	/**
	 * Serijski broj.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor koji inicijalizira gumb.
	 */
	public CalcButton(String text, ActionListener operation) {
		this.setText(text);
		addActionListener(e -> {
			try {
				operation.actionPerformed(e);
			} catch (CalculatorInputException err) {
				JOptionPane.showMessageDialog(this.getParent(), "Nedozvoljen unos u kalkulator!", "Greška", JOptionPane.WARNING_MESSAGE);
			} catch (EmptyStackException err) {
				JOptionPane.showMessageDialog(this.getParent(), "Stog je prazan!", "Greška", JOptionPane.WARNING_MESSAGE);
			}
		});
	}
}

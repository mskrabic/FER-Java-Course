package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionListener;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Razred predstavlja model gumba kalkulatora {@link Calculator} koji obavlja unarnu operaciju.
 * 
 * @author mskrabic
 *
 */
public class UnaryOperationButton extends OperationButton {

	/**
	 * Serijski broj
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 * 
	 * @param text tekst gumba.
	 * @param invText tekst gumba kada je invertiran.
	 * @param operation operacija koju gumb provodi.
	 * @param invOperation inverz operacije koju gumb provodi.
	 * @param model model kalkulatora.
	 */
	public UnaryOperationButton(String text, String invText, DoubleUnaryOperator operation, DoubleUnaryOperator invOperation, CalcModel model) {
		this.text = text;
		this.model = model;
		this.invText = invText;
		this.inverted = false;
		this.listener = createUnaryOperationListener(operation);
		this.invListener = createUnaryOperationListener(invOperation);
		this.setText(text);
		this.addActionListener(listener);
	}
	
	/**
	 * Metoda stvara prikladnog promatrača za predanu operaciju.
	 * 
	 * @param op unarna operacija.
	 * @return promatrač.
	 */
	private ActionListener createUnaryOperationListener(DoubleUnaryOperator op) {
		return  e -> {
			try {
				if (model.hasFrozenValue())
					throw new CalculatorInputException("Calculator has a frozen value!");
				model.setValue(op.applyAsDouble(model.getValue()));
			} catch (CalculatorInputException err) {
				JOptionPane.showMessageDialog(this.getParent(), "Nedozvoljen unos u kalkulator!", "Greška", JOptionPane.WARNING_MESSAGE);
			}
		};
		
	}

}

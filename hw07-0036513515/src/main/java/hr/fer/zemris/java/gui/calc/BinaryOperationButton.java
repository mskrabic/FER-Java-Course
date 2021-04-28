package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Razred predstavlja model gumba kalkulatora {@link Calculator} koji provodi binarnu operaciju.
 * 
 * @author mskrabic
 *
 */
public class BinaryOperationButton extends OperationButton {

	/**
	 * Serijski broj.
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
	public BinaryOperationButton(String text, String invText, DoubleBinaryOperator operation, DoubleBinaryOperator invOperation, CalcModel model) {
		this.text = text;
		this.invText = invText == null ? text : invText;
		this.model = model;
		this.listener = createBinaryOperationListener(operation);
		this.invListener = createBinaryOperationListener(invOperation);
		this.inverted = false;
		this.setText(text);
		this.addActionListener(listener);
	}

	/**
	 * Metoda koja priprema izvođenje binarne operacije - ako već postoji zakazana operacija najprije izvede nju,
	 * postavi aktivni operand i zakaže novu operaciju.
	 * 
	 * @param operation operacija koja se sljedeća treba obaviti.
	 */
	protected void prepareOperation(DoubleBinaryOperator operation) {
		if (model.hasFrozenValue())
			throw new CalculatorInputException("Calculator has a frozen value!");
		if (model.getPendingBinaryOperation() == null) {
			model.setActiveOperand(model.getValue());
		} else if (model.isActiveOperandSet()) {
			model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
			model.setActiveOperand(model.getValue());
		}	
		model.setPendingBinaryOperation(operation);
		model.freezeValue();
	}
	
	/**
	 * Metoda stvara prikladnog promatrača za binarnu operaciju.
	 * 
	 * @param op binarna operacija.
	 * 
	 * @return promatrač.
	 */
	private ActionListener createBinaryOperationListener(DoubleBinaryOperator op) {
		return e -> {
			try {
				prepareOperation(op);
			} catch (CalculatorInputException err) {
				JOptionPane.showMessageDialog(this.getParent(), "Nedozvoljen unos u kalkulator!", "Greška", JOptionPane.WARNING_MESSAGE);
			}
		};
	}
}

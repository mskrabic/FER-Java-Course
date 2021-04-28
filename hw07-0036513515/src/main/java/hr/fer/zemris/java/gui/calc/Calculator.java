package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.calc.model.impl.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Razred predstavlja implementaciju jednostavnog kalkulatora. Unos se vrši
 * klikom miša na gumbe, a podržane su neke osnovne funkcije i njihovi inverzi.
 * 
 * @author mskrabic
 *
 */
public class Calculator extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Model kalkulatora.
	 */
	private CalcModel model = new CalcModelImpl();
	/**
	 * Lista koja pamti gumbe koji provode operacije koje imaju inverz.
	 */
	private List<OperationButton> invertibleOperations = new ArrayList<>();

	/**
	 * Stog kalkulatora.
	 */
	private Stack<Double> stack = new Stack<>();

	/**
	 * Pretpostavljeni konstruktor koji inicijalizira kalkulator.
	 */
	public Calculator() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		setLocation(20, 20);
		initGUI();
		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * Pomoćna metoda za inicijalizaciju grafičkih komponenti kalkulatora.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		
		JLabel screen = new JLabel(model.toString());
		model.addCalcValueListener(e -> screen.setText(model.toString()));
		screen.setBackground(Color.YELLOW);
		screen.setOpaque(true);
		screen.setHorizontalAlignment(SwingConstants.RIGHT);
		screen.setFont(screen.getFont().deriveFont(30f));

		CalcButton buttonClr = new CalcButton("clr", (e) -> model.clear());
		CalcButton buttonReset = new CalcButton("reset",(e) -> model.clearAll());
		CalcButton buttonPush = new CalcButton("push", (e) -> {
			if (model.hasFrozenValue())
				throw new CalculatorInputException("Calculator has a frozen value!");
			stack.push(model.getValue());
		});
		CalcButton buttonPop = new CalcButton("pop",(e) -> {
			if (model.hasFrozenValue())
				throw new CalculatorInputException("Calculator has a frozen value!");
			if (stack.isEmpty()) {
				throw new EmptyStackException();
			}
			model.setValue(stack.pop());
		});
		CalcButton buttonSwitch = new CalcButton("+/-", (e) -> {
			if (model.hasFrozenValue())
				throw new CalculatorInputException("Calculator has a frozen value!");
			model.swapSign();
		});
		CalcButton buttonDecimalPoint = new CalcButton(".", (e) -> {
			if (model.hasFrozenValue())
				throw new CalculatorInputException("Calculator has a frozen value!");
			model.insertDecimalPoint();
		});

		CalcButton buttonEquals = new CalcButton("=", (e) -> {
			if (model.isActiveOperandSet() && model.getPendingBinaryOperation() != null) {
				model.setValue(
						model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
				model.setPendingBinaryOperation(null);
			} else {
				model.setValue(model.getValue());
			}
		});

		JCheckBox checkboxInv = new JCheckBox("Inv");
		checkboxInv.addActionListener(e -> invert());
		
		UnaryOperationButton buttonOneOverX = new UnaryOperationButton("1/x", null, v -> 1/v, null, model);

		UnaryOperationButton buttonSin = new UnaryOperationButton("sin", "arcsin", v -> Math.sin(v), v -> Math.asin(v), model);
		
		UnaryOperationButton buttonLog = new UnaryOperationButton("log", "10^x", v -> Math.log10(v), v -> Math.pow(10, v), model);
		
		UnaryOperationButton buttonCos = new UnaryOperationButton("cos", "arccos", v -> Math.cos(v), v -> Math.acos(v), model);
		
		UnaryOperationButton buttonLn = new UnaryOperationButton("ln", "e^x", v -> Math.log(v), v -> Math.exp(v),model);
		
		UnaryOperationButton buttonTan = new UnaryOperationButton("tan", "arctan", v -> Math.tan(v), v -> Math.atan(v), model);
		
		UnaryOperationButton buttonCtg = new UnaryOperationButton("ctg", "arcctg", v -> 1 / Math.tan(v), (v) -> Math.atan(1 / v), model);

		BinaryOperationButton buttonDiv = new BinaryOperationButton("/", null, (l, r) -> l / r, null, model);
		
		BinaryOperationButton buttonMul = new BinaryOperationButton("*", null, (l, r) -> l * r, null, model);
		
		BinaryOperationButton buttonMinus = new BinaryOperationButton("-", null, (l, r) -> l - r, null, model);
		
		BinaryOperationButton buttonPow = new BinaryOperationButton("x^n", "x^(1/n)", (l, r) -> Math.pow(l, r), (l, r) -> Math.pow(l, 1 / r), model);
		
		BinaryOperationButton buttonPlus = new BinaryOperationButton("+", null, (l, r) -> l + r, null, model);

		
		CalcButton button0 = new CalcButton("0", (e) -> model.insertDigit(0));
		button0.setFont(button0.getFont().deriveFont(30f));
		
		CalcButton button1 = new CalcButton("1", (e) -> model.insertDigit(1));
		button1.setFont(button1.getFont().deriveFont(30f));
		
		CalcButton button2 = new CalcButton("2", (e) -> model.insertDigit(2));
		button2.setFont(button2.getFont().deriveFont(30f));
		
		CalcButton button3 = new CalcButton("3", (e) -> model.insertDigit(3));
		button3.setFont(button3.getFont().deriveFont(30f));
		
		CalcButton button4 = new CalcButton("4", (e) -> model.insertDigit(4));
		button4.setFont(button4.getFont().deriveFont(30f));
		
		CalcButton button5 = new CalcButton("5", (e) -> model.insertDigit(5));
		button5.setFont(button5.getFont().deriveFont(30f));
		
		CalcButton button6 = new CalcButton("6", (e) -> model.insertDigit(6));
		button6.setFont(button6.getFont().deriveFont(30f));
		
		CalcButton button7 = new CalcButton("7", (e) -> model.insertDigit(7));
		button7.setFont(button7.getFont().deriveFont(30f));
		
		CalcButton button8 = new CalcButton("8", (e) -> model.insertDigit(8));
		button8.setFont(button8.getFont().deriveFont(30f));
		
		CalcButton button9 = new CalcButton("9", (e) -> model.insertDigit(9));
		button9.setFont(button9.getFont().deriveFont(30f));
		
		invertibleOperations.add(buttonCtg);
		invertibleOperations.add(buttonPow);
		invertibleOperations.add(buttonSin);
		invertibleOperations.add(buttonLog);
		invertibleOperations.add(buttonCos);
		invertibleOperations.add(buttonLn);
		invertibleOperations.add(buttonTan);
		
		cp.add(screen, "1,1");
		cp.add(button0, "5,3");
		cp.add(button1, "4,3");
		cp.add(button2, "4,4");
		cp.add(button3, "4,5");
		cp.add(button4, "3,3");
		cp.add(button5, "3,4");
		cp.add(button6, "3,5");
		cp.add(button7, "2,3");
		cp.add(button8, "2,4");
		cp.add(button9, "2,5");
		cp.add(buttonEquals, "1,6");
		cp.add(buttonClr, "1,7");
		cp.add(buttonOneOverX, "2,1");
		cp.add(buttonSin, "2,2");
		cp.add(buttonDiv, "2,6");
		cp.add(buttonReset, "2,7");
		cp.add(buttonLog, "3,1");
		cp.add(buttonCos, "3,2");
		cp.add(buttonMul, "3,6");
		cp.add(buttonPush, "3,7");
		cp.add(buttonLn, "4,1");
		cp.add(buttonTan, "4,2");
		cp.add(buttonMinus, "4,6");
		cp.add(buttonPop, "4,7");
		cp.add(buttonPow, "5,1");
		cp.add(buttonCtg, "5,2");
		cp.add(buttonSwitch, "5,4");
		cp.add(buttonDecimalPoint, "5,5");
		cp.add(buttonPlus, "5,6");
		cp.add(checkboxInv, "5,7");
	}

	/**
	 * Metoda koja invertira sve potrebne gumbe na kalkulatoru.
	 */
	private void invert() {
		for (OperationButton b : invertibleOperations) {
			b.invert();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Calculator calc = new Calculator();
			calc.setVisible(true);
		});
	}

}
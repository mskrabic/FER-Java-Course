package hr.fer.zemris.java.gui.calc.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Razred predstavlja jednostavni model kalkulatora i implementira sučelje {@link CalcModel}
 * 
 * @author mskrabic
 *
 */
public class CalcModelImpl implements CalcModel {
	
	/**
	 * Zastavica koja određuje je li model editabilan.
	 */
	private boolean editable;
	
	/**
	 * Zastavica koja određuje je li trenutno uneseni broj pozitivan.
	 */
	private boolean positive;
	
	/**
	 * String trenutnog unosa u kalkulator.
	 */
	private String input;
	
	/**
	 * Brojčana vrijednost trenutnog unosa u kalkulator.
	 */
	private double value;
	
	/**
	 * Zamrznuta vrijednost na ekranu kalkulatora.
	 */
	private String frozenValue;
	
	/**
	 * Zakazana operacija kalkulatora - čeka drugi operand.
	 */
	private DoubleBinaryOperator pendingOperation;
	
	/**
	 * Aktivni operand kalkulatora.
	 */
	private Double activeOperand;	
	
	/**
	 * Promatrači kalkulatora.
	 */
	private List<CalcValueListener> listeners;
	
	
	/**
	 * Konstruktor koji inicijalizira kalkulator u početno stanje.
	 */
	public CalcModelImpl() {
		this.editable = true;
		this.positive = true;
		this.input = "";
		this.value = 0;
		this.frozenValue = null;
		this.pendingOperation = null;
		this.activeOperand = null;
		this.listeners = new ArrayList<>();
	}
	

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
		
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		if (this.hasFrozenValue())
			frozenValue = null;
		this.value = value;
		if (value == Double.POSITIVE_INFINITY) {
			this.input = "Infinity";
		} else if (value == Double.NEGATIVE_INFINITY) {
			this.input = "-Infinity";
		} else if (value == Double.NaN) {
			this.input = "NaN";
		} else {
			this.input = Double.toString(value);			
		}
		editable = false;
		
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		this.input = "";
		this.value = 0;
		this.frozenValue = null;
		this.editable = true;
		this.positive = true;
		
		notifyListeners();
		
	}

	@Override
	public void clearAll() {
		clear();
		this.activeOperand = null;
		this.pendingOperation = null;
		
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!isEditable())
			throw new CalculatorInputException("Model is not editable!");
		
		value = -1 * value;
		positive = !positive;
		input = input.length() == 0 ? "" : (positive ? input.substring(1) : "-" + input );
		
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!isEditable())
			throw new CalculatorInputException("Model is not editable!");
		if (input.contains("."))
			throw new CalculatorInputException("Decimal point already inserted!");
		if (input.length() == 0)
			throw new CalculatorInputException("Decimal point can't be at the start of the input!");
		
		if (hasFrozenValue())
			frozenValue = null;
		
		input += ".";
		
		notifyListeners();	
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!isEditable())
			throw new CalculatorInputException("Model is not editable!");	
		if (hasFrozenValue())
			frozenValue = null;
		
		String newInput = input+digit;
		try {
			value = Double.parseDouble(newInput);
			if (Double.isInfinite(value)) throw new NumberFormatException();
			input = newInput;
			notifyListeners();
		} catch(Exception e) {
			throw new CalculatorInputException("Input cannot be parsed into a double value!");
		}	
	}

	@Override
	public boolean isActiveOperandSet() {
		return (activeOperand != null);
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (activeOperand == null)
			throw new IllegalStateException("There is not an active operand!");
		
		return activeOperand.doubleValue();
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = Double.valueOf(activeOperand);
		
	}

	@Override
	public void clearActiveOperand() {
		this.activeOperand = null;
		
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
		
	}

	@Override
	public String toString() {
		if (frozenValue != null) 
			return frozenValue;
		
		if (input.length() == 0)
			return positive ? "0" : "-0";
		
		while (input.length() > 1 && input.charAt(0) == '0' && input.charAt(1) == '0')
			input = input.substring(1);
		
        if (input.length() > 1 && input.charAt(0) == '0' && input.charAt(1) != '.') {
            input = input.substring(1);
        }
        
        return input;
	}


	@Override
	public void freezeValue() {
		this.frozenValue = this.input;	
		this.editable = true;
		this.value = 0;
		this.input = "";
	}
	
	@Override
	public boolean hasFrozenValue() {
		return frozenValue != null;
	}
	
	/**
	 * Pomoćna metoda za obavještavanje promatrača.
	 */
	private void notifyListeners() {
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}
}

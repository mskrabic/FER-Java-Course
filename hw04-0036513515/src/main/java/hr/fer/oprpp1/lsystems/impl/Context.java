package hr.fer.oprpp1.lsystems.impl;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Razred omogućava postupak prikazivanja fraktala.
 * 
 * @author mskrabic
 *
 */
public class Context {
	/**
	 * Stog na koji se stavljaju stanja kornjače. Stanje na vrhu stoga je trenutno.
	 */
	private ObjectStack<TurtleState> stack;
	
	/**
	 * Konstruktor koji inicijalizira prazan stog.
	 */
	public Context() {
		this.stack = new ObjectStack<>();
	}
	
	/**
	 * Metoda vraća trenutno stanje, tj. stanje s vrha stoga (bez uklanjanja).
	 * 
	 * @return trenutno stanje.
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * Metoda na vrh stoga gura predano stanje, tj. postavlja ga za trenutno.
	 * 
	 * @param state stanje koje se želi gurnuti na vrh stoga.
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Metoda briše stanje s vrha stoga.
	 */
	public void popState() {
		stack.pop();
	}

}

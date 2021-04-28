package hr.fer.oprpp1.lsystems.impl;

import java.awt.Color;

import hr.fer.oprpp1.math.Vector2D;

/**
 * Razred modelira stanje u kojem se kornjača za crtanje nalazi.
 * 
 * @author mskrabic
 *
 */
public class TurtleState {
	/**
	 * Trenutna pozicija kornjače (radij-vektor).
	 */
	private Vector2D position;
	/**
	 * Smjer u kojem kornjača gleda.
	 */
	private Vector2D direction;
	/**
	 * Boja kojom kornjača crta.
	 */
	private Color color;
	/**
	 * Efektivna duljina jediničnog pomaka kornjače.
	 */
	private double unitLength;
	
	/**
	 * Konstruktor koji inicijalizira stanje kornjače na predane vrijednosti.
	 * 
	 * @param position željena pozicija.
	 * @param direction željeni smjer.
	 * @param color željena boja.
	 * @param length željena jedinična duljina pomaka.
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double unitLength) {
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.unitLength = unitLength;
	}
	
	/**
	 * Vraća kopiju trenutnog stanja.
	 * 
	 * @return novi objekt koji predstavlja isto stanje kao trenutno.
	 */
	public TurtleState copy() {
		return new TurtleState(this.position.copy(), this.direction.copy(), this.color, this.unitLength);
	}
	
	/**
	 * Rotira kornjaču za predani kut.
	 * 
	 * @param angle kut za koji se želi rotirati kornjača.
	 */
	public void rotate(double angle) {
		direction.rotate(angle);
	}
	
	/**
	 * Metoda translatira poziciju za predani vektor.
	 * 
	 * @param v vektor za koji se želi translatirati.
	 */
	public void updatePosition(Vector2D v) {
		this.position.add(v);
	}
	
	/**
	 * Metoda vraća smjer u kojem kornjača gleda.
	 * 
	 * @return smjer kornjače.
	 */
	public Vector2D getDirection() {
		return this.direction;
	}
	
	/**
	 * Metoda vraća boju kojom kornjača crta.
	 * 
	 * @return boja kojom kornjača crta.
	 */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * Metoda vraća trenutnu poziciju kornjače.
	 * 
	 * @return trenutna pozicija kornjače.
	 */
	public Vector2D getPosition() {
		return this.position;
	}
	
	/**
	 * Metoda vraća efektivnu duljinu koraka kornjače.
	 * 
	 * @return efektivna duljina koraka kornjače.
	 */
	public double getUnitLength() {
		return this.unitLength;
	}
	
	/**
	 * Metoda skalira efektivnu duljinu koraka za predani faktor.
	 * 
	 * @param f faktor za koji se želi skalirati.
	 */
	public void updateUnitLength(double f) {
		this.unitLength *= f;
	}
	
	/**
	 * Metoda koja postavlja boju kornjače.
	 * 
	 * @param c željena boja kornjače.
	 */
	public void setColor(Color c) {
		this.color = c;
	}
}

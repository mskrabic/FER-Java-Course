package hr.fer.oprpp1.math;

/**
 * Razred predstavlja model dvodimenzionalnog vektora.
 * 
 * @author mskrabic
 *
 */
public class Vector2D {
	
	/**
	 * x-koordinata vektora.
	 */
	
	private double x;
	/**
	 * y-koordinata vektora.
	 */
	private double y;
	
	/**
	 * Konstruktor inicijalizira koordinate vektora na predane vrijednosti.
	 * 
	 * @param x željena x koordinata.
	 * @param y željena y koordinata.
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Metoda vraća x-koordinatu vektora.
	 * 
	 * @return x-koordinata vektora.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Metoda vraća y-koordinatu vektora.
	 * 
	 * @return y-koordinata vektora.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Metoda pribraja trenutnom vektoru predani vektor.
	 * 
	 * @param offset vektor koji se želi pribrojiti trenutnom.
	 */
	public void add(Vector2D offset) {
		this.x = this.x + offset.x;
		this.y = this.y + offset.y;
	}
	
	/**
	 * Metoda stvara novi vektor koji je zbroj trenutnog i predanog vektora. Trenutni vektor ostaje nepromjenjen.
	 * 
	 * @param offset vektor koji se želi pribrojiti trenutnom.
	 * 
	 * @return novi vektor koji je zbroj trenutnog i predanog vektora.
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}
	
	/**
	 * Metoda rotira trenutni vektor za predani kut.
	 * 
	 * @param angle kut za koji se želi rotirati trenutni vektor.
	 */
	public void rotate(double angle) {
		double magnitude = Math.sqrt(x*x + y*y);
		double oldAngle = Math.atan2(y, x);
		if (oldAngle < 0) {
			oldAngle += 2*Math.PI;
		}
		double newAngle = oldAngle+angle;
		
		this.x = magnitude*Math.cos(newAngle);
		this.y = magnitude*Math.sin(newAngle);
	}
	
	/**
	 * Metoda stvara novi vektor koji je jednak trenutnom vektoru zarotiranom za predani kut.
	 * 
	 * @param angle kut za koji se želi rotirati trenutni vektor.
	 * 
	 * @return novi vektor koji je rezultat rotacije trenutnog vektora za predani kut.
	 */
	public Vector2D rotated(double angle) {
		double magnitude = Math.sqrt(x*x + y*y);
		double oldAngle = Math.atan2(y, x);
		if (oldAngle < 0) {
			oldAngle += 2*Math.PI;
		}
		double newAngle = oldAngle+angle;
		
		return new Vector2D(magnitude * Math.cos(newAngle), magnitude * Math.sin(newAngle));
	}
	
	/**
	 * Metoda skalira trenutni vektor za predanu vrijednost.
	 * 
	 * @param scaler vrijednost za koju se želi skalirati trenutni vektor.
	 */
	public void scale(double scaler) {
		this.x = this.x * scaler;
		this.y = this.y * scaler;
	}
	
	/**
	 * Metoda stvara novi vektor koji je jednak trenutnom vektoru skaliranom za predanu vrijednost.
	 * 
	 * @param scaler vrijednost za koju se želi skalirati trenutni vektor.
	 * 
	 * @return novi vektor koji je rezultat skaliranja trenutnog vektora za predanu vrijednost.
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x * scaler, this.y * scaler);
	}
	
	/**
	 * Metoda stvara novi vektor koji je jednak trenutnom.
	 * 
	 * @return novi vektor koji je jednak trenutnom.
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

}

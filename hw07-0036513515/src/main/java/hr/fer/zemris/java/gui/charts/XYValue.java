package hr.fer.zemris.java.gui.charts;

/**
 * Razred predstavlja model jedne vrijednosti na stupčastom dijagramu, tj. par (X,Y).
 * 
 * @author mskrabic
 *
 */
public class XYValue {
	
	/**
	 * x-vrijednost.
	 */
	private int x;
	
	/**
	 * y-vrijednost.
	 */
	private int y;
	
	/**
	 * Konstruktor.
	 * @param x željena x-vrijednost.
	 * @param y željena y-vrijednost.
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Metoda vraća x-vrijednost ove točke.
	 * 
	 * @return x-vrijednost.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Metoda vraća y-vrijednost ove točke.
	 * 
	 * @return y-vrijednost.
	 */
	public int getY() {
		return y;
	}

}

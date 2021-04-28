package hr.fer.zemris.java.gui.layouts;

/**
 * Iznimka koju {@link CalcLayout} baca u slučaju neispravnog postavljanja komponenti u razmještaj.
 * 
 * @author mskrabic.
 *
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 */
	public CalcLayoutException() {
		super();
	}
	
	/**
	 * Konstruktor.
	 * @param message poruka o pogrešci.
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

}

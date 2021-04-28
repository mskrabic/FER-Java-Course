package hr.fer.zemris.java.gui.layouts;

/**
 * Razred predstavlja poziciju komponente u "rešetki" kalkulatora {@link Calculator}.
 * 
 * @author mskrabic
 *
 */
public class RCPosition {
	
	/**
	 * Redak kalkulatora.
	 */
	private int row;
	
	/**
	 * Stupac kalkulatora.
	 */
	private int column;
	
	/**
	 * Konstruktor.
	 * 
	 * @param row redak kalkulatora.
	 * @param column stupac kalkulatora.
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Metoda vraća redak ove pozicije.
	 * 
	 * @return redak
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Metoda vraća stupac ove pozicije.
	 * 
	 * @return stupac.
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Metoda za parsiranje pozicije iz stringa. Dozvoljeni zapis je X,Y.
	 * 
	 * @param text zapis pozicije kao string.
	 * 
	 * @throws IllegalArgumentException ako se preda neispravan string.
	 * 
	 * @return pozicija.
	 */
	public static RCPosition parse(String text) {
		String[] splitted = text.split(",");
		
		if (splitted.length != 2) {
			throw new IllegalArgumentException("Invalid RCPosition!");
		}
		int row, column;
		try {
			row = Integer.parseInt(splitted[0].trim());
			column = Integer.parseInt(splitted[1].trim());			
		} catch (Exception e) {
			throw new IllegalArgumentException("String " + text + " cannot be parsed into RCPosition!");
		}
		
		return new RCPosition(row, column);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	

}

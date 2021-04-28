package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Razred predstavlja model stupčastog dijagrama.
 * 
 * @author mskrabic
 *
 */
public class BarChart {
	/**
	 * Vrijednosti na dijagramu - parovi (X,Y).
	 */
	private List<XYValue> values;
	
	/**
	 * Opis uz x-os.
	 */
	private String descX;
	
	/**
	 * Opis uz y-os.
	 */
	private String descY;
	
	/**
	 * Minimalna vrijednost y-osi koja se prikazuje.
	 */
	private int minY;
	
	/**
	 * Maksimalna vrijednost y-osi koja se prikazuje.
	 */
	private int maxY;
	
	/**
	 * Razmak između dva susjedna y na y-osi.
	 */
	private int gap;

	/**
	 * Konstruktor koji inicijalizira stupčasti dijagram s predanim vrijednostima.
	 * 
	 * @param values vrijednosti dijagrama.
	 * @param descX opis uz x os.
	 * @param descY opis uz y os.
	 * @param minY minimalna vrijednost y-osi koja se prikazuje.
	 * @param maxY maksimalna vrijednost y-osi koja se prikazuje.
	 * @param gap razmak između dva susjedna y na y-osi.
	 * 
	 * @throws IllegalArgumentException ako je y-koordinata neke od predanih vrijednosti manja od predane minimalne vrijednosti, ako je 
	 * predana minimalna vrijednost manja od 0 ili ako je predana maksimalna vrijednost manja od predane minimalne vrijednosti.
	 */
	public BarChart(List<XYValue> values, String descX, String descY, int minY, int maxY, int gap) {
		for (XYValue v : values) {
			if (v.getY() < minY) throw new IllegalArgumentException("All values must be greater or equal to the minimal Y value!");
		}
		this.values = values;
		this.descX = descX;
		this.descY = descY;
		if (minY < 0)
			throw new IllegalArgumentException("Minimum Y value must be >= 0!");
		this.minY = minY;
		if (maxY < minY)
			throw new IllegalArgumentException("Maximum Y value must be greater than the minimum Y value!");
		this.maxY = maxY;
		this.gap = gap;
		while (((maxY - minY) / gap)*gap != (maxY-minY)) {
			maxY++;
		}
	}

	/**
	 * Metoda vraća vrijednosti dijagrama.
	 * 
	 * @return vrijednosti dijagrama.
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Metoda vraća opis uz x-os.
	 * 
	 * @return opis uz x-os.
	 */
	public String getDescX() {
		return descX;
	}

	/**
	 * Metoda vraća opis uz y-os.
	 * 
	 * @return opis uz y-os.
	 */
	public String getDescY() {
		return descY;
	}

	/**
	 * Metoda vraća minimalnu vrijednost prikazanu na y-osi.
	 * 
	 * @return minimalna vrijednost na y-osi.
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Metoda vraća maksimalnu vrijednost prikazanu na y-osi.
	 * 
	 * @return maksimalna vrijednost na y-osi.
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Metoda vraća razmak između dva susjedna y na y-osi.
	 * 
	 * @return razmak između dva susjedna y na y-osi.
	 */
	public int getGap() {
		return gap;
	}
	
	
}

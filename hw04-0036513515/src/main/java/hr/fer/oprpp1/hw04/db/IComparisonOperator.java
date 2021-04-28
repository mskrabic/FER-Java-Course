package hr.fer.oprpp1.hw04.db;

/**
 * Sučelje koje modelira operator usporedbe.
 * 
 * @author mskrabic
 *
 */
public interface IComparisonOperator {
	
	/**
	 * Metoda provjerava zadovoljavaju li predane vrijednosti operator usporedbe.
	 * 
	 * @param value1 prvi operand.
	 * @param value2 drugi operand.
	 * 
	 * @return <code>true</code> ako vrijedi v1 (operator) v2, <code>false</code> inače.
	 */
	public boolean satisfied(String value1, String value2);

}

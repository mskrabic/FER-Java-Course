package hr.fer.oprpp1.hw04.db;

/**
 * Implementacije sučelja <code>IFieldValueGetter</code>.
 * 
 * @author mskrabic
 *
 */
public class FieldValueGetters {
	
	/**
	 * Getter za ime studenta.
	 */
	public static final IFieldValueGetter FIRST_NAME = r -> r.getFirstName();
	
	/**
	 * Getter za prezime studenta.
	 */
	public static final IFieldValueGetter LAST_NAME = r -> r.getLastName();
	
	/**
	 * Getter za JMBAG studenta.
	 */
	public static final IFieldValueGetter JMBAG = r -> r.getJmbag();
}

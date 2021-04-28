package hr.fer.oprpp1.hw04.db;

/**
 * Sučelje koje se koristi za dohvat vrijednosti određenog atributa iz zapisa o studentu.
 * 
 * @author mskrabic
 *
 */
public interface IFieldValueGetter {
	
	/**
	 * Metoda dohvaća vrijednost traženog atributa iz predanog zapisa.
	 * 
	 * @param record zapis o studentu.
	 * 
	 * @return vrijednost traženog atributa za predani zapis.
	 */
	public String get(StudentRecord record);

}

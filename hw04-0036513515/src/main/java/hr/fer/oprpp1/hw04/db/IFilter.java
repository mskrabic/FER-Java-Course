package hr.fer.oprpp1.hw04.db;

/**
 * Sučelje koje se koristi za filtriranje zapisa o studentima po nekom kriteriju.
 * 
 * @author mskrabic
 *
 */
public interface IFilter {
	
	/**
	 * Metoda provjerava zadovoljava li predani zapis kriterij filtriranja.
	 * 
	 * @param record zapis koji se želi provjeriti.
	 * 
	 * @return <code>true</code> ako zapis zadovoljava kriterij filtriranja, <code>false</code> inače.
	 */
	 public boolean accepts(StudentRecord record);
}

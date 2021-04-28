package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje koje predstavlja jednostavni model procesora.
 * 
 * @author mskrabic
 */
public interface Processor<E> {
	
	/**
	 * Metoda koja obrađuje predanu vrijednost.
	 * 
	 * @param value vrijednost koju se želi obraditi.
	 */
	void process(E value);
}

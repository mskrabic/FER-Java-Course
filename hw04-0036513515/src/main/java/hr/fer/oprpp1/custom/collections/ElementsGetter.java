package hr.fer.oprpp1.custom.collections;


/**
 * Sučelje koje se koristi za pristup elementima kolekcije.
 * 
 * @author mskrabic
 *
 */
public interface ElementsGetter<E> {
	
	/**
	 * Metoda provjerava postoji li nepredanih elemenata u kolekciji.
	 * 
	 * @return <code>true</code> ako ElementsGetter nije dohvatio sve podatke, <code>false</code> inače.
	 */
	boolean hasNextElement();
	
	/**
	 * Metoda vraća sljedeći nepredani element iz kolekcije.
	 * 
	 * @return element kolekcije.
	 */
	E getNextElement();
	
	/**
	 * Metoda poziva metodu <code>process()</code> predanog procesora nad svim preostalim elementima kolekcije.
	 * 
	 * @param p procesor čiju metodu <code>process()</code> se želi pozvati nad elementima kolekcije.
	 */
	default void processRemaining(Processor<? super E> p) {
		while (this.hasNextElement()) {
			p.process(this.getNextElement());
		}
	}

}

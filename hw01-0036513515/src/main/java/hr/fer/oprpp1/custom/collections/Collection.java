package hr.fer.oprpp1.custom.collections;

/**
 * Razred predstavlja općenitu kolekciju <code>Object</code> elemenata.
 * Od konkretnih implementacija kolekcije očekuje se da nadjačaju potrebne metode na prikladan način.
 * 
 * @author mskrabic
 *
 */
public class Collection {

	/**
	 * Pretpostavljeni konstruktor.
	 */
	protected Collection() {
		
	}
	
	/**
	 * Metoda provjerava je li kolekcija prazna.
	 * 
	 * @return <code>true</code> ako je kolekcija prazna, <code>false</code> inače.
	 */
	public boolean isEmpty() {
		return (this.size() == 0);
	}
	
	/**
	 * Metoda vraća veličinu kolekcije, tj. broj elemenata koji su trenutno pohranjeni u njoj.
	 * U razredu <code>Collection</code> ova metoda uvijek vraća 0 i očekuje se da će je izvedeni razred prikladno nadjačati.
	 * 
	 * @return broj elemenata u kolekciji.
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Metoda koja dodaje element u kolekciju.
	 * U razredu <code>Collection</code> ova metoda je prazna i očekuje se da će je izvedeni razred prikladno nadjačati.
	 * 
	 * @param value vrijednost koja treba biti dodana u kolekciju.
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Metoda koja provjerava sadrži li kolekcija traženi element.
	 * U razredu <code>Collection</code> ova metoda uvijek vraća <code>false</code> i očekuje se da će je 
	 * izvedeni razred prikladno nadjačati.
	 * 
	 * @param value vrijednost čiju prisutnost u kolekciji želimo ispitati.
	 * 
	 * @return <code>true</code> ako kolekcija sadrži traženu vrijednost, <code>false</code> inače.
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Metoda koja izbacuje traženi element iz kolekcije.
	 * U razredu <code>Collection</code> ova metoda ne radi ništa i očekuje se da će je izvedeni razred prikladno nadjačati.
	 * 
	 * @param value element koji se želi izbaciti iz kolekcije
	 * 
	 * @return <code>true</code> ako kolekcija sadrži traženu vrijednost i izbaci jedno njeno pojavljivanje.
	 * iz kolekcije, <code>false</code> inače.
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Metoda koja vraća novo polje koje sadrži elemente kolekcije.
	 * U razredu <code>Collection</code> ova metoda nije podržana i očekuje se da će je izvedeni razred prikladno nadjačati.
	 * 
	 * @return novo polje koje sadrži elemente kolekcije.
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException("Generic \"Collection\" class does not allow the use of \"toArray().\"");
	}
	
	/**
	 * Metoda koja nad svakim elementom kolekcije poziva <code>process()</code> metodu predanog procesora.
	 * U razredu <code>Collection</code> ova metoda je prazna i očekuje se da će je izvedeni razred prikladno nadjačati.
	 * 
	 * @param processor procesor čiju metodu <code>process()</code> se želi pozvati nad elementima kolekcije.
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Metoda koja u trenutnu kolekciju dodaje sve elemente iz predane kolekcije.
	 * Predana kolekcija pri tome ostaje nepromjenena.
	 * 
	 * @param other kolekcija čije elemente se želi dodati u trenutnu kolekciju.
	 */
	public void addAll(Collection other) {
		
		/**
		 * Razred koji predstavlja procesor za dodavanje elemenata u kolekciju.
		 * 
		 * @author mskrabic
		 *
		 */
		class AddAllProcessor extends Processor {
			
			/**
			 * Metoda koja predanu vrijednost dodaje u kolekciju.
			 */
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		AddAllProcessor p = new AddAllProcessor();
		other.forEach(p);
	}
	
	/**
	 * Metoda koja izbacuje sve elemente iz kolekcije.
	 * U razredu <code>Collection</code> ova metoda je prazna i očekuje se da će je izvedeni razred prikladno nadjačati.
	 */
	public void clear() {
		
	}
}

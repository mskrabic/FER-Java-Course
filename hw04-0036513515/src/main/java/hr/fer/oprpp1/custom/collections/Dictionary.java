package hr.fer.oprpp1.custom.collections;

/**
 * Razred predstavlja jednostavnu implementaciju parametrizirane mape, tj. rječnika.
 * Koristi se za pohranu parova ključ-vrijednost.
 * 
 * @author mskrabic
 *
 * @param <K> tip ključa.
 * @param <V> tip vrijednosti.
 */
public class Dictionary<K, V> {
	
	/**
	 * Razred modelira jedan zapis u rječniku, tj. jedan par ključ-vrijednost.
	 * 
	 * @author mskrabic
	 *
	 * @param <K> tip ključa.
	 * @param <V> tip vrijednosti.
	 */
	private static class Entry<K, V> {
		/**
		 * Ključ zapisa.
		 */
		private K key;
		/**
		 * Vrijednost zapisa.
		 */
		private V value;
		
		/**
		 * Konstruktor koji inicijalizira zapis s predanim ključem i vrijednosti.
		 * 
		 * @param key ključ zapisa.
		 * @param value vrijednost zapisa.
		 * 
		 * @throws NullPointerException ako se kao ključ preda <code>null</code> vrijednost.
		 */
		public Entry(K key, V value) {
			if (key == null)
				throw new NullPointerException("The key must not be null!");
			
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Metoda vraća ključ zapisa.
		 * 
		 * @return ključ zapisa.
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * Metoda vraća vrijednost zapisa.
		 * 
		 * @return vrijednost zapisa.
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Metoda postavlja vrijednost zapisa na predanu vrijednost.
		 * 
		 * @param value željena vrijednost zapisa.
		 */
		private void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * Metoda uspoređuje ovaj zapis s drugim. Zapisi su jednaki ako su im ključevi jednaki.
		 */
		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object other) {
			Entry<K, V> otherEntry = null;
			try {
				otherEntry = (Entry<K, V>) other;	
			} catch (ClassCastException e) {
				return false;
			}
			return this.getKey().equals(otherEntry.getKey());
		}
	}
	
	/**
	 * Interna kolekcija koja se koristi za pohranu zapisa.
	 */
	private ArrayIndexedCollection<Entry<K, V>> col;
	
	/**
	 * Konstruktor koji inicijalizira prazni rječnik.
	 */
	public Dictionary() {
		this.col = new ArrayIndexedCollection<>();
	}
	
	/**
	 * Metoda provjerava je li rječnik prazan.
	 * 
	 * @return <code>true</code> ako je rječnik prazan, <code>false</code> inače.
	 */
	public boolean isEmpty() {
		return col.isEmpty();
	}
	
	/**
	 * Metoda vraća veličinu rječnika, tj. broj trenutno pohranjenih zapisa u rječniku.
	 * 
	 * @return broj zapisa u rječniku.
	 */
	public int size() {
		return col.size();
	}
	
	/**
	 * Metoda briše sve zapise iz rječnika.
	 */
	public void clear() {
		col.clear();
	}
	
	/**
	 * Metoda pohranjuje novi zapis u rječnik. Ako već postoji zapis s predanim ključem, metoda zapisuje novu vrijednost u taj zapis, a pozivatelju
	 * vraća staru vrijednost (overwrite). Ako zapis s predanim ključem ne postoji, dodaje se novi zapis i metoda vraća null.
	 * 
	 * @param key ključ zapisa.
	 * @param value vrijednost zapisa.
	 * 
	 * @return Prethodna vrijednost zapisa s predanim ključem, ako postoji, <code>null</code> inače.
	 * 
	 * @throws NullPointerException ako se pokuša dodati zapis s <code>null</code> ključem.
	 */
	public V put(K key, V value) {
		if (key == null) 
			throw new NullPointerException("Key must not be null!");
		
		Entry<K, V> e = new Entry<>(key, value);
		V result = null;
		if (col.contains(e)) {
			result = col.get(col.indexOf(e)).getValue();
			col.get(col.indexOf(e)).setValue(value);
		} else {
			col.add(e);
		}
		
		return result;
	}
	
	/**
	 * Metoda vraća vrijednost zapisa s predanim ključem, ako on postoji.
	 * 
	 * @param key ključ zapisa čija se vrijednost želi dobiti.
	 * 
	 * @return vrijednost zapisa, ako postoji u rječniku, <code>null</code> inače.
	 */
	public V get(Object key) {
		if (key == null)
			return null;
		
		for (int i = 0; i < col.size(); i++) {
			if (col.get(i).getKey().equals(key)) {
				return col.get(i).getValue();
			}
		}
		return null;
	}
	
	/**
	 * Metoda briše zapis sa zadanim ključem iz rječnika, ako on postoji.
	 * 
	 * @param key ključ zapisa koji se želi izbrisati.
	 * 
	 * @return vrijednost izbrisanog zapisa ili <code>null</code> ako zapis nije pronađen.
	 */
	public V remove(K key) {
		V result = null;
		
		if (key == null)
			return result;
		
		for (int i = 0; i < col.size(); i++) {
			if (col.get(i).getKey().equals(key)) {
				result = col.get(i).getValue();
				col.remove(i);
				return result;
			}
		}
		return result;
	}
}

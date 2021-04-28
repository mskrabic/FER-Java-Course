package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Razred predstavlja jednostavnu tablicu raspršenog adresiranja. Za rješavanje overflowa koristi se ulančavanje zapisa u liste.
 * 
 * @author mskrabic
 *
 * @param <K> tip ključeva.
 * @param <V> tip vrijednosti.
 */
public class SimpleHashtable<K, V> implements  Iterable<SimpleHashtable.TableEntry<K,V>> {
	
	/**
	 * Pretpostavljena vrijednost kapaciteta tablice.
	 */
	private static final int DEFAULT_CAPACITY = 16;

	/**
	 * Razred predstavlja jedan zapis u tablici.
	 * 
	 * @author mskrabic
	 *
	 * @param <K> tip ključa.
	 * @param <V> tip vrijednosti.
	 */
	public static class TableEntry<K, V> {
		
		/**
		 * Ključ zapisa.
		 */
		private K key;
		
		/**
		 * Vrijednost zapisa.
		 */
		private V value;
		
		/**
		 * Sljedeći zapis u povezanoj listi.
		 */
		private TableEntry<K, V> next;
		
		/**
		 * Konstruktor koji inicijalizira novi zapis s predanim ključem i vrijednosti.
		 */
		private TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
			this.next = null;
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
		 * Metoda postavlja vrijednost zapisa.
		 * 
		 * @param value željena vrijednost zapisa.
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * Metoda vraća string oblika "ključ=vrijednost".
		 */
		public String toString() {
			return key + "=" + value;
		}
	}
	
	/**
	 * Razred predstavlja implementaciju iteratora za SimpleHashtable.
	 * 
	 * @author mskrabic
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		
		/**
		 * Index elementa na kojem se iterator trenutno nalazi.
		 */
		private int currentIndex;
		
		/**
		 * Zadnji zapis koji je metoda <code>next()</code> vratila.
		 */
		private TableEntry<K, V> lastEntry;
		
		/**
		 * Broj strukturnih modifikacija izvršenih na tablici u trenutku stvaranja iteratora.
		 */
		private long savedModificationCount;
		
		/**
		 * Konstruktor koji inicijalizira novi iterator i postavlja ga na početak tablice.
		 */
		public IteratorImpl() {
			this.currentIndex = 0;
			lastEntry = null;
			savedModificationCount = modificationCount;
		}
		
		/**
		 * Metoda provjerava ima li još elemenata u tablici koje iterator nije već "potrošio".
		 * 
		 * @return <code>true</code> ako ima još nepredanih elemenata, <code>false</code> inače.
		 * 
		 * @throws ConcurrentModificationException ako je tablica mijenjana izvana otkad je iterator inicijaliziran.
		 */
		public boolean hasNext() {
			if (savedModificationCount != modificationCount) 
				throw new ConcurrentModificationException("Collection has been changed!");
			
			return (currentIndex < size());
			
		}
		
		/**
		 * Metoda vraća sljedeći zapis iz tablice raspršenog adresiranja.
		 * 
		 * @return zapis iz tablice.
		 * 
		 * @throws ConcurrentModificationException ako je tablica mijenjana izvana otkad je iterator inicijaliziran.
		 * @throws NoSuchElementException ako je iterator već prošao sve elemente tablice.
		 */
		public SimpleHashtable.TableEntry<K, V> next() {
			if (savedModificationCount != modificationCount) 
				throw new ConcurrentModificationException("Collection has been changed!");
			if (currentIndex >= size())
				throw new NoSuchElementException("No more elements!");
			
			int i = 0, currentSlot = 0;
			TableEntry<K, V> node = table[currentSlot];
			while (node == null) {
				node = table[++currentSlot];
			}
			while (i < currentIndex) {
				while (node != null) {
					node = node.next;
					i++;
					if (i == currentIndex) {
						break;
					}
				}
				while (node == null) {
					node = table[++currentSlot];
				}
			}
			currentIndex++;
			lastEntry = node;
			return node;
		}
		
		/**
		 * Metoda briše zadnji element koji je iterator dohvatio pozivom <code>next()</code> metode.
		 * 
		 * @throws ConcurrentModificationException ako je tablica mijenjana izvana otkad je iterator inicijaliziran.
		 * @throws IllegalStateException ako <code>next()</code> nije još bila pozvana ili se ova metoda pokuša pozvati više od jednom
		 * nakon poziva <code>next()</code> metode.
		 */
		public void remove() {
			if (savedModificationCount != modificationCount) 
				throw new ConcurrentModificationException("Collection has been changed!");
			if (lastEntry == null) 
				throw new IllegalStateException("Can be called only once after calling the next() method!");
			
			for (int i = 0; i < table.length; i++) {
				TableEntry<K, V> node = table[i];
				while (node == null)
					node = table[++i];
				if (node.getKey().equals(lastEntry.getKey())) {
					lastEntry = null;
					table[i] = node.next;
					size--;
					currentIndex--;
					savedModificationCount++;
					modificationCount++;
					return;
				}
				TableEntry<K, V> nextNode = node.next;
				while (nextNode != null) {
					if (nextNode.getKey().equals(lastEntry.getKey())) {
						node.next = nextNode.next;
						lastEntry =  null;
						size--;
						currentIndex--;
						savedModificationCount++;
						modificationCount++;
						return;
					}
					node = nextNode;
					nextNode = nextNode.next;
				}	
			}
		}
	}
	
	/**
	 * Interno polje za pohranu zapisa.
	 */
	private TableEntry<K, V>[] table;
	
	/**
	 * Veličina tablice, tj. broj trenutno pohranjenih zapisa.
	 */
	private int size;
	
	/**
	 * Broj strukturnih modifikacija izvedenih nad tablicom.
	 */
	private long modificationCount;
		
	/**
	 * Konstruktor koji inicijalizira novu praznu tablicu pretpostavljenog kapaciteta.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		this.table = (TableEntry<K, V>[]) new TableEntry[DEFAULT_CAPACITY];	
		size = 0;
		modificationCount = 0;
	}
	
	/**
	 * Konstruktor koji inicijalizira novu praznu tablicu s kapacitetom koji odgovara prvoj potenciji broja 2 većoj ili jednakoj
	 * predanoj vrijednosti.
	 * @param capacity željeni kapacitet tablice.
	 * 
	 * @throws IllegalArgumentException ako se preda vrijednost manja od 1.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException("Capacity must be at least 1!");
		
		if (capacity != 1 && (capacity & (capacity-1)) != 0) {
			do {
				capacity++;
			} while ((capacity & (capacity-1)) != 0);
		}
		
		size = 0;
		modificationCount = 0;
		
		this.table = (TableEntry<K, V>[]) new TableEntry[capacity];	
	}
	
	/**
	 * Metoda dodaje zapis s predanim ključem i vrijednosti u tablicu. Ako zapis s predanim ključem već postoji, metoda mu postavlja novu vrijednost.
	 * 
	 * @param key ključ zapisa.
	 * @param value vrijednost zapisa.
	 * 
	 * @return stara vrijednost zapisa s predanim ključem, ako on postoji, <code>null</code> inače.
	 * 
	 * @throws NullPointerException ako se kao ključ preda <code>null</code> vrijednost.
	 */
	public V put(K key, V value) {
		if (key == null)
			throw new NullPointerException("Key must not be null!");
		
		resize();
		
		int hash = Math.abs(key.hashCode()) % table.length;
		
		if (table[hash] == null) {
			table[hash] = new TableEntry<>(key, value);
			modificationCount++;
			size++;
			return null;
		}
		
		TableEntry<K, V> node = table[hash];
		while (true) {
			if (node.getKey().equals(key)) {
				V result = node.getValue();
				node.setValue(value);
				return result;
			}
			
			if (node.next == null) {
				break;
			}
			node = node.next;
		}
		node.next = new TableEntry<>(key, value);
		modificationCount++;
		size++;
		
		return null;
	}
	
	/**
	 * Metoda dohvaća vrijednost zapisa s predanim ključem, ako on postoji.
	 * 
	 * @param key ključ traženog zapisa.
	 * 
	 * @return vrijednost zapisa, ako on postoji, <code>null</code> inače.
	 */
	public V get(Object key) {
		if (key == null)
			return null;
		
		int hash = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> node = table[hash];
		
		while (node != null) {
			if (node.getKey().equals(key)) {
				return node.getValue();
			}
			node = node.next;	
		}
		return null;
	}
	
	/**
	 * Metoda vraća veličinu tablice, tj. broj trenutno pohranjenih zapisa.
	 * 
	 * @return broj trenutno pohranjenih zapisa.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Metoda provjerava sadrži li tablica zapis s predanim ključem.
	 * 
	 * @param key traženi ključ.
	 * 
	 * @return <code>true</code> ako sadrži traženi zapis, <code>false</code> inače.
	 */
	public boolean containsKey(Object key) {
		if (key == null)
			return false;
		
		int hash = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> node = table[hash];
		
		while (node != null) {
			if (node.getKey().equals(key))
				return true;
			node = node.next;
		}
		return false;
	}
	
	/**
	 * Metoda provjerava sadrži li tablica zapis s predanom vrijednosti.
	 * 
	 * @param value tražena vrijednost.
	 * 
	 * @return <code>true</code> ako sadrži traženi zapis, <code>false</code> inače.
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> node = table[i];
			while (node != null) {
				if (node.getValue().equals(value))
					return true;
				node = node.next;
			}
		}
		return false;
	}
	
	/**
	 * Metoda briše zapis s predanim ključem iz tablice.
	 * 
	 * @param key ključ zapisa koji se želi izbrisati.
	 * 
	 * @return vrijednost zapisa koji je izbrisan, ako je on postojao, <code>null</code> inače.
	 */
	public V remove(Object key) {
		if (key == null)
			return null;
		
		int hash = Math.abs(key.hashCode()) % table.length;
		
		TableEntry<K, V> node = table[hash];
		if (node.getKey().equals(key)) {
			V result = node.getValue();
			table[hash] = node.next;
			size--;
			modificationCount++;
			return result;
		}
		
		while (node.next != null) {
			if (node.next.getKey().equals(key)) {
				V result = node.next.getValue();
				node.next = node.next.next;
				size--;
				modificationCount++;
				return result;
			}
			node = node.next;
		}		
		
		return null;
	}
	
	/**
	 * Metoda provjerava je li tablica prazna.
	 * 
	 * @return <code>true</code> ako je prazna, <code>false</code> inače.
	 */
	public boolean isEmpty() {
		return (size == 0);
	}
	
	/**
	 * Metoda vraća string formata [ključ1=vrijednost1, ključ2=vrijednost2 ... ].
	 * 
	 * @return zapis tablice u obliku stringa.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		boolean firstElement = true;
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> node = table[i];
			while (node != null) {
				if (firstElement) {
					firstElement = false;
					sb.append(node.toString());
				} else {
					sb.append(", " + node.toString());
				}
				node = node.next;
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Metoda vraća novo polje koje sadrži elemente tablice u istom poretku.
	 * 
	 * @return novo polje koje sadrži elemente tablice.
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K, V>[] toArray() {
		TableEntry<K, V>[] result = (TableEntry<K, V>[]) new TableEntry[size];
		int index = 0;
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> node = table[i];
			while (node != null) {
				result[index++] = node;
				node = node.next;
			}
		}
		return result;
	}
	
	/**
	 * Metoda briše sve zapise iz tablice.
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		modificationCount++;
		this.size = 0;
	}
	
	/**
	 * Metoda provjerava popunjenost tablice i u slučaju velike popunjenosti povećava kapacitet internog polja
	 * radi poboljšanja performansi tablice.
	 */
	@SuppressWarnings("unchecked")
	private void resize() {
		if ((double) this.size / table.length >= 0.75) {
			modificationCount++;
			TableEntry<K, V>[] array = this.toArray();
			table = (TableEntry<K, V>[]) new TableEntry[table.length*2];
			size = 0;
			for (int i = 0; i < array.length; i++) {
				this.put(array[i].getKey(), array[i].getValue());
			}
		}
	}

	/**
	 * Metoda stvara novu instancu iteratora za tablicu.
	 * 
	 * @return nova instanca <code>IteratorImpl</code>.
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}	
}

package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Razred predstavlja model liste prostih brojeva.
 * 
 * @author mskrabic
 *
 */
public class PrimListModel implements ListModel<Integer> {
	/**
	 * Lista dosad generiranih prostih brojeva.
	 */
	private List<Integer> list = new ArrayList<>();
	
	/**
	 * Promatrači liste.
	 */
	private List<ListDataListener> listeners = new ArrayList<>();
	
	/**
	 * Zadnji generirani prosti broj.
	 */
	private int current = 1;
	
	/**
	 * Konstruktor koji inicijalizira model liste i dodaje prvi prosti broj - 1.
	 */
	public PrimListModel() {
		this.list.add(1);
	}
	
	/**
	 * Metoda za generiranje sljedećeg prostog broja.
	 */
	public void next() {
		list.add(getNextPrime());

		ListDataEvent e = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, list.size()-1, list.size()-1);
		for(ListDataListener l : listeners) {
			l.intervalAdded(e);
		}
	}

	/**
	 * Metoda za izračun sljedećeg prostog broja.
	 * 
	 * @return sljedeći prosti broj.
	 */
	private int getNextPrime() {
		current++;
		while (true) {
			int i = 2;
			while (current != i) {
				if (current % i == 0)
					break;
				i++;
			}
			if (current == i) 
				return current;
			current++;
		}
	}

	@Override
	public int getSize() {
		return list.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return list.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);	
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);	
	}

}

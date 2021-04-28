package hr.fer.oprpp1.hw04.db;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Razred predstavlja bazu podataka sa zapisima o studentima.
 * 
 * @author mskrabic
 *
 */
public class StudentDatabase {
	/**
	 * Zapisi baze podataka.
	 */
	private List<StudentRecord> records;
	/**
	 * Mapa koja se koristi za indeksiranje studenata na osnovu njihovog JMBAG-a.
	 */
	private Map<String, StudentRecord> index;
	
	/**
	 * Konstruktor koji iz predane liste stringova gradi zapise o studentima i puni bazu podataka.
	 * 
	 * @param list lista stringova - svaki string predstavlja jedan zapis u bazi.
	 * 
	 * @throws IllegalArgumentException ako se zada neispravna ocjena ili pokuša dodati dva studenta s istim JMBAG-om.
	 */
	public StudentDatabase(List<String> list) {
		records = new LinkedList<>();
		index = new HashMap<>();
		for (String line : list) {
			String[] splitted = line.split("\t");
			StudentRecord r = new StudentRecord(splitted[0], splitted[1], splitted[2], Integer.parseInt(splitted[3]));
			
			if (index.containsKey(r.getJmbag())) {
				throw new IllegalArgumentException("Duplicate JMBAG: " + r.getJmbag());
			}
			if ((r.getFinalGrade() < 1 || r.getFinalGrade() > 5)) {
				throw new IllegalArgumentException("Invalid grade:" + r.getFinalGrade() + " for student with JMBAG: " + r.getJmbag() + ".");
			}
			
			index.put(r.getJmbag(), r);
			records.add(r);
			
		}
	}
	
	/**
	 * Metoda vraća zapis o studentu s predanim JMBAG-om.
	 * 
	 * @param jmbag JMBAG traženog studenta.
	 * 
	 * @return zapis o studentu.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}
	/**
	 * Metoda vraća zapise koji zadovoljavaju predani filter.
	 * 
	 * @param filter filter koji ispituje zapise na osnovu nekog kriterija.
	 * 
	 * @return nova lista sa zapisima koji zadovoljavaju predani filter.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> temp = new LinkedList<>();
		
		for (StudentRecord record : records) {
			if (filter.accepts(record)) {
				temp.add(record);
			}
		}
		
		return temp;
	}

}

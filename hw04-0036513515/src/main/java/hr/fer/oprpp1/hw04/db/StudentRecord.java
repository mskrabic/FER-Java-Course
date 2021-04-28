package hr.fer.oprpp1.hw04.db;

/**
 * Razred modelira zapis studenta u bazi podataka.
 * 
 * @author mskrabic
 *
 */
public class StudentRecord {
	/**
	 * Studentov JMBAG.
	 */
	private String jmbag;
	
	/**
	 * Studentovo ime.
	 */
	private String firstName;
	
	/**
	 * Studentovo prezime.
	 */
	private String lastName;
	
	/**
	 * Studentova konačna ocjena.
	 */
	private int finalGrade;
	
	/**
	 * Konstruktor koji inicijalizira zapis studenta na predane vrijednosti.
	 * 
	 * @param jmbag željeni jmbag.
	 * @param lastName željeno prezime.
	 * @param firstName željeno ime.
	 * @param finalGrade željena konačna ocjena.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.finalGrade = finalGrade;
	}
	
	/**
	 * Metoda vraća studentov JMBAG.
	 * 
	 * @return studentov JMBAG.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Metoda vraća studentovo ime.
	 * 
	 * @return studentovo ime.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Metoda vraća studentovo prezime.
	 * 
	 * @return studentovo prezime.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Metoda vraća studentovu ocjenu.
	 * 
	 * @return studentova ocjena.
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}	
}

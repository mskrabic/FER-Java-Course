package hr.fer.oprpp1.hw04.db.lexer;

/**
 * Razred predstavlja token, tj. leksičku jedinicu.
 * 
 * @author mskrabic
 *
 */
public class QueryToken {
	
	/**
	 * Tip tokena.
	 */
	private QueryTokenType type;
	
	/**
	 * Vrijednost tokena.
	 */
	private String value;
	
	/**
	 * Konstruktor koji incijalizira vrijednosti tokena na predane vrijednosti.
	 * 
	 * @param type vrsta tokena.
	 * @param value vrijednost tokena.
	 */
	public QueryToken(QueryTokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Metoda vraća vrijednost tokena.
	 * 
	 * @return vrijednost tokena.
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Metoda vraća tip (vrstu) tokena.
	 * 
	 * @return tip tokena.
	 */
	public QueryTokenType getType() {
		return this.type;
	}
}

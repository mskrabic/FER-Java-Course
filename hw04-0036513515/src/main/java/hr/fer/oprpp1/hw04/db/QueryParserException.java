package hr.fer.oprpp1.hw04.db;

/**
 * Razred predstavlja iznimku koju <code>QueryParser</code> baca u slučaju bilo kakve pogreške.
 * 
 * @author mskrabic
 *
 */
public class QueryParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public QueryParserException(String msg) {
		super(msg);
	}
	
	public QueryParserException() {
		super("Invalid input!");
	}

}
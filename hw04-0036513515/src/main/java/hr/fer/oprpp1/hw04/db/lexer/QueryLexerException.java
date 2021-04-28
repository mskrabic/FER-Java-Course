package hr.fer.oprpp1.hw04.db.lexer;

/**
 * Razred predstavlja iznimku koju <code>QueryLexer</code> baca u slučaju pogreške.
 * 
 * @author mskrabic
 *
 */
public class QueryLexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public QueryLexerException(String message){
		super(message);
	}

	public QueryLexerException() {
		super("Lexer error!");
	}

}

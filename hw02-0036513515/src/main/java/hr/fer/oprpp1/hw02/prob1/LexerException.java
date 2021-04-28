package hr.fer.oprpp1.hw02.prob1;

/**
 * Razred predstavlja iznimku koju leksički analizator baca u slučaju pogreške.
 * 
 * @author mskrabic
 *
 */
public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public LexerException(String message){
		super(message);
	}

	public LexerException() {
		super("Lexer error!");
	}

}

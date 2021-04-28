package hr.fer.oprpp1.hw02.prob1;

/**
 * Tipovi tokena koje generira <code>Lexer</code>.
 * 
 * @author mskrabic
 *
 */
public enum TokenType {
	/**
	 * Kraj ulaznog teksta.
	 */
	 EOF,
	 /**
	  * Riječ (niz slova).
	  */
	 WORD,
	 /**
	  * Broj prikaziv u tipu Long.
	  */
	 NUMBER,
	 /**
	  * Sve što ostane u tekstu nakon izbacivanja riječi, brojeva i praznina.
	  */
	 SYMBOL;
}

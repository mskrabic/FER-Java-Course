package hr.fer.oprpp1.hw02.prob1;

/**
 * Stanja rada za <code>Lexer</code>.
 * 
 * @author mskrabic
 *
 */
public enum LexerState {
	/**
	 * Osnovno stanje lexera.
	 */
	BASIC,
	/**
	 * Stanje lexera za analizu dijela ulaznog teksta omeđenog znakovima "#".
	 */
	EXTENDED;
}

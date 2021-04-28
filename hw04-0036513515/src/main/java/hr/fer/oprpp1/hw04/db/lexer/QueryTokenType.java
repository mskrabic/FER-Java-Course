package hr.fer.oprpp1.hw04.db.lexer;

/**
 * Tipovi koje <code>QueryToken</code> može poprimiti.
 * 
 * @author mskrabic
 *
 */
public enum QueryTokenType {
	/**
	 * Logički operator AND.
	 */
	AND,
	/**
	 * Kraj ulaznog teksta.
	 */
	EOF,
	/**
	 * Operator usporedbe: >, >=, <, <=, =, != ili LIKE.
	 */
	OPERATOR,
	/**
	 * String literal.
	 */
	STRING, 
	/**
	 * Varijabla: firstName, lastName ili jmbag.
	 */
	VARIABLE;
}

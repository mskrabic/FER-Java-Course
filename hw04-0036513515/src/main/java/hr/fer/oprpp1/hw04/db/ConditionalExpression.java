package hr.fer.oprpp1.hw04.db;

/**
 * Razred modelira jedan logički izraz formata (ATRIBUT) (OPERATOR) (LITERAL).
 * 
 * @author mskrabic
 *
 */
public class ConditionalExpression {
	
	/**
	 * Getter za atribut koji se uspoređuje.
	 */
	private IFieldValueGetter fieldGetter;
	
	/**
	 * String s kojim se atribut uspoređuje.
	 */
	private String stringLiteral;
	
	/**
	 * Operator usporedbe.
	 */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Konstruktor inicijalizira logički uvjet prema predanim vrijednostima.
	 * 
	 * @param getter getter za atribut koji se uspoređuje.
	 * @param literal String s kojim se atribut uspoređuje.
	 * @param operator operator usporedbe.
	 */
	public ConditionalExpression(IFieldValueGetter getter, String literal, IComparisonOperator operator) {
		this.fieldGetter = getter;
		this.stringLiteral = literal;
		this.comparisonOperator = operator;
	}

	/**
	 * Metoda vraća getter za atribut koji se uspoređuje.
	 * 
	 * @return Getter za atribut koji se uspoređuje.
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Metoda vraća String s kojim se atribut uspoređuje.
	 * 
	 * @return String s kojim se atribut uspoređuje.
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Metoda vraća operator usporedbe.
	 * 
	 * @return operator usporedbe.
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}

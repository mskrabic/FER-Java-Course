package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw04.db.lexer.QueryLexer;
import hr.fer.oprpp1.hw04.db.lexer.QueryToken;
import hr.fer.oprpp1.hw04.db.lexer.QueryTokenType;

/**
 * Parser za upite nad bazom podataka.
 * 
 * @author mskrabic
 *
 */
public class QueryParser {
	
	/**
	 * Lista uvjeta od kojih je upit sastavljen.
	 */
	private ArrayList<ConditionalExpression> query;
	
	/**
	 * Lekser koji se koristi za tokenizaciju ulaza.
	 */
	private QueryLexer lexer;
	
	/**
	 * Konstruktor koji inicijalizira lekser i praznu listu uvjeta te započinje parsiranje.
	 * 
	 * @param text tekstualni zapis upita.
	 * 
	 * @throws QueryParserException ako dođe do pogreške pri parsiranju.
	 */
	public QueryParser(String text) {
		this.lexer = new QueryLexer(text);
		this.query = new ArrayList<>();
		
		try {
			parseText();
		} catch (Exception e) {
			throw new QueryParserException(e.getMessage());
		}
	}
	
	/**
	 * Provjerava je li upit direktni upit, tj. oblika jmbag = "xxxxx".
	 * 
	 * @return <code>true</code> ako je upit direktan, <code>false</code> inače.
	 */
	public boolean isDirectQuery() {
		if (query.size() != 1) {
			return false;
		}
		
		ConditionalExpression c = query.get(0);
		if (c.getFieldGetter() != FieldValueGetters.JMBAG || c.getComparisonOperator() != ComparisonOperators.EQUALS) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Ako je upit direktan, vraća JMBAG koji se provjerava u upitu.
	 * 
	 * @return JMBAG iz direktnog upita.
	 * 
	 * @throws IllegalStateException ako upit nije direktan.
	 */
	public String getQueriedJMBAG() {
		if (query.size() == 1) {
			return (query.get(0).getStringLiteral());
		}
		
		throw new IllegalStateException("Not a direct query!");
	}
	
	/**
	 * Metoda vraća uvjete od kojih je sastavljen upit.
	 * 
	 * @return lista uvjeta koji čine upit.
	 */
	public List<ConditionalExpression> getQuery() {
		return query;
	}
	
	/**
	 * Metoda za parsiranje ulaznog teksta.
	 */
	private void parseText() {
		while (lexer.nextToken().getType() != QueryTokenType.EOF) {		
			QueryToken t1 = lexer.getToken();
			QueryToken t2 = lexer.nextToken();
			QueryToken t3 = lexer.nextToken();
			
			if (t1.getType() != QueryTokenType.VARIABLE || t2.getType() != QueryTokenType.OPERATOR || t3.getType() != QueryTokenType.STRING) {
				throw new QueryParserException("Invalid query!");
			}
			
			ConditionalExpression expression = createExpression(t1.getValue(), t2.getValue(), t3.getValue());
			this.query.add(expression);
			
			if (lexer.nextToken().getType() != QueryTokenType.AND) {
				break;
			}
		}
		
	}
	
	/**
	 * Metoda stvara <code>ConditionalExpression</code> na temelju predanih stringova.
	 * 
	 * @param variable atribut (firstName, lastName ili jmbag).
	 * @param operator operator usporedbe.
	 * @param literal String s kojim se uspoređuje atribut.
	 * 
	 * @return <code>ConditionalExpression</code> tj. uvjet sagrađen na temelju predanih stringova.
	 */
	private ConditionalExpression createExpression(String variable, String operator, String literal) {
		IFieldValueGetter getter = parseVariable(variable);
		IComparisonOperator comparisonOperator = parseOperator(operator);
		
		return new ConditionalExpression(getter, literal, comparisonOperator);
	}
	
	/**
	 * Metoda vraća odgovarajući <code>IFieldValueGetter</code> na temelju imena atributa.
	 * 
	 * @param variable ime atributa.
	 * 
	 * @return <code>IFieldValueGetter</code> za predani atribut.
	 * 
	 * @throws QueryParserException ako je predano neispravno ime atributa.
	 */
	private IFieldValueGetter parseVariable(String variable) {
		switch (variable) {
			case "firstName": 
				return FieldValueGetters.FIRST_NAME;
			case "lastName":
				return FieldValueGetters.LAST_NAME;
			case "jmbag":
				return FieldValueGetters.JMBAG;
			default:
				throw new QueryParserException("Invalid variable: " + variable + ".");
		}
	}
	
	/**
	 * Metoda vraća odgovarajući <code>IComparisonOperator</code> za predani operator.
	 * 
	 * @param operator simbol operatora.
	 * 
	 * @return Odgovarajući <code>IComparisonOperator</code> za predani operator.
	 * 
	 * @throws QueryParserException ako je predan neispravan operator.
	 */
	private IComparisonOperator parseOperator(String operator) {
		switch (operator) {
			case ">":
				return ComparisonOperators.GREATER;
			case ">=":
				return ComparisonOperators.GREATER_OR_EQUALS;
			case "<":
				return ComparisonOperators.LESS;
			case "<=":
				return ComparisonOperators.LESS_OR_EQUALS;
			case "=":
				return ComparisonOperators.EQUALS;
			case "!=":
				return ComparisonOperators.NOT_EQUALS;
			case "LIKE":
				return ComparisonOperators.LIKE;
			default:
				throw new QueryParserException("Invalid operator: " + operator + ".");			
		}
	}

}

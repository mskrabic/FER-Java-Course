package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Razred predstavlja implementaciju filtera koji provjerava zadovoljava li predani zapis o studentu
 * sve uvjete iz upita.
 * 
 * @author mskrabic
 *
 */
public class QueryFilter implements IFilter {
	
	/**
	 * Lista uvjeta iz upita.
	 */
	private List<ConditionalExpression> expressions;
	
	/**
	 * Konstruktor koji incijalizira listu uvjeta.
	 * 
	 * @param expressions uvjeti upita.
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		return expressions.stream()
				.allMatch((e) -> e.getComparisonOperator().satisfied(e.getFieldGetter().get(record),e.getStringLiteral()));
	}

}

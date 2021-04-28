package hr.fer.oprpp1.hw04.db;

/**
 * Implementacije <code>IComparisonOperator</code>-a.
 * 
 * @author mskrabic
 *
 */
public class ComparisonOperators {
	/**
	 * Operator manje (<).
	 */
	public static final IComparisonOperator LESS = (v1, v2) -> {
		return (v1.compareTo(v2) < 0);
	};
	
	/**
	 * Operator manje ili jednako (<=).
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> {
		return (v1.compareTo(v2) <= 0);
	};
	
	/**
	 * Operator veće (>).
	 */
	public static final IComparisonOperator GREATER = (v1, v2) -> {
		return (v1.compareTo(v2) > 0);
	};
	
	/**
	 * Operator veće ili jednako (>=).
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> {
		return (v1.compareTo(v2) >= 0);
	};
	
	/**
	 * Operator jednako (=).
	 */
	public static final IComparisonOperator EQUALS = (v1, v2) -> {
		return (v1.compareTo(v2) == 0);
	};
	
	/**
	 * Operator različito (!=).
	 */
	public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> {
		return (v1.compareTo(v2) != 0);
	};
	
	/**
	 * Operator LIKE - dozvoljava upotrebu wildcard (*) simbola.
	 */
	public static final IComparisonOperator LIKE = (v1, v2) -> {
		if (!v2.contains("*")) {
			return (v1.compareTo(v2) == 0);
		}
		
		if (v2.startsWith("*")) {
			return (v1.endsWith(v2.substring(1)));
		}
		if (v2.endsWith("*")) {
			return (v1.startsWith(v2.substring(0, v2.length()-1)));
		}
		
		String[] splitted = v2.split("\\*");
		if (splitted.length != 2)
			throw new IllegalArgumentException("Invalid wildcard: " + v2);
		String prefix = v2.substring(0, v2.indexOf('*'));
		String postfix = v2.substring(v2.indexOf('*')+1);
		return (v1.startsWith(prefix) && v1.endsWith(postfix)
				 && v1.length() >= (prefix.length()+postfix.length()));	
	};
}

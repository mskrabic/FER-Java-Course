package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {
	
	@Test
	public void forJMBAGtest() {
		String[] records = { "0000000001	Akšamović	Marin	2", "0000000002	Bakamović	Petra	3",
				"0000000003	Bosnić	Andrea	4", "0000000004	Božić	Marin	5" };
		List<String> list = Arrays.stream(records).collect(Collectors.toList());
		StudentDatabase db = new StudentDatabase(list);
		
		assertEquals(new StudentRecord("0000000001", "Akšamović", "Marin", 2), db.forJMBAG("0000000001"));
	}
	
	@Test
	public void filtersTest() {
		String[] records = { "0000000001	Akšamović	Marin	2", "0000000002	Bakamović	Petra	3",
				"0000000003	Bosnić	Andrea	4", "0000000004	Božić	Marin	5" };
		List<String> list = Arrays.stream(records).collect(Collectors.toList());
		StudentDatabase db = new StudentDatabase(list);
		
		IFilter f1 = r -> true;
		IFilter f2 = r -> false;
		
		assertEquals(4, db.filter(f1).size());
		assertEquals(0, db.filter(f2).size());
	}
	
	@Test
	public void operatorLessTest() {
		IComparisonOperator o = ComparisonOperators.LESS;
		
		assertTrue(o.satisfied("a", "b"));
		assertFalse(o.satisfied("a", "a"));
		assertFalse(o.satisfied("b", "a"));
	}
	
	@Test
	public void operatorLessOrEqualTest() {
		IComparisonOperator o = ComparisonOperators.LESS_OR_EQUALS;
		
		assertTrue(o.satisfied("a", "b"));
		assertTrue(o.satisfied("a", "a"));
		assertFalse(o.satisfied("b", "a"));	
	}
	
	@Test
	public void operatorGreaterTest() {
		IComparisonOperator o = ComparisonOperators.GREATER;
		
		assertFalse(o.satisfied("a", "b"));
		assertFalse(o.satisfied("a", "a"));
		assertTrue(o.satisfied("b", "a"));	
	}
	
	@Test
	public void operatorGreaterOrEqualTest() {
		IComparisonOperator o = ComparisonOperators.GREATER_OR_EQUALS;
		
		assertFalse(o.satisfied("a", "b"));
		assertTrue(o.satisfied("a", "a"));
		assertTrue(o.satisfied("b", "a"));	
	}
	
	@Test
	public void operatorEqualsTest() {
		IComparisonOperator o = ComparisonOperators.EQUALS;
		
		assertFalse(o.satisfied("a", "b"));
		assertTrue(o.satisfied("a", "a"));
		assertFalse(o.satisfied("b", "a"));	
	}
	
	@Test
	public void operatorNotEqualsTest() {
		IComparisonOperator o = ComparisonOperators.NOT_EQUALS;
		
		assertTrue(o.satisfied("a", "b"));
		assertFalse(o.satisfied("a", "a"));
		assertTrue(o.satisfied("b", "a"));	
		
	}
	
	@Test
	public void operatorLikeTest1() {
		IComparisonOperator o = ComparisonOperators.LIKE;
		
		assertTrue(o.satisfied("Ivana", "Ivana"));
		assertFalse(o.satisfied("Ivana", "ana"));
	}
	
	@Test
	public void operatorLikeTest2() {
		IComparisonOperator o = ComparisonOperators.LIKE;
		
		assertTrue(o.satisfied("Ivana", "*ana"));
		assertFalse(o.satisfied("Ivana", "*an"));
	}
	
	@Test
	public void operatorLikeTest3() {
		IComparisonOperator o = ComparisonOperators.LIKE;
		
		assertTrue(o.satisfied("Ivana", "I*"));
		assertFalse(o.satisfied("Ivana", "Ivi*"));
	}
	
	@Test
	public void operatorLikeTest4() {
		IComparisonOperator o = ComparisonOperators.LIKE;
		
		assertTrue(o.satisfied("Ivana", "Iv*a"));
		assertFalse(o.satisfied("Ivana", "Iva*ana"));
		assertFalse(o.satisfied("AAA", "AA*AA"));
	}
	
	@Test
	public void fieldGettersTest() {
		String[] records = { "0000000001	Akšamović	Marin	2", "0000000002	Bakamović	Petra	3",
				"0000000003	Bosnić	Andrea	4", "0000000004	Božić	Marin	5" };
		List<String> list = Arrays.stream(records).collect(Collectors.toList());
		StudentDatabase db = new StudentDatabase(list);
		
		StudentRecord record = db.forJMBAG("0000000003");
		assertEquals("Andrea", FieldValueGetters.FIRST_NAME.get(record));
		assertEquals("Bosnić", FieldValueGetters.LAST_NAME.get(record));
		assertEquals("0000000003", FieldValueGetters.JMBAG.get(record));
	}
	
	@Test
	public void conditionalExpressionTest() {
		String[] records = { "0000000001	Akšamović	Marin	2", "0000000002	Bakamović	Petra	3",
				"0000000003	Bosnić	Andrea	4", "0000000004	Božić	Marin	5" };
		List<String> list = Arrays.stream(records).collect(Collectors.toList());
		StudentDatabase db = new StudentDatabase(list);
		StudentRecord record1 = db.forJMBAG("0000000003");
		StudentRecord record2 = db.forJMBAG("0000000001");
		
		ConditionalExpression expr = new ConditionalExpression(
											FieldValueGetters.LAST_NAME,
											"Bos*",
											ComparisonOperators.LIKE
										);
		assertTrue(expr.getComparisonOperator()
					.satisfied(expr.getFieldGetter().get(record1), expr.getStringLiteral()));
		assertFalse(expr.getComparisonOperator()
				.satisfied(expr.getFieldGetter().get(record2), expr.getStringLiteral()));
	}
	
	@Test
	public void queryParserTest1() {
		QueryParser qp = new QueryParser(" jmbag =\"0123456789\" ");
		
		assertTrue(qp.isDirectQuery()); // true
		assertEquals("0123456789", qp.getQueriedJMBAG()); // 0123456789
		assertEquals(1, qp.getQuery().size()); // 1	
	}
	
	@Test
	public void queryParserTest2() {
		QueryParser qp = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		
		assertFalse(qp.isDirectQuery()); // false
		assertThrows(IllegalStateException.class,() -> qp.getQueriedJMBAG()); // would throw!
		assertEquals(2, qp.getQuery().size()); // 2
	}
	
	@Test
	public void queryFilterTest() {
		String[] records = { "0000000001	Akšamović	Marin	2", "0000000002	Bakamović	Petra	3",
				"0000000003	Bosnić	Andrea	4", "0000000004	Božić	Marin	5" };
		List<String> list = Arrays.stream(records).collect(Collectors.toList());
		StudentDatabase db = new StudentDatabase(list);
		
		ConditionalExpression c1 = new ConditionalExpression(
				FieldValueGetters.LAST_NAME, "B*", ComparisonOperators.LIKE);
		ConditionalExpression c2 = new ConditionalExpression(
				FieldValueGetters.JMBAG, "0000000003", ComparisonOperators.LESS_OR_EQUALS);
		
		List<ConditionalExpression> expressions = new ArrayList<>();
		expressions.add(c1);
		expressions.add(c2);
		
		IFilter f = new QueryFilter(expressions);
		
		List<StudentRecord> result = db.filter(f);
		
		assertEquals(2, result.size());
		assertEquals(new StudentRecord("0000000002", "Bakamović", "Petra", 3), result.get(0));
		assertEquals(new StudentRecord("0000000003", "Bosnić", "Andrea", 4), result.get(1));
		
	}
	

}

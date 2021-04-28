package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class SimpleHashtableTest {

	@Test
	public void testHashTablePutsValues() {
		SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

		testTable.put("Ivana", 2);
		testTable.put("Ante", 2);
		testTable.put("Jasna", 2);
		testTable.put("Kristina", 2);
		testTable.put("Ivana", 5); // overwrites old grade for Ivana
		testTable.put("Josip", 100);

		assertEquals(2, testTable.get("Kristina"));
		assertEquals(5, testTable.get("Ivana"));
		assertEquals(5, testTable.size());
	}

	@Test
	public void testHashTableToString() {
		SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

		testTable.put("Ivana", 2);
		testTable.put("Ante", 2);
		testTable.put("Jasna", 2);
		testTable.put("Ivana", 5); // overwrites old grade for Ivana

		assertEquals("[Ante=2, Ivana=5, Jasna=2]", testTable.toString());
	}

	@Test
	public void testContainsKey() {
		SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

		testTable.put("Ivana", 2);
		testTable.put("Ante", 2);
		testTable.put("Jasna", 2);
		testTable.put("Kristina", 2);
		testTable.put("Ivana", 5); // overwrites old grade for Ivana
		testTable.put("Josip", 100);

		assertTrue(testTable.containsKey("Kristina"));
	}

	@Test
	public void testContainsValue() {
		SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

		testTable.put("Ivana", 2);
		testTable.put("Ante", 2);
		testTable.put("Jasna", 2);
		testTable.put("Kristina", 2);
		testTable.put("Ivana", 5); // overwrites old grade for Ivana
		testTable.put("Josip", 100);

		assertTrue(testTable.containsValue(100));
		assertFalse(testTable.containsValue("Matej"));
	}

	@Test
	public void testRemoveElement() {
		SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

		testTable.put("Ivana", 2);
		testTable.put("Ante", 2);
		testTable.put("Jasna", 2);
		testTable.put("Kristina", 2);
		testTable.put("Ivana", 5); // overwrites old grade for Ivana
		testTable.put("Josip", 100);

		testTable.remove("Ivana");

		assertFalse(testTable.containsKey("Ivana"));
		assertTrue(testTable.containsKey("Jasna"));
	}

	@Test
	public void testRemoveElement2() {
		SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

		testTable.put("Ivana", 2);
		testTable.put("Ante", 2);
		testTable.put("Jasna", 2);
		testTable.put("Kristina", 2);
		testTable.put("Ivana", 5); // overwrites old grade for Ivana
		testTable.put("Josip", 100);

		testTable.remove("Ivana");

		assertFalse(testTable.containsKey("Ivana"));
		assertFalse(testTable.containsValue(5));
	}

	@Test
	public void testHashtableIteratorInForEach() {
		SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

		testTable.put("Ivana", 2);
		testTable.put("Ante", 2);
		testTable.put("Jasna", 2);
		testTable.put("Kristina", 2);
		testTable.put("Ivana", 5); // overwrites old grade for Ivana
		testTable.put("Josip", 100);

		StringBuilder result = new StringBuilder();

		for (var element : testTable) {
			result.append(element.toString());
		}

		assertEquals("Josip=100Ante=2Ivana=5Jasna=2Kristina=2", result.toString());
	}

	@Test
	public void testHashtableIteratorRemoveValid() {
		SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

		testTable.put("Ivana", 2);
		testTable.put("Ante", 2);
		testTable.put("Jasna", 2);
		testTable.put("Kristina", 2);
		testTable.put("Ivana", 5); // overwrites old grade for Ivana
		testTable.put("Josip", 100);

		var it = testTable.iterator();

		while (it.hasNext()) {
			if (it.next().getKey().equals("Ivana"))
				it.remove();
		}
		assertFalse(testTable.containsKey("Ivana"));
	}

	@Test
	public void testHashtableIteratorNextThrowsException() {
		SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

		testTable.put("Ivana", 2);
		testTable.put("Ante", 2);

		var it = testTable.iterator();

		it.next();
		it.next();

		assertThrows(NoSuchElementException.class, it::next);
	}

	@Test
	public void testHashtableIteratorConcurrentModificationError() {
		SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

		testTable.put("Ivana", 2);
		testTable.put("Ante", 2);

		var it = testTable.iterator();

		it.next();
		testTable.put("Lucija", 2);

		assertThrows(ConcurrentModificationException.class, it::next);
	}

	@Test
	public void testHashtableIteratorRemoveCalledTwiceThrowsException() {
		SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

		testTable.put("Ivana", 2);
		testTable.put("Ante", 2);
		testTable.put("Jasna", 2);
		testTable.put("Kristina", 2);
		testTable.put("Ivana", 5); // overwrites old grade for Ivana
		testTable.put("Josip", 100);

		var it = testTable.iterator();

		assertThrows(IllegalStateException.class, () -> {
			while (it.hasNext()) {
				if (it.next().getKey().equals("Ivana")) {
					it.remove();
					it.remove();
				}
			}
		});
	}

	@Test
	public void testHashtableDoubleIterator() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		var it1 = examMarks.iterator();
		var it2 = examMarks.iterator();

		assertEquals(it1.next(), it2.next());
	}

	@Test
	public void testRemoveThenNext() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		var it = examMarks.iterator();

		assertEquals("Ante", it.next().getKey());
		it.remove();
		assertEquals("Ivana", it.next().getKey());
	}

	@Test
	public void testConcurrentModification() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		assertThrows(ConcurrentModificationException.class, () -> {
			var iter = examMarks.iterator();
			while (iter.hasNext()) {
				SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
				if (pair.getKey().equals("Ivana")) {
					examMarks.remove("Ivana");
				}
			}
		});
	}

}

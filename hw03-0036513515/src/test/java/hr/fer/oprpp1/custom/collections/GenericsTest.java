package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class GenericsTest {
		
		@Test
		public void testConstructorFromOtherCol() {
			LinkedListIndexedCollection<String> col1 = new LinkedListIndexedCollection<>();
			col1.add("Matej");
			col1.add("Ivo");
			col1.add("Pero");
			LinkedListIndexedCollection<String> col2 = new LinkedListIndexedCollection<>(col1);
			
			assertEquals("Matej", col2.get(0));
			assertEquals("Ivo", col2.get(1));
			assertEquals("Pero", col2.get(2));
			assertEquals(3, col2.size());
		}
	    
	    @Test
	    public void testGetMethod() {
	        String test = "Test";
	        ArrayIndexedCollection<String> testCollection = new ArrayIndexedCollection<>();
	        testCollection.add(test);

	        assertEquals(test, testCollection.get(0));
	    }

	    @Test
	    public void testClearMethod() {
	        String test = "Test";
	        ArrayIndexedCollection<String> testCollection = new ArrayIndexedCollection<>();
	        testCollection.add(test);

	        testCollection.clear();
	        assertFalse(testCollection.contains(test));
	    }

	    @Test
	    public void testInsertMethod() {
	        var first = 1;
	        var second = "Second";
	        var third = 3.0;
	        var fourth = "4";

	        var testCollection = new ArrayIndexedCollection<>(5);
	        testCollection.add(first);
	        testCollection.add(second);
	        testCollection.add(third);
	        testCollection.add(fourth);

	        var test = "Test";

	        testCollection.insert(test, 1);
	        var array = testCollection.toArray();

	        var expectedArray = new Object[]{first, test, second, third, fourth};

	        assertArrayEquals(expectedArray, array);

	    }

	    @Test
	    public void testIndexOfMethod() {
	        var test = "Test";
	        var testCollection = new ArrayIndexedCollection<>();
	        testCollection.add(1);
	        testCollection.add(2);
	        testCollection.add(test);
	        testCollection.add(3);

	        assertEquals(2, testCollection.indexOf(test));
	    }

	    @Test
	    public void testRemoveMethod() {
	        var first = 1;
	        var second = "Second";
	        var third = 3.0;
	        var fourth = "4";

	        var testCollection = new ArrayIndexedCollection<>(5);
	        testCollection.add(first);
	        testCollection.add(second);
	        testCollection.add(third);
	        testCollection.add(fourth);

	        testCollection.remove(1);
	        var array = testCollection.toArray();

	        var expectedArray = new Object[]{first, third, fourth};

	        assertArrayEquals(expectedArray, array);
	    }

	    @Test
	    public void testElementsGetter() {
	        Collection<String> col1 = new ArrayIndexedCollection<>();
	        Collection<String> col2 = new ArrayIndexedCollection<>();
	        col1.add("Ivo");
	        col1.add("Ana");
	        col1.add("Jasna");
	        col2.add("Jasmina");
	        col2.add("Štefanija");
	        col2.add("Karmela");
	        ElementsGetter<String> getter1 = col1.createElementsGetter();
	        ElementsGetter<String> getter2 = col1.createElementsGetter();
	        ElementsGetter<String> getter3 = col2.createElementsGetter();

	        String result = "";
	        result += getter1.getNextElement() + " ";
	        result += getter1.getNextElement() + " ";
	        result += getter2.getNextElement() + " ";
	        result += getter3.getNextElement() + " ";
	        result += getter3.getNextElement();

	        assertEquals("Ivo Ana Ivo Jasmina Štefanija", result);
	    }

	    @Test
	    public void testElementsGetterException() {
	        assertThrows(NoSuchElementException.class, () -> {
	            Collection<String> col = new ArrayIndexedCollection<>(); // npr. new ArrayIndexedCollection();
	            col.add("Ivo");
	            col.add("Ana");
	            col.add("Jasna");
	            ElementsGetter<String> getter1 = col.createElementsGetter();
	            @SuppressWarnings("unused")
				String result = "";
	            result += getter1.getNextElement() + " ";
	            result += getter1.getNextElement() + " ";
	            result += getter1.getNextElement() + " ";
	            result += getter1.getNextElement();
	        });
	    }

	    @Test
	    public void hasNextElementsGetterTest() {
	        Collection<String> col = new ArrayIndexedCollection<>(); // npr. new ArrayIndexedCollection();
	        col.add("Ivo");
	        col.add("Ana");
	        col.add("Jasna");
	        ElementsGetter<String> getter = col.createElementsGetter();
	        getter.getNextElement();
	        getter.getNextElement();
	        var result = new boolean[2];
	        result[0] = getter.hasNextElement();
	        getter.getNextElement();
	        result[1] = getter.hasNextElement();
	        assertArrayEquals(new boolean[]{true, false}, result);
	    }

	    @Test
	    public void testElementsGetterThrowsConcurrentModificationException() {
	        assertThrows(ConcurrentModificationException.class, () -> {
	            Collection<String> col = new ArrayIndexedCollection<>();
	            col.add("Ivo");
	            col.add("Ana");
	            col.add("Jasna");
	            ElementsGetter<String> getter = col.createElementsGetter();
	            getter.getNextElement();
	            getter.getNextElement();
	            col.clear();
	            getter.getNextElement();
	        });
	    }
	    
	    @Test
	    public void testAddAllSatisfying() {
	        Collection<Integer> col1 = new LinkedListIndexedCollection<>();
	        Collection<Integer> col2 = new ArrayIndexedCollection<>();
	        col1.add(2);
	        col1.add(3);
	        col1.add(4);
	        col1.add(5);
	        col1.add(6);
	        col2.add(12);
	        col2.addAllSatisfying(col1, v -> v % 2 == 0);

	        StringBuilder sb = new StringBuilder();
	        col2.forEach(sb::append);

	        assertEquals("12246", sb.toString());
	    }
	    
	    @Test
	    public void testAddMethodEmpty() {
	        var testList = new LinkedListIndexedCollection<>();
	        var testElement = "Test";
	        testList.add(testElement);

	        assertTrue(testList.contains(testElement));
	    }

	    @Test
	    public void testAddMethodNotEmpty() {
	        var testList = new LinkedListIndexedCollection<>();
	        testList.add(1);
	        var testElement = "Test";
	        testList.add(testElement);

	        assertArrayEquals(new Object[]{1, testElement}, testList.toArray());
	    }

	    @Test
	    public void testGetMethodCorrectIndexNotEmpty() {
	        var testList = new LinkedListIndexedCollection<>();
	        testList.add(1);
	        var testElement = "Test";
	        testList.add(testElement);

	        assertEquals(testList.get(1), testElement);
	    }

	    @Test
	    public void testGetMethodCorrectIndexEmpty() {
	        var testList = new LinkedListIndexedCollection<>();
	        var testElement = "Test";
	        testList.add(testElement);

	        assertEquals(testList.get(0), testElement);
	    }

	    @Test
	    public void testClearMethodLinkedList() {
	        var testList = new LinkedListIndexedCollection<>();
	        testList.add("1");
	        testList.add(2);

	        testList.clear();
	        assertEquals(0, testList.toArray().length);
	    }

	    @Test
	    public void testInsertMethodLinkedList() {
	        var testList = new LinkedListIndexedCollection<>();
	        testList.add("1");
	        testList.add(2);

	        testList.insert("New", 0);
	        assertArrayEquals(new Object[]{"New", "1", 2}, testList.toArray());
	    }

	    @Test
	    public void testIndexOfMethodLinkedList() {
	        var testList = new LinkedListIndexedCollection<>();
	        testList.add("1");
	        testList.add(2);
	        testList.add("3");

	        assertEquals(1, testList.indexOf(2));
	    }

	    @Test
	    public void testRemoveMethodLinkedList() {
	        var testList = new LinkedListIndexedCollection<>();
	        testList.add("1");
	        testList.add(2);
	        testList.add("3");

	        testList.remove(1);

	        assertArrayEquals(new Object[]{"1", "3"}, testList.toArray());
	    }

	    @Test
	    public void testRemoveMethodWrongArgument() {
	        var testList = new LinkedListIndexedCollection<>();
	        testList.add("1");
	        testList.add(2);
	        testList.add("3");

	        assertThrows(IndexOutOfBoundsException.class, () -> testList.remove(10));
	    }

	    @Test
	    public void testIndexOfMethodDoesntExist() {
	        var testList = new LinkedListIndexedCollection<>();
	        testList.add("1");
	        testList.add(2);
	        testList.add("3");

	        assertEquals(- 1, testList.indexOf(4));
	    }

	    @Test
	    public void testInsertWrongArgumentNullPointer() {
	        var testList = new LinkedListIndexedCollection<>();
	        testList.add("1");
	        testList.add(2);

	        assertThrows(NullPointerException.class, () -> testList.insert(null, 1));
	    }

	    @Test
	    public void testInsertWrongArgumentInvalidIndex() {
	        var testList = new LinkedListIndexedCollection<>();
	        testList.add("1");
	        testList.add(2);

	        assertThrows(IndexOutOfBoundsException.class, () -> testList.insert("3", 10));
	    }

	    @Test
	    public void testGetWrongArgumentInvalidIndex() {
	        var testList = new LinkedListIndexedCollection<>();

	        assertThrows(IndexOutOfBoundsException.class, () -> testList.get(10));
	    }

	    @Test
	    public void testAddWrongArgumentNullPointer() {
	        var testList = new LinkedListIndexedCollection<>();
	        testList.add("1");
	        testList.add(2);

	        assertThrows(NullPointerException.class, () -> testList.add(null));
	    }

	    @Test
	    public void testElementsGetterLinkedList() {
	        Collection<String> col = new LinkedListIndexedCollection<>(); // npr. new ArrayIndexedCollection();
	        col.add("Ivo");
	        col.add("Ana");
	        col.add("Jasna");
	        ElementsGetter<String> getter1 = col.createElementsGetter();
	        ElementsGetter<String> getter2 = col.createElementsGetter();
	        String result = "";
	        result += getter1.getNextElement() + " ";
	        result += getter1.getNextElement() + " ";
	        result += getter2.getNextElement() + " ";
	        result += getter1.getNextElement() + " ";
	        result += getter2.getNextElement();

	        assertEquals("Ivo Ana Ivo Jasna Ana", result);
	    }

	    @Test
	    public void testElementsGetterExceptionLinkedList() {
	        assertThrows(NoSuchElementException.class, () -> {
	            Collection<String> col = new LinkedListIndexedCollection<>(); // npr. new ArrayIndexedCollection();
	            col.add("Ivo");
	            col.add("Ana");
	            col.add("Jasna");
	            ElementsGetter<String> getter1 = col.createElementsGetter();
	            @SuppressWarnings("unused")
				String result = "";
	            result += getter1.getNextElement() + " ";
	            result += getter1.getNextElement() + " ";
	            result += getter1.getNextElement() + " ";
	            result += getter1.getNextElement();
	        });
	    }

	    @Test
	    public void hasNextElementsGetterLinkedListTest() {
	        Collection<String> col = new LinkedListIndexedCollection<>(); // npr. new ArrayIndexedCollection();
	        col.add("Ivo");
	        col.add("Ana");
	        col.add("Jasna");
	        ElementsGetter<String> getter = col.createElementsGetter();
	        getter.getNextElement();
	        getter.getNextElement();
	        var result = new boolean[2];
	        result[0] = getter.hasNextElement();
	        getter.getNextElement();
	        result[1] = getter.hasNextElement();
	        assertArrayEquals(new boolean[]{true, false}, result);
	    }

	    @Test
	    public void testElementsGetterThrowsConcurrentModificationExceptionLinkedList() {
	        assertThrows(ConcurrentModificationException.class, () -> {
	            Collection<String> col = new LinkedListIndexedCollection<>();
	            col.add("Ivo");
	            col.add("Ana");
	            col.add("Jasna");
	            ElementsGetter<String> getter = col.createElementsGetter();
	            getter.getNextElement();
	            getter.getNextElement();
	            col.add("David");
	            getter.getNextElement();
	        });
	    }
	    @Test
	    public void testProcessRemaining() {
	        Collection<String> col = new ArrayIndexedCollection<>();
	        col.add("Ivo");
	        col.add("Ana");
	        col.add("Jasna");
	        ElementsGetter<String> getter = col.createElementsGetter();
	        getter.getNextElement();
	        StringBuilder stringBuilder = new StringBuilder();
	        getter.processRemaining(stringBuilder::append);
	        assertEquals("AnaJasna",stringBuilder.toString());
	    }
}

package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class ArrayIndexedCollectionTest {
	
	@Test
	public void defaultConstructorTest() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(16);
		
		assertArrayEquals(col1.toArray(), col2.toArray());
	}
	
	@Test
	public void constructorShouldThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> {
			ArrayIndexedCollection col = new ArrayIndexedCollection(0);
			col.add("Matej");
		});
	}
	
	@Test
	public void constructorShouldCopyOtherCollection() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		other.add(Integer.valueOf(1));
		other.add(Integer.valueOf(2));
		other.add(Integer.valueOf(3));
		
		ArrayIndexedCollection col = new ArrayIndexedCollection(other);
		
		assertArrayEquals(other.toArray(), col.toArray());
	}
	
	@Test
	public void constructorShouldIncreaseCapacity() {
		ArrayIndexedCollection other = new ArrayIndexedCollection(3);
		other.add(Integer.valueOf(1));
		other.add(Integer.valueOf(2));
		other.add(Integer.valueOf(3));
		
		ArrayIndexedCollection col = new ArrayIndexedCollection(other, 1);
		
		assertArrayEquals(other.toArray(), col.toArray());
	}
	
	@Test
	public void constructorShouldThrowNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			ArrayIndexedCollection col = new ArrayIndexedCollection(null);
			col.add("Matej");
		});
	}
	
	@Test
	public void sizeTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add(Integer.valueOf(1));
		col.add(Integer.valueOf(2));
		
		assertEquals(2, col.size());
	}
	
	@Test
	public void addTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add("Matej");
		assertEquals("Matej", col.toArray()[0]);
	}
	
	@Test
	public void addNullTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		assertThrows(NullPointerException.class, () -> {
			col.add(null);
		});
	}
	
	@Test
	public void containsTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add(Integer.valueOf(1));
		col.add(Integer.valueOf(2));
		col.add(Integer.valueOf(3));
		
		assertTrue(col.contains(Integer.valueOf(1)) && col.contains(Integer.valueOf(2)) && col.contains(Integer.valueOf(3)));
	}
	
	@Test
	public void getTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add(Integer.valueOf(1));
		col.add(Integer.valueOf(2));
		col.add(Integer.valueOf(3));
		
		assertEquals(Integer.valueOf(2), col.get(1));
	}
	
	@Test
	public void getInvalidIndexTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add(Integer.valueOf(1));
		col.add(Integer.valueOf(2));
		col.add(Integer.valueOf(3));
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			col.get(10);
		});
	}
	
	@Test
	public void clearTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add(Integer.valueOf(1));
		col.add(Integer.valueOf(2));
		col.add(Integer.valueOf(3));
		
		ArrayIndexedCollection other = new ArrayIndexedCollection(3);
		col.clear();
		assertTrue(col.size() == 0 && Arrays.equals(col.toArray(), other.toArray()));
	}
	
	@Test
	public void insertTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add(Integer.valueOf(1));
		col.add(Integer.valueOf(2));
		col.add(Integer.valueOf(3));
		
		col.insert(Integer.valueOf(5), 1);
		
		assertEquals(col.get(1), Integer.valueOf(5));
	}
	
	@Test
	public void insertNullTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		
		assertThrows(NullPointerException.class, () -> {
			col.insert(null, 1);
		});	
	}
	
	@Test
	public void insertInvalidIndexTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			col.insert("Matej", 10);
		});
	}
	
	@Test
	public void indexOfTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add(Integer.valueOf(1));
		col.add(Integer.valueOf(2));
		col.add(Integer.valueOf(3));
		
		assertEquals(col.indexOf(Integer.valueOf(3)), 2);
	}
	
	@Test
	public void indexOfNull() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		
		assertEquals(-1, col.indexOf(null));
	}
	
	@Test
	public void removeFromIndexTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add(Integer.valueOf(1));
		col.add(Integer.valueOf(2));
		col.add(Integer.valueOf(3));
		
		col.remove(0);
		
		assertFalse(col.contains(Integer.valueOf(1)));
		assertEquals(col.size(), 2);
	}
	
	@Test
	public void removeElementTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add(Integer.valueOf(1));
		col.add(Integer.valueOf(2));
		col.add(Integer.valueOf(3));
		
		col.remove(Integer.valueOf(2));
		
		assertEquals(col.get(1), Integer.valueOf(3));
		assertEquals(col.size(), 2);
	}
	
	@Test
	public void toArrayTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add(Integer.valueOf(1));
		col.add(Integer.valueOf(2));
		col.add(Integer.valueOf(3));
		
		Object[] arr = {1, 2, 3};
		
		assertArrayEquals(arr, col.toArray());
	}
	
	@Test
	public void forEachTest() {
		class TestProcessor extends Processor {
			int count = 0;
			@Override
			public void process(Object value) {
				count++;
			}
		}
		
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		TestProcessor tp = new TestProcessor();
		col.add(Integer.valueOf(1));
		col.add(Integer.valueOf(2));
		col.add(Integer.valueOf(3));
		col.forEach(tp);
		
		assertEquals(3, tp.count);
	}
	
	@Test
	public void isEmptyTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		
		assertTrue(col.isEmpty());
		col.add(Integer.valueOf(1));
		assertFalse(col.isEmpty());
	}
	
	@Test
	public void addAllTest() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection(3);
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(3);
		col1.add(Integer.valueOf(1));
		col1.add(Integer.valueOf(2));
		col1.add(Integer.valueOf(3));
		
		col2.addAll(col1);
		
		assertArrayEquals(col1.toArray(), col2.toArray());
	}
}
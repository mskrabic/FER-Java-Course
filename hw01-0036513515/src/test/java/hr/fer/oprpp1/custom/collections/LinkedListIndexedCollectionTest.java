package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkedListIndexedCollectionTest {
	
	@Test
	public void defaultConstructorTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		
		assertEquals(col.size(), 0);
	}
	
	@Test
	public void constructorShouldCopyOtherCollection() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		other.add(1);
		other.add(2);
		
		LinkedListIndexedCollection col = new LinkedListIndexedCollection(other);
		
		assertArrayEquals(col.toArray(), other.toArray());
	}
	
	@Test
	public void constructorShouldThrowNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			LinkedListIndexedCollection col = new LinkedListIndexedCollection(null);
			col.add(1);
		});
	}
	
	@Test
	public void sizeTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		
		assertEquals(col.size(), 2);
	}
	
	@Test
	public void isEmptyTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		
		assertTrue(col.isEmpty());
		col.add(Integer.valueOf(1));
		assertFalse(col.isEmpty());
	}
	
	@Test
	public void addTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		
		assertEquals(2, col.get(col.size()-1));
	}
	
	@Test
	public void addNullTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		
		assertThrows(NullPointerException.class, () -> {
			col.add(null);
		});
	}
	
	@Test
	public void containsTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		
		assertFalse(col.contains(1));
		col.add(1);
		assertTrue(col.contains(1));
	}
	
	@Test
	public void containsNullTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		
		assertFalse(col.contains(null));
	}
	
	@Test
	public void removeElementTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		
		class P extends Processor {
			 public void process(Object o) {
			 System.out.println(o);
			 }
			};
		col.forEach(new P());
		System.out.println("-------------");
		
		assertTrue(col.contains(Integer.valueOf(1)));
		col.remove(Integer.valueOf(1));
		
		col.forEach(new P());
		assertFalse(col.contains(Integer.valueOf(1)));
		
	}
	
	@Test
	public void removeFromIndexTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		
		assertTrue(col.contains(Integer.valueOf(1)));
		col.remove(0);
		assertFalse(col.contains(Integer.valueOf(1)));
	}
	
	@Test
	public void toArrayTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		
		Object[] arr = {1, 2, 3};
		
		assertArrayEquals(col.toArray(), arr);
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
		
		TestProcessor tp = new TestProcessor();
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		col.forEach(tp);
		
		assertEquals(3, tp.count);
	}
	
	@Test
	public void clearTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		
		col.clear();
		assertTrue(col.size() == 0);
	}
	
	@Test
	public void getTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		
		assertEquals(col.get(1), 2);
	}
	
	@Test
	public void getInvalidIndexTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			col.get(10);
		});
	}
	
	@Test
	public void insertTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		col.insert(4, 1);
		
		assertEquals(col.get(1), 4);
	}
	
	@Test
	public void insertTest2() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		col.insert(4, 0);
		
		assertEquals(col.get(0), 4);
	}
	
	@Test
	public void insertInvalidIndexTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			col.insert(4, 10);
		});
	}
	
	@Test
	public void indexOfTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		
		assertEquals(col.indexOf(3), 2);
	}
	
	@Test 
	public void indexOfNullTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		
		assertEquals(col.indexOf(null), -1);
	}
	
	@Test
	public void addAllTest() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection();
		col1.add(Integer.valueOf(1));
		col1.add(Integer.valueOf(2));
		col1.add(Integer.valueOf(3));
		
		col2.addAll(col1);
		
		assertArrayEquals(col1.toArray(), col2.toArray());
	}
	
}

package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrimListModelTest {
	private PrimListModel model;
	
	private static PrimListModel newPrimListModel() {
		return new PrimListModel();
	}

	@BeforeEach
	public void setup() {
		model = newPrimListModel();
	}
	
	@Test
	public void newModelTest() {
		assertEquals(model.getSize(), 1);
		assertEquals(model.getElementAt(0), 1);
	}
	
	@Test
	public void nextTest() {
		model.next();
		model.next();
		model.next();
		
		assertEquals(model.getSize(), 4);
		assertEquals(model.getElementAt(0), 1);
		assertEquals(model.getElementAt(1), 2);
		assertEquals(model.getElementAt(2), 3);
		assertEquals(model.getElementAt(3), 5);
	}

}

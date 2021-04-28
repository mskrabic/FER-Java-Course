package hr.fer.oprpp1.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class Vector2DTest {
	@Test
	public void testVectorCreate() {
		var vector = new Vector2D(2.2, 10);

		assertEquals(2.2, vector.getX());
		assertEquals(10, vector.getY());
	}

	@Test
	public void testVectorAdd() {
		var vector = new Vector2D(2.2, 10);
		vector.add(new Vector2D(1.1, 100));

		assertTrue(Math.abs(3.3 - vector.getX()) < 1E-8);
		assertTrue(Math.abs(110 - vector.getY()) < 1E-8);
	}

	@Test
	public void testVectorAdded() {
		var vector = new Vector2D(2.2, 10);
		var newVector = vector.added(new Vector2D(1.1, 100));

		assertTrue(Math.abs(2.2 - vector.getX()) < 1E-8);
		assertTrue(Math.abs(10 - vector.getY()) < 1E-8);

		assertTrue(Math.abs(3.3 - newVector.getX()) < 1E-8);
		assertTrue(Math.abs(110 - newVector.getY()) < 1E-8);
	}

	@Test
	public void testVectorRotate() {
		var vector = new Vector2D(2.2, 0);
		vector.rotate(Math.PI / 2);

		assertTrue(Math.abs(vector.getX()) < 1E-8);
		assertTrue(Math.abs(2.2 - vector.getY()) < 1E-8);
	}

	@Test
	public void testVectorRotate2() {
		var vector = new Vector2D(2.2, 2.2);
		vector.rotate(Math.PI);

		assertTrue(Math.abs(-2.2 - vector.getX()) < 1E-8);
		assertTrue(Math.abs(-2.2 - vector.getY()) < 1E-8);
	}

	@Test
	public void testVectorRotated() {
		var vector = new Vector2D(2.2, 2.2);
		var newVector = vector.rotated(Math.PI);

		assertNotSame(newVector, vector);
		assertTrue(Math.abs(-2.2 - newVector.getX()) < 1E-8);
		assertTrue(Math.abs(-2.2 - newVector.getY()) < 1E-8);
	}

	@Test
	public void testVectorCopy() {
		var vector = new Vector2D(2.2, 2.2);
		var newVector = vector.copy();

		assertNotSame(newVector, vector);
	}

	@Test
	public void testVectorScale() {
		var vector = new Vector2D(2.2, 2.2);
		vector.scale(2);

		assertTrue(Math.abs(4.4 - vector.getX()) < 1E-8);
		assertTrue(Math.abs(4.4 - vector.getY()) < 1E-8);
	}

	@Test
	public void testVectorScaled() {
		var vector = new Vector2D(2.2, 2.2);
		var newVector = vector.scaled(2);

		assertNotSame(newVector, vector);
		assertTrue(Math.abs(4.4 - newVector.getX()) < 1E-8);
		assertTrue(Math.abs(4.4 - newVector.getY()) < 1E-8);
	}
}

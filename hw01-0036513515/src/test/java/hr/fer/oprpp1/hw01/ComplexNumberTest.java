package hr.fer.oprpp1.hw01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComplexNumberTest {
	
	private static final double DELTA = 1E-7;
	
	@Test
	public void constructorTest() {
		ComplexNumber c = new ComplexNumber(1, 1);
		
		assertEquals(c.getReal(), 1);
		assertEquals(c.getImaginary(), 1);
	}
	
	@Test
	public void fromRealTest() {
		ComplexNumber c = ComplexNumber.fromReal(4);
		
		assertEquals(c.getReal(), 4);
		assertEquals(c.getImaginary(), 0);
	}
	
	@Test
	public void fromImaginaryTest() {
		ComplexNumber c = ComplexNumber.fromImaginary(4);
		
		assertEquals(c.getReal(), 0);
		assertEquals(c.getImaginary(), 4);
	}
	
	@Test
	public void fromMagnitudeAndAngleTest() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2),  Math.PI/4);
		
		assertEquals(c.getReal(), 1, DELTA);
		assertEquals(c.getImaginary(), 1, DELTA);
	}
	
	@Test
	public void parseTest() {
		ComplexNumber c = ComplexNumber.parse("7");
		assertEquals(c.getReal(), 7);
		assertEquals(c.getImaginary(), 0);
		
		c = ComplexNumber.parse("3.51");
		assertEquals(c.getReal(), 3.51);
		assertEquals(c.getImaginary(), 0);
		
		c = ComplexNumber.parse("+3.51");
		assertEquals(c.getReal(), 3.51);
		assertEquals(c.getImaginary(), 0);
		
		c = ComplexNumber.parse("-3.17");
		assertEquals(c.getReal(), -3.17);
		assertEquals(c.getImaginary(), 0);
		
		c = ComplexNumber.parse("351i");
		assertEquals(c.getReal(), 0);
		assertEquals(c.getImaginary(), 351);
		
		c = ComplexNumber.parse("-3.17i");
		assertEquals(c.getReal(), 0);
		assertEquals(c.getImaginary(), -3.17);
		
		c = ComplexNumber.parse("-i");
		assertEquals(c.getReal(), 0);
		assertEquals(c.getImaginary(), -1);
		
		c = ComplexNumber.parse("-1-i");
		assertEquals(c.getReal(), -1);
		assertEquals(c.getImaginary(), -1);
		
		c = ComplexNumber.parse("31+24i");
		assertEquals(c.getReal(), 31);
		assertEquals(c.getImaginary(), 24);
		
		c = ComplexNumber.parse("-2.71-3.15i");
		assertEquals(c.getReal(), -2.71);
		assertEquals(c.getImaginary(), -3.15);
		
		c = ComplexNumber.parse("i");
		assertEquals(c.getReal(), 0);
		assertEquals(c.getImaginary(), 1);
		
		c = ComplexNumber.parse("2+3i");
		assertEquals(c.getReal(), 2);
		assertEquals(c.getImaginary(), 3);
		
		c = ComplexNumber.parse("3-i");
		assertEquals(c.getReal(), 3);
		assertEquals(c.getImaginary(), -1);
		
		c = ComplexNumber.parse("+4.13+2i");
		assertEquals(c.getReal(), 4.13);
		assertEquals(c.getImaginary(), 2);
		
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber c2 = ComplexNumber.parse("i35");
			c2.getReal();
		});
		
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber c2 = ComplexNumber.parse("3+-i");
			c2.getReal();
		});
		
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber c2 = ComplexNumber.parse("--5");
			c2.getReal();
		});
		
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber c2 = ComplexNumber.parse("");
			c2.getReal();
		});
	}
	
	@Test
	public void toStringTest() {
		assertEquals(new ComplexNumber(3, 2).toString(), "3.0+2.0i");
	}
	
	@Test
	public void getRealTest() {
		assertEquals(new ComplexNumber(3, 2).getReal(), 3);
	}
	
	@Test
	public void getImaginaryTest() {
		assertEquals(new ComplexNumber(3, 2).getImaginary(), 2);
	}
	
	@Test
	public void getMagnitudeTest() {
		assertEquals(new ComplexNumber(4, 3).getMagnitude(), 5);
	}
	
	@Test
	public void getAngleTest() {
		assertEquals(new ComplexNumber(1, 1).getAngle(), Math.PI/4);
	}

	@Test
	public void addTest() {
		ComplexNumber c1 = new ComplexNumber(4, 4);
		ComplexNumber c2 = new ComplexNumber(2, 3);
		ComplexNumber c3 = c1.add(c2);
		
		assertEquals(c3.getReal(), 6);
		assertEquals(c3.getImaginary(), 7);
	}
	
	@Test
	public void subTest() {
		ComplexNumber c1 = new ComplexNumber(4, 4);
		ComplexNumber c2 = new ComplexNumber(2, 3);
		ComplexNumber c3 = c1.sub(c2);
		
		assertEquals(c3.getReal(), 2);
		assertEquals(c3.getImaginary(), 1);
	}
	
	@Test
	public void mulTest() {
		ComplexNumber c1 = new ComplexNumber(4, 4);
		ComplexNumber c2 = new ComplexNumber(2, 3);
		ComplexNumber c3 = c1.mul(c2);
		
		assertEquals(c3.getReal(), -4);
		assertEquals(c3.getImaginary(), 20);
	}
	
	@Test
	public void divTest() {
		ComplexNumber c1 = new ComplexNumber(4, 4);
		ComplexNumber c2 = new ComplexNumber(2, 2);
		ComplexNumber c3 = c1.div(c2);
		
		assertEquals(c3.getReal(), 2, DELTA);
		assertEquals(c3.getImaginary(), 0, DELTA);
	}
	
	@Test
	public void divideByZeroTest() {
		ComplexNumber c1 = new ComplexNumber(4, 4);
		ComplexNumber c2 = new ComplexNumber(0, 0);
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c3 = c1.div(c2);
			c3.getReal();
		});
	}
	
	@Test
	public void powTest() {
		ComplexNumber c1 = new ComplexNumber(4, 4);
		ComplexNumber c2 = c1.power(2);
		
		
		assertEquals(c2.getReal(), 0, DELTA);
		assertEquals(c2.getImaginary(), 32, DELTA);
	}
	
	@Test
	public void powNegativeExponentTest() {
		ComplexNumber c1 = new ComplexNumber(4, 4);
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c2 = c1.power(-1);
			c2.getReal();
		});	
	}
	
	@Test
	public void rootTest() {
		ComplexNumber c1 = new ComplexNumber(0, 2);
		ComplexNumber[] roots = c1.root(2);
		
		ComplexNumber[] arr = new ComplexNumber[2];
		arr[0] = new ComplexNumber(1, 1);
		arr[1] = new ComplexNumber(-1, -1);
		
		assertEquals(roots[0].getReal(), arr[0].getReal(), DELTA);
		assertEquals(roots[0].getImaginary(), arr[0].getImaginary(), DELTA);
		
		assertEquals(roots[1].getReal(), arr[1].getReal(), DELTA);
		assertEquals(roots[1].getImaginary(), arr[1].getImaginary(), DELTA);
	}
	
	@Test
	public void rootNegativeExponentTest() {
		ComplexNumber c1 = new ComplexNumber(4, 4);
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber[] c2 = c1.root(-1);
			c2[0].getReal();
		});	
	}
}

package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;


public class CalcLayoutTest {

	@Test
	public void CalcLayoutExceptionTest1() {
		CalcLayout cl = new CalcLayout();
		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(new JLabel("prva"), new RCPosition(0, 1));
		});
		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(new JLabel("prva"), new RCPosition(6, 1));
		});
		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(new JLabel("prva"), new RCPosition(1, 0));
		});
		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(new JLabel("prva"), new RCPosition(1, 8));
		});
	}
	
	@Test
	public void CalcLayoutExceptionTest2() {
		CalcLayout cl = new CalcLayout();
		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(new JLabel("prva"), new RCPosition(1, 2));
		});
		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(new JLabel("prva"), new RCPosition(1, 3));
		});
		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(new JLabel("prva"), new RCPosition(1, 4));
		});
		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(new JLabel("prva"), new RCPosition(1, 5));
		});
		
	}
	
	@Test
	public void CalcLayoutExceptionTest3() {
		CalcLayout cl = new CalcLayout();
		cl.addLayoutComponent(new JLabel("prva"), new RCPosition(1,1));
		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(new JLabel("druga"), new RCPosition(1, 1));
		});	
	}
	
	@Test
	public void CalcLayoutPrefSizeTest1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(dim.height, 158);
		assertEquals(dim.width, 152);

	}
	
	@Test
	public void CalcLayoutPrefSizeTest2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(dim.height, 158);
		assertEquals(dim.width, 152);

	}
}

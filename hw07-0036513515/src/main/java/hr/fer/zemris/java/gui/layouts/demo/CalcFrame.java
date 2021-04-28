package hr.fer.zemris.java.gui.layouts.demo;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class CalcFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public CalcFrame() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("CalcFrame");
		setLocation(20, 20);
		setSize(500, 200);
		
		JPanel p = new JPanel();
		//p.setBorder(BorderFactory.createLineBorder(Color.BLUE, 50));
		setContentPane(p);
		
		initGUI();
	}
	
	private void initGUI() {
		Container p = getContentPane();
		p.setLayout(new CalcLayout(3));
		JLabel l1 = new JLabel("x", SwingConstants.CENTER);
		l1.setOpaque(true);
		l1.setBackground(Color.pink);
		JLabel l2 = new JLabel("x",SwingConstants.CENTER);
		l2.setOpaque(true);
		l2.setBackground(Color.pink);
		JLabel l3 = new JLabel("x", SwingConstants.CENTER);
		l3.setOpaque(true);
		l3.setBackground(Color.pink);
		JLabel l4 = new JLabel("x", SwingConstants.CENTER);
		l4.setOpaque(true);
		l4.setBackground(Color.pink);
		JLabel l5 = new JLabel("x", SwingConstants.CENTER);
		l5.setOpaque(true);
		l5.setBackground(Color.pink);
		JLabel l6 = new JLabel("x", SwingConstants.CENTER);
		l6.setOpaque(true);
		l6.setBackground(Color.pink);
		
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(2,3));
		p.add(l3, new RCPosition(2,7));
		p.add(l4, new RCPosition(4,2));
		p.add(l5, new RCPosition(4,5));
		p.add(l6, new RCPosition(4,7));

	
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			CalcFrame frame = new CalcFrame();
			frame.setVisible(true);
		});
	}

}

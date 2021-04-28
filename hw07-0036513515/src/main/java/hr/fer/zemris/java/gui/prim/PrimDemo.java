package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program za prikaz generatora prostih brojeva.
 * Generirani brojevi se prikazuju u dvjema listama.
 * 
 * @author mskrabic
 *
 */
public class PrimDemo extends JFrame {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public PrimDemo() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("PrimDemo");
		initGUI();
		setSize(500, 200);
		setLocationRelativeTo(null);
	}
	
	/**
	 * Metoda za inicijalizaciju grafičkog sučelja programa.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();

		JList<Integer> lista1 = new JList<>(model);
		JList<Integer> lista2 = new JList<>(model);
		lista1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lista2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
//		lista2.setSelectionModel(lista1.getSelectionModel());
		
		JPanel p = new JPanel(new GridLayout(1, 0));
		p.add(new JScrollPane(lista1));
		p.add(new JScrollPane(lista2));
		
		cp.add(p, BorderLayout.CENTER);
		
		JButton buttonNext = new JButton("Sljedeći");
		cp.add(buttonNext, BorderLayout.PAGE_END);
		
		buttonNext.addActionListener(e->{
			model.next();
		});
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			PrimDemo demo = new PrimDemo();
			demo.setVisible(true);
		});
	}
}

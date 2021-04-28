package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program za prikaz stupčastih dijagrama na osnovu konfiguracijske datoteke.
 * 
 * @author mskrabic
 *
 */
public class BarChartDemo extends JFrame {

	/**
	 * Serijski broj.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 * @param path put do konfiguracijske datoteke.
	 * @param comp komponenta za prikaz stupčastog dijagrama.
	 */
	public BarChartDemo(String path, BarChartComponent comp) {
		super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("BarChart");
        setSize(600, 200);
        initGUI(path, comp);
        setLocationRelativeTo(null);
	}

	/**
	 * Metoda za inicijalizaciju grafičkog sučelja prozora.
	 */
	private void initGUI(String path, BarChartComponent comp) {
		Container cp = getContentPane();
		Insets ins = cp.getInsets();
		cp.setLayout(new BorderLayout());
		JLabel pathLabel = new JLabel(path);
		pathLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cp.add(pathLabel, BorderLayout.PAGE_START);
		comp.setOpaque(true);
		comp.setSize(cp.getWidth() - ins.left - ins.right, cp.getHeight() - ins.top - ins.bottom - pathLabel.getHeight());
		cp.add(comp, BorderLayout.CENTER);
	}
	
	/**
	 * Main metoda za pokretanje programa. Prima jedan argument - put do konfiguracijske datoteke.
	 * 
	 * @param args put do konfiguracijske datoteke stupčastog dijagrama.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("This program accepts only one argument: path to the bar chart configuration file!");
			System.exit(1);
		}
		BarChart chart = null;
		try (BufferedReader br = new BufferedReader(
					new InputStreamReader(Files.newInputStream(Paths.get(args[0]), StandardOpenOption.READ)))) {
			String descX = br.readLine();
			String descY = br.readLine();
			String values = br.readLine();
			List<XYValue> list = new ArrayList<>();
			for (String v : values.split("\\s+")) {
				String[] splitted = v.split(",");
				list.add(new XYValue(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1])));
			}
			int minY = Integer.parseInt(br.readLine());
			int maxY = Integer.parseInt(br.readLine());
			int gap = Integer.parseInt(br.readLine());
			
			chart = new BarChart(list, descX, descY, minY, maxY, gap);
		} catch (IOException e) {
			System.out.println("Error while reading from file: " + args[0]);
			System.exit(1);
		}
		
		if (chart == null) {
			throw new RuntimeException("Chart was not properly initialized!");
		}
		BarChartComponent comp = new BarChartComponent(chart);
		SwingUtilities.invokeLater(() -> {
			BarChartDemo demo = new BarChartDemo(args[0], comp);
			demo.setVisible(true);
		});
	}
}

package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.util.stream.Collectors;

import javax.swing.JComponent;

/**
 * Razred predstavlja grafičku komponentu za prikaz stupčastog dijagrama.
 * 
 * @author mskrabic
 *
 */
public class BarChartComponent extends JComponent {
	
	/**
	 * Stupčasti dijagram koji se prikazuje.
	 */
	private BarChart chart;
	
	/**
	 * Konstanta koja se koristi za izračun razmaka između opisa uz pojedinu koordinatnu os i vrijednosti koordinatne osi.
	 */
	private static final int GAP = 3;
	
	/**
	 * Konstanta koja određuje veličinu strelica na osima dijagrama.
	 */
	private static final int ARROW_SPACE = 3 * GAP;
	
	/**
	 * Konstanta koja određuje polovicu duljine "oznake" na osima. (crtica koja označava mjesto vrijednosti na osi).
	 */
	private static final int AXIS_MARK_HALFLENGTH = 3;

	/**
	 * Serijski broj.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 * @param chart stupčasti dijagram koji se želi prikazati.
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Insets ins = getInsets();
		Graphics2D g2d = (Graphics2D) g;
		Font f = g2d.getFont();
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform((AffineTransform.getQuadrantRotateInstance(3)));
		g2d.drawString(chart.getDescY(), -((getHeight()-ins.top-ins.bottom)/2+g2d.getFontMetrics().stringWidth(chart.getDescY())/2), ins.left+getFont().getSize());
		g2d.setTransform(saveAT);
		g2d.drawString(chart.getDescX(), (getWidth()-ins.left-ins.right)/2-g2d.getFontMetrics().stringWidth(chart.getDescX())/2, getHeight()-ins.bottom -getFont().getSize()/2);
		
		String longestYValue = "";
		int numberOfBars = chart.getValues().stream().map(v -> v.getX()).distinct().mapToInt(x -> 1).sum();
		for (XYValue v : chart.getValues()) {
			if (longestYValue.length() == 0 || String.valueOf(v.getY()).length() > longestYValue.length())
				longestYValue = String.valueOf(v.getY());
		}
		int xAxis = getHeight()-ins.bottom-5*getFont().getSize()/2 - GAP - AXIS_MARK_HALFLENGTH;
		int yAxis = ins.left + getFont().getSize() + 3* GAP + g2d.getFontMetrics().stringWidth(longestYValue) + AXIS_MARK_HALFLENGTH;		
		int barWidth = (getWidth() - ins.left - ins.right - ARROW_SPACE - yAxis) / numberOfBars;
		g2d.drawLine(yAxis, xAxis, yAxis, ARROW_SPACE);
		g2d.drawLine(yAxis, xAxis, getWidth() - ARROW_SPACE, xAxis);
		
		int yDistance = (xAxis-5*GAP)/((chart.getMaxY()-chart.getMinY())/chart.getGap());
		
		for (int i = chart.getMinY(); i <= chart.getMaxY(); i += chart.getGap()) {
			g2d.setFont(f.deriveFont(Font.BOLD));
			g2d.drawString(String.valueOf(i), yAxis - AXIS_MARK_HALFLENGTH - g2d.getFontMetrics().stringWidth(String.valueOf(i)) - GAP, xAxis - (i - chart.getMinY())/chart.getGap()*yDistance + getFont().getSize()/2);
			g2d.setFont(f);
			g2d.drawLine(yAxis-AXIS_MARK_HALFLENGTH, xAxis - (i - chart.getMinY())/chart.getGap()*yDistance, yAxis+AXIS_MARK_HALFLENGTH,  xAxis - (i - chart.getMinY())/chart.getGap()*yDistance);
		}
		
		int i = 0;
		for (XYValue v : chart.getValues().stream().sorted((v1, v2) -> Integer.compare(v1.getX(), v2.getX())).collect(Collectors.toList())) {
			int barHeight = (v.getY()-chart.getMinY())/chart.getGap()*yDistance;
			g2d.setColor(Color.BLACK);
			g2d.drawRect(yAxis + (i)*barWidth, xAxis-barHeight, barWidth, barHeight);
			g2d.setFont(f.deriveFont(Font.BOLD));
			g2d.drawString(String.valueOf(v.getX()),yAxis+(2*i+1)*barWidth/2, xAxis + getFont().getSize() + AXIS_MARK_HALFLENGTH);
			g2d.setFont(f);
			g2d.setColor(Color.ORANGE);
			g2d.fillRect(yAxis + 1 + (i)*barWidth, xAxis-barHeight+1, barWidth-1, barHeight-1);
			i++;
		}
		g2d.setColor(Color.BLACK);
		g2d.drawPolygon(new int[] {yAxis-AXIS_MARK_HALFLENGTH, yAxis, yAxis+AXIS_MARK_HALFLENGTH}, new int[] {ARROW_SPACE, ARROW_SPACE/3, ARROW_SPACE}, 3);
		g2d.fillPolygon(new int[] {yAxis-AXIS_MARK_HALFLENGTH,yAxis, yAxis+AXIS_MARK_HALFLENGTH}, new int[] {ARROW_SPACE, ARROW_SPACE/3, ARROW_SPACE}, 3);
		g2d.drawPolygon(new int[] {getWidth()-ARROW_SPACE, getWidth()-ARROW_SPACE/3, getWidth()-ARROW_SPACE}, new int[] { xAxis-AXIS_MARK_HALFLENGTH, xAxis, xAxis+AXIS_MARK_HALFLENGTH}, 3);
		g2d.fillPolygon(new int[] {getWidth()-ARROW_SPACE, getWidth()-ARROW_SPACE/3, getWidth()-ARROW_SPACE}, new int[] { xAxis-AXIS_MARK_HALFLENGTH, xAxis, xAxis+AXIS_MARK_HALFLENGTH}, 3);
	}

}

package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;

/**
 * Layout manager za raspored komponenti pri prikazu kalkulatora modeliranog razredom {@link Calculator}.
 * 
 * @author mskrabic
 *
 */
public class CalcLayout implements LayoutManager2 {
	
	/**
	 * Broj redaka kalkulatora.
	 */
	private static final int ROWS = 5;
	
	/**
	 * Broj stupaca kalkulatora.
	 */
	private static final int COLUMNS = 7;
	
	/**
	 * Razmak između komponenti kalkulatora.
	 */
	private int gap;
	
	/**
	 * Komponente kalkulatora i njihove pozicije.
	 */
	private HashMap<Component, RCPosition> grid;
	
	/**
	 * Konstruktor.
	 * 
	 * @param gap željeni razmak između komponenti kalkulatora.
	 */
	public CalcLayout(int gap) {
		super();
		this.gap = gap;
		this.grid = new HashMap<>();
	}
	
	/**
	 * Pretpostavljeni konstruktor - postavlja razmak na 0.
	 */
	public CalcLayout() {
		this(0);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		this.grid.remove(comp);	
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return layoutSize(parent, "preferred");
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return layoutSize(target, "maximum");
	}
	
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return layoutSize(parent, "minimum");
	}
	
	/**
	 * Pomoćna metoda koja ovisno o parametru <code>option</code> računa preferiranu, maksimalnu ili minimalnu veličinu razmještaja.
	 * 
	 * @param parent roditelj.
	 * @param option opcija veličine: 'preferred', 'maximum' ili 'minimum'.
	 * 
	 * @return tražena dimenzija razmještaja.
	 */
	private Dimension layoutSize(Container parent, String option) {
		int maxWidth = -1, maxHeight = -1;
		
		for (Component c: parent.getComponents()) {
			Dimension size;
			
			switch (option) {
			case "preferred":
				size = c.getPreferredSize();
				break;
			case "maximum":
				size = c.getMaximumSize();
				break;
			case "minimum":
				size = c.getMinimumSize();
				break;
			default: throw new IllegalArgumentException("Allowed options are 'preferred', 'maximum' or 'minimum'!");		
			}
			
			RCPosition pos = grid.get(c);
			if (pos == null)
				continue;
			if (pos.getRow() == 1 && pos.getColumn() == 1) {
				size.width = (size.width - (COLUMNS-3) * gap)/(COLUMNS-2);
			}
			
			if (maxWidth == -1 || size.width > maxWidth) {
				maxWidth = size.width;
			}
			if (maxHeight == -1 || size.height > maxHeight) {
				maxHeight = size.height;
			}
		}
		Insets ins = parent.getInsets();
		
		int layoutWidth = (COLUMNS - 1) * gap + COLUMNS * maxWidth + ins.left + ins.right;
		int layoutHeight = (ROWS - 1) * gap + ROWS * maxHeight + ins.top + ins.bottom;
		
		return new Dimension(layoutWidth, layoutHeight);
	}

	@Override
	public void layoutContainer(Container parent) {
			Insets ins = parent.getInsets();
			
			double columnWidth = (double)(parent.getWidth() - ins.left - ins.right - (COLUMNS-1) * gap)/COLUMNS;
			double rowHeight = (double)(parent.getHeight() - ins.top - ins.bottom - (ROWS-1) * gap)/ROWS;
			
//			int widerColumns = getNumberOfLargerElements(columnWidth, COLUMNS);
//			int largerRows = getNumberOfLargerElements(rowHeight, ROWS);
			
			for (Component c : parent.getComponents()) {
				RCPosition pos = grid.get(c);
				int row = pos.getRow();
				int column = pos.getColumn();
				
				double x = ins.left + (column-1) * (gap + columnWidth);
				double y = ins.top + (row - 1) * (gap + rowHeight);
				
				if (row == 1 && column == 1) {
					int width = (int)((COLUMNS-2)*columnWidth + (COLUMNS-3) * gap);
					c.setBounds((int)x, (int)y, width, (int)rowHeight);
					continue;
				}
				
				c.setBounds((int)x, (int)y, (int)columnWidth, (int)rowHeight);
			}
		}			
	
	@Override
	public void addLayoutComponent(Component comp, Object constraints) 	{
		if (comp == null || constraints == null) {
			throw new NullPointerException("Component and constraints cannot be null!");
		}
		RCPosition pos = validatePosition(constraints);	
		this.grid.put(comp, pos);
	}

	/**
	 * Pomoćna metoda koja provjerava je li predana pozicija ispravna.
	 * 
	 * @param constraints željena pozicija komponente.
	 * 
	 * @throws IllegalArgumentException ako nije predan ni {@link RCPosition} ni string koji se u njega može parsirati.
	 * @throws CalcLayoutException ako je zadana neispravna pozicija.
	 * 
	 * @return pozicija komponente, ako je ispravna. 
	 */
	private RCPosition validatePosition(Object constraints) {
		if (!(constraints instanceof String || constraints instanceof RCPosition))
			throw new IllegalArgumentException("Invalid constraint!");
		
		RCPosition pos;
		if (constraints instanceof String) {
			pos = RCPosition.parse((String)constraints);
		} else {
			pos = (RCPosition) constraints;
		}
		int row = pos.getRow();
		int column = pos.getColumn();
		
		if (row < 1 || row > 5 || column < 1 || column > 7)
			throw new CalcLayoutException("Invalid position: (" + row + "," + column + ").");
		if (row == 1 && column > 1 && column < 6)
			throw new CalcLayoutException("Positions (1, 1) to (1,5) are reserved for the component at position (1, 1).");
		if (this.grid.containsValue(pos))
			throw new CalcLayoutException("That position is already occupied!");
		
		return pos;
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	
	}
	
//	private int getNumberOfLargerElements(double size, int numberOfElements) {
//		double counter = Math.abs(size*numberOfElements - Math.round(size)*numberOfElements);
//		System.out.println(counter);
//		return (int)counter;
//	}

}

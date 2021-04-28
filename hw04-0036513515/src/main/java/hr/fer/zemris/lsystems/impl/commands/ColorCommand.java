package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.zemris.lsystems.Painter;

/**
 * Naredba koja postavlja boju kornjače.
 * 
 * @author mskrabic
 *
 */
public class ColorCommand implements Command{
	
	/**
	 * Boja kornjače.
	 */
	private Color color;
	
	/**
	 * Konstruktor koji postavlja boju naredbi.
	 * 
	 * @param color željena boja.
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}

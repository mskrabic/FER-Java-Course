package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.zemris.lsystems.Painter;

/**
 * Naredba skalira efektivnu duljinu koraka kornjače za predani faktor.
 * 
 * @author mskrabic
 *
 */
public class ScaleCommand implements Command {

	/**
	 * Faktor za koji se želi skalirati kornjačina duljina koraka.
	 */
	private double factor;
	
	/**
	 * Konstruktor koji postavlja faktor na predanu vrijednost.
	 * 
	 * @param factor željeni faktor.
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().updateUnitLength(factor);
		
	}

}

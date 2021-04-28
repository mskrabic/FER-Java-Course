package hr.fer.oprpp1.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Sučelje modelira naredbu koja se zadaje kornjači.
 * 
 * @author mskrabic
 *
 */
public interface Command {
	
	/**
	 * Metoda izvršava naredbu koristeći predani <code>Context</code> i <code>Painter</code>.
	 * 
	 * @param ctx kontekst za prikazivanje fraktala.
	 * @param painter objekt kojim je moguće crtati po prozoru.
	 */
	public void execute(Context ctx, Painter painter);
}

package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.zemris.lsystems.Painter;

/**
 * Naredba koja preskače zadanu udaljenost, bez crtanja.
 * 
 * @author mskrabic
 *
 */
public class SkipCommand implements Command {
	
	/**
	 * Udaljenost koju treba preskočiti.
	 */
	private double step;

	/**
	 * Konstruktor koji inicijalizira udaljenost.
	 * 
	 * @param step udaljenost koju treba preskočiti.
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().updatePosition(ctx.getCurrentState().getDirection().scaled(step));
	}

}

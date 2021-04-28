package hr.fer.zemris.lsystems.impl.commands;


import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.oprpp1.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.Painter;

/**
 * Naredba koja crta liniju od trenutne pozicije do predane i ažurira stanje kornjače.
 * 
 * @author mskrabic
 *
 */
public class DrawCommand implements Command {
	/**
	 * Korak koji kornjača treba napraviti.
	 */
	private double step;
	
	/**
	 * Pretpostavljena širina linije.
	 */
	private final static float LINE_WIDTH = 1f;
	
	/**
	 * Konstruktor koji postavlja korak kornjače.
	 * 
	 * @param step željeni korak kornjače.
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState oldState = ctx.getCurrentState().copy();
		ctx.getCurrentState().updatePosition(ctx.getCurrentState().getDirection().scaled(step*ctx.getCurrentState().getUnitLength()));
		
		painter.drawLine(oldState.getPosition().getX(), oldState.getPosition().getY(),
				ctx.getCurrentState().getPosition().getX(), ctx.getCurrentState().getPosition().getY(), ctx.getCurrentState().getColor(), LINE_WIDTH);
		
		
	}

}

package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.zemris.lsystems.Painter;

/**
 * Naredba koja rotira kornjaču za zadani kut.
 * 
 * @author mskrabic
 *
 */
public class RotateCommand implements Command{
	/**
	 * Kut rotacije.
	 */
	private double angle;
	
	/**
	 * Konstruktor kojim se postavlja kut rotacije.
	 * 
	 * @param angle željeni kut rotacije.
	 */
	public RotateCommand(double angle) {
		this.angle = angle * Math.PI / 180;
	}
	

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().rotate(angle);
	}

}

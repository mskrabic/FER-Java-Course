package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.zemris.lsystems.Painter;

/**
 * Naredba koja briše jedno stanje s vrha stoga.
 * 
 * @author mskrabic
 *
 */
public class PopCommand implements Command{

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}

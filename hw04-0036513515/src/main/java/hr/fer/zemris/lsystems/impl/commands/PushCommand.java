package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.zemris.lsystems.Painter;

/**
 * Naredba koja kopira stanje s vrha stoga i kopiju stavlja na stog.
 * 
 * @author mskrabic
 *
 */
public class PushCommand implements Command{

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}

}

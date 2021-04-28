package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Naredba za prekid rada konzole.
 * 
 * @author mskrabic
 *
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * Metoda prekida rad konzole.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.length() > 0) {
			env.writeln("exit command takes no arguments!");
			env.writeln(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("Terminates the shell.");
		
		return Collections.unmodifiableList(desc);
	}

}

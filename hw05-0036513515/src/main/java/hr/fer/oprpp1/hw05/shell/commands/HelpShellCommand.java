package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Naredba za pomoć pri radu s {@link MyShell}-om.
 * 
 * @author mskrabic
 *
 */
public class HelpShellCommand implements ShellCommand {

	/**
	 * Ako ne primi nijedan argument, metoda ispisuje sve dostupne naredbe.
	 * Ako primi ispravno ime naredbe, ispisuje njen opis.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.length() == 0) {
			env.writeln("Available commands:");
			env.commands().keySet().forEach(c -> env.writeln(c));
		} else {
			ShellCommand c = env.commands().get(arguments);
			if (c == null) {
				env.writeln("Invalid command: " + arguments);
			} else {
				env.writeln(c.getCommandName());
				c.getCommandDescription().forEach(l -> env.writeln(l));
			}
		}
		
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Prints usage instructions for given command.");
		desc.add("If no arguments are given, prints available commands.");
		
		return Collections.unmodifiableList(desc);
		
	}

}

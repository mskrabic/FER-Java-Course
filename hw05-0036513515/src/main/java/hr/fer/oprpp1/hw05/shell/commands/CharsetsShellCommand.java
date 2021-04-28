package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Naredba koja ispisuje dostupne kodne stranice.
 * 
 * @author mskrabic
 *
 */
public class CharsetsShellCommand implements ShellCommand{

	/**
	 * Metoda ispisuje dostupne kodne stranice. Ne prima nikakve argumente.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.length() > 0) {
			env.writeln("charsets command takes no arguments!");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		Charset.availableCharsets().keySet().forEach(cs -> env.writeln(cs));
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Prints available charsets.");
		
		return Collections.unmodifiableList(desc);
	}

}

package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.Util;

/**
 * Naredba za stvaranje direktorija.
 * 
 * @author mskrabic
 *
 */
public class MkdirShellCommand implements ShellCommand {

	/**
	 * Metoda stvara direktorij.
	 * Prima jedan parametar koji predstavlja odredište na kojem se želi stvoriti direktorij.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] splitted;
		try {
			splitted = Util.parseArgs(arguments);
			if (splitted.length != 1) 
				throw new ShellIOException();
			Files.createDirectories(Paths.get(splitted[0]));
		} catch (IOException e) {
			env.writeln("Unable to create directory " + arguments);
		} catch (ShellIOException e) {
			env.writeln("Invalid argument for mkdir command.");
		}
		
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("Creates a directory with the given name.");
		
		return Collections.unmodifiableList(desc);
	}

}

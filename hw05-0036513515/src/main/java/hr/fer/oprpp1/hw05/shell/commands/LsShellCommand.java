package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
import java.nio.file.Path;
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
 * Naredba za listanje sadržaja direktorija.
 * 
 * @author mskrabic
 *
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * Metoda izlistava (nerekurzivno) sadržaj datoteke.
	 * Prima jedan parametar koji predstavlja put do direktorija.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] splitted;
		try {
			splitted = Util.parseArgs(arguments);			
		} catch (ShellIOException e) {
			splitted = new String[0];
		}
		if (splitted.length != 1) {
			env.writeln("Invalid arguments for ls command.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		Path path = Paths.get(splitted[0]);
		if (!path.toFile().exists()) {
			env.writeln("Can't find file!");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		if (!path.toFile().isDirectory()) {
			env.writeln("Given file is not a directory!");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		for (File f : path.toFile().listFiles()) {
			try {
				String line = Util.formatFile(f.toPath());
				env.writeln(line);
			} catch (RuntimeException e) {
				env.write("Error while listing files.");
			}

		}
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Writes a non-recursive directory listing.");
		desc.add("The argument is the path to the directory.");
		
		return Collections.unmodifiableList(desc);
	}

}

package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.Util;

/**
 * Naredba koja ispisuje sadržaj datoteke.
 * 
 * @author mskrabic
 *
 */
public class CatShellCommand implements ShellCommand {

	/**
	 * Metoda ispisuje sadržaj datoteke.
	 * Prvi parametar je obavezan i predstavlja put do datoteke.
	 * Drugi parametar je opcionalan i predstavlja kodnu stranicu koji se treba koristiti za dekodiranje datoteke,
	 * ako se ne preda koristi se pretpostavljena kodna stranica.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] splitted;
		try {
			splitted = Util.parseArgs(arguments);
		} catch (ShellIOException e) {
			splitted = new String[0];
		}	 
		if (splitted.length < 1 || splitted.length > 2) {
			env.writeln("Invalid arguments for cat command.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		String pathName = splitted[0];
		String cs = splitted.length == 1 ? null : splitted[1];
		Path path = Paths.get(pathName).toAbsolutePath();
		
		if (!path.toFile().isFile()) {
			env.writeln("Passed argument is not a file!");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		Charset charset;
		if (cs == null) {
			charset = Charset.defaultCharset();
		} else {
			charset = Charset.availableCharsets().getOrDefault(cs, Charset.defaultCharset());
		}
		
		try(InputStream is =  Files.newInputStream(path)) {
			byte[] buff = new byte[4096];
			int k;
			while (true) {
				k = is.read(buff);
				if (k == -1)
					break;
				String s = new String(Arrays.copyOf(buff, k), charset);
				env.write(s);
			}
			env.writeln("");

		} catch (IOException e) {
			env.writeln("Error while reading file: " + path.getFileName());
		}
		
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Prints the contents of the given file.");
		desc.add("First argument is the path to the file");
		desc.add("Second argument is the charset to be used for decoding the file. If ommited, default charset is used.");
		
		return Collections.unmodifiableList(desc);
	}

}

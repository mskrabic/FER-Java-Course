package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
 * Naredba za kopiranje datoteke.
 * 
 * @author mskrabic
 *
 */
public class CopyShellCommand implements ShellCommand {

	/**
	 * Metoda za kopiranje datoteke.
	 * Prvi parametar je put do ulazne datoteke (koja se kopira).
	 * Drugi parametar je odredište na koje se želi kopirati.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] splitted;
		try {
			splitted = Util.parseArgs(arguments);
		} catch (ShellIOException e) {
			splitted = new String[0];
		}
		if (splitted.length != 2) {
			env.writeln("Invalid arguments for copy command!");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		Path source = Paths.get(splitted[0]);
		Path dest = Paths.get(splitted[1]);
		
		if (!source.toFile().exists()) {
			env.writeln("Source file cannot be found!");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
			
		}
		if (!source.toFile().isFile()) {
			env.writeln("Source is not a file!");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		if (dest.toFile().isDirectory()) {
			dest = Paths.get(dest.toAbsolutePath().toString() + "\\" + source.getFileName().toString());
		}
		if (source.toAbsolutePath().toString().equals(dest.toAbsolutePath().toString())) {
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		if (dest.toFile().exists()) {
			env.writeln("Destination file already exists. Do you want to overwrite it? (Y/N)");
			env.write(env.getPromptSymbol() + " ");
			String decision = env.readLine();
			if (decision.equalsIgnoreCase("N")) {
				env.writeln("Aborted.");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}
		}
		
		try(InputStream is = Files.newInputStream(source); OutputStream os = Files.newOutputStream(dest)) {
			byte[] buff = new byte[4096];
			while (true) {
				int k = is.read(buff);
				if (k == -1)
					break;
				os.write(Arrays.copyOf(buff, k));
			}
		} catch (IOException e) {
			env.writeln("Error copying the file.");
		}
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Copies the source file to the destination");
		desc.add("If destination file already exists, the user can choose to overwrite or quit.");
		desc.add("First argument is the path to the source file.");
		desc.add("Second argument is the path to the destination file.");
		
		return Collections.unmodifiableList(desc);
	}

}

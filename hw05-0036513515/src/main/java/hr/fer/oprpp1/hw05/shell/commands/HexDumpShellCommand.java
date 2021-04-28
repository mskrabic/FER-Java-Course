package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.io.InputStream;
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
 * Naredba koja ispisuje heksadekadski i znakovni sadržaj datoteke.
 * 
 * @author mskrabic
 *
 */
public class HexDumpShellCommand implements ShellCommand {

	/**
	 * Metoda ispisuje heksadekadski i znakovni sadržaj datoteke.
	 * Prima jedan parametar koji predstavlja put do datoteke.
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
			env.writeln("Invalid arguments for hexdump command");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		Path path = Paths.get(splitted[0]);
		try(InputStream is = Files.newInputStream(path)) {
			byte[] buff = new byte[16];
			int k;
			int index = 0x0;
			StringBuilder sb = new StringBuilder();
			while (true) {
				 k = is.read(buff);
				 if (k == -1)
					 break;
				 
				 sb.append(String.format("%08X", index));
				 sb.append(": ");
				 for (int i = 0; i < 16; i++) {
					 if (i >= k) {
						 sb.append("   ");
					 } else {
						 sb.append(String.format("%02X", buff[i]));
						 sb.append(i == 7 ? "|" : " ");
					 }
				 }
				 sb.append("| ");
				 for (int i = 0; i < buff.length; i++) {
					 if (buff[i] < 32 || buff[i] > 127)
						 buff[i] = 46;
				 }
				 String s = new String(Arrays.copyOf(buff, k));
				 sb.append(s);
				 env.writeln(sb.toString());
				 index  += 0x10;
				 sb.setLength(0);
			}
		} catch (IOException e) {
			env.writeln("Error while reading file.");
		}
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Prints usage instructions for given command.");
		desc.add("If no arguments are given, prints available commands.");
		
		return Collections.unmodifiableList(desc);
	}

}

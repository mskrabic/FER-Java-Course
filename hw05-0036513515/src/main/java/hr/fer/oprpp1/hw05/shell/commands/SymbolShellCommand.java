package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.Util;

/**
 * Naredba za rad sa simbolima koje {@link MyShell} koristi u komunikaciji s korisnikom.
 * 
 * @author mskrabic
 *
 */
public class SymbolShellCommand implements ShellCommand {

	/**
	 * Ako primi samo jedan argument i on je ispravno ime simbola (PROMPT, MORELINES ili MULTILINE) metoda
	 * ispisuje koji se simbol koristi za tu svrhu. Ako primi dodatni parametar (1 simbol), postavlja traženi
	 * simbol na predanu vrijednost.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] splitted;
		try {
			splitted = Util.parseArgs(arguments);
		} catch (ShellIOException e) {
			splitted = new String[0];
		}

		if (splitted.length == 1) {
			printSymbol(env, splitted[0]);
		} else if (splitted.length == 2) {
			setSymbol(env, splitted[0], splitted[1]);
		} else {
			env.writeln("Invalid arguments for symbol command.");
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	/**
	 * Pomoćna metoda za postavljanje simbola.
	 * 
	 * @param env okruženje preko kojega naredba komunicira s korisnikom.
	 * @param name oznaka simbola.
	 * @param newName nova vrijednost simbola.
	 */
	private void setSymbol(Environment env, String name, String newName) {
		if (newName.length() != 1) {
			env.writeln("Symbol must be only 1 character long!");
			return;
		}

		switch (name) {
		case "PROMPT":
			env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '" + newName + "'");
			env.setPromptSymbol(newName.charAt(0));
			break;
		case "MORELINES":
			System.out.println(
					"Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "' to '" + newName + "'");
			env.setMorelinesSymbol(newName.charAt(0));
			break;
		case "MULTILINE":
			System.out.println(
					"Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "' to '" + newName + "'");
			env.setMultilineSymbol(newName.charAt(0));
			break;
		default:
			env.writeln("Invalid symbol: " + name);
		}
	}

	/**
	 * Metoda za ispis simbola.
	 * 
	 * @param env okruženje preko kojega naredba komunicira s korisnikom.
	 * @param name oznaka simbola.
	 */
	private void printSymbol(Environment env, String name) {
		switch (name) {
		case "PROMPT":
			env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
			break;
		case "MORELINES":
			env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
			break;
		case "MULTILINE":
			env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
			break;
		default:
			env.writeln("Invalid symbol: " + name);
		}
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("If a valid symbol name (PROMPT, MORELINES, MULTILINE) is passed, prints the wanted symbol.");
		desc.add("If an additional argument is given, sets the wanted symbol to the given value.");
		return Collections.unmodifiableList(desc);
	}
}

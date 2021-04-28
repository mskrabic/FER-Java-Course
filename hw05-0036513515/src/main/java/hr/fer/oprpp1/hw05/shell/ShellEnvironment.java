package hr.fer.oprpp1.hw05.shell;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.oprpp1.hw05.shell.commands.*;

/**
 * Razred preko kojega sve naredbe komuniciraju s korisnikom konzole.
 * 
 * @author mskrabic
 *
 */
public class ShellEnvironment implements Environment {
	
	/**
	 * Pretpostavljeni simbol kojim konzola javlja korisniku da čeka ulaz.
	 */
	private static final Character DEFAULT_PROMPT = '>';
	
	/**
	 * Pretpostavljeni simbol kojim konzola označava linije u slučaju višelinijskog ispisa.
	 */
	private static final Character DEFAULT_MULTILINE = '|';
	
	/**
	 * Pretpostavljeni simbol kojim korisnik označava višelinijski ulaz.
	 */
	private static final Character DEFAULT_MORELINES = '\\';
			
	/**
	 * Naredbe koje {@link MyShell} podržava.
	 */
	private TreeMap<String, ShellCommand> commands;
	
	/**
	 * Simbol kojim konzola javlja korisniku da čeka ulaz.
	 */
	private Character prompt;
	
	/**
	 * Simbol kojim konzola označava linije u slučaju višelinijskog ispisa.
	 */
	private Character multiLine;
	
	/**
	 * Simbol kojim korisnik označava višelinijski ulaz.
	 */
	private Character moreLines;
	
	/**
	 * Scanner za čitanje ulaznih podataka.
	 */
	private Scanner sc = new Scanner(System.in);

	/**
	 * Konstruktor koji inicijalizira okruženje i ispisuje pozdravnu poruku korisniku.
	 */
	public ShellEnvironment() {
		prompt = DEFAULT_PROMPT;
		multiLine = DEFAULT_MULTILINE;
		moreLines = DEFAULT_MORELINES;
		commands = new TreeMap<>();
		putCommands();
		
		writeln("Welcome to MyShell v 1.0");
		write(prompt + " ");
	}

	public String readLine() throws ShellIOException {
		try {
			String line = sc.nextLine();
			if (line.length() == 0) {
				write(prompt + " ");
				line = readLine();
			}
			while (line.charAt(line.length()-1) == moreLines) {
				
				line = line.length() == 1 ? "" : line.substring(0, line.length()-1);
				write(multiLine + " ");
				line = line + " " + sc.nextLine();
			}
			return line.trim();			
		} catch (NoSuchElementException | IllegalStateException e) {
			throw new ShellIOException(e.getMessage());
		}
	}

	public void write(String text) throws ShellIOException {
		try {
			System.out.print(text);			
		} catch (Exception e) {
			throw new ShellIOException(e.getMessage());
		}
	}

	public void writeln(String text) throws ShellIOException {
		try {
			System.out.println(text);
		} catch (Exception e) {
			throw new ShellIOException(e.getMessage());
		}
	}

	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	public Character getMultilineSymbol() {
		return multiLine;
	}

	public void setMultilineSymbol(Character symbol) {
		this.multiLine = symbol;
	}

	public Character getPromptSymbol() {
		return prompt;
	}

	public void setPromptSymbol(Character symbol) {
		this.prompt = symbol;
	}

	public Character getMorelinesSymbol() {
		return moreLines;
	}

	public void setMorelinesSymbol(Character symbol) {
		this.moreLines = symbol;
	}
	
	/**
	 * Pomoćna metoda za punjenje mape naredbi.
	 */
	private void putCommands() {
		commands.put("exit", new ExitShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("hexdump", new HexDumpShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("tree", new TreeShellCommand());

	}
}

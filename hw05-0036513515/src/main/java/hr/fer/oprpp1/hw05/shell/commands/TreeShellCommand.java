package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.Util;

/**
 * Naredba za ispis stabla direktorija.
 * 
 * @author mskrabic
 *
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * Metoda ispisuje stablo direktorija.
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
			env.writeln("Invalid arguments for tree command.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		Path path = Paths.get(splitted[0]);
		if (!path.toFile().isDirectory()) {
			env.writeln("Given file is not a directory!");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		try {
			Files.walkFileTree(path, new MyVisitor());
		} catch (IOException e) {
			env.write("Error while printing tree.");
		}
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Prints the tree for given directory.");
		desc.add("The argument is the path to the directory.");
		
		return Collections.unmodifiableList(desc);
	}
	
	/**
	 * Implementacija {@link FileVisitor}-a koju koristi ova naredba.
	 * 
	 * @author mskrabic
	 *
	 */
	private class MyVisitor implements FileVisitor<Path> {
		int level = 0;

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			print(dir);
			level++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			print(file);
			return FileVisitResult.CONTINUE;
		}


		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}
		

		/**
		 * Metoda za formatirani ispis datoteke ovisno o položaju u stablu.
		 * 
		 * @param path datoteka za ispis.
		 */
		private void print(Path path) {
			System.out.printf("%s%s%n", " ".repeat(2*level), path.toFile().getName());	
		}
	}

}

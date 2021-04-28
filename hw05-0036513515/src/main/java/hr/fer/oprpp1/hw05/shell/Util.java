package hr.fer.oprpp1.hw05.shell;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Pomoćni razred koji {@link MyShell} koristi pri radu.
 * 
 * @author mskrabic
 *
 */
public class Util {

	/**
	 * Metoda iz pročitane linije vraća unesenu naredbu.
	 * 
	 * @param unesena linija.
	 * 
	 * @return ime naredbe.
	 */
	public static String parseCommand(String line) {
		return line.split(" ")[0];
	}

	/**
	 * Metoda koja formatira ispis naredbe ls {@link MyShell}-a.
	 * 
	 * @param path put do datoteke koju treba ispisati.
	 * 
	 * @return formatirani ispis datoteke.
	 */
	public static String formatFile(Path path) {
		StringBuilder sb = new StringBuilder();
		File file = path.toFile();
		
		sb.append(file.isDirectory() ? "d" : "-");
		sb.append(file.canRead() ? "r" : "-");
		sb.append(file.canWrite() ? "w" : "-");
		sb.append(file.canExecute() ? "x" : "-");
		sb.append(" ");
		sb.append(String.format("%10d", path.toFile().length()));
		sb.append(" ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasicFileAttributeView faView = Files.getFileAttributeView(
		path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
		);
		BasicFileAttributes attributes;
		try {
			attributes = faView.readAttributes();
		} catch (IOException e) {
			throw new RuntimeException();
		}
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		sb.append(formattedDateTime + " " + path.getFileName());
	
		return sb.toString();
	}
	
	/**
	 * Metoda za parsiranje argumenata naredbe.
	 * 
	 * @param unesena linija bez imena naredbe.
	 * 
	 * @return polje parametara s kojima treba izvršiti naredbu.
	 */
	public static String[] parseArgs(String input) {
		ArrayList<String> list = new ArrayList<>();
		
		int index = 0;
		boolean insideQuotes = false;
		StringBuilder sb = new StringBuilder();
		while (index < input.length()) {
			if (input.charAt(index) == ' ' && !insideQuotes) {
				if (!sb.isEmpty()) {
					list.add(sb.toString());
					sb.setLength(0);
				}
				index++;
				continue;
			}
			if (!insideQuotes && input.charAt(index) == '"') {
				insideQuotes = true;
				if (!sb.isEmpty()) {
					list.add(sb.toString());
					sb.setLength(0);
				}
				index++;
				continue;
			}
			if (insideQuotes && input.charAt(index) == '\\') {
				if (index+1 >= input.length())
					throw new ShellIOException("Invalid escape sequence!");
				if (input.charAt(index+1) == '"') {
					sb.append('"');
					index += 2;
				} else if (input.charAt(index+1) == '\\') {
					sb.append('\\');
					index += 2;
				} else {
					sb.append(input.charAt(index++));
				}
			} else {
				if (input.charAt(index) == '"') {
					insideQuotes = false;
					if (index + 1 != input.length() && input.charAt(index+1) != ' ') {
						throw new ShellIOException("Can't parse arguments: " + input);
					}
					list.add(sb.toString());
					sb.setLength(0);
					index++;
					continue;
				}
				sb.append(input.charAt(index++));
			}
		}
		if (insideQuotes) {
			throw new ShellIOException("Quotes not closed!");
		}
		if (!sb.isEmpty()) {
			list.add(sb.toString());
		}
		
		String[] arguments = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arguments[i] = list.get(i).trim();
		}
		return arguments;
	}
}

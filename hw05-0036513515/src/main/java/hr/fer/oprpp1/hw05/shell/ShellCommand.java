package hr.fer.oprpp1.hw05.shell;

import java.util.List;

/**
 * Sučelje definira način na koji konzola komunicira s naredbama.
 * 
 * @author mskrabic
 *
 */
public interface ShellCommand {

	/**
	 * Metoda za izvođenje naredbe.
	 * 
	 * @param env okruženje u kojem se treba izvesti naredba.
	 * @param arguments argumenti naredbe.
	 * 
	 * @return status konzole nakon izvršene naredbe.
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Metoda vraća ime naredbe.
	 * 
	 * @return ime naredbe.
	 */
	String getCommandName();
	
	/**
	 * Metoda vraća opis naredbe.
	 * 
	 * @return opis naredbe.
	 */
	List<String> getCommandDescription();

}

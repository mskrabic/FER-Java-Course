package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

/**
 * Sučelje definira način rada {@link MyShell}-a.
 * 
 * @author mskrabic
 *
 */
public interface Environment {

	/**
	 * Metoda za čitanje linije (ili linija u slučaju višelinijskog inputa od korisnika).
	 * 
	 * @return pročitana linija u obliku stringa.
	 * 
	 * @throws ShellIOException u slučaju pogreške pri čitanju linije.
	 */
	 String readLine() throws ShellIOException;
	 
	 /**
	  * Metoda za ispis na konzolu (bez prelaska u novi red).
	  * 
	  * @param text tekst koji se želi ispisati korisniku.
	  * 
	  * @throws ShellIOException u slučaju pogreške pri ispisu teksta.
	  */
	 void write(String text) throws ShellIOException;
	 
	 /**
	  * Metoda za ispis na konzolu (s prelaskom u novi red).
	  * 
	  * @param text tekst koji se želi ispisati korisniku.
	  * 
	  * @throws ShellIOException u slučaju pogreške pri ispisu teksta.
	  */
	 void writeln(String text) throws ShellIOException;
	 
	 /**
	  * Metoda vraća sve dostupne komande koje {@link MyShell} prepoznaje.
	  * 
	  * @return komande koje {@link MyShell} prepoznaje.
	  */
	 SortedMap<String, ShellCommand> commands();
	 
	 /**
	  * Metoda vraća simbol koji {@link MyShell} koristi na početku linije u slučaju višelinijskog ispisa.
	  * 
	  * @return simbol koji označava višelinijski ispis.
	  */
	 Character getMultilineSymbol();
	 
	 /**
	  * Metoda postavlja simbol koji {@link MyShell} koristi na početku linije u slučaju višelinijskog ispisa.
	  * 
	  * @param symbol simbol koji se želi postaviti.
	  */
	 void setMultilineSymbol(Character symbol);
	 
	 /**
	  * Metoda vraća simbol koji {@link MyShell} koristi kao prompt, tj. kojim daje do znanja korisniku da čeka ulaz.
	  * 
	  * @return simbol kojim se označava da konzola čeka ulaz.
	  */
	 Character getPromptSymbol();
	 
	 /**
	  * Metoda postavlja simbol koji {@link MyShell} koristi kao prompt, tj. kojim daje do znanja korisniku da čeka ulaz.
	  *
	  * @param symbol simbol koji se želi postaviti.
	  */
	 void setPromptSymbol(Character symbol);
	
	 /**
	  * Metoda vraća simbol koji {@link MyShell} koji se koristi za oznaku višelinijskog ulaza.
	  * 
	  * @return simbol kojim se konzoli označava višelinijski ulaz.
	  */
	 Character getMorelinesSymbol();
	 
	 /**
	  * Metoda postavlja simbol koji {@link MyShell} koji se koristi za oznaku višelinijskog ulaza.
	  * 
	  * @param symbol simbol kji se želi postaviti.
	  */
	 void setMorelinesSymbol(Character symbol);
}

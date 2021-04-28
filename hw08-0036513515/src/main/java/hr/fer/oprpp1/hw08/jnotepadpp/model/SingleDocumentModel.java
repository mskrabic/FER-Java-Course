package hr.fer.oprpp1.hw08.jnotepadpp.model;

import java.nio.file.Path;

import javax.swing.JTextArea;

import hr.fer.oprpp1.hw08.jnotepadpp.model.listener.SingleDocumentListener;

/**
 * Sučelje koje modelira jedan dokument u {@link JNotePadPP}.
 * 
 * @author mskrabic
 */
public interface SingleDocumentModel {
	/**
	 * Metoda dohvaća tekstualnu komponentu dokumenta.
	 * 
	 * @return tekstualna komponenta dokumenta.
	 */
	JTextArea getTextComponent();

	/**
	 * Metoda dohvaća lokaciju dokumenta na disku.
	 * 
	 * @return lokacija dokumenta na disku.
	 */
	Path getFilePath();

	/**
	 * Metoda postavlja lokaciju dokumenta na disku.
	 * 
	 * @param path lokacija koja se želi postaviti.
	 */
	void setFilePath(Path path);

	/**
	 * Metoda provjerava je li dokument mijenjan.
	 * 
	 * @return <code>true</code< ako je dokument mijenjan, <code>false</code> inače.
	 */
	boolean isModified();

	/**
	 * Metoda postavlja status dokumenta.
	 * 
	 * @param modified zastavica koja određuje je li dokument mijenjan ili ne.
	 */
	void setModified(boolean modified);

	/**
	 * Metoda za dodavanje promatrača.
	 * 
	 * @param l novi promatrač.
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Metoda za uklanjanje promatrača.
	 * 
	 * @param l promatrač koji se želi ukloniti.
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
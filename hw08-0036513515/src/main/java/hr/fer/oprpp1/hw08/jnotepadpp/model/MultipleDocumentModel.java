package hr.fer.oprpp1.hw08.jnotepadpp.model;

import java.nio.file.Path;

import hr.fer.oprpp1.hw08.jnotepadpp.model.listener.MultipleDocumentListener;

/**
 * Sučelje pruža podršku za rad s više dokumenata modeliranih sučeljem {@link SingleDocumentModel}.
 * 
 * @author mskrabic
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Metoda za stvaranje novog dokumenta.
	 * 
	 * @return novi dokument.
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Metoda za dohvat trenutnog dokumenta.
	 * 
	 * @return trenutni dokument.
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Metoda za učitavanje postojećeg dokumenta.
	 * 
	 * @param path put do dokumenta.
	 * 
	 * @return učitani dokument.
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Metoda za spremanje dokumenta.
	 * 
	 * @param model dokument koji se želi spremiti.
	 * 
	 * @param newPath lokacija na koju se želi spremiti dokument.
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Metoda za zatvaranje dokumenta.
	 * 
	 * @param model dokument koji se želi zatvoriti.
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Metoda za dodavanje promatrača.
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Metoda za uklanjanje promatrača.
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Metoda vraća broj dokumenata.
	 * 
	 * @return broj dokumenata.
	 */
	int getNumberOfDocuments();

	/**
	 * Metoda za dohvat dokumenta.
	 * 
	 * @param index indeks dokumenta.
	 * 
	 * @return dokument.
	 */
	SingleDocumentModel getDocument(int index);
}
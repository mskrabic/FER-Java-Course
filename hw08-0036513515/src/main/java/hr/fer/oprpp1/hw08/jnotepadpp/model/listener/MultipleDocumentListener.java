package hr.fer.oprpp1.hw08.jnotepadpp.model.listener;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

/**
 * Sučelje modelira promatrača nad {@link MultipleDocumentModel}-om.
 * 
 * @author mskrabic
 *
 */
public interface MultipleDocumentListener {
	
	/**
	 * Metoda za obavještavanje o promjeni trenutnog dokumenta.
	 * 
	 * @param previousModel bivši trenutni dokument.
	 * 
	 * @param currentModel novi trenutni dokument.
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Metoda za obavještavanje da je dodan novi dokument.
	 * 
	 * @param previousModel dodani dokument.
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Metoda za obavještavanje da je zatvoren dokument.
	 * 
	 * @param previousModel zatvoreni dokument.
	 */
	void documentRemoved(SingleDocumentModel model);
}

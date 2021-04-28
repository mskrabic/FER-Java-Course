package hr.fer.oprpp1.hw08.jnotepadpp.model.listener;

import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

/**
 * Promatrač nad {@link SingleDocumentModel}.
 * 
 * @author mskrabic
 *
 */
public interface SingleDocumentListener {
	
	/**
	 * Metoda za obavještavanje da je promijenjen status dokumenta.
	 * 
	 * @param model dokument.
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Metoda za obavještavanje da je promijenjena lokacija(put) dokumenta.
	 * 
	 * @param model dokument.
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
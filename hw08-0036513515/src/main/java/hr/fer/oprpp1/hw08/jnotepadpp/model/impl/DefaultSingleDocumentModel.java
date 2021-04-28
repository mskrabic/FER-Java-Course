package hr.fer.oprpp1.hw08.jnotepadpp.model.impl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.listener.SingleDocumentListener;

/**
 * Razred implementira sučelje {@link SingleDocumentModel}.
 * 
 * @author mskrabic
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	/**
	 * Tekstualna komponenta dokumenta.
	 */
	private JTextArea textComponent;
	
	/**
	 * Lokacija dokumenta na disku.
	 */
	private Path path;
	
	/**
	 * Zastavica koja određuje je li dokument mijenjan.
	 */
	private boolean modified;
	
	/**
	 * Promatrači.
	 */
	private List<SingleDocumentListener> listeners = new ArrayList<>();
	
	/**
	 * Početni tekst dokumenta pri učitavanju/otvaranju.
	 */
	private String initialText;
	
	/**
	 * Konstruktor.
	 * 
	 * @param path put do dokumenta.
	 * 
	 * @param text početni tekst.
	 */
	public DefaultSingleDocumentModel(Path path, String text) {
		textComponent = new JTextArea();
		this.initialText = text;
		textComponent.setText(text);
		modified = false;
		this.path = path;
		textComponent.getDocument().addDocumentListener(new DocumentListener() {
			private void updateStatus() {
				if (textComponent.getText().equals(initialText)) {
					modified = false;
				} else {
					modified = true;					
				}
				statusChanged();
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateStatus();			
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateStatus();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				updateStatus();
			}
		});
	}

	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path, "Path must not be null!");
		
		this.path = path;	
		pathChanged();
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		statusChanged();
		
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);	
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);	
	}
	
	/**
	 * Pomoćna metoda za obavještavanje promatrača da je promijenjena zastavica <code>modified</code>.
	 */
	private void statusChanged() {
		for (SingleDocumentListener l : listeners) {
			l.documentModifyStatusUpdated(this);
		}
	}
	
	/**
	 * Pomoćna metoda za obavještavanje promatrača da je promijenjen put do dokumenta.
	 */
	private void pathChanged() {
		for (SingleDocumentListener l : listeners) {
			l.documentFilePathUpdated(this);
		}
	}

}

package hr.fer.oprpp1.hw08.jnotepadpp.model.impl;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.listener.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.listener.SingleDocumentListener;

/**
 * Razred implementira sučelje {@link MultipleDocumentModel} i nudi potporu za rad s više dokumenata.
 * 
 * @author mskrabic
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * Serijski broj.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Otvoreni dokumenti.
	 */
	private List<SingleDocumentModel> documents;
	
	/**
	 * Trenutni dokument.
	 */
	private SingleDocumentModel currentDocument;
	
	/**
	 * Promatrači.
	 */
	private List<MultipleDocumentListener> listeners = new ArrayList<>();
	
	/**
	 * Konstruktor.
	 */
	public DefaultMultipleDocumentModel() {
		super();
		documents = new ArrayList<>();
		currentDocument = null;
		this.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel oldDocument = currentDocument;
				currentDocument = documents.get(DefaultMultipleDocumentModel.this.getSelectedIndex());	
				currentDocumentChanged(oldDocument, currentDocument);
			}
		});
		
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		DefaultSingleDocumentModel document = new DefaultSingleDocumentModel(null, "");
		document.addSingleDocumentListener(new DefaultSingleDocumentListener());
		addDocument(document);

		return document;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path, "Path must not be null!");
		
		if(!Files.isReadable(path)) {
			return null;
		}
		
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
		} catch(Exception ex) {
			return null;
		}
		SingleDocumentModel document = new DefaultSingleDocumentModel(path, new String(bytes, StandardCharsets.UTF_8));
		for (SingleDocumentModel m : documents) {
			if (document.getFilePath().equals(m.getFilePath())) {
				this.setSelectedIndex(documents.indexOf(m));
				return m;
			}			
		}
		document.addSingleDocumentListener(new DefaultSingleDocumentListener());
		addDocument(document);
		
		return document;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if (newPath == null) 
			newPath = model.getFilePath();
		
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(newPath)))) {
			bw.write(model.getTextComponent().getText());
			model.setFilePath(newPath);
			changePath(model);
		} catch (IOException e) {
			System.err.println("Error opening file.");
		}	
		model.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {	
		if (documents.size() == 1) {
			createNewDocument();
		}
		int idx = documents.indexOf(model);
		this.documents.remove(model);
		this.currentDocument = documents.get(idx-1 < 0 ? 0 : idx-1);
		this.removeTabAt(idx);
		documentRemoved(model);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
		
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		Iterator<SingleDocumentModel> it = iterator();
		for (int i = 0; i < index; i++) {
			it.next();
		}
		return it.next();
	}
	
	/**
	 * Pomoćna metoda za dohvat ikone za tab.
	 * 
	 * @param color boja ikone - "red" ili "green".
	 * 
	 * @throws RuntimeException ako ne može učitati traženu ikonu.
	 * 
	 * @return ikona.
	 */
	private ImageIcon loadIcon(String color) {
		InputStream is = this.getClass().getResourceAsStream("../../icons/"+color+"_diskette.png");
		if(is==null)
			throw new RuntimeException("Can't find the image!");
		try {
			byte[] bytes = is.readAllBytes();
			is.close();
			ImageIcon icon = new ImageIcon(bytes);
			Image image = icon.getImage(); 
			Image newimg = image.getScaledInstance(12, 12,  java.awt.Image.SCALE_SMOOTH);
			return new ImageIcon(newimg); 
		} catch (IOException e ) {
			throw new RuntimeException("Error reading bytes!");
		}
	}
	
	/**
	 * Pomoćna metoda za promjenu ikone dokumenta.
	 * 
	 * @param model dokument kojem se želi promjeniti ikona.
	 */
	private void changeIcon(SingleDocumentModel model) {
		if (model.isModified())
			this.setIconAt(documents.indexOf(model), loadIcon("red"));
		else {
			this.setIconAt(documents.indexOf(model), loadIcon("green"));
		}
	}
	
	/**
	 * Pomoćna metoda za promjenu lokacije(puta) dokumenta.
	 * 
	 * @param model dokument kojem se želi promjeniti lokacija na disku.
	 */
	private void changePath(SingleDocumentModel model) {
		int index = documents.indexOf(model);
		setTitleAt(index, model.getFilePath().getFileName().toString());
		setToolTipTextAt(index, model.getFilePath().toAbsolutePath().toString());
	}
	
	/**
	 * Pomoćna metoda za obavještavanje promatrača da je dodan dokument.
	 * 
	 * @param model dodani dokument.
	 */
	private void documentAdded(SingleDocumentModel model) {
		for (MultipleDocumentListener l : listeners) {
			l.documentAdded(model);
		}
	}
	
	/**
	 * Pomoćna metoda za obavještavanje promatrača da je zatvoren dokument.
	 * 
	 * @param model zatvoreni dokument.
	 */
	private void documentRemoved(SingleDocumentModel model) {
		for (MultipleDocumentListener l : listeners) {
			l.documentRemoved(model);
		}
	}
	
	/**
	 * Pomoćna metoda za obavještavanje promatrača da je promijenjen trenutni dokument.
	 * 
	 * @param oldDocument bivši trenutni dokument.
	 * 
	 * @param newDocument novi trenutni dokument.
	 */
	private void currentDocumentChanged(SingleDocumentModel oldDocument, SingleDocumentModel newDocument) {
		for (MultipleDocumentListener l : listeners) {
			l.currentDocumentChanged(oldDocument, newDocument);
		}
	}
	
	/**
	 * Pomoćna metoda za dodavanje dokumenta (učitavanje/stvaranje novog dokumenta).
	 * 
	 * @param document dokument koji se dodaje.
	 */
	private void addDocument(SingleDocumentModel document) {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new JScrollPane(document.getTextComponent()), BorderLayout.CENTER);
		String title, tooltip;
		if (document.getFilePath() == null) {
			title = tooltip = "(unnamed)";
		} else {
			title = document.getFilePath().getFileName().toString();
			tooltip = document.getFilePath().toAbsolutePath().toString();
		}
		this.documents.add(document);	
		this.addTab(title, loadIcon("green"), p, tooltip);
		this.setSelectedIndex(documents.size()-1);
		documentAdded(document);
	}
	
	/**
	 * Razred implementira sučelje {@link SingleDocumentListener}.
	 * 
	 * @author mskrabic
	 *
	 */
	private class DefaultSingleDocumentListener implements SingleDocumentListener {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				changeIcon(model);	
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				changePath(model);	
			}
	}
}

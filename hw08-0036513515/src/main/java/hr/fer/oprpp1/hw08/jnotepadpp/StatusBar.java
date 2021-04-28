package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.GridLayout;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.listener.MultipleDocumentListener;

/**
 * Razred predstavlja statusnu traku za {@link JNotepadPP}.
 * 
 * @author mskrabic
 *
 */
public class StatusBar extends JPanel {
	
	/**
	 * Serijski broj.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Promatrač za ažuriranje statusne trake.
	 */
	private ChangeListener listener = new StatusCaretListener();
	
	/**
	 * Tekstualna komponenta trenutnog dokumenta.
	 */
	private JTextArea editor;
	
	/**
	 * Duljina teksta u trenutnom dokumentu.
	 */
	private JLabel length;
	
	/**
	 * Trenutna linija, stupac i duljina selekcije u trenutnom dokumentu.
	 */
	private JLabel lineColSel;
	
	/**
	 * Trenutno vrijeme - "sat".
	 */
	private JLabel time;
	
	/**
	 * {@link ILocalizationProvider} koji se koristi za lokalizaciju statusne trake.
	 */
	private ILocalizationProvider lp;
	
	/**
	 * {@link Timer} koji se koristi za prikaz vremena.
	 */
	private Timer timer;
	
	/**
	 * Format za prikaz vremena.
	 */
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	/**
	 * Konstruktor.
	 * 
	 * @param model {@link MultipleDocumentModel} koji koristi {@link JNotepadPP}.
	 * 
	 * @param lp {@link ILocalizationProvider} koji se koristi za lokalizaciju statusne trake.
	 */
	public StatusBar(MultipleDocumentModel model, ILocalizationProvider lp) {
		this.lp = lp;
		this.length = new JLabel();
		this.lineColSel = new JLabel();
		this.time = new JLabel();
		this.timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				time.setText(sdf.format(timestamp));
				
			}
		}, 0, 1000);
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				updateStatus();
				
			}
		});
		
		model.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				model.getTextComponent().getCaret().removeChangeListener(listener);
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				model.getTextComponent().getCaret().addChangeListener(listener);
				StatusBar.this.editor = model.getTextComponent();
				updateStatus();
				
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (previousModel != null)
					previousModel.getTextComponent().getCaret().removeChangeListener(listener);
				if (currentModel != null)
					currentModel.getTextComponent().getCaret().addChangeListener(listener);
				StatusBar.this.editor = currentModel.getTextComponent();
				updateStatus();
				
			}
		});
		
		this.setLayout(new GridLayout(1, 4));
		this.add(length);
		this.add(lineColSel);
		this.add(time);
	}
	
	/**
	 * Metoda vraća {@link Timer} statusne trake.
	 * 
	 * @return timer.
	 */
	public Timer getTimer() {
		return timer;
	}
	
	/**
	 * Pomoćna metoda za ažuriranje statusne trake.
	 */
	private void updateStatus() {	
		this.length.setText(String.format("%s:%d", lp.getString("length"), editor.getText().length()));
		try {
			int currentLine = editor.getLineOfOffset(editor.getCaretPosition());
			int currentColumn = editor.getCaretPosition()-editor.getLineStartOffset(currentLine);
			int selection = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
			this.lineColSel.setText(String.format("%s:%d %s:%d %s:%d", lp.getString("ln"), currentLine+1, lp.getString("col"), currentColumn+1, lp.getString("sel"), selection));
		} catch (BadLocationException err) {
			System.err.println("Caret error!");
		}
	}

	/**
	 * Promatrač kojeg koristi statusna traka.
	 * 
	 * @author mskrabic
	 *
	 */
	private class StatusCaretListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			updateStatus();
			
		}

		
	}
}

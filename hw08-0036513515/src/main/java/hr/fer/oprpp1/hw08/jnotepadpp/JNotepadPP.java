package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit.CopyAction;
import javax.swing.text.DefaultEditorKit.CutAction;
import javax.swing.text.DefaultEditorKit.PasteAction;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.local.*;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.impl.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.listener.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.listener.SingleDocumentListener;

/**
 * Razred koji predstavlja implementaciju "notepad" aplikacije.
 * 
 * @author mskrabic
 *
 */
public class JNotepadPP extends JFrame {

	/**
	 * Serijski broj.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link FormLocalizationProvider} koji se koristi za lokalizaciju grafičkog sučelja.
	 */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	
	/**
	 * Traka izbornika.
	 */
	private JMenuBar menuBar;
	
	/**
	 * Alatna traka.
	 */
	private JToolBar toolBar;
	
	/**
	 * Model koji sadrži aktivne dokumente.
	 */
	private DefaultMultipleDocumentModel model = new DefaultMultipleDocumentModel();
	
	/**
	 * Statusna traka.
	 */
	private StatusBar statusBar;

	/**
	 * Konstruktor.
	 */
	public JNotepadPP() {
		super();

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("JNotepad++");
		setSize(700, 700);
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				JNotepadPP.this.closeWindow();	
			}	
		});
		addTitleListener();
		addModificationListener();

		initGUI();
	}

	/**
	 * Pomoćna metoda za inicijalizaciju grafičkog sučelja.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		initToolbar();
		initMenuBar();
		statusBar = new StatusBar(model, flp);
		JPanel content = new JPanel(new BorderLayout());
		content.add(model, BorderLayout.CENTER);
		content.add(statusBar, BorderLayout.PAGE_END);
		
		setJMenuBar(menuBar);
		cp.add(toolBar, BorderLayout.PAGE_START);		
		cp.add(content, BorderLayout.CENTER);

		initAccelerators();
		initActions();
		model.createNewDocument();
	}
	
	/**
	 * Pomoćna metoda za inicijalizaciju izborničke trake.
	 */
	private void initMenuBar() {
		menuBar = new JMenuBar();
		createFileMenu();	
		createEditMenu();
		createToolsMenu();
		createLanguageMenu();	
	}
	
	/**
	 * Pomoćna metoda za stvaranje izbornika "Datoteka".
	 */
	private void createFileMenu() {
		LJMenu fileMenu = new LJMenu("file", flp);

		fileMenu.add(newDocumentAction);
		fileMenu.add(openDocumentAction);
		fileMenu.add(saveDocumentAction);
		fileMenu.add(saveDocumentAsAction);
		fileMenu.add(closeDocumentAction);
		fileMenu.add(statisticsAction);
		fileMenu.add(exitAction);
		
		menuBar.add(fileMenu);	
	}

	/**
	 * Pomoćna metoda za stvaranje izbornika "Uredi".
	 */
	private void createEditMenu() {
		LJMenu editMenu = new LJMenu("edit", flp);
	
		editMenu.add(cutAction);
		editMenu.add(copyAction);
		editMenu.add(pasteAction);
		
		menuBar.add(editMenu);		
	}
	
	/**
	 * Pomoćna metoda za stvaranje izbornika "Alati".
	 */
	private void createToolsMenu() {
		LJMenu toolsMenu = new LJMenu("tools", flp);
		
		LJMenu changeCase = new LJMenu("changecase", flp);	
		changeCase.add(uppercaseAction);
		changeCase.add(lowercaseAction);
		changeCase.add(invertAction);
		
		LJMenu sort = new LJMenu("sort", flp);
		sort.add(ascendingSortAction);
		sort.add(descendingSortAction);
		
		toolsMenu.add(changeCase);
		toolsMenu.add(sort);
		toolsMenu.add(uniqueAction);
		
		menuBar.add(toolsMenu);	
	}
	
	/**
	 * Pomoćna metoda za stvaranje izbornika "Jezici".
	 */
	private void createLanguageMenu() {
		LJMenu languageMenu = new LJMenu("languages", flp);

		languageMenu.add(croatianAction);
		languageMenu.add(englishAction);
		languageMenu.add(germanAction);
		
		menuBar.add(languageMenu);	
	}

	/**
	 * Pomoćna metoda za inicijalizaciju alatne trake..
	 */
	private void initToolbar() {
		toolBar = new JToolBar();
		toolBar.add(newDocumentAction);
		toolBar.add(openDocumentAction);
		toolBar.add(saveDocumentAction);
		toolBar.add(saveDocumentAsAction);
		toolBar.add(closeDocumentAction);
		toolBar.add(cutAction);
		toolBar.add(copyAction);
		toolBar.add(pasteAction);
//		toolBar.add(uppercaseAction);
//		toolBar.add(lowercaseAction);
//		toolBar.add(invertAction);
//		toolBar.add(ascendingSortAction);
//		toolBar.add(descendingSortAction);
//		toolBar.add(uniqueAction);
		toolBar.add(statisticsAction);
		toolBar.add(exitAction);		
	}

	/**
	 * Pomoćna metoda za inicijalizaciju akcija i dodavanje potrebnih promatrača.
	 */
	private void initActions() {
		uppercaseAction.setEnabled(false);
		lowercaseAction.setEnabled(false);
		invertAction.setEnabled(false);
		ascendingSortAction.setEnabled(false);
		descendingSortAction.setEnabled(false);
		uniqueAction.setEnabled(false);
		cutAction.setEnabled(false);
		copyAction.setEnabled(false);
		saveDocumentAction.setEnabled(false);
		CaretListener listener = new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				boolean enabled = (e.getDot()-e.getMark() != 0);
				
				uppercaseAction.setEnabled(enabled);
				lowercaseAction.setEnabled(enabled);
				invertAction.setEnabled(enabled);
				ascendingSortAction.setEnabled(enabled);
				descendingSortAction.setEnabled(enabled);
				uniqueAction.setEnabled(enabled);
				cutAction.setEnabled(enabled);
				copyAction.setEnabled(enabled);				
			}
		};
		model.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (previousModel != null) {
					previousModel.getTextComponent().removeCaretListener(listener);
				}
				if (currentModel != null) {
					currentModel.getTextComponent().addCaretListener(listener);
				}		
			}
		});
		
	}

	/**
	 * Akcija za promjenu jezika u hrvatski.
	 */
	private LocalizableAction croatianAction = new LocalizableAction("croatian", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};

	/**
	 * Akcija za promjenu jezika u engleski.
	 */
	private LocalizableAction englishAction = new LocalizableAction("english", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};
	
	/**
	 * Akcija za promjenu jezika u njemački.
	 */
	private LocalizableAction germanAction = new LocalizableAction("german", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}
	};

	/**
	 * Akcija za otvaranje dokumenta.
	 */
	private LocalizableAction openDocumentAction = new LocalizableAction("open", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(JNotepadPP.this);	
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				SingleDocumentModel m = model.loadDocument(file.toPath());
				if (m == null) {
					JOptionPane.showMessageDialog(
							JNotepadPP.this, 
							"Datoteka "+file.getAbsolutePath()+" ne postoji!", 
							"Pogreška", 
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
	};

	/**
	 * Akcija za stvaranje novog dokumenta.
	 */
	private LocalizableAction newDocumentAction = new LocalizableAction("new", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.createNewDocument();
		}
	};

	/**
	 * Akcija za spremanje dokumenta.
	 */
	private LocalizableAction saveDocumentAction = new LocalizableAction("save", flp) {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.saveDocument(model.getCurrentDocument(), model.getCurrentDocument().getFilePath());
		}
	};

	/**
	 * Akcija za spremanje dokumenta na novu lokaciju.
	 */
	private LocalizableAction saveDocumentAsAction = new LocalizableAction("saveas", flp) {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.saveDocument(model.getCurrentDocument(), null);
		}
	};
	
	/**
	 * Akcija za zatvaranje aktivnog dokumenta.
	 */
	private LocalizableAction closeDocumentAction = new LocalizableAction("close", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model.getCurrentDocument().isModified()) {
				Path path = model.getCurrentDocument().getFilePath();
				int choice = showDialog(path == null ? ("(unnamed)"): path.getFileName().toString());
				switch (choice) {
				case 0:
					model.saveDocument(model.getCurrentDocument(), path);
					break;
				case 1:
					break;
				case 2:
					return;
				}
			}
			model.closeDocument(model.getCurrentDocument());
		}

	};
	
	/**
	 * Akcija za zatvaranje aplikacije.
	 */
	private LocalizableAction exitAction = new LocalizableAction("exit", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			closeWindow();
		}
	};
	
	/**
	 * Akcija za prikaz statističkih informacija o dokumentu.
	 */
	private LocalizableAction statisticsAction = new LocalizableAction("stats", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = model.getCurrentDocument().getTextComponent();
			int chars = editor.getText().length();
			int nonBlankChars = chars;
			for (char c : editor.getText().toCharArray()) {
				if (isBlankSpace(c))
					nonBlankChars--;
			}
			int lines = editor.getLineCount();
			JOptionPane.showMessageDialog(JNotepadPP.this, getStatisticalInfo(chars, nonBlankChars, lines), flp.getString("stats"), JOptionPane.INFORMATION_MESSAGE, null);
			
		}

		private String getStatisticalInfo(int chars, int nonBlankChars, int lines) {
			StringBuilder sb = new StringBuilder();
			
			sb.append(flp.getString("statsintro")).append(" " + chars + " ").append(flp.getString("chars"));
			sb.append(", ").append(nonBlankChars + " ").append(flp.getString("nonblank"));
			sb.append(" " + flp.getString("and")).append(" " + lines + " " ).append(flp.getString("lines") + ".");
			
			return sb.toString();
		}

		private boolean isBlankSpace(char c) {
			return (c == '\n' || c == '\t' || c == ' ');
		}
		
	};
	
	/**
	 * Akcija za promjenu odabranog teksta u velika slova.
	 */
	private LocalizableAction uppercaseAction = new LocalizableAction("uppercase", flp) {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase("uppercase");
		}
	};
	
	/**
	 * Akcija za promjenu odabranog teksta u mala slova. 
	 */
	private LocalizableAction lowercaseAction = new LocalizableAction("lowercase", flp) {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase("lowercase");
		}
	};
	
	/**
	 * Akcija za obrtanje veličine slova u odabranom tekstu.
	 */
	private LocalizableAction invertAction = new LocalizableAction("invert", flp) {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			String text = model.getCurrentDocument().getTextComponent().getSelectedText();
			StringBuilder sb = new StringBuilder();
			for (char c : text.toCharArray()) {
				if (Character.isLowerCase(c)) {
					c = Character.toUpperCase(c);
				} else if (Character.isUpperCase(c)) {
					c = Character.toLowerCase(c);
				}
				sb.append(c);
			}
			Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
			int length = Math.abs(caret.getDot()-caret.getMark());
			int pos = Math.min(caret.getDot(), caret.getMark());
			Document document = model.getCurrentDocument().getTextComponent().getDocument();
			
			try {
				document.remove(pos, length);
				document.insertString(pos, sb.toString(), null);
			} catch (BadLocationException err) {}
			
		}
	};
	
	/**
	 * Akcija za brisanje duplikata među odabranim linijama.
	 */
	private LocalizableAction uniqueAction = new LocalizableAction("unique", flp) {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			useTool("unique");
			
		}
	};
	
	/**
	 * Akcija za uzlazno sortiranje.
	 */
	private LocalizableAction ascendingSortAction = new LocalizableAction("ascending", flp) {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			useTool("ascending");
		}
	};
	
	/**
	 * Akcija za silazno sortiranje.
	 */
	private LocalizableAction descendingSortAction = new LocalizableAction("descending", flp) {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			useTool("descending");
			
		}
	};
	
	/**
	 * Akcija za rezanje teksta.
	 */
	private LocalizableAction cutAction = new LocalizableAction("cut", flp) {
		
		private static final long serialVersionUID = 1L;
		
		private CutAction action = new CutAction();
		
		@Override
		public void actionPerformed(ActionEvent e) {
			action.actionPerformed(e);	
		}
	};
	
	/**
	 * Akcija za kopiranje teksta.
	 */
	private LocalizableAction copyAction = new LocalizableAction("copy", flp) {
		
		private static final long serialVersionUID = 1L;
		
		private CopyAction action = new CopyAction();
		
		@Override
		public void actionPerformed(ActionEvent e) {
			action.actionPerformed(e);	
		}
	};
	
	/**
	 * Akcija za lijepljenje teksta.
	 */
	private LocalizableAction pasteAction = new LocalizableAction("paste", flp) {

		private static final long serialVersionUID = 1L;
		
		private PasteAction action = new PasteAction();
		
		@Override
		public void actionPerformed(ActionEvent e) {
			action.actionPerformed(e);	
		}
	};

	/**
	 * Pomoćna metoda za inicijalizaciju mnemonika i akceleratora.
	 */
	private void initAccelerators() {
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));

		newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocumentAction.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));

		closeDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));

		croatianAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F1);
		croatianAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.CTRL_MASK));

		englishAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F2);
		englishAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F2, ActionEvent.CTRL_MASK));

		germanAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F3);
		germanAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.CTRL_MASK));
		
		uppercaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		uppercaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		
		lowercaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		lowercaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		
		invertAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		invertAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		
		ascendingSortAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		ascendingSortAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK));
		
		descendingSortAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		descendingSortAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK));
		
		uniqueAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
		uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		
		statisticsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		statisticsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
	}
	
	/**
	 * Pomoćna metoda koju koriste akcije za promjenu veličine slova.
	 * 
	 * @param newCase nova veličina slova - "uppercase" ili "lowercase".
	 * 
	 * @throws IllegalArgumentException ako se preda argument koji nije među gore navedenim vrijednostima.
	 */
	protected void changeCase(String newCase) {
		String text = model.getCurrentDocument().getTextComponent().getSelectedText();
		String newText;
		switch(newCase) {
		case "uppercase":
			newText = text.toUpperCase();
			break;
		case "lowercase":
			newText = text.toLowerCase();
			break;
		default:
			throw new IllegalArgumentException("Invalid argument: " + newCase);
		}
		Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
		int length = Math.abs(caret.getDot()-caret.getMark());
		int pos = Math.min(caret.getDot(), caret.getMark());
		Document document = model.getCurrentDocument().getTextComponent().getDocument();
		
		try {
			document.remove(pos, length);
			document.insertString(pos, newText, null);
		} catch (BadLocationException err) {}	
	}

	/**
	 * Pomoćna metoda koju koriste akcije sortiranja/izbacivanja duplikata.
	 * 
	 * @param tool akcija koja se želi primjeniti - "ascending", "descending" ili "unique".
	 * 
	 * @throws IllegalArgumentException ako se preda argument koji nije među gore navedenim vrijednostima.
	 */
	protected void useTool(String tool) {
		Locale locale = new Locale(flp.getCurrentLanguage());
		Collator collator = Collator.getInstance(locale);
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		List<String> lines = new ArrayList<>();
		try {
			int firstLine = editor.getLineOfOffset(editor.getSelectionStart());
			int lastLine = editor.getLineOfOffset(editor.getSelectionEnd());
			int offset, length, totalLength = 0;
			String line;
			
			for (int i = firstLine; i <= lastLine; i++) {
				offset = editor.getLineStartOffset(i);
				length = editor.getLineEndOffset(i) - offset;
				totalLength += length;
				line = editor.getText(offset, length);
				if (i == lastLine && lastLine == editor.getLineOfOffset(editor.getText().length())) {
					line += "\n";
				}
				lines.add(line);
			}
			switch(tool) {
			case "ascending":
				lines.sort((l, r) -> collator.compare(l, r));
				break;
			case "descending":
				lines.sort((l, r) -> collator.compare(r, l));
				break;
			case "unique":
				lines = lines.stream().distinct().collect(Collectors.toList());
				break;
			default:
				throw new IllegalArgumentException("Invalid tool!");
			}
			
			line = lines.get(lines.size()-1);
			lines.remove(lines.size()-1);
			line = line.substring(0, line.length()-1);
			lines.add(line);
			
			
			offset = editor.getLineStartOffset(firstLine);
			editor.getDocument().remove(offset, totalLength);
			for (String l : lines) {
				editor.getDocument().insertString(offset, l, null);
				offset += l.length();
			}
		} catch (BadLocationException err) {}
	}	

	/**
	 * Pomoćna metoda za prikaz dijaloga za spremanje datoteke.
	 * 
	 * @param file ime datoteke za koju se prikazuje dijalog.
	 * 
	 * @return korisnikov odabir.
	 */
	protected int showDialog(String file) {
		String[] options = { LocalizationProvider.getInstance().getString("yes"),
				LocalizationProvider.getInstance().getString("no"),
				LocalizationProvider.getInstance().getString("cancel") };

		int choice = JOptionPane.showOptionDialog(JNotepadPP.this,
				LocalizationProvider.getInstance().getString("warningquesiton")+file,
				LocalizationProvider.getInstance().getString("warning"), JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE, null, options, 0);
		return choice;
	}
	
	/**
	 * Pomoćna metoda za zatvaranje prozora. Provjerava ima li nespremljenih dokumenata i pita korisnika što s njima učiniti.
	 */
	private void closeWindow() {
		int choice;
		Path path;
		SingleDocumentModel m;
		for (int i = 0, n = model.getNumberOfDocuments(); i < n; i++) {
			m = model.getDocument(i);
			path = m.getFilePath();
			if (!m.isModified()) {
				continue;
			}
			choice = showDialog(path == null ? "(unnamed)" : path.getFileName().toString());
			switch (choice) {
			case 0:
				Path p = getPath();
				if (p == null)
					return;
				model.saveDocument(m, p);
				break;
			case 1:
				break;
			case 2:
				return;
			}
		}
		statusBar.getTimer().cancel();
		dispose();
	}
	
	private Path getPath() {
		JFileChooser chooser = new JFileChooser();
		int choice = chooser.showSaveDialog(this);
		if (choice != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		
		Path newPath = chooser.getSelectedFile().toPath();
		for (int i = 0, n = model.getNumberOfDocuments(); i < n; i++) {
			SingleDocumentModel m = model.getDocument(i);
			if (m.getFilePath() != null
					&& m.getFilePath().toAbsolutePath().toString().equals(newPath.toAbsolutePath().toString())) {
				JOptionPane.showMessageDialog(this,
						LocalizationProvider.getInstance().getString("saveerror"),
						LocalizationProvider.getInstance().getString("error"), JOptionPane.WARNING_MESSAGE, null);
				return null;
			}
		}
		if (newPath.toFile().exists()) {
			String[] options = {LocalizationProvider.getInstance().getString("yes"),
								LocalizationProvider.getInstance().getString("no"),
								LocalizationProvider.getInstance().getString("cancel")
								};
			choice = JOptionPane.showOptionDialog(this.getParent(),
					LocalizationProvider.getInstance().getString("exists"),
					LocalizationProvider.getInstance().getString("warning"),
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE, null, options, options[0]);
			if (choice != 0)
				return null;
		}
		return newPath;
	}

	/**
	 * Pomoćna metoda za dodavanje promatrača za naslov prozora aplikacije.
	 */
	private void addTitleListener() {
		model.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				// TODO Auto-generated method stub

			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				// TODO Auto-generated method stub

			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				String title = currentModel.getFilePath() == null ? "(unnamed)"
						: currentModel.getFilePath().toAbsolutePath().toString();
				JNotepadPP.this.setTitle(title + " - JNotepad++");
			}
		});	
	}
	
	/**
	 * Pomoćna metoda za dodavanje promatrača za status trenutnog dokumenta.
	 */
	private void addModificationListener() {
		model.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			private SingleDocumentListener listener = new SingleDocumentListener() {
				
				@Override
				public void documentModifyStatusUpdated(SingleDocumentModel model) {
					saveDocumentAction.setEnabled(model.isModified());
				}
				
				@Override
				public void documentFilePathUpdated(SingleDocumentModel model) {
					// TODO Auto-generated method stub
					
				}
			};

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				// TODO Auto-generated method stub

			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				// TODO Auto-generated method stub

			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (previousModel != null) {
					previousModel.removeSingleDocumentListener(listener);				
				}
				if (currentModel != null) {
					currentModel.addSingleDocumentListener(listener);
				}
			}
		});	
	}

	/**
	 * Metoda za pokretanje aplikacije.
	 * @param args Ne koriste se.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}

}
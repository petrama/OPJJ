package hr.fer.zemris.java.tecaj_11.notepad;



import hr.fer.zemris.java.tecaj_11.local.swing.LJMenue;
import hr.fer.zemris.java.tecaj_11.local.swing.LJToolBar;
import hr.fer.zemris.java.tecaj_11.local.swing.LocalizableAction;
import hr.fer.zemris.java.tecaj_11.local.LocalizationProvider;
import hr.fer.zemris.java.tecaj_11.local.swing.SomeFrame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;

/**
 * Razred predstavlja tekst editor koji omogućuje standardne operacije s tekstualnim datotekama.
 * Internacionalizirana verzija.
 * @author Petra Marče
 *
 */
public class JNotepad extends SomeFrame {

	private static final long serialVersionUID = 1L;
	private JTextArea editor;
	private Path openedFilePath;
	
	
	public JNotepad() {
		
	
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(0, 0);
		setSize(800, 800);
		initGUI();
		pack();
		
	}

	private void initGUI() {
		
		editor = new JTextArea();

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(new JScrollPane(editor), BorderLayout.CENTER);

		createMenus();
		createActions();
	
		createToolbars();

	}
	/**
	 * Stvaranje potrebnih akcija.
	 */

	private void createActions() {
		newDocumentAction.putValue(Action.NAME,"New");
		newDocumentAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control N"));
		newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocumentAction.putValue(Action.SHORT_DESCRIPTION,
				"Creates a new file");

		openDocumentAction.putValue(Action.NAME, "Open");
		openDocumentAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION,
				"Used to open existing file from disk.");

		saveDocumentAction.putValue(Action.NAME, "Save");
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.putValue(Action.SHORT_DESCRIPTION,
				"Used to save current file to disk.");

		saveAsAction.putValue(Action.NAME, "Save as");
		saveAsAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control T"));
		saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		saveAsAction.putValue(Action.SHORT_DESCRIPTION,
				"Used to save current file to disk.");

		deleteSelectedPartAction.putValue(Action.NAME, "Delete selected text");
		deleteSelectedPartAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("F2"));
		deleteSelectedPartAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		deleteSelectedPartAction.putValue(Action.SHORT_DESCRIPTION,
				"Used to delete the selected part of text.");

		cutSelectedAction.putValue(Action.NAME, "Cut selected text");
		cutSelectedAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control x"));
		cutSelectedAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cutSelectedAction.putValue(Action.SHORT_DESCRIPTION,
				"Used to cut the selected part of text.");

		copySelectedAction.putValue(Action.NAME, "copy selected text");
		copySelectedAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control x"));
		copySelectedAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		copySelectedAction.putValue(Action.SHORT_DESCRIPTION,
				"Used to copy the selected part of text.");

		pasteAction.putValue(Action.NAME, "Paste text");
		pasteAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		pasteAction.putValue(Action.SHORT_DESCRIPTION,
				"Used to paste some  text.");

		toggleCaseAction.putValue(Action.NAME, "Toggle case");
		toggleCaseAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control F3"));
		toggleCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		toggleCaseAction
				.putValue(Action.SHORT_DESCRIPTION,
						"Used to toggle character case in selected part of text or in entire document.");

		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control X"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit application.");
	}

	/**
	 * Stvaranje Menu izbornika.
	 */
	private void createMenus() {

		JMenuBar menuBar = new JMenuBar();

		
		JMenu menuLanguages = new LJMenue("languages", flp);
		JMenuItem menuItemEn = new JMenuItem("en");
		menuItemEn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");

			}
		});
		
		JMenuItem menuItemHr = new JMenuItem("hr");
		menuItemHr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");

			}
		});
		
		menuLanguages.add(menuItemHr);
		menuLanguages.add(menuItemEn);
	
		menuBar.add(menuLanguages);
		
		
		
		

		JMenu fileMenu =new LJMenue("file", flp);
		menuBar.add(fileMenu);
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));

		JMenu editMenu =new LJMenue("edit", flp);
		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(cutSelectedAction));
		editMenu.add(new JMenuItem(copySelectedAction));
		editMenu.add(new JMenuItem(pasteAction));
		editMenu.add(new JMenuItem(toggleCaseAction));
		editMenu.add(new JMenuItem(deleteSelectedPartAction));
		editMenu.add(new JMenuItem(toggleCaseAction));

		this.setJMenuBar(menuBar);
	}

	private void createToolbars() {
		JToolBar toolBar = new LJToolBar("toolBar", flp);
		toolBar.setFloatable(true);
		toolBar.add(new JButton(newDocumentAction));
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveAsAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(cutSelectedAction));
		toolBar.add(new JButton(copySelectedAction));
		toolBar.add(new JButton(pasteAction));
		toolBar.add(new JButton(deleteSelectedPartAction));
		toolBar.add(new JButton(toggleCaseAction));

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	private Action newDocumentAction = new LocalizableAction("new","newDesc",flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (stillWantOperation()) {
				openedFilePath = null;
				editor.setText("");
			}
		}
	};

	private Action openDocumentAction = new LocalizableAction("open","openDesc",flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if (fc.showOpenDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JNotepad.this, "Datoteka "
						+ fileName.getAbsolutePath() + " ne postoji!",
						"Pogreška", JOptionPane.ERROR_MESSAGE);
				return;
			}
			byte[] okteti;
			try {
				okteti = Files.readAllBytes(filePath);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(
						JNotepad.this,
						"Pogreška prilikom čitanja datoteke "
								+ fileName.getAbsolutePath() + ".", "Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String tekst = new String(okteti, StandardCharsets.UTF_8);
			editor.setText(tekst);
			openedFilePath = filePath;
		}
	};

	private Action saveDocumentAction = new LocalizableAction("save","saveDesc",flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (openedFilePath == null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save document");
				if (jfc.showSaveDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JNotepad.this,
							"Ništa nije snimljeno.", "Upozorenje",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				openedFilePath = jfc.getSelectedFile().toPath();
			}
			byte[] podatci = editor.getText().getBytes(StandardCharsets.UTF_8);
			try {
				Files.write(openedFilePath, podatci);
			} catch (IOException e1) {
				JOptionPane
						.showMessageDialog(
								JNotepad.this,
								"Pogreška prilikom zapisivanja datoteke "
										+ openedFilePath.toFile()
												.getAbsolutePath()
										+ ".\nPažnja: nije jasno u kojem je stanju datoteka na disku!",
								"Pogreška", JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(JNotepad.this,
					"Datoteka je snimljena.", "Informacija",
					JOptionPane.INFORMATION_MESSAGE);
		}
	};

	private Action saveAsAction = new LocalizableAction("saveAs","saveAsDesc",flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			openedFilePath = null;
			saveDocumentAction.actionPerformed(e);

		}
	};

	private Action deleteSelectedPartAction = new LocalizableAction("delete","deleteDesc",flp){

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			deleteAndReturn();

		}
	};

	private String deleteAndReturn() {
		Document doc = editor.getDocument();
		int len = Math.abs(editor.getCaret().getDot()
				- editor.getCaret().getMark());
		if (len == 0)
			return "";
		int offset = Math.min(editor.getCaret().getDot(), editor.getCaret()
				.getMark());
		try {
			String text = doc.getText(offset, len);
			doc.remove(offset, len);
			return text;
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		return "";
	}

	private Action cutSelectedAction =new LocalizableAction("cut","cutDesc",flp){

		private static final long serialVersionUID = 1L;

//		@Override
//		public void actionPerformed(ActionEvent e) {
//			cutBuffer = deleteAndReturn();
			
			  Action cut = new DefaultEditorKit.CutAction();
			  
			  @Override
			  public void actionPerformed(ActionEvent e) {
			   cut.actionPerformed(e);
			  }

		
	};

	private Action copySelectedAction = new LocalizableAction("copy","copyDesc",flp){

		private static final long serialVersionUID = 1L;
		Action copy=new DefaultEditorKit.CopyAction();
		@Override
		public void actionPerformed(ActionEvent e) {
//			cutSelectedAction.actionPerformed(null);
//			pasteAction.actionPerformed(null);
			copy.actionPerformed(e);

		}
	};
	private Action pasteAction = new LocalizableAction("paste","pasteDesc",flp){

		private static final long serialVersionUID = 1L;

		Action paste=new DefaultEditorKit.PasteAction();
		@Override
		public void actionPerformed(ActionEvent e) {

			paste.actionPerformed(e);
		}
	};

	private Action toggleCaseAction = new LocalizableAction("togg","toggDesc",flp){

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot()
					- editor.getCaret().getMark());
			int offset = 0;
			if (len != 0) {
				offset = Math.min(editor.getCaret().getDot(), editor.getCaret()
						.getMark());
			} else {
				len = doc.getLength();
			}
			try {
				String text = doc.getText(offset, len);
				text = changeCase(text);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}

		private String changeCase(String text) {
			char[] znakovi = text.toCharArray();
			for (int i = 0; i < znakovi.length; i++) {
				char c = znakovi[i];
				if (Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if (Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
			return new String(znakovi);
		}
	};

	private boolean stillWantOperation() {

		if (openedFilePath == null) {// nije dosad spremano
			if (editor.getText().isEmpty()) {// i fajl je prazan
				return true;// mozemo izaci slobodno
			}
		}

		if (openedFilePath != null) {// ako je dosad spremano
			byte[] okteti;
			try {
				okteti = Files.readAllBytes(openedFilePath);// učitaj sve
															// bajtove iz
															// tog fajla
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(JNotepad.this,
						"Pogreška prilikom čitanja datoteke " + openedFilePath
								+ ".", "Pogreška", JOptionPane.ERROR_MESSAGE);
				return true;
			}
			

			if (Arrays.equals(okteti, editor.getText().getBytes(StandardCharsets.UTF_8))) {// nema
																		// nikakvih
																		// promjena
				return true;
			}
		}
		// u bilo kojem drugom slucaju pita se korisnika
		Object[] options = { "Yes", "No", "Cancel" };

		int n = JOptionPane.showOptionDialog(JNotepad.this,
				"Would you like save changes? ", "You have unsaved changes!",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[0]);

		if (n == JFileChooser.APPROVE_OPTION) {
			saveDocumentAction.actionPerformed(null);
			return true;
		} else {
			if (n == JFileChooser.CANCEL_OPTION) {
				return true;

			}

			return false;

		}
	};

	private Action exitAction = new LocalizableAction("exit","exitDesc",flp){

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			if (stillWantOperation()) {
				System.exit(0);
			}

		}
	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				LocalizationProvider.getInstance().setLanguage("en");
				new JNotepad().setVisible(true);
			}
		});
	}
	
	

}

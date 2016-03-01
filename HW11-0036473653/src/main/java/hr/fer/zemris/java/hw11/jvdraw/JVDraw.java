package hr.fer.zemris.java.hw11.jvdraw;

import hr.fer.zemris.java.hw11.jvdraw.canvas.JDrawingCanvas;
import hr.fer.zemris.java.hw11.jvdraw.color.DLabel;
import hr.fer.zemris.java.hw11.jvdraw.color.DrawToolBar;
import hr.fer.zemris.java.hw11.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.list.DrawingObjectListModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * Razred koji predstavlja jednostavnu aplikaciju za crtanje geometrijskih likova.
 * Podržani likovi su linija, kružnica, krug.
 * Vrlo je jednostavno dodati još elemenata, kod se ne mora gotovo uopće mjenjati.
 * Podržava promjenu boje, i naknadnu izmjenu jednom stvorenih elemenata.
 * Nudi eksportanje u slikovnu datoteku.
 * @author Petra Marče
 *
 */
public class JVDraw extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Path openedFilePath;
	private DrawingModelImpl model;


	public JVDraw(){
		
		initGUI();
		pack();
		openedFilePath=null;
		
		
	}

	
	/**
	 * Stvaranje GUI-a
	 */
	private void initGUI(){
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(getPreferredSize());
		getContentPane().setLayout(new BorderLayout());
		
		JColorArea out=new JColorArea(Color.BLUE);
		JColorArea in=new JColorArea(Color.red);
		DLabel label=new DLabel(in,out);
		this.add(label,BorderLayout.SOUTH);
		DrawToolBar tool=new DrawToolBar(out,in);
		this.model=new DrawingModelImpl();
		
		
		
		final DrawingObjectListModel modelLis=new DrawingObjectListModel(model);
		  JList<GeometricalObject> lista1 = new JList<>(modelLis);
		  
		  MouseListener mouse=new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
				JList<GeometricalObject> lista=(JList<GeometricalObject>)e.getSource();
				if(e.getClickCount()!=2){
					return;
				}
				int index= lista.getSelectedIndex();
				if(index==-1){
					return;
				}
				GeometricalObject ob=modelLis.getElementAt(index);
				ob.showOptions(JVDraw.this);
				repaint();
				}
			};
			lista1.addMouseListener(mouse);

			JScrollPane s1=new JScrollPane(lista1);
		
			s1.setPreferredSize(new Dimension(100,50));
			getContentPane().add(s1, BorderLayout.LINE_END);
		
		JDrawingCanvas canvas=new JDrawingCanvas(model,  out, in);
		
		tool.addModeChangedListener(canvas);
		this.getContentPane().add(tool,BorderLayout.NORTH);
		this.getContentPane().add(canvas,BorderLayout.CENTER);
		
		
		this.addMouseListener(canvas);
		this.addMouseMotionListener(canvas);
		
		
		
		
		createMenus();
		
		
		
	}
	/**
	 * Akcija otvaranja postojeceg dokumenta.
	 * Podrzana ekstenzija je jvd.
	 * Akcija ucitava datoteku i iz nje stvara model.
	 * 
	 */
	private Action openDocumentAction = new AbstractAction("Open") {
		
		

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
				     "jvd", "jvd");
				   fc.addChoosableFileFilter(filter);
			fc.setDialogTitle("Open file");
			if (fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			
			
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JVDraw.this, "Datoteka "
						+ fileName.getAbsolutePath() + " ne postoji!",
						"Pogreška", JOptionPane.ERROR_MESSAGE);
				return;
			}
			byte[] okteti;
			try {
				okteti = Files.readAllBytes(filePath);
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(
						JVDraw.this,
						"Pogreška prilikom čitanja datoteke "
								+ fileName.getAbsolutePath() + ".", "Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String tekst = new String(okteti, StandardCharsets.UTF_8);
			model.fromText(tekst);
			repaint();
			openedFilePath = filePath;
		}
	};

	/**
	 * Operacija spremanja.
	 * Dozvoljena ekstenzija je jvd.
	 */
	private Action saveDocumentAction=new AbstractAction("Save") {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (openedFilePath == null) {
				
				JFileChooser jfc = new JFileChooser();
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
					     "jvd", "jvd");
					   jfc.addChoosableFileFilter(filter);
				jfc.setDialogTitle("Save document");
				if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JVDraw.this,
							"Ništa nije snimljeno.", "Upozorenje",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				Path tempPath=jfc.getSelectedFile().toPath();
				String ex=jfc.getFileFilter().getDescription();
				if(!tempPath.toString().contains(".")){
					tempPath= Paths.get(tempPath.toString().concat("."+ex));
				}
				openedFilePath = tempPath;
				
			}
			byte[] podatci = model.toText().getBytes(StandardCharsets.UTF_8);
			try {
				Files.write(openedFilePath, podatci);
			} catch (IOException e1) {
				JOptionPane
						.showMessageDialog(
								JVDraw.this,
								"Pogreška prilikom zapisivanja datoteke "
										+ openedFilePath.toFile()
												.getAbsolutePath()
										+ ".\nPažnja: nije jasno u kojem je stanju datoteka na disku!",
								"Pogreška", JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(JVDraw.this,
					"Datoteka je snimljena.", "Informacija",
					JOptionPane.INFORMATION_MESSAGE);
		
		}
	};
	
	/**
	 * Akcija eksportanja.
	 * Moguće je dobiti png,gif i jpg format slike.
	 * Dimenenzije slike prilagođavaju se elementima u modelu.
	 */
	private Action export=new AbstractAction("Export") {
		
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Path exportFilePath;
				JFileChooser fc = new JFileChooser();
			   fc.removeChoosableFileFilter(fc.getChoosableFileFilters()[0]);
			   FileNameExtensionFilter filter = new FileNameExtensionFilter(
			     "jpg", "jpg");
			   fc.addChoosableFileFilter(filter);
			   filter = new FileNameExtensionFilter("gif", "gif");
			   fc.addChoosableFileFilter(filter);
			   filter = new FileNameExtensionFilter("png", "png");
			   fc.addChoosableFileFilter(filter);
			   
			   
			   if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JVDraw.this,
							"Ništa nije snimljeno.", "Upozorenje",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				exportFilePath =fc.getSelectedFile().toPath();
				System.out.println(exportFilePath);
				String ex=fc.getFileFilter().getDescription();
				if(!exportFilePath.toString().contains(".")){
					exportFilePath= Paths.get(exportFilePath.toString().concat("."+ex));
				}
				System.out.println(exportFilePath);
				
				int w=model.getBottomRightCorner().x-model.getUpperLeftCorner().x;
				int h=model.getBottomRightCorner().y-model.getUpperLeftCorner().y;
				BufferedImage image=new BufferedImage(w, h,BufferedImage.TYPE_3BYTE_BGR);
				Graphics2D g=image.createGraphics();
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, w, h);
				drawModel(g,model.getUpperLeftCorner());
				
				File file=exportFilePath.toFile();
				
				 
				try {
					ImageIO.write(image, ex, file);
				} catch (IOException e1) {
					System.err.println("Cannot Export");
					
					
					e1.printStackTrace();
				}
	
				JOptionPane.showMessageDialog(JVDraw.this,
						"Slika je stvorena.", "Informacija",
						JOptionPane.INFORMATION_MESSAGE);
				
		}

		private void drawModel(Graphics2D g,Point c) {
			   
			for(int i=0;i<model.getSize();i++){
				GeometricalObject lik=model.getObject(i);
				lik.nacrtajSe(g,c);
				
			}
		}
	};
	
	
	
	private boolean stillWantOperation() {

		if (openedFilePath == null) {// nije dosad spremano
			if (model.toText().isEmpty()) {// i fajl je prazan
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
				JOptionPane.showMessageDialog(JVDraw.this,
						"Pogreška prilikom čitanja datoteke " + openedFilePath
								+ ".", "Pogreška", JOptionPane.ERROR_MESSAGE);
				return true;
			}
			

			if (Arrays.equals(okteti, model.toText().getBytes(StandardCharsets.UTF_8))) {// nema
																		// nikakvih
																		// promjena
				return true;
			}
		}
		
		Object[] options = { "Yes", "No", "Cancel" };

		int n = JOptionPane.showOptionDialog(JVDraw.this,
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
	
	/**
	 * Akcija spremanja jvd filea.
	 */
	private Action saveAsAction = new AbstractAction("Save As") {
		
		

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
	
	private Action exitAction = new AbstractAction("Exit"){

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			if (stillWantOperation()) {
				System.exit(0);
			}

		}
	};
	
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(export));
		fileMenu.add(new JMenuItem(exitAction));
	
		
		
		this.setJMenuBar(menuBar);
	}
	
	
	/**
	 * Metoda koja se poziva pri pokretanju programa.
	 * @param args argumenti iz komandne linije, ne koriste se.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				new JVDraw().setVisible(true);
			}
		});


		
		
}



	
}
package hr.fer.zemris.java.hw11.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;




import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
/**
 * Komponenta predstavlja izbornik boje JVDraw-a.
 * Na klik ove komponente otvara se izbornik.
 * Kada korisnik izabere novu boju komponenta obavjestava svoje promatrace ako je doslo do promjene.
 *
 */
public class JColorArea extends JComponent implements IColorProvider,MouseListener{
	private final static int W=15;
	private final static int H=15;
	private Color selectedColor;
	private static final long serialVersionUID = 1L;
	private List<ColorChangeListener> listeners;

	
	public JColorArea(Color c) {
		selectedColor=c;
		listeners=new ArrayList<>();
		
	}

	
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(W,H);
	}
	/**
	 * Metoda koja vraća preferirane dimenzije komponente.
	 * Vraća W*H.
	 */
	@Override
	public Dimension getPreferredSize() {
				return new Dimension(W,H);
	}
	
	@Override
	public Dimension getMinimumSize() {
				return new Dimension(W,H);
				
	}
	
	
	
	

	
	@Override
	public void paintComponent(Graphics g) {
		Insets insets=getInsets();
		g.setColor(selectedColor);
		g.fillRect(insets.left, insets.top, W-insets.left-insets.right, H-insets.top-insets.bottom);
		
	
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener listener) {
		if(!listeners.contains(listener)){
			listeners=new ArrayList<>(listeners);
			listeners.add(listener);
		}
		
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener listener) {
		listeners=new ArrayList<>(listeners);
		listeners.remove(listener);
		
	}

	@Override
	 public void mouseClicked(java.awt.event.MouseEvent e) {
	  Color newColor = JColorChooser.showDialog(this,
	    "Choose color", this.selectedColor);
	  if (newColor!=null && newColor!=this.selectedColor) {
	   Color oldColor=this.selectedColor;
	   this.selectedColor = newColor;
	   for(ColorChangeListener l : listeners){
	    l.newColorSelected(this, oldColor, this.selectedColor);
	   }
	   this.repaint();
	  }
	 }

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}

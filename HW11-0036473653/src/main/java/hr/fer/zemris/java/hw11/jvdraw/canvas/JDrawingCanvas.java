package hr.fer.zemris.java.hw11.jvdraw.canvas;

import hr.fer.zemris.java.hw11.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw11.jvdraw.DrawingModelListener;
import hr.fer.zemris.java.hw11.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw11.jvdraw.color.ModeChangedListener;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.Line;

import java.awt.Color;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
/**
 * Razred koji predstavlja crtaću površinu JVDraw prozora.
 * Novi objekti se dodaju kliktanjem po površini.
 * @author Petra Marče
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener,ModeChangedListener,MouseListener,MouseMotionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrawingModel model;
	private JColorArea foreground;
	private JColorArea background;
	
	private boolean firstClick;
	private Point firstPoint;
	private Point secondTempClickPoint;
	private Point leftTopCorner;
	private GeometricalObject kojiSeCrta;

	
	public  JDrawingCanvas(DrawingModel m,JColorArea fore,JColorArea back){
		model=m;
		firstClick=false;
		foreground=fore;
		background=back;
		leftTopCorner=new Point(0,0);
		
		kojiSeCrta=new Line(new Point(), new Point(), Color.white);

		
		
	}
	

	

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();

	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();

	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		System.out.println("added");
		repaint();
		
	}
	
	
	@Override
	protected void paintComponent(java.awt.Graphics g) {
		 g.setColor(Color.WHITE);
		  Insets ins = this.getInsets();
		  g.fillRect(ins.left, ins.top, this.getWidth(), this.getHeight());
	
	
		
		for(int i=0;i<model.getSize();i++){
			GeometricalObject lik=model.getObject(i);
			lik.nacrtajSe(g,leftTopCorner);
			
		}
		if(firstClick){
			
//			kojiSeCrta.update(firstPoint,secondTempClickPoint,foreground.getCurrentColor(),background.getCurrentColor());
			kojiSeCrta.nacrtajSe(g,leftTopCorner);
		}
		
		
	}



	@Override
	public void newStatusSelected(GeometricalObject status) {
	this.kojiSeCrta=status;
		
	}
	
	

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		if(JDrawingCanvas.this.firstClick){
			secondTempClickPoint=e.getPoint();
			kojiSeCrta.update(firstPoint,secondTempClickPoint,foreground.getCurrentColor(),background.getCurrentColor());
			
			repaint();
		}
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		
		firstClick=!firstClick;

		System.out.println("firstClick"+firstClick);
		
		if(firstClick){
			JDrawingCanvas.this.firstPoint=e.getPoint();
		}else{
//			GeometricalObject obj=stvori(e.getPoint());
//			
//			model.add(obj);
			
			kojiSeCrta.update(firstPoint, e.getPoint(),foreground.getCurrentColor() , background.getCurrentColor());
			GeometricalObject obj=kojiSeCrta.copy();
//			kojiSeCrta=null;
			model.add(obj);
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

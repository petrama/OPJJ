package hr.fer.zemris.java.hw11.jvdraw.color;

import hr.fer.zemris.java.hw11.jvdraw.geometrical.Circle;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.FilledCircle;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.Line;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class DrawToolBar extends JToolBar implements IModeProvider{



	/**
	 * Razred predstavlja alatnu traku JVDrawa.
	 * Ona se sastoji od dva izbornika boje i jedne button-grupe.
	 */
	private static final long serialVersionUID = 1L;

	
	JColorArea out;
	JColorArea in; List<ModeChangedListener> listeners;
	private GeometricalObject defaultObject;
	
	public DrawToolBar(JColorArea out,JColorArea in){
		super();
		this.out=out;
		this.in=in;
		listeners=new ArrayList<>();
		final GeometricalObject defaultLine=new Line(new Point(1,1),new Point(1,1), Color.WHITE);
		this.defaultObject=defaultLine;
		final GeometricalObject defaultCir=new Circle(new Point(5,5),new Point(1,1), Color.WHITE);
		final GeometricalObject defaultFil=new FilledCircle(new Point(1,1),new Point(1,1), Color.WHITE,Color.WHITE);
		JToggleButton line=new JToggleButton("Line", true);
		
		line.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setMode(defaultLine);
				
			}

			
		});
		JToggleButton circle=new JToggleButton("Circle");
		circle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setMode(defaultCir);
			}
		});
		JToggleButton filled=new JToggleButton("Filled circle");
		filled.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setMode(defaultFil);
				
			}
		});
		ButtonGroup group=new ButtonGroup();
		group.add(line);
		group.add(circle);
		group.add(filled);
		this.add(out);
		out.addMouseListener(out);
		

		
		this.add(in);
		in.addMouseListener(in);
		
		
		this.setFloatable(true);
		this.add(out);
		this.add(in);
		addSeparator();
		this.add(line);
		this.add(circle);
		this.add(filled);

	
		
	}

	/**
	 * Metoda postavljanja defaultnog mode-a.
	 * Poziva se kao reakcija na klik jednog od botuna.
	 * Taj geometrijski objekt kojeg postavljamo predtsavlja element koji ce se crtati u canvasu
	 * @param g defaultni geometrijski objekt.
	 */
	private void setMode(GeometricalObject g) {
		this.defaultObject=g;
		fireMode();
		
	}
	
	/**
	 * Metoda koja obavještava sve promatrače da je došlo do promjene.
	 */
	private void fireMode() {
		for(ModeChangedListener ls:listeners){
			ls.newStatusSelected(defaultObject);
		}
		
	}


	
	@Override
	public void addModeChangedListener(ModeChangedListener listener) {
		if(!listeners.contains(listener)){
			listeners=new ArrayList<>(listeners);
			listeners.add(listener);
			System.out.println(listeners);
		}
		
		
	}

	@Override
	public void removeModeChangedListener(ModeChangedListener listener) {
		listeners=new ArrayList<>(listeners);
		listeners.remove(listener);
		
	}
	
	
}
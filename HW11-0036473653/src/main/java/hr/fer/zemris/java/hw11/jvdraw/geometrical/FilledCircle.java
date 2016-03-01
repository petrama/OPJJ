package hr.fer.zemris.java.hw11.jvdraw.geometrical;

import hr.fer.zemris.java.hw11.jvdraw.color.JColorArea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FilledCircle extends Circle {
	
	private Color ispunjenje;

	 public FilledCircle(int x,int y,double radius,int r, int g,int b,int r2,int g2, int b2){
			
		 this(new Point(x,y),new Point(x+(int)radius,y), new Color(r,g,b),new Color(r2,g2,b2));
	 }
	
	public FilledCircle(Point prva, Point druga, Color boja, Color drugaBoja) {
		super(prva, druga, boja);
		ispunjenje = drugaBoja;

	}

	@Override
	public void nacrtajSe(Graphics g,Point c) {
		g.setColor(ispunjenje);
		int r = (int) prva.distance(druga);
		g.fillOval(prva.x - r-c.y, prva.y - r-c.y, r * 2, r * 2);

		super.nacrtajSe(g,c);
	}

	public void setFill(Color c) {
		ispunjenje = c;
	}

	@Override
	public String toString() {
		String s=String.format("%.2f",prva.distance(druga));
		return "FCIRCLE " + prva.x + " " + prva.y + " " + s + 
				 " " + boja.getRed() + " " + boja.getGreen() + " "
				+ boja.getBlue()+" "+ispunjenje.getRed() + " " + ispunjenje.getGreen() + " "+ispunjenje.getBlue();
	}

	@Override
	public void update(Point first, Point second, Color out, Color in) {

		super.update(first, second, out, in);
		setFill(in);
	}

	@Override
	public GeometricalObject copy() {
		FilledCircle old = this;
		return new FilledCircle(old.prva, old.druga, old.boja, old.ispunjenje);
	}

	@Override
	public void showOptions(JFrame f) {

	    JPanel panel = new JPanel(new BorderLayout(5, 5));

	    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
	    label.add(new JLabel("Centar X", SwingConstants.RIGHT));
	    
	    label.add(new JLabel("Centar Y", SwingConstants.RIGHT));
	    label.add(new JLabel("Radius",SwingConstants.RIGHT));
	    label.add(new JLabel("Color ",SwingConstants.RIGHT));
	    label.add(new JLabel("Fill color ",SwingConstants.RIGHT));
	    
	    panel.add(label, BorderLayout.WEST);

	    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
	    JTextField centarX = new JTextField();
	    JTextField centarY = new JTextField();
	    JTextField radius = new JTextField();
	    JColorArea color = new JColorArea(boja);
	    JColorArea fill = new JColorArea(ispunjenje);
	    
	    centarX.setText( Integer.toString(this.prva.x).toString());
	    centarY.setText(Integer.toString(this.prva.y).toString());
	    radius.setText( Integer.toString( (int)prva.distance(druga)).toString());
	    color.addMouseListener(color);
	    fill.addMouseListener(fill);
	    controls.add(centarX);
	    controls.add(centarY);
	    controls.add(radius);
	    controls.add(color);
	    controls.add(fill);
	    panel.add(controls, BorderLayout.CENTER);

	    JOptionPane.showMessageDialog(f, panel, "Modify", JOptionPane.QUESTION_MESSAGE);
	 
	    Point p=new Point(parse(centarX.getText()),parse(centarY.getText()));
	    Point d=new Point(p.x+parse(radius.getText()),p.y);
	    Color boj=color.getCurrentColor();
	    
	    update(p, d, boj, fill.getCurrentColor());
	}

}

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
/**
 * Model Kružnice.
 * Krug određuje središte radius i boja.
 * @author Petra Marče
 *
 */
public  class Circle implements GeometricalObject{

	 Point prva;
	 Point druga;
	 Color boja;
	
	
	
	 public Circle(int x,int y,double radius,int r, int g,int b){
		
		 this(new Point(x,y),new Point(x+(int)radius,y), new Color(r,g,b));
	 }
	
	public Circle(Point prva, Point druga, Color boja) {
		super();
		this.prva = prva;
		this.druga = druga;
		this.boja = boja;

	
	}



	
	@Override
	public void nacrtajSe(Graphics g,Point  topLeftCorner) {
		g.setColor(boja);
		
		int r= (int)prva.distance(druga);
		g.drawOval(prva.x-r-topLeftCorner.x, prva.y-r-topLeftCorner.y,r*2 ,r*2);
		

	}
	
	

	
	@Override
	public String toString() {
		String s=String.format("%.2f",prva.distance(druga));
		return "CIRCLE " + prva.x + " " + prva.y + " " +s + 
				 " " + boja.getRed() + " " + boja.getGreen() + " "
				+ boja.getBlue();
	}



	
	public Point getPrva() {
		return prva;
	}




	public void setPrva(Point prva) {
		this.prva = prva;
	}




	public Point getDruga() {
		return druga;
	}




	public void setDruga(Point druga) {
		this.druga = druga;
	}




	public Color getBoja() {
		return boja;
	}




	public void setBoja(Color boja) {
		this.boja = boja;
	}




	@Override
	public void update(Point first, Point second,
			Color out, Color in) {
		
		setPrva(first);
		setDruga(second);
		setBoja(out);
		
	}

	@Override
	public GeometricalObject copy() {
		Circle old=this;
		return new Circle(new Point(old.prva),new Point(old.druga),old.boja);
	}




	@Override
	public void showOptions(JFrame f) {
		
	

	    JPanel panel = new JPanel(new BorderLayout(5, 5));

	    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
	    label.add(new JLabel("Centar X", SwingConstants.RIGHT));
	    
	    label.add(new JLabel("Centar Y", SwingConstants.RIGHT));
	    label.add(new JLabel("Radius",SwingConstants.RIGHT));
	    label.add(new JLabel("Color ",SwingConstants.RIGHT));
	    
	    panel.add(label, BorderLayout.WEST);

	    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
	    JTextField centarX = new JTextField();
	    JTextField centarY = new JTextField();
	    JTextField radius = new JTextField();
	    JColorArea color = new JColorArea(boja);
	    
	    centarX.setText(Integer.toString(this.prva.x));
	    centarY.setText( Integer.toString(this.prva.y).toString());
	    radius.setText( Integer.toString( (int)prva.distance(druga)).toString());
	    color.addMouseListener(color);
	    controls.add(centarX);
	    controls.add(centarY);
	    controls.add(radius);
	    controls.add(color);
	    panel.add(controls, BorderLayout.CENTER);

	    JOptionPane.showMessageDialog(f, panel, "Modify", JOptionPane.QUESTION_MESSAGE);
	 
	    Point p=new Point(parse(centarX.getText()),parse(centarY.getText()));
	    Point d=new Point(p.x+parse(radius.getText()),p.y);
	    Color boj=color.getCurrentColor();
	    
	    update(p, d, boj, boj);
//	    logininformation.put("user", username.getText());
//	    logininformation.put("pass", new String(password.getPassword()));
//	    return logininformation;
		
	
	
	
	
	
	}
	
	
	public static int parse(String s){
		System.out.println(s);
		int  broj=0;
		try{
		broj=Integer.parseInt(s);	
		}catch (NumberFormatException n ){
			System.out.println(s);
			throw new IllegalArgumentException("Please give integers!");
		}
		
		return broj;
	}

	@Override
	public int getMinX() {
		return (int)(prva.x-prva.distance(druga));
		
	}

	@Override
	public int getMinY() {
		return (int)(prva.y-prva.distance(druga));
	}

	@Override
	public int getMaxX() {
		return (int)(prva.x+prva.distance(druga));
		
	}

	@Override
	public int getMaxY() {
		return (int)(prva.y+prva.distance(druga));
	}

	
	
}

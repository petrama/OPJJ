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

public class Line implements GeometricalObject {
	

	private Point prva;
	private Point druga;
	private Color boja;

	public Line(int x1,int y1,int x2,int y2,int r, int g, int b){
		this(new Point(x1,y1),new Point(x2,y2),new Color(r,g,b));
	}
	public Line(Point prva, Point druga, Color boja) {
		super();
		this.prva = prva;
		this.druga = druga;
		this.boja = boja;
	

	}

	@Override
	public void nacrtajSe(Graphics g,Point c) {
		g.setColor(boja);
		g.drawLine(prva.x-c.x, prva.y-c.y, druga.x, druga.y);

	}

	@Override
	public String toString() {
		return "LINE " + prva.x + " " + prva.y + " " + druga.x + " " + druga.y
				+ " " + boja.getRed() + " " + boja.getGreen() + " "
				+ boja.getBlue();
	}

	Point getPrva() {
		return prva;
	}

	void setPrva(Point prva) {
		this.prva = prva;
	}

	Point getDruga() {
		return druga;
	}

	void setDruga(Point druga) {
		this.druga = druga;
	}

	Color getBoja() {
		return boja;
	}

	void setBoja(Color boja) {
		this.boja = boja;
	}

	@Override
	public void update(Point first, Point second, Color out, Color in) {
		setPrva(first);
		setDruga(second);
		setBoja(out);
	}

	@Override
	public GeometricalObject copy() {
		Line old = this;
		return new Line(new Point(old.prva), new Point(old.druga), old.boja);
	}

	@Override
	public void showOptions(JFrame f) {

		JPanel panel = new JPanel(new BorderLayout(5, 5));

		JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
		label.add(new JLabel("StartPoint X", SwingConstants.RIGHT));
		label.add(new JLabel("StartPoint Y", SwingConstants.RIGHT));

		label.add(new JLabel("EndPoint X", SwingConstants.RIGHT));

		label.add(new JLabel("EndPoint Y", SwingConstants.RIGHT));

		label.add(new JLabel("Color ", SwingConstants.RIGHT));

		panel.add(label, BorderLayout.WEST);

		JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
		JTextField startX = new JTextField();
		JTextField startY = new JTextField();
		JTextField endX = new JTextField();
		JTextField endY = new JTextField();

		JColorArea color = new JColorArea(boja);

		startX.setText(Integer.toString(this.prva.x).toString());
		startY.setText(Integer.toString(this.prva.y).toString());
		endX.setText(Integer.toString(this.druga.x).toString());
		endY.setText(Integer.toString(this.druga.y).toString());
		color.addMouseListener(color);
		controls.add(startX);
		controls.add(startY);
		controls.add(endX);
		controls.add(endY);
		controls.add(color);
		panel.add(controls, BorderLayout.CENTER);

		JOptionPane.showMessageDialog(f, panel, "Modify",
				JOptionPane.QUESTION_MESSAGE);

		Point p = new Point(Circle.parse(startX.getText()), Circle.parse(startY
				.getText()));

		Point d = new Point(Circle.parse(endX.getText()), Circle.parse(endY
				.getText()));
		Color boj = color.getCurrentColor();

		update(p, d, boj, boj);

	}
	
	
	@Override
	public int getMinX() {
		return (prva.x<druga.x?prva.x:druga.x);
	}

	@Override
	public int getMinY() {
		return (prva.y<druga.y?prva.y:druga.y);
	}

	@Override
	public int getMaxX() {
		return (prva.x>druga.x?prva.x:druga.x);
		
	}

	@Override
	public int getMaxY() {
		return (prva.y>druga.y?prva.y:druga.y);
	}


}
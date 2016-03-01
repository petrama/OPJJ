package hr.fer.zemris.java.gui.layouts;

import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class Test extends JFrame {
	
	private static final long serialVersionUID = 1L;


	public Test(){
		Container pane = this.getContentPane();
		  pane.setLayout(new CalcLayout(3));
		  JLabel numberLabel = new JLabel("0");
		  numberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		  pane.add(numberLabel, new RCPosition(1, 1));
		  JButton button1 = new JButton("1");
		  JButton functionLog = new JButton("log");
		  JCheckBox checkBox = new JCheckBox();
		   pane.add(button1,  new RCPosition(2, 1));
		  pane.add(functionLog,  new RCPosition(3, 1));
		  pane.add(checkBox,  new RCPosition(4, 1));
		  JButton b = new JButton("sin");
		   pane.add(b,  new RCPosition(5, 1));
	}
	
	
	public static void main(String []args){
		Test t=new Test();
		t.setVisible(true);
	}
}

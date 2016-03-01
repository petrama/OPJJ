import java.awt.Container;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


import org.junit.Assert;




public class TestLayout {
	
	@org.junit.Test
	public void TestExceptions(){
		JFrame frame=new JFrame();
		Container pane = frame.getContentPane();
		  pane.setLayout(new CalcLayout(3));
		  JLabel numberLabel = new JLabel("0");
		  numberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		  pane.add(numberLabel, new RCPosition(1, 1));
		  JButton botun=new JButton("5");
		  String message="";
		  try{
			  pane.add(botun, new RCPosition(1, 1));
		  }catch(IllegalArgumentException i){
			  message=i.getMessage();
		  }
		  Assert.assertTrue(message.equals("Component on this position already exists!"));
		  
		  JButton b=new JButton();
		  try{
		  pane.add(b, new RCPosition(1, 3));
		  }catch(IllegalArgumentException i){
		  message=i.getMessage();
		  System.out.println(message);
	  }
	  Assert.assertTrue(message.equals("Positions (1,2),(1,3),(1,4) and (1,5) are invalid!"));

	  try{
		  pane.add(b, "");
		  }catch(IllegalArgumentException i){
		  message=i.getMessage();
		  System.out.println(message);
	  }
	  Assert.assertTrue(message.equals("Given position is invalid, you must provide row and column index separated with ','!"));

	  JButton c=new JButton();
	  try{
		  pane.add(c, "1.5,5");
		  }catch(IllegalArgumentException i){
		  message=i.getMessage();
		  System.out.println(message);
	  }
	  Assert.assertTrue(message.equals("Given position is invalid, number of row must be Integer!"));
	
	  
	
	
	}}
	
		
		
		  

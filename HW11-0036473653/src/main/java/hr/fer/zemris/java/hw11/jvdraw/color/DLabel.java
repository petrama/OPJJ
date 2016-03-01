package hr.fer.zemris.java.hw11.jvdraw.color;

import java.awt.Color;

import javax.swing.JLabel;
/**
 * Razred predstavlja labelu koja ispisuje trenutno odabrane boje JVDraw modela.
 * Na svaku promjenu boje ispis se mjenja.
 * @author Petra Marče
 *
 */
public class DLabel extends JLabel implements ColorChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JColorArea back;
	JColorArea fore;

	
	public DLabel(JColorArea back, JColorArea fore) {
		super();
		this.back = back;
		this.fore = fore;
		back.addColorChangeListener(this);
		fore.addColorChangeListener(this);
	}
	
	
	@Override
	
	
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor) {
		this.setText("Foreground color: ("+fore.getCurrentColor().getRed()+","+fore.getCurrentColor().getGreen()+","+fore.getCurrentColor().getBlue()+
				") Background color: ("+back.getCurrentColor().getRed()+","+back.getCurrentColor().getGreen()+","+fore.getCurrentColor().getBlue()+")");
		
	}

	
}

package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
/**
 * Razred koji predstavlja layout za aplikaciju <code>Calculator</code>.
 * @author Petra Marče.
 *
 */
public class CalcLayout implements LayoutManager2 {
	/** broj redaka **/
	private final static int ROWS = 5;
	/** broj stupaca **/
	private final static int COLS = 7;
	/** razmak između komponenti u pikselima **/
	private int gap;
	/** pomoćna mapa koja pamti pozicije određenih komponenti**/
	private Map<RCPosition, Component> positions;
	
	/**
	 * Konstruktor, stvara novu instancu razreda u kojoj je razmak između tipki 0.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Konstruktor.
	 * @param gap prima željeni razmak između tipki u pikselima.
	 */
	public CalcLayout(int gap) {
		super();
		this.gap = gap;
		positions = new HashMap<>(31); // maksimalni kapacitet;
	}

	/**
	 * Pomoćna metoda koja stavlja predanu komponentu na željenu poziciju.
	 * @param pos pozicija na koju se stavlja element.
	 * @param comp komponenta koja se stavlja.
	 * Ako element na toj poziciji već postoji metoda baca iznimku.
	 */
	public void setPosition(RCPosition pos, Component comp) {
		if(positions.containsKey(pos)){
			throw new IllegalArgumentException("Component on this position already exists!");
		}
				positions.put(pos, comp);
	}

	@Override
	public void addLayoutComponent(String arg0, Component arg1) {

	}
	
	/**
	 * Pomoćna metoda koja ispituje valjanost pozicije.
	 * Valjane su pozicije koje sadrže retke od 1 do maskimalnog broja retka,
	 * stupce od 1 do maks broja stupaca, ali bez kobinacija (1,i) gdje je i=2,3,4,5.
	 * Ako pozicija nije valjana metoda baca odgovarajuću iznimku.
	 * @param row broj retka pozicije.
	 * @param col broj stupca.
	 */
	public static void checkPosition(int row, int col) {
		if (row < 1 || row > ROWS || col < 1 || col > COLS) {
			throw new IllegalArgumentException(
					"Given position is invalid, must be: 1<=cols<=7 and 1<=rows<=5!");
		}
		if ((row == 1 && (col > 1 && col <= 5))) {
			throw new IllegalArgumentException(
					"Positions (1,2),(1,3),(1,4) and (1,5) are invalid!");
		}
	}
	/**
	 * Pomoćna metoda koja služi sa nalaženje minimalne ili preferiranje veličine kontejnera.
	 * @param parent Kontejner koji se ispituje.
	 * @param preff ako je true traži se preferirana veličina kontejnera, minimalna inače
	 * @return vraća se tražena dimenzija.
	 */
	private Dimension findLayoutSize(Container parent, boolean preff){
		Insets insets = parent.getInsets();
		int nComponents = parent.getComponentCount();

		int w = 0;
		int h = 0;

		for (int i = 0; i < nComponents; i++) {
			
			Component comp = parent.getComponent(i);
			Dimension d = comp.getMinimumSize();
			if(preff){
				d = comp.getPreferredSize();}
			
			if (d == null) {
				d = comp.getSize();
			}
			if (d.width > w) {
				w = d.width;
			}
			if (d.height > h) {
				h = d.height;
			}

		}
		return new Dimension(insets.left + insets.right + COLS * w
				+ (gap - 1) *COLS, insets.top + insets.bottom + ROWS
				* h + (gap - 1) * ROWS);
	}
	
	
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (constraints instanceof RCPosition) {
			setPosition((RCPosition) constraints, comp);
		} else if (constraints instanceof String) {
			setPosition(RCPosition.parsePosition((String) constraints), comp);
		} else if (constraints != null) {

			throw new IllegalArgumentException(
					"Cannot add to layout: position must be a RCPosition or a String!");
		}
	}

	@Override
	public void removeLayoutComponent(Component arg0) {
		if (positions.containsValue(arg0)) {
			RCPosition pos = null;
			for (Entry<RCPosition, Component> p : positions.entrySet()) {
				if (p.getValue().equals(arg0)) {
					pos = p.getKey();
				}
			}
			positions.remove(pos);
			return;
		}
		throw new IllegalArgumentException("Cannot remove what isn't here!");

	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return findLayoutSize(parent, false);
	}

	@Override
	public Dimension maximumLayoutSize(Container parent) {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}
	
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return findLayoutSize(parent, true);
	}


	

	@Override
	public void layoutContainer(Container parent) {
		Dimension d =parent.getSize();
		Insets insets=parent.getInsets();
		int sirina=d.width-insets.left-insets.right;//oduzimam insete
		int visina=d.height-insets.bottom-insets.top;
		sirina-=(COLS-1)*gap; //oduzimam praznine
		visina-=(ROWS-1)*gap;
		
		int sirinaCelije=sirina/COLS;
		int visinaCelije=visina/ROWS;
		 for (Entry<RCPosition, Component> entry : positions.entrySet()) {
			   if (entry.getKey().equals(new RCPosition(1, 1))) {
			    entry.getValue().setBounds(insets.left+0, insets.top+0, ROWS * sirinaCelije+ (ROWS-1) * gap, visinaCelije);
			   } else {
			    RCPosition de = entry.getKey();
			    int x = (de.getColumn() - 1) * (sirinaCelije + gap)+insets.left;
			    int y = (de.getRow() - 1) * (visinaCelije + gap)+insets.top;
			    entry.getValue().setBounds(x, y, sirinaCelije, visinaCelije);
			   }
			  }
		
	}

	@Override
	public float getLayoutAlignmentX(Container arg0) {
		return (float)0.5;
	}

	@Override
	public float getLayoutAlignmentY(Container arg0) {
		// TODO Auto-generated method stub
		return (float)0.5;
	}

	@Override
	public void invalidateLayout(Container arg0) {
		// TODO Auto-generated method stub

	}
	
	

}

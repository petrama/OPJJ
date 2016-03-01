package hr.fer.zemris.java.gui.layouts;

public class RCPosition {

	/** indeks stupca **/
	private int column;
	/** indeks retka **/
	private int row;

	/**
	 * Konstruktor.
	 * Prima indeks retka i indeks stupca.
	 * @param row indeks retka.
	 * @param column indeks stupca.
	 */
	public RCPosition(int row, int column) {
		super();
		this.column = column;
		this.row = row;
		CalcLayout.checkPosition(row, column);
	}
	/**
	 * Metoda koja stvara novu instancu razreda <code>RCPosition</code> iz zadanog stringa.
	 * Metoda ocekuje string koji predstavlja dva cijela broja odvojena zarezom.
	 * @param position pozicija zadana stringom.
	 * @return novi primjerak razreda.
	 */
	public static RCPosition parsePosition(String position) {
		String[] num = position.split(",");
		if (num.length != 2) {
			throw new IllegalArgumentException(
					"Given position is invalid, you must provide row and column index separated with ','!");
		}
		int row=0;
		try{
			row=Integer.parseInt(num[0]);
		}catch (NumberFormatException n){
			throw new IllegalArgumentException(
					"Given position is invalid, number of row must be Integer!");
		}
		int col=0;
		try{
			col=Integer.parseInt(num[1]);
		}catch (NumberFormatException n){
			throw new IllegalArgumentException(
					"Given position is invalid, number of columns must be Integer!");
		}
		CalcLayout.checkPosition(row, col);
		return new RCPosition(row, col);
		
	}
	/**
	 * Pomoćna metoda koja oređuje valjanost predanih indeksa.
	 * Valjani indeksi stupaca su između 1 i 7 uključivo a redaka 1 i 5 uključivo bez indeksa (1,i) gdje je i između 2 i p uključivo.
	 * @param row indeks retka.
	 * @param col indeks stupca.
	 */
	
	

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

}

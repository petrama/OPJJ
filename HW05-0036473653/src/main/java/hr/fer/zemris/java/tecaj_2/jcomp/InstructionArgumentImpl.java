package hr.fer.zemris.java.tecaj_2.jcomp;
/**
 * Razred koji predstavlja argument instrukcije.
 * Koristi se kao pomoć pri testiranju.
 * @author Petra Marče
 *
 */
public class InstructionArgumentImpl implements InstructionArgument {
	private String name;

	public InstructionArgumentImpl(String name) {
		this.name = name;
	}
	/**
	 * Metoda koja utvrđuje da li je argument registar.
	 * @return Vraća true ako je,false inače.
	 */
	@Override
	public boolean isRegister() {
		if (this.name.startsWith("r") || name.startsWith("R")) {
			try {
				Integer.parseInt(name.substring(1));
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		}
		return false;
	}
	/**
	 * Metoda koja utvrđuje da li je argument string.
	 * @return Vraća true ako je,false inače.
	 */
	@Override
	public boolean isString() {
		if (isNumber() == false && isRegister() == false) {
			return true;
		}
		return false;
	}
	/**
	 * Metoda koja utvrđuje da li je argument broj.
	 * @return Vraća true ako je,false inače.
	 */
	@Override
	public boolean isNumber() {
		try {
			Integer.parseInt(name);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public Object getValue() {
		if (isRegister()) {
			return  Integer.valueOf(Integer.parseInt(name.substring(1)));
		}
		if (isNumber()) {
			return Integer.valueOf(Integer.parseInt(name));
		}
		return name;
	}

}

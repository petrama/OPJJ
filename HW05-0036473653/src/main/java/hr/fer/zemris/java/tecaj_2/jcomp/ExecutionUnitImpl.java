package hr.fer.zemris.java.tecaj_2.jcomp;
/**
 * Rared koji predstavlja izvršnu jedinicu računala.
 * @author Petra Marče
 *
 */
public class ExecutionUnitImpl implements ExecutionUnit {
	
	/**
	 * Metoda koja radi dohvat instrukcije iz memorije.
	 * Dekodira Instrukciju te ju izvršava,dok je procesor uključen.
	 */
	@Override
	public boolean go(Computer computer) {
		computer.getRegisters().setProgramCounter(0);
		while (true) {
			Instruction instrukcija;
			try {
				instrukcija = (Instruction) computer.getMemory().getLocation(
						computer.getRegisters().getProgramCounter());
			} catch (ClassCastException c) {
				return false;
			}
			computer.getRegisters().incrementProgramCounter();
			if (instrukcija.execute(computer)) {
				break;
			}
		}
		return true;
	}

}

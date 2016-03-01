package hr.fer.zemris.bool.opimpl;

import hr.fer.zemris.bool.*;
import java.util.Arrays;

/**
 * Razred koji služi stvaranju novih instanci Boolovih operatora
 * @author Petra Marče
 *
 */
public class BooleanOperators {
	
	/**
	 * Stvara novi primjerak razreda BooleanOperatorNOT
	 * @param localSource ulazni parametri za operator
	 * @return vraća referencu na stvoreni operator
	 */
	public static BooleanOperator not(BooleanSource localSource) {
		return new BooleanOperatorNOT(localSource);

	}
	/**
	 * Stvara novi primjerak razreda BooleanOperatorAND
	 * @param localSource ulazni parametri za operator
	 * @return vraća referencu na stvoreni operator
	 */
	public static BooleanOperator and(BooleanSource... localSources) {
		return new BooleanOperatorAND(Arrays.asList(localSources));

	}
	/**
	 * Stvara novi primjerak razreda BooleanOperatorOR
	 * @param localSource ulazni parametri za operator
	 * @return vraća referencu na stvoreni operator
	 */
	public static BooleanOperator or(BooleanSource... localSources) {
		return new BooleanOperatorOR(Arrays.asList(localSources));

	}

}

package hr.fer.zemris.bool.opimpl;

import java.util.Arrays;

import hr.fer.zemris.bool.*;

/**
 * Razred koji predstavlja Boolov operator not.
 * @author Petra Marče
 *
 */
public class BooleanOperatorNOT extends BooleanOperator {
	
	/**
	 * Konstruktor.
	 * @param source lista ulaznih operanada.
	 */
	public BooleanOperatorNOT(BooleanSource source) {
		
		super(Arrays.asList(source));

	}

	/**
	 * Meotda koja vraća vrijednost logičke operacije komplementiranja nad argumentom.
	 */
	public BooleanValue getValue() {
		if (getSources().size() > 1)
			throw new IllegalArgumentException(
					"Illegal number of arguments for operator not!");

		BooleanValue tempValue = getSources().get(0).getValue();
		if (tempValue == BooleanValue.FALSE) {
			return BooleanValue.TRUE;
		} else if (tempValue == BooleanValue.TRUE) {
			return BooleanValue.FALSE;

		}
		return BooleanValue.DONT_CARE;
	}

}

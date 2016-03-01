package hr.fer.zemris.java.filechecking.fmagician.lexical;
/**
 * Enumeracija vrsta tokena od kojih se sastoji <i>fmagician</i> program.
 * @author Petra Marče
 *
 */
public enum FMagicianTokenType {
	/** Označava da više nema tokena **/
	EOF,
	/** Identifikator **/
	IDENT,
	/** Ključna riječ **/
	KEYWORD,
	/** Navodnik **/
	QUOTE,
	/** Oznaka negacije **/
	NEGATION,
	/** Oznaka otvaranja bloka **/
	OPEN_BLOCK,
	/** Oznaka zatvaranja bloka **/
	CLOSE_BLOCK,
	/** Oznaka zamjene **/
	SUPSTITUTION,
	/** Oznaka paketa **/
	PACKAGE,
	/** Oznaka poruke **/
	MESSAGE,
	/**  Oznaka za case-insensitive string **/
	CASE_INSENSITIVE,
	/** String **/
	STRING,
	/** Oznaka za argument dir naredbe exists **/
	EXISTS_DIR,
	/** Oznaka za argument file naredbe exists **/
	EXISTS_FILE
}

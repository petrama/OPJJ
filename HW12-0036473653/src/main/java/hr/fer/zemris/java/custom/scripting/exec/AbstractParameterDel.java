package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Apstraktni razred koji služi kao pomoć pri operacijama brisanja parametara iz
 * neke od mapa razreda RequestContext.
 * 
 * @author Petra Marče
 * 
 */
public abstract class AbstractParameterDel {
	ObjectMultistack stack;
	String name;
	String keyName;

	/**
	 * Konstruktor.
	 * Prima referencu na stog s kojeg treba skidati podatke te ime stoga.
	 * @param stack stog sa podatcima.
	 * @param nameOfStack ime stoga.
	 */
	public AbstractParameterDel(ObjectMultistack stack, String nameOfStack) {
		this.stack = stack;
		this.name = nameOfStack;
		paramDel();
	}

	/**
	 * Metoda skida sa stoga jedan parametar koji je ključ u mapi iz koje treba izbrisati zapis.
	 * U slučaju 
	 * 
	 */
	public void popName() {
		Object keyName = stack.pop(name).getValue();
		
			
			if(!(keyName instanceof String || keyName instanceof Double || keyName instanceof Integer)){
			throw new IllegalArgumentException("Argument of function @"
					+ functionName() + " must be one String!");
			}
			
			this.keyName = (String) keyName;
	}

	/**
	 * Središnja metoda koja obavlja operaciju.
	 */
	public void paramDel() {
		popName();
		del();

	}
	/**
	 * Apstraktna metoda koja pristupa mapi i briše iz nje traženi podatak.
	 */
	protected abstract void del();
	/**
	 * Metoda koa vraća ime funkcije smart-script jezika koja je pozvala operaciju.
	 * Služi kao dio poruke u bacanju iznimke ako dođe do pogreške.
	 * Može biti pparamDel ili tparamDel.
	 * U prvom slučaju podatak se briše iz mape persistantParamneters  a udrugom temporaryParameters razreda RequestContext.
	 * @return ime funkcije.
	 */
	protected abstract String functionName();

	public String getKeyName() {
		return keyName;
	}

}

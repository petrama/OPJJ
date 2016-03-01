package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Apststraktni razred koji služi kao pomoć u operacijama dohvata parametara
 * iz neke od mapa razreda RequestContext.
 *  Ovaj razred koriste tri naredbe jezika smartscript: paramGet pparamGet i tparamGet. 
 * 
 * @author Petra Marče
 * 
 */
public abstract class AbstractParameterGet {
	ObjectMultistack stack;
	String name;
	String dv;
	String keyName;
	/**
	 * Konstruktor.
	 * Prima referencu na stog s kojeg treba skidati podatke te ime stoga.
	 * @param stack stog sa podatcima.
	 * @param nameOfStack ime stoga.
	 */
	public AbstractParameterGet(ObjectMultistack stack, String nameOfStack) {
		AbstractParameterGet.this.stack = stack;
		AbstractParameterGet.this.name = nameOfStack;
		paramGet();
	}

	/**
	 * Metoda koja sa stoga skida dva parametra.
	 * Sa stoga se prvo skida defaultna vrijednost a drugo ključ sa kojeg iz mape treba dohvatiti vrijednost.
	 * Defaultna vrijednost se koristi ako u mapi nema traženog ključa.
	 */
	public void popNames() {
		Object dv = stack.pop(name).getValue();
		Object keyName = stack.pop(name).getValue();
		if(!(keyName instanceof String || keyName instanceof Double || keyName instanceof Integer)
				&&(dv instanceof String || dv instanceof Double || dv instanceof Integer)){
				throw new IllegalArgumentException(
						"Arguments of function @"+functionName()+" must be two Strings!");
			}
			this.dv = (String) dv;
			this.keyName = (String) keyName;
		
	}
	
	/**
	 * Metoda koja na stog stavlja dohvaćenu vrijednost ili defaultnu, ovisno o uspješnosti dohvata.
	 */
	public void paramGet() {
		popNames();
		String valueFromMap = requiredString();
		String valueToPush = valueFromMap == null ? (String) dv : valueFromMap;
		stack.push(name, new ValueWrapper(valueToPush));

	}
	/**
	 * Metoda koja vraća vrijednost iz mape na traženom ključu.
	 * Ako takvoga nema vraća null.
	 * @return vrijednost ili null.
	 */
	protected abstract String requiredString();
	/**
	 * Metoda koa vraća ime funkcije smart-script jezika koja je pozvala operaciju.
	 * Služi kao dio poruke u bacanju iznimke ako dođe do pogreške.
	 * Može biti pparamDel ili tparamDel.
	 * U prvom slučaju podatak se briše iz mape persistantParamneters  a udrugom temporaryParameters razreda RequestContext.
	 * @return ime funkcije.
	 */
	protected abstract String functionName();
	/**
	 * Metoda dohvata ključa kojeg treba potražiti u mapi.
	 * @return ključ.
	 */
	public String getKeyName() {
		return keyName;
	}
}

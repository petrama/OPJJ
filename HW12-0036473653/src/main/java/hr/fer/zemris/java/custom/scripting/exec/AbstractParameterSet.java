package hr.fer.zemris.java.custom.scripting.exec;
/**
 * Apstraktni razred koji služi kao pomoć pri operacijama postavljanja parametara iz
 * neke od mapa razreda RequestContext.
 * Ovaj razred koriste dvije naredbe jezika smartscript: pparamSet i tparamSet. 
 * 
 * @author Petra Marče
 * 
 */
public abstract class AbstractParameterSet {
	ObjectMultistack stack;
	String name;
	String value;
	String keyName;

	/**
	 * Konstruktor.
	 * Prima referencu na stog s kojeg treba skidati podatke te ime stoga.
	 * @param stack stog sa podatcima.
	 * @param nameOfStack ime stoga.
	 */
	public AbstractParameterSet(ObjectMultistack stack, String nameOfStack) {
		this.stack = stack;
		this.name = nameOfStack;
		paramSet();
	}
	
	/**
	 * Metoda koja sa stoga skida dva parametra.
	 * Sa stoga se prvo skida ključ u mapi zatim vrijednost.
	 */
	
	public void popNames(){
		
		Object keyName = stack.pop(name).getValue();
		Object value = stack.pop(name).getValue();
		
		if(!(keyName instanceof String || keyName instanceof Double || keyName instanceof Integer)
			&&(value instanceof String || value instanceof Double || value instanceof Integer)){
			throw new IllegalArgumentException(
					"Arguments of function @"+functionName()+" must be two Strings!");
		}
			this.value=""+value;
			this.keyName=""+keyName;
	}

	/**
	 * Pomoćna metoda koja radi postavljanje.
	 */
	public  void paramSet() {
			popNames();
			set();
				
	}
	
	/**
	 * Apstraktna metoda kojom se pristupa mapi i postavlja nova vrijednost.
	 */
	protected abstract void set();
	/**
	 * Metoda koa vraća ime funkcije smart-script jezika koja je pozvala operaciju.
	 * Služi kao dio poruke u bacanju iznimke ako dođe do pogreške.
	 * Može biti pparamDel ili tparamDel.
	 * U prvom slučaju podatak se briše iz mape persistantParamneters  a udrugom temporaryParameters razreda RequestContext.
	 * @return ime funkcije.
	 */
	protected abstract String functionName();
	/**
	 * Metoda koja vraća ključ na koji treba spremiti novu vrijednost u mapi.
	 * @return ključ
	 */
	public String getKeyName(){
		return keyName;
	}
	/**
	 * Metoda koja vraća novu vrijednost na koju se stara u mapi treba postaviti.
	 * @return vrijednost
	 */
	
	public String getValue(){
		return value;
	}
}

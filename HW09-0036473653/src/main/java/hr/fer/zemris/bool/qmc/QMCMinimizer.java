package hr.fer.zemris.bool.qmc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanSource;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.MaskValue;
import hr.fer.zemris.bool.Masks;
import hr.fer.zemris.bool.fimpl.MaskBasedBF;
import hr.fer.zemris.bool.fimpl.OperatorTreeBF;
import hr.fer.zemris.bool.opimpl.BooleanOperators;

/**
 * Minimizator Boolovih funkcija.
 * Razred nudi minimizaciju boolovih funkcija pomoću Quine-McCluskeyevog i Pyne-McCluskeyevog algoritma.
 * Glavna metoda <code>minimize</code> očekuje  bilo kakvu ispravno zadanu instancu razeda koji implementira,
 * sučelje <code>BooleanFunction</code> te zastavicu koja označava koji oblik zapisa kao izlaz želimo.
 * Dva moguća oblika zapisa su oblik sume minterma i produkta maksterma.
 * @author Petra Marče
 *
 */
public class QMCMinimizer {
	/** lista primarnih implikanata **/
	private static List<Mask> primaryImplicants;
	/** lista bitnih primarnih implikanata **/
	private static List<Mask> essentialImplicants;
	/** tablica pokrivenosti **/
	private static Map<Integer, List<Mask>> table;
	/**
	 * mapa koja povezuje masku i varijablu koja se koristi u Pyne-McCluskey
	 * algoritmu
	 **/
	private static Map<Mask, BooleanVariable> mappedPyne;
	/** Lista minimalnih oblika funkcije **/
	private static List<List<Mask>> allMinForms;

	/**
	 * Metoda koja minimizira ulaznu funkciju i vraća sve njene minimalne oblike.
	 * @param function funkcija koja se minimizira.
	 * @param wantProducts zastavica ako je true daje zapis u obliku maksterma, u obliku minterma inače
	 * @return vraća polje funkcija koje su minimalni oblici originalne funkcije.
	 */
	public static MaskBasedBF[]minimize(BooleanFunction function,
			boolean wantProducts) {
		List<Mask> toMinimize=new ArrayList<>();
		List<Integer> indexes;
		if (wantProducts){
			indexes=function.getMaxterms();
			
		}else{
			indexes=function.getMinterms();//indeksi koje treba dohvatiti
		}
	
		for(Integer i:indexes){
			toMinimize.add(Mask.fromIndex(function.getDomain().size(), i));//popis maski koje se minimiz
			
		}
		
		for(Integer i:function.getDonts()){
			toMinimize.add(Mask.fromIndex(function.getDomain().size(), i));
		}
		
		//isčitavanje primarnih implikanata
		primaryImplicants=getPrimaryImplicants(toMinimize);

		//modeliranje tablice pokrivenosti
		table=makeTable(indexes, primaryImplicants, function.getDomain().size());

		//isčitavanje bitnih primarnih implikanata
		essentialImplicants=getEssentialPrimaryImplicants(table);

		//reduciranje tablice
		Map<Integer, List<Mask>> rTable=reduceTable(table, essentialImplicants);
		
		if(rTable.isEmpty()){//ako je tablica prazna, bitni impl su već sve pokrili.
			MaskBasedBF[] allfunc=new MaskBasedBF[1];
			
				allfunc[0]=new MaskBasedBF("1. form", function.getDomain(),!wantProducts,essentialImplicants,new ArrayList<Mask>());
				return allfunc;
		}
		
		
		//svakoj maski pridruzi jednu BooleanVarijablu
		mappedPyne=mapNames(listMasks(rTable));
		//izgradi Pyne funkciju
		OperatorTreeBF op=pyneOperator(rTable);
		
		int size=op.getDomain().size();//velicina domene pyne-funkcije

		List<Integer> arg=new ArrayList<>();
		
		arg.add(size);
		arg.addAll(op.getMinterms());//svi mogući mintermi

		Integer[] polje=new Integer[arg.size()];
		List<Mask> minterms=Masks.fromIndexes( arg.toArray(polje));//pretvaranje svih minterma u maske

		
	
		allMinForms=getAllMinimumForms(useMasks(minimum(minterms),op));//izvlacenje onih s najmanjim brojem jedinica

		minimizeNumOfVars();//dodatno minimiziranje po ulazima
	
		//stvaranje polja funkcija
		MaskBasedBF[] allfunc=new MaskBasedBF[allMinForms.size()];
		for(int i=0;i<allMinForms.size();i++){
			allfunc[i]=new MaskBasedBF(i+1+". form", function.getDomain(),!wantProducts, allMinForms.get(i),new ArrayList<Mask>());
		}
		return allfunc;
	}
	
	/**
	 * Pomoćna metoda koja vraća listu maski koje se međusobno više ne mogu kombinirati.
	 * Sve maske u povratnoj listi koje nisu dontcareovi su primarni implikanti funkcije.
	 * @param masks
	 *            maske koje predstavljaju minterme ili maksterme;
	 * @return listu maski koje predtsavljaju maske koje jedine mogu činiti minimalni oblik funkcije.
	 */

	public static List<Mask> kombiniraj(List<Mask> masks) {
		int n = masks.size();
		Set<Mask> covered = new HashSet<>();
		List<Mask> newMasks = new ArrayList<>();
		for (int i = 0; i < n - 1; i++) {// za sve kombinacije
			for (int j = 0; j < n; j++) {
				Mask combinedMask = Mask.combine(masks.get(i), masks.get(j));
				if (combinedMask != null) {// ako se mogu kombinirat
					newMasks.add(combinedMask);// dodaj u mapu novih maski tu
												// masku
					covered.add(masks.get(i));// oznaka da su maske uspjesno
												// kobinirane
					covered.add(masks.get(j));
				}
			}
		}
		if (newMasks.isEmpty() == false) {// ako si išta u životu uspio
											// kombinirat
			masks.removeAll(covered); // neiskoristene makni
			masks.addAll(kombiniraj(newMasks));// dodaj kombinacije kombiniranih
		}// inače ne mjenjaj ništa
		Set<Mask> s = new HashSet<>(masks);
		return new ArrayList<>(s);// vrati listu maski
	}

	/**
	 * Pomoćna metoda koja vraća listu maski koje predstavljaju primarne
	 * implikante funkcije. Primarni implikant je implikant koji nema drugi
	 * implikant koji ga pokriva. Minimalni oblik funkcije ne mogu činiti
	 * implikanti koji nisu primarni.
	 * 
	 * @param masks
	 *            maske koje predstavljaju minterme ili maksterme;
	 * @return listu maski koje predtsavljaju primarne implikante.
	 */
	public static List<Mask> getPrimaryImplicants(List<Mask> masks) {
		List<Mask> pomocna = kombiniraj(masks);
		for (Mask m : pomocna) {
			if (m.isDontCare()) {
				masks.remove(m);
			}
		}
		Set<Mask> s = new HashSet<>(masks);
		return new ArrayList<>(s);
	}

	/**
	 * Pomoćna metoda koja vraća bitne primarne implikante neke funkcije. Bitni
	 * primarni implikanti su primarni implikanti koji jedini pokrivaju neki
	 * minterm/maksterm.
	 * 
	 * @param table
	 *            tablica minimizacije iz koje se čitaju bitni primarni
	 *            implikanti.
	 * @return lista bitnih primarnih implikanata.
	 */

	public static List<Mask> getEssentialPrimaryImplicants(
			Map<Integer, List<Mask>> table) {
		List<Mask> primary = new ArrayList<>();
		// prolazi kroz sve liste kojeg nekoga pokrivaju
		for (List<Mask> list : table.values()) {
			if (list.size() == 1) {// ako neki indeks pokriva samo jedna maska
				primary.add(list.get(0));// ta maska je sigurno primarni
											// implikant pa je dodaj u listu
			}
		}
		Set<Mask> s = new HashSet<>(primary);
		return new ArrayList<>(s);

	}

	/**
	 * Pomoćna metoda koja radi tablicu koja služi kao pomoć pri minimizaciji.
	 * Retci tablice su svi implikanti dobiveni kobiniranjem maski, dok su
	 * stupci tablice indeksi koji predstavljaju minterme ili maksterme. U
	 * tablici se za svaki indeks pamti koji implikanti ga pokrivaju. Implikant
	 * koji jedini pokriva neki indeks primarni je implikant.
	 * 
	 * @param indexes
	 *            indeksi koji prestavljaju minterme ili maksterme.
	 * @param implicants
	 *            lista maski implikanata.
	 * @param domainSize
	 *            broj varijabla funkcije koja se minimizira.
	 * @return tablicu minimizacije
	 */
	public static Map<Integer, List<Mask>> makeTable(List<Integer> indexes,
			List<Mask> primaryImplicants, int domainSize) {
		Map<Integer, List<Mask>> table = new HashMap<>();

		for (Integer i : indexes) {// inicijalizacija
			table.put(i, new ArrayList<Mask>());// stvaranje mape oblika
												// indeks-prazna lista maski
		}

		for (Integer i : indexes) {// za svaki indeks
			for (Mask m : primaryImplicants) {// pogledaj svaki implikant
				if (m.isMoreGeneralThan(Mask.fromIndex(domainSize, i))
						|| m.equals(Mask.fromIndex(domainSize, i))) {// ako
					// ga
					// on
					// pokriva
					// dodaj ga u listu onih koji pokrivaju taj indeks
					List<Mask> pom = table.get(i);
					pom.add(m);
					table.put(i, pom);

				}
			}
		}
		return table;
	}

	/**
	 * Pomoćna metoda koja reducira tablicu pokrivenosti.
	 * Reducirati znači izbaciti iz tablice sve minterme/maksterme koji su pokriveni bitnim primarnim implikantima.
	 * Reduciranje se mora provesti prije Pyne-McCluskeyevog algoritma.
	 * @param table tablica koju je potrebno reducirati.
	 * @param essential lista bitnih primarnih implikanata.
	 * @return reducirana tablica
	 */
	public static Map<Integer, List<Mask>> reduceTable(
			Map<Integer, List<Mask>> table, List<Mask> essential) {
		Map<Integer, List<Mask>> reducedTable = new HashMap<>();
		for (Map.Entry<Integer, List<Mask>> entry : table.entrySet()) {

			if (entry.getValue().removeAll(essential) == false) {
				reducedTable.put(entry.getKey(), entry.getValue());
			}

		}
		return reducedTable;
	}

	/**
	 * Metoda koja svakoj maski pridružuje jednu instancu razreda <code>BooleanVariable</code>.
	 * @param varNames skup svih maski.
	 * @return mapa maski i njihovih varijabli.
	 */
	public static Map<Mask, BooleanVariable> mapNames(Set<Mask> varNames) {
		Map<Mask, BooleanVariable> map = new HashMap<>();
		for (Mask name : varNames) {
			map.put(name, new BooleanVariable(name.toString()));
		}
		return map;
	}

	/**
	 * Pomoćna metoda koja gradi Pyne-McCluskeyevu funkciju.
	 * Ta funkcija određuje koje sve primarne implikante moramo uzeti da bi minimizirali pocetnu funkciju.
	 * @param table reducirana tablica pokrivenosti.
	 * @return Pyne-McCluskeyeva funkcija.
	 */
	public static OperatorTreeBF pyneOperator(Map<Integer, List<Mask>> table) {
		BooleanSource[] zagrade = new BooleanSource[table.size()];
		int j = 0;
		for (List<Mask> list : table.values()) {

			BooleanVariable[] elementiZagrade = new BooleanVariable[list.size()];

			for (int i = 0; i < list.size(); i++) {

				elementiZagrade[i] = mappedPyne.get(list.get(i));

			}

			
			// OR-am sve elemente zagrade po formuli
			zagrade[j] = BooleanOperators.or(elementiZagrade);

			j++;
		}
		// AND-am sve zagrade
		BooleanOperator product = BooleanOperators.and(zagrade);
		// radim od toga operator tree
		return new OperatorTreeBF("pyne", product.getDomain(), product);
	}

	
	/**
	 * Pomoćna metoda koja reducira minimalne oblike.
	 * Iz liste minimalnih oblika miče one koji nemaju minimalan broj ulaza.
	 */
	public static void minimizeNumOfVars() {
		int max = 0;
		for (List<Mask> l : allMinForms) {
			int pom = 0;
			for (Mask m : l) {
				pom += m.numberOfDontCares();
			}
			if (pom > max) {
				max = pom;
			}
		}

		List<List<Mask>> pom = new ArrayList<>(allMinForms);
		for (List<Mask> l : pom) {
			int s = 0;
			for (Mask m : l) {
				s += m.numberOfDontCares();
			}
			if (s != max) {
				allMinForms.remove(l);
			}
		}

	}

	/**
	 * Metoda koja vraća liste maski koje predstavljaju minimalne oblike funkcije.
	 * @param l ulazna lista maski koje su dobivene Pyne-McCluskeyevom metodom.
	 * U svaku tu listu dodaje i primarne implikante i tako dobiva potpune minimalne oblike.
	 * @return Lista listi maski koje predstavljaju minimalan oblik funkcije.
	 */
	public static List<List<Mask>> getAllMinimumForms(List<List<Mask>> l) {
		List<List<Mask>> forms = new ArrayList<>();
		for (List<Mask> list : l) {
			List<Mask> oneForm = new ArrayList<>();
			oneForm.addAll(essentialImplicants);
			oneForm.addAll(list);
			forms.add(oneForm);
		}
		return forms;
	}

	
	/**
	 * Pomoćna metoda koja vraća skup svih maski koje se nalaze u nekoj tablici.
	 * @param table tablica iz koje se vadi skup svih maski.
	 * @return skup svih maski.
	 */
	public static Set<Mask> listMasks(Map<Integer, List<Mask>> table) {
		Set<Mask> names = new HashSet<>();
		for (List<Mask> list : table.values()) {

			for (int i = 0; i < list.size(); i++) {
				names.add(list.get(i));
			}
		}
		return names;
	}

	
	/**
	 * Pomoćna metoda koja iz liste maski koje predstavljaju minterme Pyne funkcije,
	 * vraća maske s najmanjim brojem jedinica, tj maske koje predstavljaju minimalnu formu.
	 * @param minterms svi mintermi Pynove funkcije.
	 * @return mintermi s minimalnim brojem jedinica.
	 */
	private static List<Mask> minimum(List<Mask> minterms) {
		int min = minterms.get(0).getNumberOfOnes();
		for (Mask k : minterms) {
			if (k.getNumberOfOnes() < min) {
				min = k.getNumberOfOnes();
			}
		}
		List<Mask> mins = new ArrayList<>();
		for (Mask k : minterms) {
			if (k.getNumberOfOnes() == min) {
				mins.add(k);
			}
		}

		return mins;
	}

	/**
	 * Metoda koja iz maski koje predstavljaju minimalne oblike Pynove funkcije,
	 * vraća skup Maski početne funkcije koje će u uniji s primarnim implikantima činiti minimalne oblike.
	 * @param minterms mintermi Pynove funkcije.
	 * @param op Pynova-funkcija.
	 * @return Lista listi maski koje predtsavljaju minimalne oblike početne funkcije, ali bez primarnih implikanata.
	 */
	public static List<List<Mask>> useMasks(List<Mask> minterms, OperatorTreeBF op) {
		List<List<Mask>> l = new ArrayList<>();

		for (Mask m : minterms) {
			List<Mask> oneMin = new ArrayList<>();
			for (int i = 0; i < m.getValues().size(); i++) {
				if (m.getValue(i) == MaskValue.ONE) {
					BooleanVariable v = op.getDomain().get(i);
					String name = v.getName();
					for (Mask k : mappedPyne.keySet()) {
						if (k.toString().equals(name)) {
							oneMin.add(k);
						}
					}
				}

			}
			l.add(oneMin);
		}
		return l;
	}
}
package hr.fer.zemris.web.radionice;

import hr.fer.zemris.web.radionice.iznimke.InconsistentDatabaseException;
import hr.fer.zemris.web.radionice.komparatori.RadioniceKomparator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class RadioniceBaza {
	/** ime datoteke u kojoj je popis radionica **/
	private static String datRadionice = "/radionice.txt";
	/** ime datoteke u kojoj je popis opreme **/
	private static String datOprema = "/oprema.txt";
	/** ime datoteke u kojoj je popis publike **/
	private static String datPublika = "/publika.txt";
	/** ime datoteke u kojoj je popis svih trajanja **/
	private static String datTrajanje = "/trajanje.txt";
	/** ime datoteke koja povezuje sifre publike s radionicama **/
	private static String datRadionicePublika = "/radionice_publika.txt";
	/** ime datoteke koja povezuje sifre dostupne opreme s radionicama **/
	private static String datRadioniceOprema = "/radionice_oprema.txt";

	/** staza do direktorija baze s kojom radimo **/
	String staza;

	/** lista koja čuva radionice učitane iz baze **/
	private List<Radionica> radionice;
	/** lista koja čuva popis opreme učitan iz baze **/
	private List<Opcija> oprema;
	/** lista koja čuva popis publike učitan iz baze **/
	private List<Opcija> publika;
	/** lista koja čuva popis trajanja učitan iz baze **/
	private List<Opcija> trajanje;
	/** lista koja povezuje sifre publike s radionicama **/
	private List<Opcija> radionicePublika;
	/** lista koja povezuje sifre dostupne opreme s radionicama **/
	private List<Opcija> radioniceOprema;

	/** dosad najveći dodjeljeni id radionice u trenutnoj bazi **/
	private Long maxId = null;

	/**
	 * Konstruktor, stvara novi primjerak razreda.
	 * 
	 * @param staza
	 *            putanja do baze s kojom se radi.
	 * @param oprema
	 *            popis opreme koja je na raspolaganju radionicama u bazi.
	 * @param publika
	 *            ciljana publika.
	 * @param trajanje
	 *            lista mogućih trajanja radionica.
	 * @param radp
	 *            lista koja povezuje sifre publike s radionicama
	 * @param rado
	 *            lista koja povezuje sifre dostupne opreme s radionicama
	 */
	public RadioniceBaza(String staza, List<Opcija> oprema,
			List<Opcija> publika, List<Opcija> trajanje, List<Opcija> radp,
			List<Opcija> rado) {
		super();
		this.staza = staza;
		this.oprema = oprema;
		this.publika = publika;
		this.trajanje = trajanje;
		this.radionicePublika = radp;
		this.radioniceOprema = rado;
		this.radionice = ucitajRadionice(staza + datRadionice);
	}

	/**
	 * Metoda za dohvat liste svih radionica. Metoda vraća listu koju je moguće
	 * samo čitati.
	 * 
	 * @return vraća listu radionica iz baze.
	 */
	public List<Radionica> getRadionice() {
		return new ArrayList<>(radionice);

	}

	/**
	 * Metoda za dohvat liste koja predstavlja popis sve raspoložive opreme.
	 * Metoda vraća listu koju je moguće samo čitati.
	 * 
	 * @return vraća listu opreme iz baze.
	 */
	public List<Opcija> getPopisOpreme() {
		return Collections.unmodifiableList(oprema);
	}

	/**
	 * Metoda za dohvat liste koja predstavlja popis moguće publike radionica u
	 * bazi. Metoda vraća listu koju je moguće samo čitati.
	 * 
	 * @return vraća lista publika iz baze.
	 */
	public List<Opcija> getPopisPublike() {
		return Collections.unmodifiableList(publika);
	}

	/**
	 * Metoda za dohvat liste koja predstavlja popis mogućih trajanja radionica
	 * u bazi. Metoda vraća listu koju je moguće samo čitati.
	 * 
	 * @return vraća lista svih trajanja iz baze.
	 */
	public List<Opcija> getPopisTrajanja() {
		return Collections.unmodifiableList(trajanje);
	}

	/**
	 * Metoda za dohvat jedne radionice iz liste radionica u bazi.
	 * 
	 * @param id
	 *            id tražene radionce.
	 * @return vraća traženu radionicu ili null ako takve nema u bazi.
	 */
	public Radionica getRadionica(Long id) {
		for (Radionica r : radionice) {
			if (r.getId().equals(id)) {

				return r;
			}
		}
		return null;
	}

	/**
	 * Pomoćna metoda koja iz predane kolekcije vraća opciju sa zadanim id-om.
	 * 
	 * @param kolekcija
	 *            kolekcija u kojoj treba potraziti opciju.
	 * @param id
	 *            id tražene opcije.
	 * @return nađena opcija ili null ako takve nema u predanoj kolekciji.
	 */
	private Opcija getOpcija(List<Opcija> kolekcija, String id) {
		for (Opcija opc : kolekcija) {
			if (opc.getId().equals(id)) {
				return opc;
			}
		}
		return null;
	}

	
	/**
	 * Metoda koja učitava podatke iz datoteka baze u privatne kolekcije.
	 * 
	 * @param direktorij
	 *            direktorij iz kojeg podatke treba učitati.
	 * @return vraća primjerak razreda koji predstavlja bazu na zadanoj putanji.
	 */
	public static RadioniceBaza ucitaj(String direktorij) {
		List<Opcija> oprema = ucitajProperty(direktorij + datOprema);
		List<Opcija> publika = ucitajProperty(direktorij + datPublika);
		List<Opcija> trajanje = ucitajProperty(direktorij + datTrajanje);
		List<Opcija> radpublika = ucitajProperty(direktorij
				+ datRadionicePublika);
		List<Opcija> radoprema = ucitajProperty(direktorij + datRadioniceOprema);
		return new RadioniceBaza(direktorij, oprema, publika, trajanje,
				radpublika, radoprema);

	}

	/**
	 * Pomoćna metoda za učitavanje liste opcija. Metoda otvara datoteku na
	 * predanoj putanji, čita njen sadržaj te tako puni kolekciju.
	 * 
	 * @param putanja
	 *            putanja do datoteke iz koje treba učitati kolekciju.
	 * @return vraća listu opcija napunjenu iz isparsiranog sadržaja datoteke
	 */
	private static List<Opcija> ucitajProperty(String putanja) {
		List<Opcija> opcije = new ArrayList<>();
		File datoteka = new File(putanja);
		if (!datoteka.exists()) {
			return opcije;
		}
		List<String> linije = new ArrayList<>();
		try {
			linije = Files.readAllLines(datoteka.toPath(),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String linija : linije) {
			String[] dijelovi = linija.split("\t");
			opcije.add(new Opcija(dijelovi[0], dijelovi[1]));
		}
		return opcije;
	}

	/**
	 * Metoda koja iz predane datoteke iščitava radionice i puni ih u bazu.
	 * 
	 * @param fileName
	 *            putanja do datoteke koja sadrži popis radionica.
	 * @return lista radionica dobivena parsiranjem predane datoteke.
	 */
	private List<Radionica> ucitajRadionice(String fileName) {
		List<Radionica> radionice = new ArrayList<>();
		File datoteka = new File(fileName);
		if (!datoteka.exists()) {
			return radionice;
		}
		List<String> linije=new ArrayList<>();
		try {
			linije = Files.readAllLines(datoteka.toPath(),
					StandardCharsets.UTF_8);
			maxId = Long.valueOf(linije.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String linija : linije) {
			if (!linija.isEmpty()) {
				radionice.add(radionicaIzLinije(linija.trim().split("\t")));
			}
		}

		return radionice;
	}

	/**
	 * Pomoćna metoda koja stvara novu radionicu iz jedne linije datoteke
	 * podjeljene po tabovima.
	 * 
	 * @param dijelovi
	 *            ulazno polje stringova nastalo splitanjem linije datoteke po
	 *            tabovima.
	 * @return dobiveni primjerak radionice.
	 */
	private Radionica radionicaIzLinije(String[] dijelovi) {
		Long id = Long.parseLong(dijelovi[0]);
		String naziv = dijelovi[1];
		String datum = dijelovi[2];

		Set<Opcija> oprema = povezi(dijelovi[0], staza + datRadioniceOprema,
				this.oprema);
		Opcija trajanje = getOpcija(this.trajanje, dijelovi[4]);// dohvat opcije
																// s trazenim id
		Set<Opcija> publika = povezi(dijelovi[0], staza + datRadionicePublika,
				this.publika);
		Integer maxPol = Integer.parseInt(dijelovi[3]);
		String email = dijelovi[5];
		String dopuna = "";
		if (dijelovi.length == 7) {
			dopuna = dijelovi[6].trim().replace("\\n", "\n")
					.replace("\\t", "\t").replace("\\\\", "\\");

		}

		return new Radionica(id, naziv, datum, oprema, trajanje, publika,
				maxPol, email, dopuna);

	}

	/**
	 * Pomoćna metoda koja radi spajanje tablice opcija sa radionicom. Metoda iz
	 * predane datoteke učitava sve opcije nakon čega radionici sa predanim
	 * id-om pridružuje samo id-eve opcija koji se na nju odnose.
	 * 
	 * @param idRadionice
	 *            id radionice koju trenutno obrađujemo.
	 * @param datoteka
	 *            datoteka iz koje se trebaju učitati opcije.
	 * @param kolekcija
	 *            kolekcija iz koje se dodaju opcije.
	 * @return
	 */
	private Set<Opcija> povezi(String idRadionice, String datoteka,
			List<Opcija> kolekcija) {
		List<Opcija> radioniceOprema = ucitajProperty(datoteka);
		Set<Opcija> oprema = new LinkedHashSet<>();
		for (Opcija opc : radioniceOprema) {
			if (opc.getId().equals(idRadionice)) {// samo ako se opcija odnosi
													// na ovu radionicnu;
				oprema.add(opc);
			}
		}
		// sada su u oprema "opcije" iz datoteke radionice_oprema jos nista nije
		// povezano
		Set<Opcija> pravaOprema = new LinkedHashSet<>(oprema.size());
		for (Opcija opc : oprema) {
			pravaOprema.add(getOpcija(kolekcija, opc.getVrijednost()));// povezivanje
		}
		return pravaOprema;
	}

	/**
	 * Metoda koja snima trenutno stanje baze u direktorij na putanji predanoj u
	 * konstruktoru.
	 */
	public void snimi() {
		snimi(staza);
	}

	/**
	 * Metoda koja snima trenutno stanje baze u direktorij na predanoj putanji.
	 * 
	 * @param putanja
	 */
	public void snimi(String putanja) {
		provjeriIspravnostOpcija();
		Collections.sort(radionice, RadioniceKomparator.PO_ID);
		snimiDatotekuRadionice(putanja + datRadionice);
		Collections.sort(oprema);
		snimiDatoteku(oprema, putanja + datOprema);
		Collections.sort(publika);
		snimiDatoteku(publika, putanja + datPublika);
		Collections.sort(trajanje);
		snimiDatoteku(trajanje, putanja + datTrajanje);
		Collections.sort(radioniceOprema);
		snimiDatoteku(radioniceOprema, putanja + datRadioniceOprema);
		Collections.sort(radionicePublika);
		snimiDatoteku(radionicePublika, putanja + datRadionicePublika);

	}

	/**
	 * Metoda koja snima datoteku s radionicama.
	 * 
	 * @param fileName
	 *            putanja do datoteke u koju treba zapisati sve radionice baze.
	 */
	private void snimiDatotekuRadionice(String fileName) {
		OutputStreamWriter writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(new File(
					fileName)), "UTF-8");

			for (Radionica rad : radionice) {
				String upisi = rad.getId() + "\t" + rad.getNaziv() + "\t"
						+ rad.getDatum() + "\t" + rad.getMaksPolaznika() + "\t"
						+ rad.getTrajanje().getId() + "\t" + rad.getEmail();

				String pom = rad.getDopuna();
				pom = pom.replace("\\", "\\\\");
				pom = pom.replace("\r\n", "\\n");
				pom = pom.replace("\n", "\\n");
				pom = pom.replace("\t", "\\t");
				upisi += "\t" + pom;

				writer.write(upisi + "\n");

			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metoda koja snima datoteke opcija baze.
	 * 
	 * @param kolekcija
	 *            kolekcija opcija koju treba snimiti.
	 * @param fileName
	 *            putanja do datoteke u koju opcije trebaju biti snimljene.
	 */
	private void snimiDatoteku(List<Opcija> kolekcija, String fileName) {

		OutputStreamWriter writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(new File(
					fileName)), "UTF-8");

			for (Opcija opc : kolekcija) {
				writer.write(opc.id + "\t" + opc.vrijednost + "\n");

			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metoda koja snima, tj. dodaje u bazu novu radionicu, ili ažurira
	 * postojeću.
	 * 
	 * @param r
	 *            radionica koja se treba dodati ili ažurirati.
	 */
	public void snimi(Radionica r) {

		if (r.getId() == null) {
			Long noviId = maxId == null ? 1 : maxId + 1;
			r.setId(noviId);
		}

		List<Radionica> nova = new ArrayList<>(radionice);
		for (Radionica postojeca : radionice) {
			if (postojeca.getId().equals(r.getId())) {
				nova.remove(postojeca);
			}
		}
		radionice = nova;
		radionice.add(r);

		this.radioniceOprema = osvjeziOpcije(radioniceOprema, r.getId(),
				r.getOprema());
		this.radionicePublika = osvjeziOpcije(radionicePublika, r.getId(),
				r.getPublika());

		if (maxId == null || r.getId().compareTo(maxId) > 0) {
			maxId = r.getId();
		}
	}

	/**
	 * Pomoćna metoda koja ažurura liste koje povezuju popis opcija s konkretnom
	 * radionicom. Mora se pozvati prilikom dodavanja nove ili uređivanja
	 * postojeće radionice.
	 * 
	 * @param listaOpcija
	 *            lista koja se treba ažurirati, u ovoj aplikaciji to je lista
	 *            opreme i publike.
	 * @param idRadionice
	 *            id-radionice čiji su se podatci promjenili.
	 * @param noveOpcije
	 *            sifre novih opcija koje karakteriziraju radionicu.
	 * @return
	 */
	private List<Opcija> osvjeziOpcije(List<Opcija> listaOpcija,
			Long idRadionice, Set<Opcija> noveOpcije) {
		List<Opcija> pom = new ArrayList<>(listaOpcija);
		for (Opcija postojeca : listaOpcija) {
			if (postojeca.getId().equals(Long.toString(idRadionice))) {
				pom.remove(postojeca);
			}
		}
		for (Opcija s : noveOpcije) {
			pom.add(new Opcija(Long.toString(idRadionice), s.getId()));
		}
		return pom;

	}

	/**
	 * Metoda koja provjerava ispravnost opcija trajanja i listu publike i
	 * opreme u bazi.
	 */
	private void provjeriIspravnostOpcija() {
		for (Radionica r : radionice) {
			zapisProvjera(trajanje, r.getTrajanje());
			provjeriSkup(publika, r.getPublika());
			provjeriSkup(oprema, r.getOprema());

		}
	}

	/**
	 * Metoda koja provjerava da li je skup opcija iz radionice napunjen
	 * legalnim vrijednostima iz baze. Predani skup je ispravan samo onda kad je
	 * svaka njegova opcija sadržana u listi referentnih opcija.
	 * 
	 * @param referentna
	 *            referentna lista koja je ispravna.
	 * @param sumnjiva
	 *            sumnjivi skup opcija čija se ispravnost provjerava.
	 */

	private void provjeriSkup(List<Opcija> referentna, Set<Opcija> sumnjiva) {
		for (Opcija sumnjivac : sumnjiva) {
			zapisProvjera(referentna, sumnjivac);
		}
	}

	/**
	 * Pomoćna metoda koja provjerava da li je jedna opcija legalna. Opcija je
	 * legalna ako je lista dopuštenih opcija sadržava.
	 * 
	 * @param referentna
	 *            referentna lista.
	 * @param sumnjivac
	 *            opcija koja se ispituje.
	 */
	private void zapisProvjera(List<Opcija> referentna, Opcija sumnjivac) {
		for (Opcija ref : referentna) {
			if (ref.getId().equals(sumnjivac.getId())
					&& ref.getVrijednost().equals(sumnjivac.getVrijednost())) {
				return;
			}
		}
		throw new InconsistentDatabaseException("Opcija: " + sumnjivac
				+ " je ilegalna!");

	}

	/**
	 * Razred koji predstavlja jednu opciju tj publiku, trajanje ili opremu
	 * raspoloživu radionicama u ovoj bazi. Svaka opcija ima svoj identifikator
	 * i vrijednost. Prirodni poredak skupa opcija je prirodni poredak njihovi
	 * id-eva.
	 * 
	 * @author Petra Marče
	 * 
	 */
	public static class Opcija implements Comparable<Opcija> {
		private String id;
		private String vrijednost;

		/**
		 * Metoda koja stvara novu opciju iz predanog id-a i vrijednosti.
		 * @param id stringovna reprezentacija id-a opcije.
		 * @param vrijednost vrijednost opcije.
		 */
		public Opcija(String id, String vrijednost) {
			try {
				Long.parseLong(id);
			} catch (NumberFormatException ne) {
				throw new IllegalArgumentException(
						"Id opcije se mora moći pretvoriti u broj!");
			}
			this.id = id;
			this.vrijednost = vrijednost;
		}
		
		/**
		 * Metoda stvara novi primjerak razreda.
		 * @param id id nove opcije.
		 * @param vrijednost vrijednost nove opcije.
		 */
		public Opcija(Long id, String vrijednost) {
			this(id.toString(), vrijednost);
		}
		
		/**
		 * Metoda za dohvat vrijednosti opcije.
		 * @return vrijednost opcije.
		 */
		public String getVrijednost() {
			return vrijednost;
		}
		/**
		 * Metoda za postavljanje vrijednosti opcije.
		 * @param vrijednost vrijednost opcije.
		 */
		public void setVrijednost(String vrijednost) {
			this.vrijednost = vrijednost;
		}

		/**
		 * Metoda za dovat id-a opcije.
		 * @return
		 */
		public String getId() {
			return id;
		}

	
		/**
		 * Metoda koja vraća hash vrijednost opcije.
		 * Ne postoje dvije opcije s istim id-om i vrijednoscu koje će imati različitu hash vrijednost.
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result
					+ ((vrijednost == null) ? 0 : vrijednost.hashCode());
			return result;
		}
		
		/**
		 * Metoda koja određuje da li su dvije opcije jednake.
		 * Dvije opcije su jednake ako imaju isti id i vrijednost.
		 */

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Opcija other = (Opcija) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (vrijednost == null) {
				if (other.vrijednost != null)
					return false;
			} else if (!vrijednost.equals(other.vrijednost))
				return false;
			return true;
		}


		@Override
		public String toString() {
			return "[ " + id + " , " + vrijednost + "]";
		}

		

		/**
		 * Metoda koja uspoređuje dvije opcije.
		 * Vraća negativnu vrijednost ako prva ima manji id, pozitivnu ako ima veći, nula inače.
		 */
		@Override
		public int compareTo(Opcija o) {
			return Long.compare(Long.parseLong(id), Long.parseLong(o.getId()));
		}

	}
}

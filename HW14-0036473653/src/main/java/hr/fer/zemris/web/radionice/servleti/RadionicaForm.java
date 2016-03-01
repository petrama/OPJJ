package hr.fer.zemris.web.radionice.servleti;

import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadioniceBaza.Opcija;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
/**
 * Razred koji predstavlja prikaz formulara jedne radionice.
 * Iz jedne radionice moguće je dobir formular radionice i obrunto ako su podatci u formularu validni.
 * @author Petra Marče
 *
 */
public class RadionicaForm {
	String id;
	String naziv;
	String datum;
	Set<String> oprema;
	String trajanje;
	Set<String> publika;
	String maksPolaznika;
	String email;
	String dopuna;

	Map<String, String> greske = new HashMap<String, String>();

	public RadionicaForm() {

	}

	/**
	 * Metoda za dohvat opisa pogreške spremljene pod zadanim imenom.
	 * Takva pogreška nastupila je prilikom uređivanja properitia čije je ime ključ u mapi pogrešaka.
	 * @param ime ime pod kojim tražimo pogrešku.
	 * @return traženi opis pogreške ili null ako pogreška nije nastupila.
	 */
	public String dohvatiPogresku(String ime) {
		return greske.get(ime);
	}

	/**
	 * Metoda koja određuje ima li pogrešaka.
	 * Pogrešaka nema kada je mapa pogrešaka prazna.
	 * @return vraća true ako ima pogrešaka, dalse inače.
	 */
	public boolean imaPogresaka() {
		return !greske.isEmpty();
	}

	/**
	 * Metoda koja određuje postoji li opis pogreške vezan za predani string.
	 * @param ime ime pod kojim se traži opis pogreške.
	 * @return opis opis pogreške.
	 */
	public boolean imaPogresku(String ime) {
		return greske.containsKey(ime);
	}

	/**
	 * Metoda koja popunjava formular tako što dohvaća parametre iz http requesta.
	 * @param req http request.
	 */
	public void popuniIzHttpRequesta(HttpServletRequest req){
		this.id=pripremi(req.getParameter("id"));
		this.naziv=pripremi(req.getParameter("naziv"));
		this.datum=pripremi(req.getParameter("datum"));
		this.oprema=pripremi(req.getParameterValues("oprema"));
		this.trajanje=pripremi(req.getParameter("trajanje"));
		this.publika=pripremi(req.getParameterValues("publika"));
		this.maksPolaznika=pripremi(req.getParameter("maksPolaznika"));
		this.email=pripremi(req.getParameter("email"));
		this.dopuna=pripremi(req.getParameter("dopuna"));
	}
	/**
	 * Metoda koja popunjava formular iz predane radionice.
	 * @param r radionica iz koje treba popuniti formular.
	 */
	public void popuniIzRadionice(Radionica r) {
		if (r.getId() == null) {
			this.id = "";

		} else {
			this.id = r.getId().toString();
		}
		naziv = r.getNaziv();
		datum = r.getDatum();
		oprema = getIDs(r.getOprema());
	
		if(r.getTrajanje()!=null){
		trajanje = r.getTrajanje().getId().toString();
		}
	
		publika = getIDs(r.getPublika());
		if(r.getMaksPolaznika()!=null){
		maksPolaznika = r.getMaksPolaznika().toString();
		}
		email = r.getEmail();
		dopuna = r.getDopuna();

	}
	
	/**
	 * Metoda koja popunjava predanu radionicu iz formulara.
	 * @param r radionica koju treba popuniti.
	 * @param publika lista raspoloživih publika. 
	 * @param trajanje lista svih trajanja.
	 * @param oprema lista sve opreme.
	 */
	 public void popuniURadionicu(Radionica r,List<Opcija> publika, List<Opcija> trajanje, List<Opcija>oprema){
		 
	 if (this.id.isEmpty()) {
	 r.setId(null);
	
	 } else {
	 r.setId(Long.valueOf(this.id));
	 }
	 r.setNaziv(naziv);
	 r.setDatum(datum);
	
	r.setOprema(izlistajOpcije(this.oprema,oprema));
	r.setTrajanje(odgovarajucaOpcija(this.trajanje, trajanje));
	r.setPublika(izlistajOpcije(this.publika,publika));
	
	r.setMaksPolaznika(Integer.parseInt(maksPolaznika));
	r.setEmail(email);
	r.setDopuna(dopuna);
	 
	 }
	 
	 
	/**
	 * Metoda koja vraća listu opcija iz predane liste ciji su identifikatori zadani u predanom skupu.
	 * @param set skup koji sadrži identifikatore opcija.
	 * @param opcije lista opcija.
	 * @return lista opcija koje odgovaraju predanom skupu identifikatora.
	 */
	private Set<Opcija> izlistajOpcije(Set<String> set,List<Opcija> opcije) {
		List<Opcija> nova=new ArrayList<>();
		for(String s:set){
			nova.add(odgovarajucaOpcija(s,opcije));
		}
		return new HashSet<Opcija>(nova);
		
	}
	/**
	 * Metoda koja vraća opciju iz predane liste sa zadanim id-om.
	 * @param id id tražene opcije. 
	 * @param kol lista u kojoj treba tražiti opciju.
	 * @return odgovarajuća opcija ili null ako takve nema.
	 */
	private Opcija odgovarajucaOpcija(String id,List<Opcija> kol){
		for(Opcija opc:kol){
			if(opc.getId().equals(id)){
				return opc;
			}
		}
		return null;
	}
	/**
	 * Metoda koja vraća skup id-eva opcija sadržanih u predanom skupu.
	 * @param lista skup opcija cije id.eve treba izdvojiti.
	 * @return skup id-eva opcija
	 */
	public static Set<String> getIDs(Set<Opcija> lista) {
		Set<String> nova = new HashSet<>();
		if(lista==null || lista.isEmpty()) return nova;
		for (Opcija opc : lista) {
			nova.add(opc.getId());
		}
		return nova;

	}

	/**
	 * Metoda koja provodi validaciju formulara.
	 * Ako bilo koji property nije validan metoda dodaje opis greske u mapu pogrešaka.
	 * Ključ u mapi je naziv propertia.
	 */
	public void validiraj() {
		greske.clear();
		if (!this.id.isEmpty()) {
			try {
				if (Long.parseLong(this.id) <= 0) {
					greske.put("id", "Identifikator mora biti pozitivan");
				}

			} catch (NumberFormatException ne) {
				greske.put("id",
						"Vrijednost identifikatora se ne moze pretvorit u broj");
			}
		}

		if (this.naziv.isEmpty()) {
			greske.put("naziv", "Ime je obvezno!");
		} else {
			if (naziv.length() > 40) {
				greske.put("naziv", "Maksimalni broj znakova naziva je 40!");

			}
		}

		if (this.datum.isEmpty()) {
			greske.put("datum", "Datum mora biti zadan!");
		} else {
			try {
				new SimpleDateFormat("yyyy-MM-dd").parse(datum);
			} catch (ParseException e) {
				greske.put("datum",
						"Datum mora biti formata godina-mjesec-dan!");
			}
		}

		if (!(this.trajanje.equals("2") || trajanje.equals("6") || trajanje
				.equals("7"))) {
			greske.put("trajanje", "Sifra trajanja mora biti 2, 6 ili 7!");
		}

		if (publika==null || publika.isEmpty()) {
			greske.put("publika",
					"Radionica mora imati barem jednu ciljanu publiku!");
		} else {
			for (String s : publika) {
				if (!(s.equals("1") || s.equals("2") || s.equals("3"))) {
					greske.put("publika", "Sifra publike mora biti 1 2 ili 3!");
				}
			}
		}

		if (maksPolaznika.isEmpty()) {
			greske.put("maksPolaznika", "Maks broj polaznika mora biti zadan!");
		} else {
			try {
				Integer broj = Integer.parseInt(maksPolaznika);
				if (broj < 10 || broj > 50) {
					greske.put("maksPolaznika",
							"Maks broj polaznika mora biti u rasponu od 10 do 50!");
				}

			} catch (NumberFormatException ne) {
				greske.put("maksPolaznika",
						"Vrijednost maks broja polaznika se ne moze pretvoriti u broj");
			}
		}

		if (email.isEmpty()) {
			greske.put("email", "Email mora biti zadan!");
		} else {
			try {

				InternetAddress emailAddr = new InternetAddress(email);
				emailAddr.validate();
			} catch (AddressException ex) {
				greske.put("email", "Neispravna email adresa!");
			}
		}

	}
	
	/**
	 * Metoda za dohvat id-a forumalara radionice.
	 * @return id formulara radionice.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Metoda za dohvat naziva formulara radionice.
	 * @return naziv formulara radionice.
	 */
	public String getNaziv() {
		return naziv;
	}


	/**
	 * Metoda za postavljanje naziva formulara radionice.
	 * @param naziv naziv formulara radionice.
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	/**
	 * Metoda za dohvat datuma održavanja formulara radionice.
	 * @return datum formulara radionice.
	 */
	public String getDatum() {
		return datum;
	}
	/**
	 * Metoda za postavljanje datuma formulara radionice.
	 * @param datum datum formulara radionice.
	 */
	public void setDatum(String datum) {
		this.datum = datum;
	}

	/**
	 * Metoda za dohvat skupa opreme potrebne za odrzavanje formulara radionice.
	 * @return skup opreme koji moze biti prazan.
	 */
	public Set<String> getOprema() {
		return oprema;
	}

	/**
	 * Metoda za dohvat trajanja formulara radionice.
	 * @return trajanje formulara radionice.
	 */
	public String getTrajanje() {
		return trajanje;
	}

	/**
	 * Metoda za postavljanje trajanja radionice.
	 * @param trajanje trajanje radionice.
	 */
	public void setTrajanje(String trajanje) {
		this.trajanje = trajanje;
	}
	/**
	 * Metoda za dohvat ciljane publke formulara radionice.
	 * @return ciljana publika formulara radionice.
	 */
	public Set<String> getPublika() {
		return publika;
	}

	/**
	 * Metoda za dohvat maksimalnog broja polaznika formulara radionice.
	 * @return maksimalan broj polaznika formulara radionice.
	 */
	public String getMaksPolaznika() {
		return maksPolaznika;
	}
	/**
	 * Metoda za postavljanje maksimalnog broja polaznika.
	 * @param maksPolaznika maks broj polaznika radionice.
	 */
	public void setMaksPolaznika(String maksPolaznika) {
		this.maksPolaznika = maksPolaznika;
	}

	/**
	 * Metoda za dohvat emaila organizatora formulara radionice.
	 * @return email organizatora formulara radionice.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Metoda za postavljanje email-a organizatora radionice.
	 * @param email email organizatora
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Metoda za dohvat dodatne napomene organizatora radionice.
	 * Može biti i prazan string.
	 * @return dodatna napomena organizatora radionice.
	 */
	public String getDopuna() {
		return dopuna;
	}

	/**
	 * Metoda za postavljanje dodatne napomene organizatora radionice.
	 * @param dopuna dodatna napomena.
	 */
	public void setDopuna(String dopuna) {
		this.dopuna = dopuna;
	}
	
	/**
	 * Metoda koja priprema string dohvaćen kao parametar iz http requesta.
	 * Ako je null vraća prazan string, inače ga trima.
	 * @param parameter parametar kojeg treba pripremiti
	 * @return string spreman za koristenje.
	 */
	private String pripremi(String parameter) {
		if (parameter == null)
			return "";
		return parameter.trim();
	}
	/**
	 * Metoda koja priprema polje strignova dohvaćeno kao parametar iz http requesta.
	 * Ako je null vraća prazan skup, inače vraća skup dohvaćenih elemenata.
	 * @param param dohvaćeno polje
	 * @return skup primljenih parametara
	 */
	private Set<String> pripremi (String[] param){
		if(param==null){
			return new HashSet<>();
		}else{
			return new HashSet<String>(Arrays.asList(param));
		}
	}
	
}

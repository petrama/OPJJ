package hr.fer.zemris.java.tecaj_14.model.forms;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;



public class BlogUserRegistrationForm {
	private String firstName;
	private String lastName;
	private String nick;
	private String email;
	private String password;
	
	Map<String,String> greske=new HashMap<String,String>();
	
	public BlogUserRegistrationForm() {
		
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
	
	public void popuniIzHttpRequesta(HttpServletRequest req){
		this.firstName=pripremi(req.getParameter("firstName"));
		this.lastName=pripremi(req.getParameter("lastName"));
		this.nick=pripremi(req.getParameter("nick"));
		System.out.println("DOBIO SAM NICK IZ HTTP: "+nick);
		this.email=pripremi(req.getParameter("email"));
		this.password=pripremi(req.getParameter("password"));
	}
	
	public void stvoriNovogUsera() {

		DAOProvider.getDAO().insertNewBlogUser(firstName, lastName, email,
				nick, calculateSHA(password));

	}
	
	public static String calculateSHA(String password)  {
	MessageDigest sha1;
	try {
		sha1 = MessageDigest.getInstance("SHA1");
	
	sha1.update(password.getBytes());
	byte[] hashBytes = sha1.digest();
	StringBuilder sb = new StringBuilder();
	for (byte hashByte : hashBytes) {
		sb.append(Integer.toHexString((hashByte & 0xff) + 0x100)
				.substring(1));
	}
	return sb.toString();
	} catch (NoSuchAlgorithmException e) {
		return null;
	}
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public void validiraj(){
		greske.clear();
		if(firstName.isEmpty()){
			greske.put("firstName", "Ime je obvezno");
		}else if(firstName.length()>40){
			greske.put("firstName", "Ime ne smije imati više od 40 znakova!");
		}
		if(lastName.isEmpty()){
			greske.put("lastName", "Prezime je obvezno!");
		
		}else if (lastName.length()>100){
			greske.put("lastName", "Prezime ne smije imati više od 100 znakova!");
		}
		if(nick.isEmpty()){
			greske.put("nick", "Nickname mora biti zadan!");
		}else{
			if(nick.length()>50){
				greske.put("nick", "Nickname ne smije imati više od 50 znakova!");
			}else{
		
		List<BlogUser> postojeci=DAOProvider.getDAO().getUserByNick(nick);
		if(!postojeci.isEmpty()){
			greske.put("nick","Korisnik sa predanim nickom već postoji!");
		}}}
		
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
	

}

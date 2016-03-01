package hr.fer.zemris.java.tecaj_14.model.forms;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;


public class BlogCommentForm {

	private String blogEntry;
	private String usersEMail;
	private String message;
	
	private String nick;
	
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	Map<String,String> greske=new HashMap<String,String>();
	
	public String getBlogEntry() {
		return blogEntry;
	}
	public void setBlogEntry(String blogEntry) {
		this.blogEntry = blogEntry;
	}
	public String getUsersEMail() {
		return usersEMail;
	}
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
		this.blogEntry=pripremi(req.getParameter("entry"));
		this.usersEMail=pripremi(req.getParameter("email"));
		this.message=pripremi(req.getParameter("message"));
		this.nick=pripremi(req.getParameter("nick"));
	}
	
	public void validiraj(){
		greske.clear();
		if(message.isEmpty()){
			greske.put("message", "Tekst u komentaru je obvezan!");
		}else{
			if(message.length()>4096){
				greske.put("message", "Tekst u komentaru ne smije imati više od 4096 znakova!");
				
			}
		}
		if (usersEMail.isEmpty()) {
			greske.put("email", "Email mora biti zadan!");
		} else {
			try {

				InternetAddress emailAddr = new InternetAddress(usersEMail);
				emailAddr.validate();
			} catch (AddressException ex) {
				greske.put("email", "Neispravna email adresa!");
			}
		}
	}
	
	public void spremi(){
		DAOProvider.getDAO().addNewComment(blogEntry,message, usersEMail);
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

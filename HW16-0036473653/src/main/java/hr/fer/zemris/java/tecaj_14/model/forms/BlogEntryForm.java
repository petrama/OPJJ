package hr.fer.zemris.java.tecaj_14.model.forms;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class BlogEntryForm {

	private String id;
//	private String createdAt;
//	private String lastModifiedAt;
	private String title;
	private String text;
	private String creatorNick;

	
	Map<String,String> greske=new HashMap<String,String>();
	
	public BlogEntryForm() {
		// TODO Auto-generated constructor stub
	}

//	public String getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(String createdAt) {
//		this.createdAt = createdAt;
//	}
//
//	public String getLastModifiedAt() {
//		return lastModifiedAt;
//	}
//
//	public void setLastModifiedAt(String lastModifiedAt) {
//		this.lastModifiedAt = lastModifiedAt;
//	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
	
	public BlogEntryForm popuniIzPosta(BlogEntry be){
		this.id=pripremi(be.getId().toString());
		this.title=pripremi(be.getTitle());
		this.text=pripremi(be.getText());
		this.creatorNick=pripremi(be.getCreator().getNick());
	return this;
		
	}
	
	public BlogEntryForm popuniIzHttpRequesta(HttpServletRequest req){
		this.id=pripremi(req.getParameter("id"));
		this.title=pripremi(req.getParameter("title"));
		this.text=pripremi(req.getParameter("text"));
		this.creatorNick=pripremi(req.getParameter("nick"));
		return this;
	}
	
	public void validiraj(){
		greske.clear();
		if(title.isEmpty()){
			greske.put("title", "Naslov je obvezan!");
		}else if(title.length()>20){
			greske.put("title", "Naslov ne smije imati više od 20 znakova!");
		}
		if(text.isEmpty()){
			greske.put("text", "Tekst komentara je obvezan!");
		
		}else if (text.length()>100){
			greske.put("text", "Tekst ne smije biti dulji od 4096 znakova!");
		}
		
		}
			
		
		
		
	
	
	public void spremi(){
		if(id.isEmpty()){
			DAOProvider.getDAO().addNewEntry(title, text,creatorNick);
		}else{
			DAOProvider.getDAO().updateEntry(creatorNick, Long.parseLong(id), title, text);
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, String> getGreske() {
		return greske;
	}

	public void setGreske(Map<String, String> greske) {
		this.greske = greske;
	}

	public String getCreatorNick() {
		return creatorNick;
	}

	public void setCreatorNick(String creatorNick) {
		this.creatorNick = creatorNick;
	}
	
	
	
}

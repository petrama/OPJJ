package hr.fer.zemris.java.tecaj_13.model;

public class Glasanje {

	private Long id;
	private String title;
	private String message;
	
	public Glasanje(Long id, String title, String message) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}
	
	
	
}

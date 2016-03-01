package hr.fer.zemris.java.tecaj_13.model;


/**
 * Razred koji predstavlja jedan rezultat glasovanja.
 * Sastoji se od instance razreda  Trio i broja glasova.
 * @author Petra Marče
 *
 */
public class Result implements Comparable<Result> {
	private Long id;
	private String optionTitle;
	private String optionLink;
	private long brojGlasova;
	private long pollId;

	
	
	public Result(Long id, String bend, String pjesma, long brojGlasova,long pollID) {
		super();
		this.id = id;
		this.optionTitle = bend;
		this.optionLink = pjesma;
		this.brojGlasova = brojGlasova;
		this.pollId=pollID;
	}

	

	public long getPollId() {
		return pollId;
	}



	public long getId() {
		return id;
	}


	


	public String getOptionTitle() {
		return optionTitle;
	}



	public String getOptionLink() {
		return optionLink;
	}



	/**
	 * Metoda za dohvat broja glasova.
	 * @return broj glasova.
	 */

	public Long getGlasovi() {
		return brojGlasova;
	}

	

	@Override
	public int compareTo(Result o) {
		return o.getGlasovi().compareTo(this.getGlasovi());
	}

}
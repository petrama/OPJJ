package hr.fer.zemris.java.tecaj.hw4.db;
/**
 * Razred koji presstavlja zapis o jednom studentu.
 * @author Petra Marče
 *
 */

public class StudentRecord {
String jmbag;
 String lastName;
 String firstName;
 int finalGrade;
 
 /**
  * Konstruktor.
  * @param jmbag jmbag studenta
  * @param lastName -prezime
  * @param firstName -ime
  * @param finalGrade -ocjena
  */
 
public StudentRecord(String jmbag, String lastName, String firstName,
		int finalGrade) {
	super();
	this.jmbag = new String(jmbag);
	this.lastName =new String( lastName);
	this.firstName = new String (firstName);
	this.finalGrade = new Integer(finalGrade);
}


@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	StudentRecord other = (StudentRecord) obj;
	if (jmbag == null) {
		if (other.jmbag != null)
			return false;
	} else if (!jmbag.equals(other.jmbag))
		return false;
	return true;
}




 
 
}

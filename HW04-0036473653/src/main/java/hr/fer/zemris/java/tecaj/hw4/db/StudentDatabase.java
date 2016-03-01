package hr.fer.zemris.java.tecaj.hw4.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Razred koji predstavlja bazu podataka Student.
 * @author Petra Marče
 *
 */
public class StudentDatabase {
	List<StudentRecord> students;
	Map<String, StudentRecord> studentsAsMap;
	
	/**
	 * Konstruktor.
	 * Iz Stringova stvara zapise, i dodaje ih u listu i mapu.
	 * @param lines linije datoteke iz kojih se stvaraju zapisi.
	 */

	public StudentDatabase(List<String> lines) {
		students = new ArrayList<>();
		studentsAsMap=new HashMap<String, StudentRecord>();
		for (String line : lines) {
			String[] arguments = line.split("\t");
		
			if (arguments.length != 4) {
				throw new IllegalArgumentException("Illegal line!");
			}
			int  grade = 0;
			String surname = "", name = "",jmbag="";
			try {
				jmbag = arguments[0];
				surname = arguments[1];
				name = arguments[2];
				grade = Integer.parseInt(arguments[3]);

			} catch (RuntimeException e) {
				throw new IllegalArgumentException("Wrong  arguments!");
			}
			
			StudentRecord record=new StudentRecord(jmbag, surname, name, grade);
			students.add(record);
			
			studentsAsMap.put(jmbag, record);

		}
	}
		/**
		 * Metoda koja vraća zapis o studentu zadanog jmbaga.
		 * @param jmbag koji se traži.
		 * @return vraća zapis za studenta s tim jmbagom ili null ako zapisa nema.
		 */
		public StudentRecord forJMBAG(String jmbag){
			if(studentsAsMap.containsKey(jmbag)){
				return studentsAsMap.get(jmbag);
			}
			return null;
			
		}
		/**
		 * Metoda koja vraća novu listu studenata koji su prošli filter.
		 * @param filter kriterij po kojem filtriramo.
		 * @return lista studenata koji zadovoljavaju uvjete
		 */
		
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> nova = new ArrayList<>();
		for (StudentRecord zapis : this.students) {
			if (filter.accepts(zapis)) {
				nova.add(zapis);
			}
		}
		return nova;

	}
	
}

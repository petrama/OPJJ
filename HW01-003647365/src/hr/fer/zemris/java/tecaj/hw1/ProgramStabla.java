package hr.fer.zemris.java.tecaj.hw1;
/**
 * 
 * @author Petra Marče
 * 
 *
 */

class ProgramStabla {
static class CvorStabla {
CvorStabla lijevi;
CvorStabla desni;
String podatak;
}

/**
 * Metoda koja se poziva prilikom pokretanja programa.
 * @param args Argumenti iz komandne linije.
 */

public static void main(String[] args) {
CvorStabla cvor = null;

	cvor = ubaci(cvor, "Jasna");
	cvor = ubaci(cvor, "Ana");
	cvor = ubaci(cvor, "Ivana");
	cvor = ubaci(cvor, "Anamarija");
	cvor = ubaci(cvor, "Vesna");
	cvor = ubaci(cvor, "Kristina");

	System.out.println("Ispisujem stablo inorder:");
		ispisiStablo(cvor);
		
		cvor = okreniPoredakStabla(cvor);
		
		System.out.println("Ispisujem okrenuto stablo inorder:");
		ispisiStablo(cvor);
		
		int vel = velicinaStabla(cvor);
		System.out.println("Stablo sadrzi elemenata: "+vel);

		boolean pronaden = sadrziPodatak(cvor, "Ivana");
		System.out.println("Trazeni podatak je pronaden: "+pronaden);
}

/**
 * Rekurzivna metoda koja određuje da li se zadani element pojavljuje u zadanom stablu.
 * @param korijen Pokazivač na korijen stabla u kojem tražimo
 * @param podatak Podatak koji se traži
 * @return ako podatak postoji u stablu vraća true, false inače
 */
static boolean sadrziPodatak(CvorStabla korijen, String podatak) {
 if(korijen==null) return false;
 if (korijen.podatak==podatak) return true;
 if (korijen.podatak.compareTo(podatak)>0) return sadrziPodatak(korijen.lijevi, podatak);
 return sadrziPodatak(korijen.desni,podatak);


}

/**
 * Rekurzivna metoda koja broji čvorove zadanog stabla. 
 * @param cvor Pokazivač na korijen zadanog stabla.
 * @return Vraća broj čvorova u stablu.
 */

static int velicinaStabla(CvorStabla cvor) {
	
	if(cvor==null) return 0;
	
	return 1+velicinaStabla(cvor.lijevi)+velicinaStabla(cvor.desni);//broji samog sebe i broj cvorova u podstablima
}

/**
 * Rekurzivna metoda koja dodaje novi element u zadano stablo prema načelu BST:
 * Ako je novi element manji od elementa u trenutnom nepraznom čvoru, nastavi posao lijevo,
 * inače nastavi posao desno, dok ne nađeš prazno mjesto za novi element.
 * @param korijen Pokazivač na korijen stabla u koje treba ubaciti novi element.
 * @param podatak Element kojeg treba ubaciti
 * @return Pokazivač na izmjenjeno stablo
 */
static CvorStabla ubaci(CvorStabla korijen, String podatak) {
	
	if (korijen==null) { 
		CvorStabla novi = new CvorStabla();
		novi.lijevi=novi.desni=null;
		novi.podatak=podatak;
		korijen=novi;

		
	}
	
	else if (korijen.podatak.compareTo(podatak)>0) korijen.lijevi= ubaci(korijen.lijevi,podatak);
		else korijen.desni= ubaci(korijen.desni,podatak);

	return korijen;
}

	
	
	
/**
 * Rekurzivna etoda koja ispisuje zadano stablo u inoreder poretku:
 * lijevo-sredina-desno
 * @param cvor Pokazivač na korijen stabla koje treba ispisati.
 */
		
		

static void ispisiStablo(CvorStabla cvor) {
if (cvor!=null){
	ispisiStablo(cvor.lijevi);
	System.out.println(cvor.podatak);
	ispisiStablo(cvor.desni);
	
	}
}

/**
 * Rekurzivna metoda koja mjenja lijevo i desno djete svakog čvora.
 * 
 * @param korijen Pokazivač na korijen stabla u kojem treba zamjeniti poredak.
 * @return vraća Pokazivač na korijen izmjenjenog stabla.
 */
static CvorStabla okreniPoredakStabla(CvorStabla korijen) {
	if (korijen!=null){
		CvorStabla t=korijen.desni;
		korijen.desni=korijen.lijevi;
		korijen.lijevi=t;
		okreniPoredakStabla(korijen.lijevi);
		okreniPoredakStabla(korijen.desni);
		
		
		
		
	}
	return korijen;
	}
}

	

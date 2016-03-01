package hr.fer.zemris.java.tecaj.hw1;
/**
 * 
 * @author Petra Marče
 * @version 2.0
 *
 */

class ProgramListe {
static class CvorListe {
CvorListe sljedeci;
String podatak;
}

/**
 * Metoda koja se poziva prilikom pokretanja programa. Argumenti su objašnjeni u nastavku;
 * 
 * @param args Argumenti iz komandne linije.
 */

public static void main(String[] args) {
	CvorListe cvor = null;
	cvor = ubaci(cvor, "Jasna");
	cvor = ubaci(cvor, "Ana");
	cvor = ubaci(cvor, "Ivana");
	System.out.println("Ispisujem listu uz originalni poredak:");
	ispisiListu(cvor);
	cvor = sortirajListu(cvor);
	System.out.println("Ispisujem listu nakon sortiranja:");
	ispisiListu(cvor);
	int vel = velicinaListe(cvor);
	System.out.println("Lista sadrzi elemenata: "+vel);
}

/**
 * Metoda koja prebrojava elemente liste na koju pokazuje ulazni argument koji je objasnjen u nastavku
 * @param cvor ulazni argument koji pokazuje na listu nepoznate veličine
 * @return vraća veličinu liste
 */
static int velicinaListe(CvorListe cvor) {
	int i=0;
for(CvorListe pom=cvor;pom!=null;pom=pom.sljedeci) i++;
return i;


}

/**
 * Metoda koja ubacuje novi element u listu. Argumenti su objašnjeni u nastavku.
 * @param prvi pokazivač na početak liste u koju treba ubaciti novi element
 * @param podatak   nova vrijednost koju treba ubaciti u listu
 * @return vraća pokazivač na početak liste
 */
static CvorListe ubaci(CvorListe prvi, String podatak) {

CvorListe novi=new CvorListe(),pom;
novi.podatak=podatak;
novi.sljedeci=null;
if (prvi==null) prvi=novi;
else{
	for( pom=prvi; pom.sljedeci!=null ;pom=pom.sljedeci);
	pom.sljedeci=novi;

}
return prvi;
}

/**
 * Metoda koja ispisuje elemente liste od početka do kraja. Argument objašnjen u nastavku
 * @param cvor pokazivač na početak liste koju treba ispisati
 */

static void ispisiListu(CvorListe cvor) {
	
	for(CvorListe pom=cvor; pom!=null ;pom=pom.sljedeci)
		System.out.println(pom.podatak);
}

/**
 * Metoda koja sortira elemente liste upotrebom pomoćnog polja pomoću Shell sorta.
 * Argument je objasnjen u nastavku.
 * @param cvor pokazivač na prvi element liste koju treba sortirati
 * @return vraća pokazivač na početak sortirane liste
 */
static CvorListe sortirajListu(CvorListe cvor) {
	int n=velicinaListe(cvor);	
	String []polje=new String[n];//*stvara se pomoćno polje
	String pom;
	CvorListe pomCvor=cvor;
	
	for(int i=0;i<n;i++){//*iz liste slijedno čita elemente, upisuje ih u polje
		polje[i]=pomCvor.podatak;
		pomCvor=pomCvor.sljedeci;
		
	}
		
	for(int korak=n/2;korak>0;korak/=2){//*postupno smanjuje korak sorta
			for(int i=korak,j=0;i<n;i++){
				
				pom=polje[i];
				for(j=i;j>=korak && (pom.compareTo(polje[j-korak])<0);j-=korak){
					polje[j]=polje[j-korak];
				}
			polje[j]=pom;
			
			}
	}
	pomCvor=cvor;
	for (int i=0;i<n;i++) {    //*sortirani elementi se upisuju u listu
		pomCvor.podatak=polje[i];
		pomCvor=pomCvor.sljedeci;
	}
return cvor;}
}

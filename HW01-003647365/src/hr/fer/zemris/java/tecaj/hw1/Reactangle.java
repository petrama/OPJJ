package hr.fer.zemris.java.tecaj.hw1;
/**
 * @author Petra Marče
 * Program racuna povrsinu i opseg pravokutnika zadanih dimenzija.
 */

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Reactangle {
	/**
	 * Metoda koja se poziva prilikom pokretanja programa. Argumenti su objasnjeni u nastavku.
	 * @param args Argumenti iz komandne linije. Program očekuje dva pozitivna broja.
	 * Ako ih korisnik ne unese, program će to od njega zatražiti.
	 */

	public static void main(String[] args) throws IOException {
		double width=0,height=0;
		if (args.length==0){
			
		 width=InputNumber("Width");
		 height =InputNumber("Height");
			
			
			}
		else if (args.length!=2) {
			System.err.println("Invalid number of arguments was provided.");
			return;
			
		}else{
			 width=Double.parseDouble(args[0]);
			 height=Double.parseDouble(args[1]);
			 
					 if(width<0 || height<0){
						 System.err.println("Invalid arguments");
						 return;
					 };
			
		}
		
	
		double area=width*height;
		double perimeter=2*(width+height);
		System.out.println("You have specified a rectangle with width "+width+" and height "+height+" Its area is "+area+" and its perimeter is "+perimeter);
				}
	 
	/**
	 * Metoda koja od korisnika trazi unos jednog broja sve dok on ne unese neki pozitivan broj
	 * @param NameVar pomoću ove varijable korisnik točno zna što unosi tj. u kojem će se smislu ono što unese tumačiti
	 * @return vraća uspješno uneseni broj
	 */
	
	
	private static double InputNumber(String NameVar)throws IOException {
		int i=1;
		double number=0;
		
	
		BufferedReader reader=new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in))
				);
		while(i==1){
			System.out.println("Please provide:"+NameVar);
			String row=reader.readLine();
			if (row==null) System.err.println("Standard input is closed and no reading is possible!");
			row.trim();
			if (row.isEmpty()==true){
				System.out.println("Nothing is given!");
			} 
			else {
				 number = Double.parseDouble(row);
				
				if (number<0) System.out.println(NameVar+" is negative");
			    else i=0;
			   
				
			    
					
				}
		
			}
		 
		return number;
				
	}
	

}




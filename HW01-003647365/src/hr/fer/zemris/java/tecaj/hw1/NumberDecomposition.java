package hr.fer.zemris.java.tecaj.hw1;
/**
 * 
 * @author Petra Marče
 * Program rastavlja zadani broj na proste faktore.
 *
 */

public class NumberDecomposition {
	/**
	 * Method that is called when the program starts. Arguments are explained below.
	 * @param args Command line arguments. One natural number grater than one required.
	 */

	public static void main(String[] args) {
		if (args.length!=1){ 
			
			System.err.println("Invalid number of arguments, please provide one natural number grater than one!");
			return;
		}
		double n=Double.parseDouble(args[0]);
		if (n<1 || n!=Math.floor(n)){
			System.err.println("Invalid argument, number is not natural, please provide one natural number grater than 1!");
			return;
		}
		
		System.out.println("You requested decomposition of number "+(int)n+" onto prime factors. Here they are: ");
		
		for(int i=2,counter=1;i<=n;i++){
			while(n%i==0 && n>1){
				System.out.println(counter+". "+i);
				counter++;
				n/=i;
			}
		}

	}

}

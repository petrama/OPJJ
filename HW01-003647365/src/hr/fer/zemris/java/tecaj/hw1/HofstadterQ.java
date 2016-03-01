package hr.fer.zemris.java.tecaj.hw1;
/**
 * @author Petra Marče 
 * @version 1.0
 * 
 * Program calculates the given number of Hofstader's Q-sequence.
 */

public class HofstadterQ {
/**
 * 
 * Method that is called when the program starts. Arguments are explained below.
 * @param args Command line arguments. One natural number required.
 */

	public static  void main(String args[]){
	if (args.length!=1){ 
		
		System.err.println("Invalid number of arguments, please provide one natural number!");
		return;
	}

	double n=Double.parseDouble(args[0]); 
	if (n<1 || n!=Math.floor(n)){
		System.err.println("Invalid argument, please provide one natural number!");
		return;
	}
	
	
	long [] polje =new long[(int)n];
	polje[0]=polje[1]=1;
	for(int i=2;i<n;i++){
		polje[i]=polje[i-(int)polje[i-1]]+polje[i-(int)polje[i-2]];
	}
	System.out.println("You requested calculation of "+(int)n+". number of Hofstader's Q-sequence. The requested number is "+polje[(int)n-1]);

	
}

}

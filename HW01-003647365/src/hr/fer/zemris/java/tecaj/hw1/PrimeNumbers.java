package hr.fer.zemris.java.tecaj.hw1;
/**@author Petra Marče
 * 
 * Program prints out the given number of first primes.
 * Prime is natural number that has no divisors than one and itself.
 * 
 */
 


public class PrimeNumbers {

/**
 *Method that is called when the program starts. Arguments are explained below.
 * @param args Command line arguments. One natural number required.
 */
	
	public static void main(String[] args) {
		
		if (args.length!=1){ 
		
			System.err.println("Invalid number of arguments, please provide one natural number!");
			return;
		}
		double n=Double.parseDouble(args[0]);
		if (n<1 || n!=Math.floor(n)){
			System.err.println("Invalid argument, please provide one natural number!");
			return;
		}

			int found=0,IsPrime=1,TempNum=1;
		while(found!=n){
			IsPrime=1;
			TempNum++;
			for(int i=2;i<=Math.sqrt(TempNum);i++){//checking after the number's square root is not necessary
				if(TempNum % i==0) {
					IsPrime=0;
					break;
				}
			}
			if(IsPrime==1){
				found++;
				if (found==1) System.out.println("You requested calculation of "+(int)n+" prime numbers. Here they are:");
				System.out.println(found+". "+TempNum);
			
			}
		}
			
		}
		
	}






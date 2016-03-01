package hr.fer.zemris.java.tecaj.hw1;


import java.text.DecimalFormat;

/**@author Petra Marče
 * @version 1.0
 * 
 */



public class Roots {
	
	
	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * Argumenti su objašnjeni u nastavku
	 * @param args argumenti iz komandne linije.
	 */
	    
	
	    public static void main(String[] args) {
	    
		if(args.length!=3) {
			System.err.println("Invalid number of arguments!");
			return;
		}
		double n=Double.parseDouble(args[2]);
		if (n!=Math.floor(n)){
			System.err.println("Required root must be natural number greater or equal than 1");
			return;
		}

		String format1="+0;-0",format2="0;-0";
		double RealPart=Double.parseDouble(args[0]);
		double ImagPart=Double.parseDouble(args[1]);
		double Angle=Math.atan(ImagPart/RealPart);
		double AbsValue=Math.sqrt(ImagPart*ImagPart+RealPart*RealPart);
		double AbsRoot=Math.pow(AbsValue, 1/n);
		double phi=0;
		double RealRoot=0;
		double ImagRoot=0;
	
		System.out.println("You requested calculation of "+(int)n+".roots. Solutions are:");
		
		for(int i=0;i<n;i++){
			
		phi=(Angle+2*i*Math.PI)/n;
		
		RealRoot=AbsRoot*Math.cos(phi);
		
		ImagRoot=AbsRoot*Math.sin(phi);
		
		if (Math.abs(RealRoot-Math.round(RealRoot))>Math.pow(10, -3)|| Math.abs(ImagRoot-Math.round(ImagRoot))>Math.pow(10, -3)){
			format1="+0.000;-0.000";
			format2="0.000;-0.000";
			}
		
		
			DecimalFormat formatterImag=new DecimalFormat(format1);  //*format za imaginarni dio
			DecimalFormat formatterReal= new DecimalFormat(format2); //*format za realni dio
		System.out.println((i+1)+")"+formatterReal.format(RealRoot)+formatterImag.format(ImagRoot)+"i");
		}
		
		
		
	};
}

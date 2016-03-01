package hr.fer.zemris.bool.qmc;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.fimpl.IndexedBF;
import hr.fer.zemris.bool.fimpl.MaskBasedBF;

import java.util.ArrayList;
import java.util.Arrays;




import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class QMCMinimizerTest {

	
		
		@Test
	public void getMintermsTest1Dz() { //primjer jedan iz zadaće
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		BooleanFunction bf = new IndexedBF("f1", Arrays.asList(varA, varB,
				varC, varD), true, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
				11, 12, 13, 14), new ArrayList<Integer>());

		boolean zelimoDobitiProdukte = false;
		MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
		System.out.println("Minimalnih oblika ima: " + fje.length);
		for (MaskBasedBF f : fje) {
			System.out.println("Mogući minimalni oblik:");
			for (Mask m : f.getMasks()) {
				System.out.println(" " + m);
			}
		}

		Assert.assertTrue("Function should have six minimal forms!",
				fje.length == 6);
		for (int i = 0; i < fje.length; i++) {
			Assert.assertTrue("Wrong " + (i + 1) + ". minimal form!", fje[i]
					.getMinterms().equals(bf.getMinterms()));
		}
	}
		
		@Test
		public void getMintermsTest2Dz(){
			BooleanVariable varA = new BooleanVariable("A");
			BooleanVariable varB = new BooleanVariable("B");
			BooleanVariable varC = new BooleanVariable("C");
			BooleanVariable varD = new BooleanVariable("D");
			BooleanFunction bf = new IndexedBF("f1",
					Arrays.asList(varA, varB, varC,varD), true,
					Arrays.asList(0, 1, 2, 3, 4, 6, 7, 8, 9, 11, 12, 13, 14, 15),new ArrayList<Integer>());
		
		
			boolean zelimoDobitiProdukte = false;
			MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
			System.out.println("Minimalnih oblika ima: " + fje.length);
			for(MaskBasedBF f : fje) {
			System.out.println("Mogući minimalni oblik:");
			for(Mask m : f.getMasks()) {
			System.out.println(" " + m);
			}
			}
			
			Assert.assertTrue("Function should have six minimal forms!",fje.length==6);
			 for(int i=0;i<fje.length;i++){
					Assert.assertTrue("Wrong "+(i+1)+". minimal form!",fje[i].getMinterms().equals(bf.getMinterms()));
				}
}
		
		@Test
		public void getMintermsTest3Dz(){
			BooleanVariable varA = new BooleanVariable("A");
			BooleanVariable varB = new BooleanVariable("B");
			BooleanVariable varC = new BooleanVariable("C");
			BooleanVariable varD = new BooleanVariable("D");
			BooleanFunction bf = new IndexedBF("f1",
					Arrays.asList(varA, varB, varC,varD), true,
					Arrays.asList(0, 1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14),new ArrayList<Integer>());
		
		
			boolean zelimoDobitiProdukte = false;
			MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
			System.out.println("Minimalnih oblika ima: " + fje.length);
			for(MaskBasedBF f : fje) {
			System.out.println("Mogući minimalni oblik:");
			for(Mask m : f.getMasks()) {
			System.out.println(" " + m);
			}
			}
			
			Assert.assertTrue("Function should have six minimal forms!",fje.length==6);
			 for(int i=0;i<fje.length;i++){
					Assert.assertTrue("Wrong "+(i+1)+". minimal form!",fje[i].getMinterms().equals(bf.getMinterms()));
				}
}
		
		@Test
		public void getMintermsTest4Dz(){
			BooleanVariable varA = new BooleanVariable("A");
			BooleanVariable varB = new BooleanVariable("B");
			BooleanVariable varC = new BooleanVariable("C");
			BooleanVariable varD = new BooleanVariable("D");
			BooleanFunction bf = new IndexedBF("f1",
					Arrays.asList(varA, varB, varC,varD), true,
					Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15),new ArrayList<Integer>());
		
		
			boolean zelimoDobitiProdukte = false;
			MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
			System.out.println("Minimalnih oblika ima: " + fje.length);
			for(MaskBasedBF f : fje) {
			System.out.println("Mogući minimalni oblik:");
			for(Mask m : f.getMasks()) {
			System.out.println(" " + m);
			}
			}
			
			Assert.assertTrue("Function should have six minimal forms!",fje.length==6);
			 for(int i=0;i<fje.length;i++){
					Assert.assertTrue("Wrong "+(i+1)+". minimal form!",fje[i].getMinterms().equals(bf.getMinterms()));
				}
}
		@Test
		public void getMintermsTest5Dz(){
			BooleanVariable varA = new BooleanVariable("A");
			BooleanVariable varB = new BooleanVariable("B");
			BooleanVariable varC = new BooleanVariable("C");
			BooleanVariable varD = new BooleanVariable("D");
			BooleanFunction bf = new IndexedBF("f1",
					Arrays.asList(varA, varB, varC,varD), true,
					Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),new ArrayList<Integer>());
		
		
			boolean zelimoDobitiProdukte = false;
			MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
			System.out.println("Minimalnih oblika ima: " + fje.length);
			for(MaskBasedBF f : fje) {
			System.out.println("Mogući minimalni oblik:");
			for(Mask m : f.getMasks()) {
			System.out.println(" " + m);
			}
			}
			
			Assert.assertTrue("Function should have six minimal forms!",fje.length==6);
			 for(int i=0;i<fje.length;i++){
					Assert.assertTrue("Wrong "+(i+1)+". minimal form!",fje[i].getMinterms().equals(bf.getMinterms()));
				}
}
		
		
		
		@Test
		public void getMintermsTest6Dz(){
			BooleanVariable varA = new BooleanVariable("A");
			BooleanVariable varB = new BooleanVariable("B");
			BooleanVariable varC = new BooleanVariable("C");
			BooleanVariable varD = new BooleanVariable("D");
			BooleanFunction bf = new IndexedBF("f1",
					Arrays.asList(varA, varB, varC,varD), true,
					Arrays.asList(1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),new ArrayList<Integer>());
		
		
			boolean zelimoDobitiProdukte = false;
			MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
			System.out.println("Minimalnih oblika ima: " + fje.length);
			for(MaskBasedBF f : fje) {
			System.out.println("Mogući minimalni oblik:");
			for(Mask m : f.getMasks()) {
			System.out.println(" " + m);
			}
			}
			
			Assert.assertTrue("Function should have six minimal forms!",fje.length==6);
			 for(int i=0;i<fje.length;i++){
					Assert.assertTrue("Wrong "+(i+1)+". minimal form!",fje[i].getMinterms().equals(bf.getMinterms()));
				}
}
		
		@Test
		public void getMintermsTest7(){
			BooleanVariable varA = new BooleanVariable("A");
			BooleanVariable varB = new BooleanVariable("B");
			BooleanVariable varC = new BooleanVariable("C");
			BooleanVariable varD = new BooleanVariable("D");
			BooleanFunction bf = new IndexedBF("f1",
					Arrays.asList(varA, varB, varC,varD), true,
					Arrays.asList(0,1,4,5,11,15),new ArrayList<Integer>());
		
		
			boolean zelimoDobitiProdukte = false;
			MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
			System.out.println("Minimalnih oblika ima: " + fje.length);
			for(MaskBasedBF f : fje) {
			System.out.println("Mogući minimalni oblik:");
			for(Mask m : f.getMasks()) {
			System.out.println(" " + m);
			}
			}
			
			Assert.assertTrue("Function should have one minimal form!",fje.length==1);
			Assert.assertTrue("Expected two masks!", fje[0].getMasks().size()==2);
			List<Mask> expected=new ArrayList<>();
			expected.add(Mask.parse("0x0x"));
			expected.add(Mask.parse("1x11"));
		
			expected.removeAll(fje[0].getMasks());
			if(expected.isEmpty()==false){
				Assert.fail("");
			}
			
			
		}	
		
		
		
	
		@Test
		public void getMintermsTest8Dz(){
			BooleanVariable varA = new BooleanVariable("A");
			BooleanVariable varB = new BooleanVariable("B");
			BooleanVariable varC = new BooleanVariable("C");
			BooleanVariable varD = new BooleanVariable("D");
			BooleanFunction bf = new IndexedBF("f1",
					Arrays.asList(varA, varB, varC,varD), true,
					Arrays.asList(0,1,4,5,9,11,15),new ArrayList<Integer>());
			
			
		
			boolean zelimoDobitiProdukte = false;
			MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
			System.out.println("PMinimalnih oblika ima: " + fje.length);
			for(MaskBasedBF f : fje) {
			System.out.println("PMogući minimalni oblik:");
			for(Mask m : f.getMasks()) {
			System.out.println(" " + m);
			}
			}
			
			Assert.assertTrue("Function should have two minimal forms!",fje.length==2);
			Assert.assertTrue("Expected three masks in first minimal form!", fje[0].getMasks().size()==3);
			 for(int i=0;i<fje.length;i++){
					Assert.assertTrue("Wrong "+(i+1)+". minimal form!",fje[i].getMinterms().equals(bf.getMinterms()));
				}
			
			
			
		}	

		
			
			@Test
			public void getMintermsTest9Dz(){
				BooleanVariable varA = new BooleanVariable("A");
				BooleanVariable varB = new BooleanVariable("B");
				BooleanVariable varC = new BooleanVariable("C");
				BooleanVariable varD = new BooleanVariable("D");
				BooleanFunction bf = new IndexedBF("f1",
						Arrays.asList(varA, varB, varC,varD), true,
						Arrays.asList(0,1,4,5,9,15),Arrays.asList(11));
			
			
				boolean zelimoDobitiProdukte = false;
				MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
				System.out.println("Minimalnih oblika ima: " + fje.length);
				for(MaskBasedBF f : fje) {
				System.out.println("Mogući minimalni oblik:");
				for(Mask m : f.getMasks()) {
				System.out.println(" " + m);
				}
				}
				
				
			    Assert.assertTrue("Function should have two minimal forms!",fje.length==2);
			    for(int i=0;i<fje.length;i++){

					Set<Integer> check=new HashSet<>(bf.getMinterms());
					check.add(11);
					Set<Integer> function=new HashSet<>(fje[i].getMinterms());
					Assert.assertTrue("Wrong "+(i+1)+". minimal form!",check.equals(function));
				}
				
	}
			@Test
			public void getMintermsTest10Dz(){
				BooleanVariable varA = new BooleanVariable("A");
				BooleanVariable varB = new BooleanVariable("B");
				BooleanVariable varC = new BooleanVariable("C");
				BooleanVariable varD = new BooleanVariable("D");
				BooleanFunction bf = new IndexedBF("f1",
						Arrays.asList(varA, varB, varC,varD), true,
						Arrays.asList(4,5,6,7,8,9,10,11,13,14),new ArrayList<Integer>());
				
				
			
				boolean zelimoDobitiProdukte = false;
				MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
				System.out.println("PMinimalnih oblika ima: " + fje.length);
				for(MaskBasedBF f : fje) {
				System.out.println("PMogući minimalni oblik:");
				for(Mask m : f.getMasks()) {
				System.out.println(" " + m);
				}
				}
				
				Assert.assertTrue("Function should have four minimal forms!",fje.length==4);
				Assert.assertTrue("Expected four masks in first minimal form!", fje[0].getMasks().size()==4);
				 for(int i=0;i<fje.length;i++){
						Assert.assertTrue("Wrong "+(i+1)+". minimal form!",fje[i].getMinterms().equals(bf.getMinterms()));
					}
				
				
				
			}	


			@Test
			public void getMintermsTest11(){
				BooleanVariable varA = new BooleanVariable("A");
				BooleanVariable varB = new BooleanVariable("B");
				BooleanVariable varC = new BooleanVariable("C");
				BooleanVariable varD = new BooleanVariable("D");
				BooleanFunction bf = new IndexedBF("f1",
						Arrays.asList(varA, varB, varC,varD), true,
						Arrays.asList(4,5,6,7,8,9,10,11,13,14),new ArrayList<Integer>());
				
				
			
				boolean zelimoDobitiProdukte = false;
				MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
				System.out.println("Minimalnih oblika ima: " + fje.length);
				for(MaskBasedBF f : fje) {
				System.out.println("Mogući minimalni oblik:");
				for(Mask m : f.getMasks()) {
				System.out.println(" " + m);
				}
				}
				
				Assert.assertTrue("Function should have four minimal forms!",fje.length==4);
				Assert.assertTrue("Expected four masks in first minimal form!", fje[0].getMasks().size()==4);
				 for(int i=0;i<fje.length;i++){
						Assert.assertTrue("Wrong "+(i+1)+". minimal form!",fje[i].getMinterms().equals(bf.getMinterms()));
					}
				
				
				
			}
			
			@Test
			public void getMintermsTest12Dz(){
				BooleanVariable varA = new BooleanVariable("A");
				BooleanVariable varB = new BooleanVariable("B");
				BooleanVariable varC = new BooleanVariable("C");
				BooleanVariable varD = new BooleanVariable("D");
				BooleanFunction bf = new IndexedBF("f1",
						Arrays.asList(varA, varB, varC,varD), true,
						Arrays.asList(4,5,7,9,10,11,13,14),Arrays.asList(6,8));
			
			
				boolean zelimoDobitiProdukte = false;
				MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
				System.out.println("Minimalnih oblika ima: " + fje.length);
				for(MaskBasedBF f : fje) {
				System.out.println("Mogući minimalni oblik:");
				for(Mask m : f.getMasks()) {
				System.out.println(" " + m);
				}
				}
				
				Assert.assertTrue("Function should have four minimal forms!",fje.length==4);
				Assert.assertTrue("Expected four masks!", fje[0].getMasks().size()==4);
				for(int i=0;i<fje.length;i++){

					Set<Integer> check=new HashSet<>(bf.getMinterms());
					check.add(6);
					check.add(8);
					Set<Integer> function=new HashSet<>(fje[i].getMinterms());
					Assert.assertTrue("Wrong "+(i+1)+". minimal form!",check.equals(function));
				}
				
			}

			@Test
			public void getMintermsTest13Dz(){
				BooleanVariable varA = new BooleanVariable("A");
				BooleanVariable varB = new BooleanVariable("B");
				BooleanVariable varC = new BooleanVariable("C");
				BooleanVariable varD = new BooleanVariable("D");
				BooleanFunction bf = new IndexedBF("f1",
						Arrays.asList(varA, varB, varC,varD), true,
						Arrays.asList(0,1,4,5,11,15),Arrays.asList(2,6,10));
			
			
				boolean zelimoDobitiProdukte = false;
				MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
				
				
				Assert.assertTrue("Function should have one minimal form!",fje.length==1);
				Assert.assertTrue("Expected two masks!", fje[0].getMasks().size()==2);
				List<Mask> expected=new ArrayList<>();
				expected.add(Mask.parse("0x0x"));
				expected.add(Mask.parse("1x11"));
			
				expected.removeAll(fje[0].getMasks());
				if(expected.isEmpty()==false){
					Assert.fail("");
				}
			}
			
			@Test
			public void getMaxtermTestZZ4_10(){ //Zadatak 4.10 iz zbirke
				BooleanVariable varA = new BooleanVariable("A");
				BooleanVariable varB = new BooleanVariable("B");
				BooleanVariable varC = new BooleanVariable("C");
				BooleanVariable varD = new BooleanVariable("D");
				BooleanFunction bf = new IndexedBF("f1",
						Arrays.asList(varA, varB, varC,varD), false,
						Arrays.asList(0,1,2,3,4,6,7,12,13,15),new ArrayList<Integer>());
			
			
				boolean zelimoDobitiProdukte = true;
				MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
				
				
			    Assert.assertTrue("Function should have two minimal forms!",fje.length==2);
				
			    for(int i=0;i<fje.length;i++){
					Assert.assertTrue("Wrong "+(i+1)+". minimal form!",fje[i].getMinterms().equals(bf.getMinterms()));
				}
	}


			@Test
			public void getMintermTestZZ4_11(){ //Zadatak 4.11 iz zbirke
				BooleanVariable varA = new BooleanVariable("A");
				BooleanVariable varB = new BooleanVariable("B");
				BooleanVariable varC = new BooleanVariable("C");
				BooleanVariable varD = new BooleanVariable("D");
				BooleanFunction bf = new IndexedBF("f1",
						Arrays.asList(varA, varB, varC,varD), true,
						Arrays.asList(4,5,7,8,9,10,11,12,15),new ArrayList<Integer>());
			
			
				boolean zelimoDobitiProdukte = false;
				MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
				
				Assert.assertTrue("Function should have two minimal forms!",fje.length==4);
				
				
				for(int i=0;i<fje.length;i++){
					Assert.assertTrue("Wrong "+(i+1)+". minimal form!",fje[i].getMinterms().equals(bf.getMinterms()));
				}
				
	}

			@Test
			public void getFerkoTest(){ //zadatak 4.18 iz zbirke
				BooleanVariable varA = new BooleanVariable("A");
				BooleanVariable varB = new BooleanVariable("B");
				BooleanVariable varC = new BooleanVariable("C");
				BooleanVariable varD = new BooleanVariable("D");
				BooleanFunction bf = new IndexedBF("f1",
						Arrays.asList(varA, varB, varC,varD), true,
						Arrays.asList(4,5,6,7,8,9,11),Arrays.asList(2,3,12,15));
			
			
				boolean zelimoDobitiProdukte = false;
				MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
				System.out.println("Minimalnih oblika ima: " + fje.length);
				for(MaskBasedBF f : fje) {
				System.out.println("Mogući minimalni oblik:");
				for(Mask m : f.getMasks()) {
				System.out.println(" " + m);
				}
				}
				Assert.assertTrue("Function should have two minimal forms!",fje.length==1);

				
				for(int i=0;i<fje.length;i++){

					Set<Integer> check=new HashSet<>(bf.getMinterms());
					check.add(3);
					check.add(15);
					Set<Integer> function=new HashSet<>(fje[i].getMinterms());
					Assert.assertTrue("Wrong "+(i+1)+". minimal form!",check.equals(function));
				}
				
	}
			

			@Test
			public void getEdgeTest(){ 
				BooleanVariable varA = new BooleanVariable("A");
				BooleanVariable varB = new BooleanVariable("B");
				BooleanVariable varC = new BooleanVariable("C");
				BooleanVariable varD = new BooleanVariable("D");
				BooleanFunction bf = new IndexedBF("f1",
						Arrays.asList(varA, varB, varC,varD), true,
						Arrays.asList(0,1,2,3),Arrays.asList(8,12));
			
			
				boolean zelimoDobitiProdukte = false;
				MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
				System.out.println("Minimalnih oblika ima: " + fje.length);
				for(MaskBasedBF f : fje) {
				System.out.println("Mogući minimalni oblik:");
				for(Mask m : f.getMasks()) {
				System.out.println(" " + m);
				}
				}
				Assert.assertTrue("Function should have one minimal form!",fje.length==1);

				for(int i=0;i<fje.length;i++){
					Assert.assertTrue("Wrong "+(i+1)+". minimal form!",fje[i].getMinterms().equals(bf.getMinterms()));
				}
	}





}

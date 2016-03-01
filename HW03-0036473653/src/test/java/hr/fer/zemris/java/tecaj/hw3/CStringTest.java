package hr.fer.zemris.java.tecaj.hw3;

import org.junit.Test;
import org.junit.Assert;


public class CStringTest {
	
	@Test
	public void getConst1Test(){
		char[] data = {'P','M','a','r','k','o'};
		CString primjer = new CString(data,1,5);
		System.out.println(primjer);          
		data[1] = 'i';
		System.out.println(primjer);          
		Assert.assertTrue(primjer.toString().compareTo("Marko")==0);
	}

	
	
	@Test
	public void getConst3Test(){
		CString primjer = new CString("Ana");
	Assert.assertTrue(primjer.toString().compareTo("Ana")==0
			&& primjer.offset==0 && primjer.length==3);
	}

		
	
	@Test
	public void konstruktor12() {
		char[] c = new char[] { 'p', 'e', 't', 'r', 'a', ' ', 'j', 'e', ' ',
				'l', 'e', 'g', 'e', 'n', 'd', 'a' };
		System.out.println(new CString(c));
		CString novi1 = new CString(c, 0, 5);
		
		CString novi2=new CString(novi1);
		Assert.assertTrue(novi2.data.length==5);
		

	}
	
	@Test
	public void lenTest(){
		Assert.assertTrue(new CString("Umor").length==4);
	}
	
	@Test
	public void charAtTest(){
		Assert.assertTrue(new CString("Umor").charAt(0)=='U');
	}
	
		
	@Test
	public void toChArrTest() {
		char[] c = new char[] { 'p', 'e', 't', 'r', 'a', ' ', 'j', 'e', ' ',
				'l', 'e', 'g', 'e', 'n', 'd', 'a' };
		CString novi = new CString(c);
		char[] d=novi.toCharArray();
		for(int i=0;i<novi.length;i++){
			Assert.assertTrue(c[i]==d[i]);
		}
	}
	@Test
	public void indexOfTest(){
		char[] c = new char[] { 'p', 'e', 't', 'r', 'a', ' ', 'j', 'e', ' ',
				'l', 'e', 'g', 'e', 'n', 'd', 'a' };
		CString novi = new CString(c);
		Assert.assertTrue(novi.indexOf('e')==1);
	}
	
	@Test
	public void startsWithTest(){
		char[] c = new char[] {'p', 'e', 't', 'r', 'a',' ', 'j', 'e', ' ',
				'l', 'e', 'g', 'e', 'n', 'd', 'a' };
		CString novi = new CString(c);
		Assert.assertFalse(novi.startsWith(new CString("ana")));
		Assert.assertTrue(novi.startsWith(new CString("petra j")));
	}
	
	@Test
	public void endsWithTest(){
		char[] c = new char[] {'p', 'e', 't', 'r', 'a',' ', 'j', 'e', ' ',
				'l', 'e', 'g', 'e', 'n', 'd', 'a' };
		CString novi = new CString(c);
		Assert.assertFalse(novi.endsWith(new CString("ana")));
		Assert.assertTrue(novi.endsWith(new CString(" legenda")));
	}
	
	@Test
	public void containsTest(){
		char[] c = new char[] {'p', 'e', 't', 'r', 'a',' ', 'j', 'e', ' ',
				'l', 'e', 'g', 'e', 'n', 'd', 'a' };
		CString novi = new CString(c);
		Assert.assertFalse(novi.contains(new CString("ana")));
		Assert.assertTrue(novi.contains(new CString(" je ")));
	}
	
	@Test
	public void subTest(){
		char[] c = new char[] {'p', 'e', 't', 'r', 'a',' ', 'j', 'e', ' ',
				'l', 'e', 'g', 'e', 'n', 'd', 'a' };
		CString novi = new CString(c);
	Assert.assertTrue(novi.substring(3, 7).toString().compareTo("ra j")==0);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void leftTestTest(){
		char[] c = new char[] {'p', 'e', 't', 'r', 'a',' ', 'j', 'e', ' ',
				'l', 'e', 'g', 'e', 'n', 'd', 'a' };
		CString novi = new CString(c);
	Assert.assertTrue(novi.left(5).toString().compareTo("petra")==0);
	Assert.assertTrue(novi.left(500).toString().compareTo("petra")==0);
	}
	
	
	
	@Test (expected=IllegalArgumentException.class)
	public void rightTestTest(){
		char[] c = new char[] {'p', 'r', 'o', 'l', 'j','e', 'c', 'e', ' ',
				'j', 'e' };
		CString novi = new CString(c);
	Assert.assertTrue(novi.right(5).toString().compareTo("ce je")==0);
	Assert.assertTrue(novi.right(500).toString().compareTo("petra")==0);
	}
	
	@Test
	public void addTest(){
		CString ime=new CString("Marin ");
		CString prezime= new CString("Golub");
		System.out.println(ime.add(prezime));
		Assert.assertTrue(ime.add(prezime).toString().compareTo("Marin Golub")==0);
		
	}
	@Test
	public void replaceTest1(){
		CString novi=new CString("acaaa");
		System.out.println(novi.replaceAll('a','B'));
		Assert.assertTrue(novi.replaceAll('a','B').toString().compareTo("BcBBB")==0);
		
	}
	
	@Test
	public void replaceTest2(){
		CString novi=new CString("Petra je dosadna");
		System.out.println(novi.replaceAll(' ','$'));
		Assert.assertTrue(novi.replaceAll(' ','$').toString().compareTo("Petra$je$dosadna")==0);
		
	}
	
	@Test
	public void replaceAllTest(){
		CString novi=new CString("ababab");
		CString trazeni=novi.replaceAll(new CString("ab"), new CString("abab"));
		System.out.println(trazeni);
		Assert.assertTrue(trazeni.toString().compareTo("abababababab")==0);
	}
		
	@Test
	public void replaceAllTest2(){
		CString novi=new CString("Proljece je a ja sjedim doma i programiram :(");
		CString trazeni=novi.replaceAll(new CString("program"), new CString("BOMBARD"));
		System.out.println(trazeni);
		
	}
	}
	
		
	
	
	
	
	
	


	
		
		
		
package hr.fer.zemris.java.filechecking.fmagician;

import hr.fer.zemris.java.filechecking.FCFileVerifier;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;



public class ExecutionTest {
@Test
public void getExecutionTested(){
	File file=new File("HW05-0036473653.zip");
	String fileName="pEtRa";
	
	String program="filename i\"pETRa\" @\"krivo ime\""
			+"format zip{"
			+"def jmbag \"0036473653\""
			+"def src \"HW05-${jmbag}/src\""
			+"def srcmain \"${src}/main/java\""
			+"def srctest \"${src}/test/java\""
			+"exists dir \"${srcmain}\" @\"Missing directory 'src/main/java'\"{"
				+"exists dir \"${srctest}\" @\"Missing directory 'src/test/java'\"{"
					+"fail @\"Good work I found both src dirs!\""
				+"}"
			+"def sorting \"${srcmain}:hr.fer.zemris.java.tecaj.hw5.sorting\""
			+"!exists f \"${sorting}/BigSort.java\" @\"Good! file BigSort is here!\"{"
				+"!fail{"
					+"fail @\"Expected file BigSort.java\""
					+"}" 
				
			+"terminate}";
	Map<String,Object> mapa=new HashMap<String, Object>();
	
	FCFileVerifier verfier=new FCFileVerifier(file, fileName, program, mapa);
	List<String> compareList=null;
	if(verfier.hasErrors()){
		 compareList=verfier.errors();
	}
	Assert.assertTrue(compareList.size()==2);
	Assert.assertTrue(compareList.get(0).equals("Good work I found both src dirs!"));
	Assert.assertTrue(compareList.get(1).equals("Good! file BigSort is here!"));
	
}
@Test
public void executionTestNonZip(){
	File file=new File("HW05-0036473653.rar");
	String fileName="pEtRa";
	
	String program="filename i\"pETRa\" @\"krivo ime\""
		+"format zip !format zip {"
			+"def jmbag \"0036473653\""
			+"def src \"HW05-${jmbag}/src\""
			+"def srcmain \"${src}/main/java\""
			+"def srctest \"${src}/test/java\""
			+"exists dir \"${srcmain}\" @\"Missing directory 'src/main/java'\"{"
				+"exists dir \"${srctest}\" @\"Missing directory 'src/test/java'\"{"
					+"fail @\"Good work I found both src dirs!\""
				+"}"
			+"def sorting \"${srcmain}:hr.fer.zemris.java.tecaj.hw5.sorting\""
			+"!exists f \"${sorting}/BigSort.java\" @\"Good! file BigSort is here!\"{"
				+"!fail{"
					+"fail @\"Expected file BigSort.java\""
					+"}}" 
				
			+"terminate";
	Map<String,Object> mapa=new HashMap<String, Object>();
	
	FCFileVerifier verfier=new FCFileVerifier(file, fileName, program, mapa);
	List<String> compareList=null;
	if(verfier.hasErrors()){
		 compareList=verfier.errors();
	}
	System.out.println(compareList);
	Assert.assertTrue(compareList.size()==4);
	Assert.assertTrue(compareList.get(0).equals("Error opening file"));
	Assert.assertTrue(compareList.get(1).equals("Error opening file"));
	Assert.assertTrue(compareList.get(2).equals("File format does not match format zip"));
	Assert.assertTrue(compareList.get(3).equals("Given file cannot be interpred as zip OR cointains no directories!"));
}

@Test
public void executionTestWrongFormat(){
	File file=new File("HW05-0036473653.zip");
	String fileName="pEtRa";
	
	String program="filename i\"pETRa\" @\"krivo ime\""
		+"format kip {"
			+"def jmbag \"0036473653\""
			+"def src \"HW05-${jmbag}/src\""
			+"def srcmain \"${src}/main/java\""
			+"def srctest \"${src}/test/java\""
			+"exists dir \"${srcmain}\" @\"Missing directory 'src/main/java'\"{"
				+"exists dir \"${srctest}\" @\"Missing directory 'src/test/java'\"{"
					+"fail @\"Good work I found both src dirs!\""
				+"}"
			+"def sorting \"${srcmain}:hr.fer.zemris.java.tecaj.hw5.sorting\""
			+"!exists f \"${sorting}/BigSort.java\" @\"Good! file BigSort is here!\"{"
				+"!fail{"
					+"fail @\"Expected file BigSort.java\""
					+"}}" 
				
			+"terminate";
	Map<String,Object> mapa=new HashMap<String, Object>();
	
	FCFileVerifier verfier=new FCFileVerifier(file, fileName, program, mapa);
	List<String> compareList=null;
	if(verfier.hasErrors()){
		 compareList=verfier.errors();
	}

	Assert.assertTrue(compareList.size()==1);
	
	Assert.assertTrue(compareList.get(0).equals("Only zip format for now is supported!"));
	
}

@Test
public void executionTest2(){
	File file=new File("proba.zip");
	String fileName="proba";
	
	String program="filename \"proba\" @\"File name should be 'proba'\"{"
			+"format zip{"
		+"def glavni \"proba/glavni\""
		+"!exists di i\"${glavn}\" @\"glavn should not be defined\"{"
			+"def paket \"${glavni}/proba\""
			+"!exists f \"${paket}/proba.txt\"{"
			+"fail @\"Ne bi se trebalo ispisati\"}"
			+"}"
		+"terminate}"
		+"}";
	Map<String,Object> mapa=new HashMap<String, Object>();
	
	FCFileVerifier verfier=new FCFileVerifier(file, fileName, program, mapa);
	List<String> compareList=null;
	if(verfier.hasErrors()){
		 compareList=verfier.errors();
	}
	Assert.assertTrue(compareList.size()==2);
	Assert.assertTrue(compareList.get(0).equals("Error:Variable 'glavn' does not exist!"));
	Assert.assertTrue(compareList.get(1).equals("File with name: 'proba/glavni/proba/proba.txt' does  exist!"));
}

@Test
public void executionTestAllOK(){
	File file=new File("proba.zip");
	String fileName="proba";
	
	String program="filename \"proba\" @\"File name should be 'proba'\"{"
			+"format zip{"
		+"def glavni \"proba/glavni\""
		+"exists di i\"${glavni}\" @\"glavni should be defined\"{"
			+"def paket \"${glavni}/proba\""
			+"exists f \"${paket}/proba.txt\"{"
			+"!fail @\"Ne bi se trebalo ispisati\"}"
			+"}"
		+"terminate}"
		+"}";
	Map<String,Object> mapa=new HashMap<String, Object>();
	
	FCFileVerifier verfier=new FCFileVerifier(file, fileName, program, mapa);
	
	Assert.assertTrue(verfier.hasErrors()==false);
	
}


}

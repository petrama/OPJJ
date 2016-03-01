package hr.fer.zemris.web.radionice;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadioniceBaza;
import hr.fer.zemris.web.radionice.RadioniceBaza.Opcija;
import hr.fer.zemris.web.radionice.iznimke.InconsistentDatabaseException;

import org.junit.Assert;
import org.junit.Test;


public class RadioniceBazaTest extends Assert {

	@Test
	public void ucitajIZapisiBazu() {
		Path d=null;
		try{
		RadioniceBaza baza=RadioniceBaza.ucitaj("./baza");
		
		d=Files.createTempDirectory("temp");
		baza.snimi(d.toString());
		
		File temp=d.toFile();
		d.toFile().deleteOnExit();
		File[] prvi=new File("./baza").listFiles();
		File[] drugi=temp.listFiles();
		assertTrue(prvi.length==drugi.length);
		
		for(int i=0;i<prvi.length;i++){
			assertTrue(isti(prvi[i].toPath(), (drugi[i].toPath())));
		}
		}catch(IOException e){
			d.toFile().delete();
			fail("Dogodila se iznimka a nije trebala!");
		}
		d.toFile().delete();
		
	}
	
	
	@Test
	public void pokusajSrusitiBazu() {
		Path d=null;
	
		RadioniceBaza baza=RadioniceBaza.ucitaj("./baza");
		Radionica radionica=baza.getRadionica(Long.valueOf(1));
		radionica.getOprema().add(new Opcija("101", "USB-stick"));
		
		try {
			d=Files.createTempDirectory("tem");
		} catch (IOException e1) {
			System.out.println("TU");
		}
		try{
		baza.snimi(d.toString());
		
		File temp=d.toFile();
		d.toFile().deleteOnExit();
		File[] prvi=new File("./baza").listFiles();
		File[] drugi=temp.listFiles();
		assertTrue(prvi.length==drugi.length);
		
		for(int i=0;i<prvi.length;i++){
			assertTrue(isti(prvi[i].toPath(), (drugi[i].toPath())));
		}
		}catch(Exception e){
			if(e instanceof InconsistentDatabaseException){
				d.toFile().delete();
				return;
			}
			
		}
		
		
		d.toFile().delete();
		fail("Trebala se dogoditi InconsistentDatabaseException a nije!");
	}
	
	
	public static boolean isti(Path prvi,Path drugi) throws IOException{
		List<String> prva=Files.readAllLines(prvi,StandardCharsets.UTF_8);
		
		List<String> druga=Files.readAllLines(drugi,StandardCharsets.UTF_8);
		if(druga.size()!=prva.size()) return false;
		for(int i=0;i<prva.size();i++){
			if(!prva.get(i).trim().equals(druga.get(i).trim())){
				
				System.out.println(prva.get(i));
				System.out.println(druga.get(i));
				return false;
			}
		}
		return true;
	}

}

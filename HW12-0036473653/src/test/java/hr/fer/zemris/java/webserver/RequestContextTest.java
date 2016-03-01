package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class RequestContextTest extends Assert {

	@Test(expected=RuntimeException.class)//dodavanje cookia nakon generiranja headera(ispravnog!)
	public void generatingHeaderBytes() throws IOException {
		OutputStream os = null;
		try {
			os = Files.newOutputStream(Paths.get("gettersNsetters.txt"));
		} catch (FileNotFoundException e) {

		}

		RCCookie cookie = new RCCookie("petra", "ljepotica",null,null,null);
		RequestContext rc = new RequestContext(os, null, null,null);
		rc.addRCCookie(cookie);
		rc.write("Petra ljepotica zeli mir u svijetu!".getBytes());

		String written = readFile("gettersNsetters.txt");
		String expected="HTTP/1.1 200 OK\r\n"
				+"Content-type: text/plain; charset=UTF-8\r\n"
				+"Set-Cookie: petra=\"ljepotica\"\r\n\r\n"
				+"Petra ljepotica zeli mir u svijetu!\r\n";

		assertTrue("Header is wrong!",expected.equals(written));
		rc.addRCCookie(new RCCookie("pogreska","dodavanje" , null,null,null));
	
		
	}

	@Test
	public void generatingHeaderString() throws IOException {
		OutputStream os = null;
		try {
			os = Files.newOutputStream(Paths.get("gettersNsetters.txt"));
		} catch (FileNotFoundException e) {

		}

		RCCookie cookie = new RCCookie("sid", "ABC", 10, "127.0.0.1", "/", true);
		List<RCCookie> list = new ArrayList<>();
		list.add(cookie);
		RequestContext rc = new RequestContext(os, null, null, list);
		rc.write("Pozdrav ");
		rc.write("svima!");
		String written = readFile("gettersNsetters.txt");
		String expected = "HTTP/1.1 200 OK\r\n"
				+ "Content-type: text/plain; charset=UTF-8\r\n"
				+ "Set-Cookie: sid=\"ABC\"; Domain=127.0.0.1; Path=/; Max-Age=10; HttpOnly\r\n\r\n"
				+ "Pozdrav svima!\r\n";
		System.out.println(written);
		
		assertTrue("Header is not generated well!", written.equals(expected));

	}
	
	@Test(expected=RuntimeException.class)//dodavanje cookia nakon generiranja headera(ispravnog!)
	public void generatingHeaderBytes2() throws IOException {
		OutputStream os = null;
		try {
			os = Files.newOutputStream(Paths.get("gettersNsetters.txt"));
		} catch (FileNotFoundException e) {

		}

		RCCookie cookie = new RCCookie("petra", "ljepotica",null,null,null);
	
		RequestContext rc = new RequestContext(os, null, null,null);
		rc.addRCCookie(cookie);
		rc.setMimeType("text/plain");
		rc.setEncoding("UTF-8");
		rc.setStatusCode(50);
		rc.setStatusText("MO");
		rc.write("Petra ljepotica zeli mir u svijetu!".getBytes());

		String written = readFile("gettersNsetters.txt");
		String expected="HTTP/1.1 50 MO\r\n"
				+"Content-type: text/plain; charset=UTF-8\r\n"
				+"Set-Cookie: petra=\"ljepotica\"\r\n\r\n"
				+"Petra ljepotica zeli mir u svijetu!\r\n";

		assertTrue("Header is wrong!",expected.equals(written));
		rc.addRCCookie(new RCCookie("pogreska","dodavanje" , null,null,null));
	
		
	}
	
	@Test
	public void changingParamsAfterHeader() throws IOException {
	
		RequestContext rc = new RequestContext(System.out, new HashMap<String,String>(),new HashMap<String,String>(), null);
		rc.write("Pozdrav");//generiram nešto
		try{
			rc.setOutputCookies(0,new RCCookie(" ", " ", 5 ,null, null));
		}catch(RuntimeException r){
			assertTrue("You are not allowed to change output-cookies after header is generated!".equals(r.getMessage()));
			
		}
		try{
			rc.setEncoding("UTF-16");
		}catch(RuntimeException r){
			
			assertTrue("You are not allowed to change encoding after header is generated!".equals(r.getMessage()));
			
		}
		try{
			rc.setMimeType("lala");
		}catch(RuntimeException r){
			assertTrue("You are not allowed to change mime-type after header is generated!".equals(r.getMessage()));
		
		}
		try{
			rc.setStatusCode(5);
		}catch(RuntimeException r){
			assertTrue("You are not allowed to change status-code after header is generated!".equals(r.getMessage()));
			
		}
		
		try{
			rc.setStatusText("5");
		}catch(RuntimeException r){
			assertTrue("You are not allowed to change status text after header is generated!".equals(r.getMessage()));
			
		}
		
		
	}
	
	@Test
	public void gettersSettersTest(){
		Map<String,String> p=new HashMap<>();
		p.put("a", "5");
		p.put("b", "4");
		RequestContext rc=new RequestContext(System.out,p,null,null);
		assertTrue(rc.getParameterNames().size()==2);
		assertTrue(rc.getParameter("a").equals("5"));
		
		
		
		
		rc.setPersistentParameter(null, "");
		rc.setPersistentParameter("mi", "seljaci");
		rc.setPersistentParameter("ja", null);
		String value=rc.getPersistentParameter("mi");
		assertTrue(value.equals("seljaci"));
		value=rc.getPersistentParameter(null);
		assertTrue(value==null);
		value=rc.getPersistentParameter("ovogNema");
		assertTrue(value==null);
		
		
		Set<String> names=rc.getPersistentParameterNames();
		assertTrue("Samo jedan element mora biti u setu!",names.size()==1);
		assertTrue(names.remove("mi"));
		assertTrue(rc.getPersistentParameterNames().size()==1);
		rc.removePersistentParameter("mi");
		assertTrue(rc.getPersistentParameterNames().isEmpty());
		
		
		rc.setTemporaryParameter("mi", "seljaci");
		rc.setTemporaryParameter("ja", null);
		value=rc.getTemporaryParameter("mi");
		assertTrue(value.equals("seljaci"));
		value=rc.getTemporaryParameter(null);
		assertTrue(value==null);
		value=rc.getTemporaryParameter("ovogNema");
		assertTrue(value==null);
		names=rc.getTemporaryParameterNames();
		assertTrue("Samo jedan element mora biti u setu!",names.size()==1);
		assertTrue(names.remove("mi"));
		assertTrue(rc.getTemporaryParameterNames().size()==1);
		rc.removeTemporaryParameter("mi");
		assertTrue(rc.getTemporaryParameterNames().isEmpty());
		try{
		rc=new RequestContext(null,null,null,null);
		} catch (RuntimeException r){
			rc=new RequestContext(System.out,p,null,null);
		}
		
		try {
			rc.setOutputCookies(5, new RCCookie("a", "a", null,null,null));
		} catch (RuntimeException e) {
			assertTrue("Index out of bounds!".equals(e.getMessage()));
			
		}
		rc.addRCCookie(new RCCookie("p", "z", null,null,null));
		rc.setOutputCookies(0, new RCCookie("novi", "unutra", null,null,null));
		
		
	}
	
	

	protected static String readFile(String filePath) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(filePath), "UTF-8"));
		String everything = "";
		try {
			StringBuilder sb = new StringBuilder();
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;

				sb.append(line);
				sb.append(System.lineSeparator());
			}
			everything = sb.toString();
		} finally {
			br.close();
		}
		;
		return everything;
	}

}

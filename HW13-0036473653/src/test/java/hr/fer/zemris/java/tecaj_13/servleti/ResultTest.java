package hr.fer.zemris.java.tecaj_13.servleti;

import org.junit.Assert;
import org.junit.Test;


import hr.fer.zemris.java.tecaj_13.servleti.GlasanjeRezultatiServlet.Result;
import hr.fer.zemris.java.tecaj_13.servleti.GlasanjeServlet.Trio;

public class ResultTest extends Assert {
@Test
public void testResultClass(){
	Result result=new Result(new Trio(1, "Beatles", "My Michelle"), 50);
	assertTrue(result.getGlasovi().equals(50));
	
	
}
}

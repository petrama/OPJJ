package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Razred predstavlja generator zaglavlja http odgovora.
 * @author Petra Marče
 *
 */


public class RequestContext {
	/**
	 * Statički razred koji predstavlja model jednog kolačića.
	 * @author Petra Marče.
	 *
	 */
	public static class RCCookie {
		private String name;
		private String value;
		private String domain;
		private String path;
		private Integer maxAge;
		private boolean httpOnly;
	

		/**
		 * Stvara novu instancu razreda RCCookie.
		 * @param name ime
		 * @param value vrijednost
		 * @param maxAge maksimalna dob
		 * @param domain domena
		 * @param path putanja
		 */
		public RCCookie(String name, String value, Integer maxAge,
				String domain, String path) {
			this(name, value, maxAge, domain, path, false);
		}

		
		
		public RCCookie(String name, String value, Integer maxAge,
				String domain, String path,boolean httpOnly) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.httpOnly=httpOnly;
		}

		/**
		 * Metoda za dohvat imena.
		 * @return ime.
		 */
		String getName() {
			return name;
		}

		/**
		 * Metoda za dohvat vrijednosti.
		 * @return vrijednost.
		 */
		String getValue() {
			return value;
		}

		/**
		 * Metoda za dohvat domene.
		 * @return domena
		 */
		String getDomain() {
			return domain;
		}
		/**
		 * Metoda za dohvat putanje.
		 * @return putanja.
		 */
		String getPath() {
			return path;
		}
		/**
		 * Metoda za dovat informacije o maks dobi.
		 * @return maksimalna dob.
		 */
		Integer getMaxAge() {
			return maxAge;
		}

	}

	private OutputStream outputStream;
	private Charset charset;
	private String encoding;
	private int statusCode;
	private String statusText;
	private String mimeType;
	private Map<String, String> parameters;
	private Map<String, String> persistentParameters;
	private Map<String, String> temporaryParameters;
	private List<RCCookie> outputCookies;
	private boolean headerGenerated;
	/**
	 * Stvara novu instancu razreda RequestContent.
	 * @param outputStream stream u kojeg se zapisuje header i sadržaj.
	 * @param parameters parametri
	 * @param persistentParameters stalni parametri
	 * @param outputCookies lista kolačića
	 */
	public RequestContext(OutputStream outputStream,
			Map<String, String> parameters,
			Map<String, String> persistentParameters,
			List<RCCookie> outputCookies) {
		
		if (outputStream == null) {
			throw new IllegalArgumentException("outputStream must ne not null!");
		}
		this.outputStream = outputStream;
		if (parameters == null) {
			this.parameters = new HashMap<>();
		} else {
			this.parameters = parameters;
		}
		if (persistentParameters == null) {
			this.persistentParameters = new HashMap<>();
		} else {
			this.persistentParameters = persistentParameters;
		}
		if (outputCookies == null) {
			this.outputCookies = new ArrayList<>();
		} else {
			this.outputCookies = outputCookies;
		}
		temporaryParameters=new HashMap<>();
		encoding = "UTF-8";
		statusCode = 200;
		statusText = "OK";
		mimeType = "text/plain";
	}

	/**
	 * Metoda za postavljanje encodinga.
	 * @param encoding prima string imena željenog encodinga.
	 */
	void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new RuntimeException(
					"You are not allowed to change encoding after header is generated!");
		}
		this.encoding = encoding;
	}

	/**
	 * Metoda za postavljanje statusnog koda.
	 * @param statusCode prima broj koji će biti novi statusni kod.
	 */
	void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new RuntimeException(
					"You are not allowed to change status-code after header is generated!");
		}
		this.statusCode = statusCode;
	}
	/**
	 * Metoda za postavljanje statusnog teksta.
	 * @param statusText tekst koji će biti novi status.
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new RuntimeException(
					"You are not allowed to change status text after header is generated!");
		}
		this.statusText = statusText;
	}

	/**
	 * Metoda za postavljanje mime tipa.
	 * @param mimeType ime novog mime tipa.
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new RuntimeException(
					"You are not allowed to change mime-type after header is generated!");
		}
		this.mimeType = mimeType;
	}
	/**
	 * Metoda za dohvat parametra sa predanim imenom.
	 * @param name ime traženog parametra.
	 * @return vrijednost parametra čije je ime predano.
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	/**
	 * Metoda za dohvat skupa imena svih parametara.
	 * Pisanje po takvom skupu neće promjeniti originalni.
	 * @return skup imena svih parametara.
	 */
	public Set<String> getParameterNames() {
		return new TreeSet<>(parameters.keySet());
	}
	/**
	 * Metoda za dohvat stalnog parametra sa predanim imenom.
	 * @param name ime traženog stalnog parametra.
	 * @return vrijednost stalnog parametra čije je ime predano.
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	/**
	 * Metoda za dohvat skupa imena svih stalnih parametara.
	 * Pisanje po takvom skupu neće promjeniti originalni.
	 * @return skup imena svih stalnih parametara.
	 */
	public Set<String> getPersistentParameterNames() {
		return new TreeSet<>(persistentParameters.keySet());
	}
	/**
	 * Metoda za postavljanje stalnog parametra.
	 * Postavlja novi parametar sa predanim imenom i vrijednošću.
	 * @param name ime novog parametra.
	 * @param value vrijednost novog parametra.
	 */

	public void setPersistentParameter(String name, String value) {
		if (name != null && value != null) {
			persistentParameters.put(name, value);
		}
	}
	/**
	 * Metoda za uklanjane stalnog parametra.
	 * @param name ime parametra kojeg treba ukloniti.
	 */

	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	/**
	 * Metoda za dohvat skupa imena svih trenutnih parametara.
	 * Pisanje po takvom skupu neće promjeniti originalni.
	 * @return skup imena svih trenutnih parametara.
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);

	}
	/**
	 * Metoda za dohvat skupa imena svih trenutnih parametara.
	 * Pisanje po takvom skupu neće promjeniti originalni.
	 * @return skup imena svih trenutnih parametara.
	 */

	public Set<String> getTemporaryParameterNames() {
		return new TreeSet<>(temporaryParameters.keySet());
	}
	/**
	 * Metoda za postavljanje trenutnog parametra.
	 * Postavlja novi parametar sa predanim imenom i vrijednošću.
	 * @param name ime novog parametra.
	 * @param value vrijednost novog parametra.
	 */

	public void setTemporaryParameter(String name, String value) {
		if (name != null && value != null) {
			temporaryParameters.put(name, value);
		}
	}
	
	/**
	 * Metoda za uklanjane trenutnih parametra.
	 * @param name ime parametra kojeg treba ukloniti.
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Metoda postavljanja novog elementa u listu kolačića.
	 * @param index index na kojeg treba postaviti novi kolačić
	 * @param cookie novi kolačić
	 */
	public void setOutputCookies(int index, RCCookie cookie) {
		if (headerGenerated) {
			throw new RuntimeException(
					"You are not allowed to change output-cookies after header is generated!");
		}
		if (index < 0 || index >= outputCookies.size()) {
			throw new RuntimeException("Index out of bounds!");

		}
		outputCookies.set(index, cookie);
	}
	/**
	 * Metoda za dodavanje novog kolačića u listu kolačića.
	 * @param coo kolečić kojeg treba dodati.
	 */

	public void addRCCookie(RCCookie coo) {
		if (headerGenerated) {
			throw new RuntimeException(
					"You are not allowed to add cookies after header is generated!");

		}
		outputCookies.add(coo);
	}

	/**
	 * Metoda koja u output stream zapisuje predano polje bajtova.
	 * @param data predano polje bajtova.
	 * @return 
	 * @throws IOException u slučaju pogreske u izlaznoj operaciji metoda baca odgovarajuću iznimku.
	 */
	public RequestContext write(byte[] data) {
		if (!headerGenerated) {
			generateHeader();
		
		}
		try {
			outputStream.write(data);
			outputStream.flush();
		} catch (IOException e) {
			System.err.println("Problem with output operation!");
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Metoda koja u output stream zapisuje predanui string.
	 * @param text predani string.
	 * @return 
	 */
	public RequestContext write(String text)  {
		if (!headerGenerated) {
			generateHeader();
	
		}
		
		try {
			outputStream.write(text.getBytes(charset));
			outputStream.flush();
		} catch (IOException e) {
			System.err.println("Problem with output operation!");
			e.printStackTrace();
		}
	
		return this;
	}

	/**
	 * Metoda koja zapisuje odgovarajuce zaglavlje u output stream.
	 * @throws IOException u slučaju pogreške u izlaznoj operaciji metoda baca odgovarajuću iznimku.
	 */
	private void generateHeader()  {
		headerGenerated=true;			
		this.charset = Charset.forName(encoding);

		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode +" "+ statusText + "\r\n");
		sb.append("Content-type: " + mimeType);
		if (mimeType.startsWith("text/")) {
			sb.append("; charset=" + encoding);
		}
		sb.append("\r\n");

		if (!outputCookies.isEmpty()) {
			for (RCCookie coo : outputCookies) {
				sb.append("Set-Cookie: " + coo.getName() + "=\""
						+ coo.getValue() + "\"");

				if(coo.getDomain()!=null){
					sb.append("; Domain=" + coo.getDomain());
				}
				
				if (coo.getPath() != null) {
					sb.append("; Path=" + coo.getPath());
				}

				if (coo.getMaxAge() != null) {
					sb.append("; Max-Age=" + coo.getMaxAge());
				}
				if(coo.httpOnly){
					sb.append("; HttpOnly");
				}
				
				sb.append("\r\n");

			}
		}
		sb.append("\r\n");		
		write(sb.toString());
	}
}

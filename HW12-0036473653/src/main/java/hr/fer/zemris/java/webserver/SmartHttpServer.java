package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * Jednostavni HTTP server koji omogućuje korištenje kolačića,
 * definiranje URL parametara, izvođenje Smart skrpti.
 * Podržava ratličite mime tipove.
 * @author Petra Marče
 *
 */
public class SmartHttpServer {

	private String address;
	private int port;
	private int workerThreads;
	private int sessionTimeout;
	private Map<String, String> mimeTypes;
	private Map<String, IWebWorker> workersMap;
	private ServerThread serverThread;
	private GarbageThread garbageThread;
	private ExecutorService threadPool;
	private Path documentRoot;
	private Map<String, SessionMapEntry> sessions;
	private Random sessionRandom;

	
	static class SessionMapEntry {
		String sid;
		long validUntil;
		Map<String, String> map;

		public SessionMapEntry(String sid, long validUntil) {
			super();
			this.sid = sid;
			this.validUntil = validUntil;
			this.map = Collections
					.synchronizedMap(new HashMap<String, String>());
			
		}
	}

	
	/**
	 * Konstruktor prima putanju do datoteke koja je konfiguracija za server.
	 * @param configFileName putanja do datoteke.
	 */
	public SmartHttpServer(String configFileName) {

		mimeTypes = new HashMap<String, String>();
		workersMap = new HashMap<String, IWebWorker>();
		this.sessions = new HashMap<String, SessionMapEntry>();
		sessionRandom = new Random();
		String configString = "";
		try {
			configString = ServerAssistant.readFile(configFileName);
		} catch (IOException e) {
			throw new RuntimeException("Cannot read server.properties!");
		}
		String[] lines = configString.split("\r\n");
		exstractFromFile(lines);

		serverThread = new ServerThread();
		garbageThread=new GarbageThread();
	}
	
	/**
	 * Metoda koja se poziva pri pokretanju programa.
	 * Pokreće server.
	 * @param args argumenti iz komandne linije.
	 * Potrebno zadati samo putanju do datoteke s konfiguracijom za server.
	 * 
	 */
	public static void main(String[] args){
		
		if (args.length != 1) {
			System.out
					.println("Only one argument is expected: path to smart script!");
			System.exit(1);
		}
		SmartHttpServer s = new SmartHttpServer(args[0]);
		s.start();
		
	 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	String l=null;
	 do{
		try {
			l= br.readLine();
		} catch (IOException e) {
		
		}
	 }while(!l.trim().equals("stop"));
	
	
	s.stop();
		

	}

	/**
	 * Metoda kojom započinje rad servera.
	 * Inicijalizira se ThreadPool čiji je kapacitet jednak broju radnih dretvi.
	 * Ako nije već pokrenuta pokreće se serverska glavna dretva.
	 */
	protected synchronized void start() {
		threadPool = Executors.newFixedThreadPool(workerThreads);
		if (!serverThread.isAlive()) {
			serverThread.start();// … start server thread if not already running
									// …
		}
		if(!garbageThread.isAlive()){
			garbageThread.start();
		}
		

	}
	
	/**
	 * Metoda kojom rad servera završava.
	 * Rad radnih dretvi se zaustavlja.
	 */
	protected synchronized void stop() {
	

		threadPool.shutdown();
			
		

	}

	/**
	 * Razred koji predstavlja radnu dretvu Servera.
	 * Dretva dočekuje klijente i prosljeđuje ih threadPoolu na izvođenje nakon čega se vraća u osluškivanje zahtjeva.
	 * @author Petra Marče
	 *
	 */
	protected class ServerThread extends Thread {
		@Override
		/**
		 * Metoda čijim izvođenjem započinje stvarni rad servera.
		 * U beskonačnoj petlji dretva dočekuje klijente i prepusta njihovu obradu drugim dretvama.
		 * 
		 */
		public void run() {
			
	
			// given in pesudo-code:
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}// open serverSocket on specified port
			while (true) {
				Socket client = null;
				try {
					client = serverSocket.accept();
				} catch (IOException e) {
					System.err.println("Problem during getting client!");
					e.printStackTrace();
				}
				ClientWorker cw = new ClientWorker(client);
				threadPool.execute(cw);

			}
		
			

		}
	}

	/**
	 * Dretva koja svakih pet minuta iterira listom sesija i briše one kojima je predružen SID koji je istekao!
	 * @author Petra Marče
	 *
	 */
	protected class GarbageThread extends Thread{
		public void run() {
			while(true){
				
//			System.out.println("POcinjem brisati!");
			long now=System.currentTimeMillis()/1000;
			Map<String,SessionMapEntry> mapa=new HashMap<>(sessions);
			for(Entry<String,SessionMapEntry> session:sessions.entrySet()){
				if (session.getValue().validUntil<now){
					mapa.remove(session.getKey());
				}
			}
			sessions=mapa;
			try {
//				System.out.println("Na spavanje");
				sleep(5*1000);
			} catch (InterruptedException e) {
			
			}
			
			}
		}}
	
	
	/**
	 * Metoda koja prima polje linija zahtjeva, parsira ih, radi provjere i puni clanske varijable.
	 * @param lines polje linija zahtjeva.
	 */
	public void exstractFromFile(String[] lines) {
		for (String line : lines) {
			String[] keyValue = line.split("=");

			if (keyValue.length != 2) {
				throw new IllegalArgumentException(
						"Each line in given file must be 'key'='value'");
			}
			keyValue[0] = keyValue[0].trim();
			keyValue[1] = keyValue[1].trim();
			switch (keyValue[0]) {
			case "server.address":

				address = keyValue[1];
				continue;
			case "server.port":
				try {
					port = Integer.parseInt(keyValue[1]);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Port must be Integer!");
				}
				continue;
			case "server.workerThreads":
				try {
					workerThreads = Integer.parseInt(keyValue[1]);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException(
							"Number of threads must be Integer!");
				}
				continue;
			case "server.documentRoot":
				documentRoot = Paths.get(keyValue[1]);
				continue;
			case "session.timeout":
				try {
					sessionTimeout = Integer.parseInt(keyValue[1]);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException(
							"Session timeout must be Integer!");
				}
				continue;
			case "server.mimeConfig":
				ServerAssistant.loadInMap(mimeTypes, keyValue[1]);
				continue;
			case "server.workers":
				loadWorkers(keyValue[1]);
				continue;

			}
		}
	}

	/**
	 * Pomoćna metoda koja otvara datoteku na predanoj putanji i iz nje čita podatke potr
	 * @param s
	 */
	private void loadWorkers(String s) {
		String fileContent = "";
		try {
			fileContent = ServerAssistant.readFile(s);
		
		} catch (IOException e) {
			System.err.println("Cannot open or read file: " + s);
			e.printStackTrace();
		}
		String[] lines = fileContent.split("\r\n");
		for (String line : lines) {
			String[] keyValue = line.split("=");
			if (keyValue.length != 2) {
				throw new IllegalArgumentException(
						"Each line in given file must be 'key'='value'");
			}
			String path = keyValue[0].trim();
			String fqcn = keyValue[1].trim();

			if (workersMap.keySet().contains(keyValue[0])) {
				throw new IllegalArgumentException(
						"workers.properties should not contain dupliacte keys!");
			}

			IWebWorker iww;
			try {
				iww = getIWebWorker(fqcn);
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException e) {
				throw new RuntimeException(
						"Cannot load workers from workers.properties!");
			}
			workersMap.put(path, iww);

		}
	}

	/**
	 * Metoda koja iz predanog punog imena vraća novu instancu razreda koji implementira sučelje IWebWorker.
	 * Metodu ima smisla zvati samo s imenima razeda koji se nalaze u paketu hr.fer.zemris.java.webserver.workers
	 * @param name puno ime razreda
	 * @return vraća novu instancu traženog razreda
	 * @throws ClassNotFoundException pogreska u slucaju ne postojanja klase s danim imenom
	 * @throws InstantiationException pogreska u slucaju nemogucnosti instanciranja klase.
	 * @throws IllegalAccessException pogreska u slucaju nedozvoljenog pristupa
	 */
	public IWebWorker getIWebWorker(String name) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		Class<?> referenceToClass = null;
		referenceToClass = this.getClass().getClassLoader().loadClass(name);
		Object newObject = null;
		newObject = referenceToClass.newInstance();
		IWebWorker iww = (IWebWorker) newObject;
		return iww;
	}

	/**
	 * Razred koji predstavlja jednog obrađivača klijenskog zahtjeva.
	 * @author Petra Marče
	 *
	 */
	
	private class ClientWorker implements Runnable {
		private Socket csocket;
		private PushbackInputStream istream;
		private OutputStream ostream;
		private String version;
		private String method;
		private Map<String, String> params;
		private Map<String, String> permPrams;
		private List<RequestContext.RCCookie> outputCookies;
		private String SID;

		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
			permPrams = null;
			params = new HashMap<String, String>();
			outputCookies = new ArrayList<RequestContext.RCCookie>();

		}

		/**
		 * Metoda koja radi pravi posao.
		 * Provjerava ispravnost zahtjeva.
		 * I daje odgovarajući odgovor.
		 * Postoji više načina obrade: obrada Smart Scripte,obrada preko IWorkera ili direktni prikaz trazene datoteke.
		 */
		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
			} catch (IOException e) {
				closeClientSocket();
				throw new RuntimeException(
						"Could not open input/output socket stream.");
				
			}

			List<String> request=null;
			try {
				request = ServerAssistant.readRequest(istream);
			} catch (IOException e) {
				closeClientSocket();
				throw new RuntimeException(
						"Could not read request from input stream!");
			}

			if (request==null || request.isEmpty()) {
				sendErrorMessage(400);
				return;

			}
			String firstLine = request.get(0);
			String[] parts = firstLine.split(" ");
			method = parts[0];
			if (!method.equalsIgnoreCase("GET")) {
				sendErrorMessage(400);
				return;
			

			}
			version = parts[2];
			if (!(version.equals("HTTP/1.0") || version.equals("HTTP/1.1"))) {
				sendErrorMessage(400);
				return;
			
			}

				checkSession(request);
		

			
			String[] pathPart = parts[1].trim().split("\\?");
			String path = pathPart[0].trim();
			if (pathPart.length == 2) {
				String paramString = pathPart[1].trim();

				ServerAssistant.parseParameters(paramString, params);
				
			}

			RequestContext rc = new RequestContext(ostream, params, permPrams,
					outputCookies);
			if (path.startsWith("/ext/")) {
				String worker = path.substring(path.lastIndexOf("/") + 1)
						.trim();
				
				if (!worker.isEmpty()) {
					worker = "hr.fer.zemris.java.webserver.workers." + worker;

				}

				try {
					getIWebWorker(worker).processRequest(rc);
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException e) {

					closeClientSocket();
					throw new RuntimeException(
							"Cannot find or instantiate class, or acess is denyied!");
				}
				closeClientSocket();
				return;
			}

			if (workersMap.containsKey(path)) {
				workersMap.get(path).processRequest(rc);
				closeClientSocket();
				return;
			}

			Path requestedPath = documentRoot.resolve("." + path);

			String root = "";
			String dobiveni = "";
			
			try {
				root = documentRoot.toFile().getCanonicalPath();
				dobiveni = requestedPath.toFile().getCanonicalPath();
			} catch (IOException e) {
				closeClientSocket();
				throw new IllegalArgumentException("Could not open resolvedPath '" + requestedPath + "'.");
			}

			if (!dobiveni.startsWith(root)) {
				sendErrorMessage(403);
				return;

			}
			if (!Files.exists(requestedPath)) {
				sendErrorMessage(404);
				return;
				
			}

			String mime = ServerAssistant.utvrdiMimeTip(requestedPath
					.getFileName().toString(), mimeTypes);

			if (mime.equals("smscr")) {
				String script = "";
				try {
					script = ServerAssistant.readFile(requestedPath.toString());
				} catch (IOException e) {
					closeClientSocket();
					throw new RuntimeException("Cannot find or read script!");
				}

				SmartScriptEngine engine = new SmartScriptEngine(
						new SmartScriptParser(script).getDocumentNode(), rc);
				engine.execute();
				closeClientSocket();
				return;
			}

			rc.setMimeType(mime);
			rc.setStatusCode(200);
			byte[] data = null;
			try {
				data = Files.readAllBytes(requestedPath);
			} catch (IOException e) {
				closeClientSocket();
				throw new RuntimeException("Cannot read file!");
			}

			rc.write(data);
			closeClientSocket();

		}
		/**
		 * Metoda koja zatvara klijentski socket.
		 */

		private void closeClientSocket() {
			try {
				csocket.close();
			} catch (IOException e) {
				closeClientSocket();
				throw new RuntimeException("Server socket on port '" + port
						+ "' could not be closed.");
			}
		}

		/**
		 * Metoda provjerava linije zahtjeva klijenta.
		 * Obrađuje samo one koje se ondose na Cookie.
		 * Metoda koja provjerava session ID klijenta, ako on nije zapamćen ili je prestar, stvara novi.
		 * Dodaje odgovarajući Cookie sa imenom sid i vrijednošću SID-a u listu cookia.
		 * @param lines linije datoteke.
		 */
		private synchronized void checkSession(List<String> lines) {
			String candidate = null;
			SessionMapEntry entry = null;

			for (String line : lines) {

				if (line.startsWith("Cookie: ")) {
					String[] cookies = line.replace("Cookie:", "").split(";");
					for (int i = 0; i < cookies.length; i++) {
						String[] cookie = cookies[i].split("=");

						String cookieName = cookie[0];
						String cookieValue = cookie[1].replace("\"", "");

						if (cookieName.trim().equals("sid")) {
							candidate = cookieValue;
						}
					}
				}
			}

			if (candidate == null) {
				entry = createNewEntry();
				System.out.println("SID was not provided, Generating new one: "
						+ entry.sid);

			} else {
				long now = System.currentTimeMillis() / 1000;
				entry = sessions.get(candidate);
				if (entry == null || entry.validUntil<now) {
					entry = createNewEntry();
					System.out.println("SID is invalid, new one is: "
							+ entry.sid);
					sessions.remove(candidate);
				} else {
					entry.validUntil = now + sessionTimeout;
					System.out.println("Valid SID: " + entry.sid);
				}
			}
			permPrams = entry.map;
		}

		/**
		 * Metoda  koja stvara novu instancu razreda SessionMapEntry, kojemu pridruzuje novi SID, novu praznu mapu, te postavlja vrijeme na sad.
		 * @return nova instanca razreda SessionMapEntry
		 */
		private SessionMapEntry createNewEntry() {
			SID = generateSID();
			SessionMapEntry clientSession = new SessionMapEntry(SID,
					sessionTimeout + System.currentTimeMillis() / 1000);
			sessions.put(SID, clientSession);

			RCCookie cookie = new RCCookie("sid", SID, null, address, "/",true);
			outputCookies.add(cookie);

			return clientSession;
		}

		/**
		 * Pomoćna metoda koja služi kao generator SID-a.
		 * Sid je niz od 20 slučajno generiranih slova engleske abecede.
		 * @return novi SID.
		 */
		private String generateSID() {

			StringBuilder SID = new StringBuilder();
			;
			while (SID.length() < 20) {
				int ranNum = sessionRandom.nextInt(25) + 65;
				SID.append(Character.toString((char) ranNum));
			}
			return SID.toString();
		}
		
		
		/**
		 * Metoda koja klijentu vraća odgovarajuću poruku greške na temelju predanog statusa.
		 * @param statusCode status
		 */
		private void sendErrorMessage(int statusCode) {
			try {
				ostream.write(getStatusMessage(statusCode).getBytes(StandardCharsets.UTF_8));
				ostream.write((byte) '\n');
				ostream.write((byte) '\n');
				ostream.flush();
			} catch (IOException e) {
				closeClientSocket();
				System.out.println("Error when writing to socket output stream.");
			}
		}

		
		/**
		 * Metoda koja na temelju predanog statusnog koda vraća poruku koju treba ispisati klijentu.
		 * @param statusCode predani kod pogreške.
		 * @return odgovarajuća poruka.
		 */
		private String getStatusMessage(int statusCode) {
			switch(statusCode) {
				case 400: return "HTTP/1.1 400 Bad Request";
				case 403: return "HTTP/1.1 403 Forbidden";
				case 404: return "HTTP/1.1 404 File Not Found";
			}

			return null;
		}
		


	}}
	
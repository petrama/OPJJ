package hr.fer.zemris.java.hw06.part2;
import java.util.List;
import hr.fer.zemris.java.tecaj_06.rays.GraphicalObject;
import hr.fer.zemris.java.tecaj_06.rays.IRayTracerProducer;
import hr.fer.zemris.java.tecaj_06.rays.IRayTracerResultObserver;
import hr.fer.zemris.java.tecaj_06.rays.LightSource;
import hr.fer.zemris.java.tecaj_06.rays.Point3D;
import hr.fer.zemris.java.tecaj_06.rays.Ray;
import hr.fer.zemris.java.tecaj_06.rays.RayIntersection;
import hr.fer.zemris.java.tecaj_06.rays.RayTracerViewer;
import hr.fer.zemris.java.tecaj_06.rays.Scene;
/**
 * Program koji prikazuje sliku 3D objekta koji je osvjetljen jednim ili više točkastih izvora koristći algoritam bacanja zrake.
 * Korišteni model osvjetljenja je Phongov model koji pretpostavlja da je cjelokupno osvjetljenje linarna kombinacija ambijentne, difuzne i reflektirane komponente.
 * Ambĳentna komponenta rezultat je interakcĳe svjetlosti među svim objektima u sceni.
 * Difuzna glavna je komponenta i proporcionalna je intenzitetu upadne svjetlosti i kosinusu kuta kojeg upadna zraka zatvara s normalom jedinične površine.
 * Reflektirana komponenta funkcija je kuta između reflektirane zrake i zrake prema promatraču.
 * @author Petra Marče
 *
 */
public class RayCaster {
	/**
	 * Metoda koja se poziva pri pokretanju programa.
	 * @param args Argumenti iz komandne linije. Ne koriste se
	 * 
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0),
				new Point3D(0, 0, 0), new Point3D(0, 0, 10), 20, 20);
	}
	/**
	 * Instanca razreda koji je u stanju stvoriti grafički prikaz nekog osvjetljenog objekta koristeći algoritam bacanja zrake.
	 * Temeljna pretpostavka modela bacanja zrake je da se svjetlost širi pravocrtno te on zanemaruje valnu priorodu svjetlosti.
	 * Samo neke zrake svjetlosti se odbiju od nekog objekta u sceni do promatračeva oka.
	 * Put tih zraka rekonstruiramo tako da se pretvaramo da je zraka svjetlosti krenula od promatračeva oka jer je takva zraka uvijek relevantna.
	 * Ispred promatrača postavljamo virtualni pomoćni ekran kroz kojeg puštamo zrake od promatračeva objekta.
	 * Tada gledamo što se s pojedinom zrakom u sceni događa: moguće je da ne siječe nijedan predmet, da siječe jedan ili više njih.
	 * Ako ne siječe nijedan  tom slikovnom elementu tj pikselu kojeg razmatramo dodjelit ćemo ambijentnu komponentu Phongovog modela osvjetljenja.
	 * Ako zraka siječe više predmeta traži se najbliže sjeciše koje promatrač zaista vidi.
	 * Nakon pronalaska sjecišta određuje se boja slikovnog elementa koji se razmatra.
	 * Prvo je potrebno provjeriti nalazi li se sjecište možda u sjeni.
	 * Ako neki drugi predmet blokira svjetlost nekog od izvora doprinos tog izvora se zanemaruje.
	 * Ako je sjecište s obzirom na sve izvore u sjeni intenzitet sjecišta bit će jednak ambijentalnoj komponenti.
	 * @return informacije potrebne grafičkom korisničkom sučelju za crtanje grafičkog prikaza.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			 /**
			  * Metoda koju zove grafičko korisničko sučelje kada su mu potrebne informacije za iscrtavanje slike.
			  * 
			  */
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				// vektor og
				Point3D pointOG = view.sub(eye).normalize();
				// pomocna var skalarbi pprodukt
				Double scalarProduct = viewUp.normalize()
						.scalarProduct(pointOG);
				// y os
				Point3D yAxis = viewUp.normalize()
						.sub(pointOG.scalarMultiply(scalarProduct)).normalize();
				// x os
				Point3D xAxis = pointOG.vectorProduct(yAxis).normalize();

				Point3D screenCorner = view.sub(
						xAxis.scalarMultiply(horizontal / 2)).add(
						yAxis.scalarMultiply(vertical / 2));
				Scene scene = RayTracerViewer.createPredefinedScene();

				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(
								xAxis.scalarMultiply(x / (width - 1.0)
										* horizontal)).sub(
								yAxis.scalarMultiply(y / (height - 1.0)
										* vertical));
						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");

			}
		};
	}
	/**
	 * Pomoćna metoda koja prati jednu zraku u sceni i vraća informacije potrebne za crtanje piksela.
	 * @param scene scena slike.
	 * @param ray upadna zraka svjetlosti.
	 * @param rgb polje u koje se sprema rezultat,
	 */

	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		double[] rgbPom = new double[]{15,15,15};
	
		RayIntersection closestintersec = closestintersec(scene, ray);
		if (closestintersec != null) {
			determineColorFor(scene, ray, rgbPom, closestintersec);
		} else {

			rgbPom = new double[] { 0, 0, 0 };

		}
		rgb[0] = (short) rgbPom[0];
		rgb[1] = (short) rgbPom[1];
		rgb[2] = (short) rgbPom[2];

	}

	/**
	 * Pomoćna metoda koja traži najbliže sjecište zadane zrake s bilo kojim predmetom u sceni.
	 * @param scene scena koja se analizira.
	 * @param ray kokretna upadna zraka.
	 * @return najbliže sjecište.
	 */
	private static RayIntersection closestintersec(Scene scene, Ray ray) {
		List<GraphicalObject> allObjects = scene.getObjects();

		boolean firstFound = true;
		double minimalDistance = Double.MAX_VALUE;
		RayIntersection closestintersec = null;

		for (GraphicalObject object : allObjects) {
			RayIntersection intersect = object.findClosestRayIntersection(ray);
			if (intersect != null) {
				if (firstFound) {
					firstFound = false;
					minimalDistance = intersect.getDistance();
					closestintersec = intersect;
				} else {
					if (intersect.getDistance() < minimalDistance) {
						minimalDistance = intersect.getDistance();
						closestintersec = intersect;
					}
				}
			}
		}
		return closestintersec;

	}
	/**
	 * Pomoćna metoda koja određuje boju za konretno presjecište.
	 * Dodaje difuznu i reflektiranu komponentu.
	 * @param scene scena slike.
	 * @param ray zraka koja siječe neki objekt u sceni.
	 * @param rgb polje u koje se spremaju parametri boje piksela.
	 * @param inter presjecište zrake i objekta.
	 */

	private static void determineColorFor(Scene scene, Ray ray, double[] rgb,
			RayIntersection inter) {

		List<LightSource> allLights = scene.getLights();
		for (LightSource ls : allLights) {
			Ray ray2 = Ray.fromPoints(ls.getPoint(), inter.getPoint());
			RayIntersection closest2 = closestintersec(scene, ray2);

			if (closest2 != null) {
				double lightSourceintersecDistance = ls.getPoint()
						.sub(closest2.getPoint()).norm();
				double eyeintersecDistance = ls.getPoint()
						.sub(inter.getPoint()).norm();

				if (Double.compare(lightSourceintersecDistance+0.01,
						eyeintersecDistance) >= 0) {
			
					getDifuseComponent(ls, closest2, rgb);
					getReflectiveComponent(ls, ray, rgb, closest2);
				}
			}
		}
	}

	/**
	 * Pomoćna metoda koja postojećoj boji za konkretno presjecište i svjetlosni izvor iz scene dodaje difuznu komponentu.
	 * @param light svjetlosni izvor čiji se doprinos dodaje.
	 * @param intersec presjecište zrake i objekta.
	 * @param rgb polje u koje se dodaje komponenta.
	 */

	private static void getDifuseComponent(LightSource light,
			RayIntersection intersec, double[] rgb) {
		//normala na sferu u točki sjecišta
		Point3D normal = intersec.getNormal();
		//vektor od izvora svjetlosti do točke sjecista
		Point3D l = light.getPoint().sub(intersec.getPoint()).normalize();
		//njihoc skalarni je jednak kosinusu kuza između njih jer su jedinicni
		double cosinus = l.scalarProduct(normal);
		//        konst.ovisi o izvoru*konst koja ovisi o sferi*kosinus kuta
		rgb[0] += light.getR() * intersec.getKdr() *  (cosinus>0?cosinus:0);
		rgb[1] += light.getG() * intersec.getKdg() * (cosinus>0?cosinus:0);
		rgb[2] += light.getB() * intersec.getKdb() * (cosinus>0?cosinus:0);
	}

	/**
	 * Pomoćna metoda koja postojećoj boji za konkretno presjecište i svjetlosni izvor iz scene dodaje reflektiranu komponentu.
	 * @param light svjetlosni izvor čiji se doprinos dodaje.
	 * @param intersec presjecište zrake i objekta.
	 * @param rgb polje u koje se dodaje komponenta.
	 */
	private static void getReflectiveComponent(LightSource light, Ray ray,
			double[] rgb, RayIntersection intersec) {
		//normala na sferu u tocki sjecista
		Point3D normal = intersec.getNormal();
		Point3D l = light.getPoint().sub(intersec.getPoint());
		Point3D lProjectionOnN = normal.scalarMultiply(l.scalarProduct(normal));
		Point3D r = lProjectionOnN.add(lProjectionOnN.negate().add(l)
				.scalarMultiply(-1));
		Point3D v = ray.start.sub(intersec.getPoint());
		double cosX = r.normalize().scalarProduct(v.normalize());

		if (Double.compare(cosX, 0) >= 0) {//ako je kosinus nula komponenta je nula
			cosX = Math.pow(cosX, intersec.getKrn());

			rgb[0] += light.getR() * intersec.getKrr() * cosX;
			rgb[1] += light.getG() * intersec.getKrg() * cosX;
			rgb[2] += light.getB() * intersec.getKrb() * cosX;
		}
	}

}

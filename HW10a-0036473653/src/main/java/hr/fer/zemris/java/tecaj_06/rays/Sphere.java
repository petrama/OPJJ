package hr.fer.zemris.java.tecaj_06.rays;

/**
 * Grafički model Sfere.
 * Sferu karakterizira koordinata središta, radijus te karakteristike povrsine materijala od kojeg je napravljena.
 * @author Petra Marče
 *
 */
public class Sphere extends GraphicalObject {
	/** koordinata centra sfere **/
	private Point3D center;
	/** radius sfere **/
	private double radius;
	/** koeficijent o kojem ovisi difuzna komponenta osvjetljenja crvene boje **/
	private final double kdr;
	/** koeficijent o kojem ovisi difuzna komponenta osvjetljenja zelene boje **/
	private final double kdg;
	/** koeficijent o kojem ovisi difuzna komponenta osvjetljenja plave boje **/
	private final double kdb;
	/** koeficijent o kojem ovisi zrcalna komponenta osvjetljenja crvene boje **/
	private final double krr;
	/** koeficijent o kojem ovisi zrcalna komponenta osvjetljenja zelene boje **/
	private final double krg;
	/** koeficijent o kojem ovisi zrcalna komponenta osvjetljenja plave boje **/
	private final double krb;
	/** eksponent kosinusa u reflektiranoj komponenti **/
	private final double krn;

	/**
	 * Stvara novi primjerak grafičkog modela sfere.
	 * @param center koordinate središta sfere.
	 * @param radius radijus sfere.
	 * @param kdr koeficijent difuzne komponente crvene boje.
	 * @param kdg koeficijent difuzne komponente zelene boje.
	 * @param kdb koeficijent difuzne komponente plave boje.
	 * @param krr koeficijent reflektirane komponente crvene boje.
	 * @param krg koeficijent reflektirane komponente zelene boje.
	 * @param krb koeficijent reflektirane komponente plave boje.
	 * @param krn eksponent kosinusa u reflektiranoj komponenti.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * Metoda koja vraća najbliže sjecište sfere i dane zrake u odnosu na početnu točku zrake.
	 * Ako se zraka i sfera ne sijeku ili su oba sjecista negativna metoda vraća null.
	 * Ako su sjecišta negativna to bi značilo da zraka počinje ispred sfere pa semantički nema sjecišta jer se kulga nalazi iza nas.
	 * 
	 */
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {

		Point3D l = ray.direction;// vektor smjera zrake
		Point3D o = ray.start; // origin of line

		double determinant = Math.pow(l.scalarProduct(o.sub(center)), 2)
				- Math.pow(o.sub(center).norm(), 2) + radius * radius;
		// sada je d najmanja udaljenost od oka do točke sjecišta
		// pitanje je samo koja je to točka
		if (determinant < 0) {
			return null;
		}
		double d = 0;
		if (determinant == 0) {// ako imamo jedinstveno rjesenje
			d = -(l.scalarProduct(o.sub(center)));
			if (d < 0) {// ako je ono manje od nule vrati null
				return null;
			}
		} else {// imamo dva rjesenja
			double d1 = -(l.scalarProduct(o.sub(center)))
					+ Math.sqrt(determinant);
			double d2 = -(l.scalarProduct(o.sub(center)))
					- Math.sqrt(determinant);
			if (d1 <= 0 && d2 <= 0) {// ako su oba rjesenja negativna vrati null
				return null;
			}
			if (d1 > 0 && d2 < 0) { // ako je prvi pozitivan a drugi nije
				d = d1;
			} else {
				

					d = d1 < d2 ? d1 : d2;
				}
			}
		

		Point3D intersection = o.add(l.scalarMultiply(d));

		final Point3D ce = center;
		
		
		return new RayIntersection(intersection, d, true) {

			@Override
			public Point3D getNormal() {
				return super.getPoint().sub(ce).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};

	}

}

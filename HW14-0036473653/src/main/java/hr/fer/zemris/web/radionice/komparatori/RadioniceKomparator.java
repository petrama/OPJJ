package hr.fer.zemris.web.radionice.komparatori;

import hr.fer.zemris.web.radionice.Radionica;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
/**
 * Razred koji nudi komparatore za sortiranje radionica potrebne u ovoj web aplikaciji.
 * Radionice se mogu sortirati prema id-u, datumu održavanja i prema nazivu.
 * @author Petra Marče
 *
 */
public class RadioniceKomparator {

	/**
	 * Implementacija komparatora koji sortira radionice po datumu.
	 */
	public static final Comparator<Radionica> PO_DATUMU = new Comparator<Radionica>() {
		@Override
		public int compare(Radionica o1, Radionica o2) {
			Date prvi = new Date();
			Date drugi = new Date();
			try {
				prvi = new SimpleDateFormat("yyyy-MM-dd").parse(o1.getDatum());
				drugi = new SimpleDateFormat("yyyy-MM-dd").parse(o2.getDatum());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (prvi.before(drugi))
				return -1;
			if (drugi.before(prvi))
				return 1;
			return 0;

		};

	};

	/**
	 * Implementacija komparatora koji uspoređuje radionice prema imenu.
	 */
	public static final Comparator<Radionica> PO_IMENU = new Comparator<Radionica>() {
		@Override
		public int compare(Radionica o1, Radionica o2) {
			return o1.getNaziv().compareTo(o2.getNaziv());
		};
	};

	/**
	 * Implementacija komparatora koji uspoređuje radionice prema id-u.
	 */
	public static final Comparator<Radionica> PO_ID = new Comparator<Radionica>() {
		@Override
		public int compare(Radionica o1, Radionica o2) {
			return Long.compare(o1.getId(), o2.getId());
		}
	};
	
	/**
	 * Implementacija komparatora koji uspoređuje radionice prema datumu održavanja i imenu.
	 */

	public static final Comparator<Radionica> DATUM_IME = new Comparator<Radionica>() {
		@Override
		public int compare(Radionica o1, Radionica o2) {
			int rez = PO_DATUMU.compare(o1, o2);
			if (rez != 0) {
				return rez;
			}
			return PO_IMENU.compare(o1, o2);
		}
	};

}

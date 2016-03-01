package hr.fer.zemris.java.sorters;
/**
 * Razred koji omogućuje sortiranje polja pomoću quick-sorta.
 * Višedretveno provođenje algoritma je podržano.
 * Razred  nudi mogućnost definiranja praga veličine posla ispod kojeg se posao obavlja jednodretveno.
 * 
 * @author Petra Marče
 *
 */
public class QSortParallel {
	/**
	 * Prag koji govori koliko elemenata u podpolju minimalno mora biti da bi se
	 * sortiranje nastavilo paralelno; ako elemenata ima manje, algoritam
	 * prelazi na klasično rekurzivno (slijedno) sortiranje.
	 */
	private final static int P_THRESHOLD = 50;
	/**
	 * Prag za prekid rekurzije. Ako elemenata ima više od ovoga, quicksort
	 * nastavlja rekurzivnu dekompoziciju. U suprotnom ostatak sortira
	 * algoritmom umetanja.
	 */
	private final static int CUT_OFF = 5;

	/**
	 * Sučelje prema klijentu: prima polje i vraća se tek kada je polje
	 * sortirano. Primjenjujući gornje pragove najprije posao paralelizira a
	 * kada posao postane dovoljno mali, rješava ga slijedno.
	 * 
	 * @param array
	 *            polje koje treba sortirati
	 */
	public static void sort(int[] array) {
		new QSortJob(array, 0, array.length - 1).run();
	}

	/**
	 * Model posla sortiranja podpolja čiji su elementi na pozicijama koje su
	 * veće ili jednake <code>startIndex</code> i manje ili jednake
	 * <code>endIndex</code>.
	 */
	static class QSortJob implements Runnable {
		private int[] array;
		private int startIndex;
		private int endIndex;

		public QSortJob(int[] array, int startIndex, int endIndex) {
			super();
			this.array = array;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}
		
		

		@Override
		public void run() {
			if(endIndex-startIndex+1 > CUT_OFF) {
				boolean doInParallel = endIndex-startIndex+1 > P_THRESHOLD;
				int i = selectPivot();
				int stozer = array[i];
				int lijevo = startIndex;
				int desno=endIndex-1;
					
					while(true){
						while(array[++lijevo]<stozer);
						while(array[--desno]>stozer);
						if(lijevo<desno){
							swap(array,lijevo,desno);
						}else{
							break;
						}
					}
					i=lijevo;
					swap(array, i, endIndex-1);
					Thread t1 = null;
					if (startIndex < i) {
						QSortJob job = new QSortJob(array, startIndex, i - 1); // lijevi
																				// dio
						t1 = executeJob(doInParallel, job);
					}
					Thread t2 = null;
					if (endIndex > i) {
						QSortJob job = new QSortJob(array, i + 1, endIndex); // desni
																				// dio
						t2 = executeJob(doInParallel, job);
					}
					
					
					if (t1 != null) {
						// ako su t1/t2 pokrenuti, joinaj
						try {
							t1.join();
						} catch (InterruptedException e) {
						}
					}
					if (t2 != null) {
						// ako su t1/t2 pokrenuti, joinaj
						try {
							t2.join();
						} catch (InterruptedException e) {
						}
					}

				} else {
					// Obavi sortiranje umetanjem
					for (int k = startIndex + 1; k <= endIndex; k++) {
						int j = k;
						while (j > startIndex && array[j - 1] > array[j]) {
							swap(array, j, j - 1);
							j -= 1;
						}
					}
				}
			
			}
		
				
		/**
		 * Direktno izvodi zadani posao pozivom run() i tada vraća
		 * <code>null</code> ili pak stvara novu dretvu, njoj daje taj posao i
		 * pokreće je te vraća referencu na stvorenu dretvu (u tom slučaju ne
		 * čeka da posao završi).
		 * 
		 * @param doInParallel
		 *            treba li posao pokrenuti u novoj dretvi
		 * @param job
		 *            posao
		 * @return <code>null</code> ili referencu na pokrenutu dretvu
		 */
		private Thread executeJob(boolean doInParallel, QSortJob job) {
			if(doInParallel){
				Thread t=new Thread(job);
				t.start();
				return t;
			}else{
				job.run();
				return null;}
		}

		/**
		 * Odabir pivota metodom medijan-od-tri u dijelu polja
		 * <code>array</code> koje je ograđeno indeksima <code>startIndex</code>
		 * i <code>endIndex</code> (oba uključena).
		 * 
		 * @return vraća indeks na kojem se nalazi odabrani pivot
		 */
		public  int selectPivot() {
			int middle=(startIndex+endIndex)>>>1;
			if(array[startIndex]>array[middle]){
				swap(array, startIndex, middle);
			}
			if(array[startIndex]>array[endIndex]){
				swap(array, startIndex, endIndex);
			}
			if(array[middle]>array[endIndex]){
				swap(array, middle, endIndex);
			}
			
			swap(array, middle, endIndex-1);
			return endIndex-1;
		}

		/**
		 * U predanom polju <code>array</code> zamjenjuje elemente na pozicijama
		 * <code>i</code> i <code>j</code>.
		 * 
		 * @param array
		 *            polje u kojem treba zamijeniti elemente
		 * @param i
		 *            prvi indeks
		 * @param j
		 *            drugi indeks
		 */
		public static void swap(int[] array, int i, int j) {
		
				int t=array[i];
				array[i]=array[j];
				array[j]=t;
			}
		}
	

	/**
	 * Pomoćna metoda koja provjerava je li predano polje doista sortirano
	 * uzlazno.
	 * 
	 * @param array
	 *            polje
	 * @return <code>true</code> ako je, <code>false</code> inače
	 */
	public static boolean isSorted(int[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			if (array[i] > array[i + 1]) {
				return false;
			}
		}
		return true;
	}
	}

	
	
	

package hr.fer.zemris.java.sorters;

import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class QuickSortTest {

	@Test
	public void testQSortParallel2(){
	
			final int SIZE =600_000;
			Random rand = new Random();
			int[] data = new int[SIZE];
			for (int i = 0; i < data.length; i++) {
				data[i] = rand.nextInt();
			}
			Assert.assertFalse("Array should not be sorted yet!",QSortParallel2.isSorted(data));
			int[] check=data;
			Arrays.sort(check);
			long t0 = System.currentTimeMillis();
			QSortParallel2.sort(data);
			long t1 = System.currentTimeMillis();
			System.out.println("Sortirano: " + QSortParallel2.isSorted(data));
			System.out.println("Vrijeme: " + (t1 - t0) + " ms");
			Assert.assertArrayEquals( check,data);
		
	}
	
	@Test
	public void testQSortParallel2Small(){
	
			final int SIZE =4;
			Random rand = new Random();
			int[] data = new int[SIZE];
			for (int i = 0; i < data.length; i++) {
				data[i] = rand.nextInt();
			}
			int[] check=data;
			Arrays.sort(check);
			long t0 = System.currentTimeMillis();
			QSortParallel2.sort(data);
			long t1 = System.currentTimeMillis();
			System.out.println("Sortirano: " + QSortParallel2.isSorted(data));
			System.out.println("Vrijeme: " + (t1 - t0) + " ms");
			Assert.assertArrayEquals( check,data);
		
	}
}

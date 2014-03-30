
package com.elusivehawk.engine.meta;

/**
 * 
 * Test log:
 * 
 * Simple vs. Array List
 * Testing matrix stuff.
 * Testing sound decoding.
 * Testing file byte reading.
 * Testing "++".
 * Using PrintStream.
 * More buffer testing.
 * List iterating.
 * Buffer.put(int, int) testing.
 * Instanceof speed benchmarking.
 * 
 * @author Elusivehawk
 */
public class BenchmarkTest
{
	public static final int TESTS = 1024;
	
	public static void main(String[] args)
	{
		System.out.println("Beginning bench testing...");
		
		/*long[] simptime = new long[TESTS],
				arrtime = new long[TESTS];
		
		long simpw = 0L;
		long simpb = 0L;
		long arrw = 0L;
		long arrb = 0L;
		
		Timer t = new Timer();
		
		for (int test = 0; test < TESTS; test++)
		{
			SimpleList<Object> simp = SimpleList.newList(32);
			ArrayList<Object> arr = Lists.newArrayListWithCapacity(32);
			
			for (int c = 0; c < TESTS; c++)
			{
				t.start();
				
				simp.add(new Object());
				
				t.stop();
				
				simptime[c] = t.report();
				
			}
			
			for (int c = 0; c < TESTS; c++)
			{
				t.start();
				
				arr.add(new Object());
				
				t.stop();
				
				arrtime[c] = t.report();
				
			}
			
			long finsimp = 0L;
			long finarr = 0L;
			
			for (int c = 0; c < TESTS; c++)
			{
				finsimp += simptime[c];
				finarr += arrtime[c];
				
			}
			
			finsimp /= TESTS;
			finarr /= TESTS;
			
			simpw = test == 0 ? finsimp : Math.max(simpw, finsimp);
			simpb = test == 0 ? finsimp : Math.min(simpb, finsimp);
			
			arrw = test == 0 ? finarr : Math.max(arrw, finarr);
			arrb = test == 0 ? finarr :  Math.min(arrb, finarr);
			
			System.out.println(String.format("Test %s results: SimpleList - %s, ArrayList - %s", test + 1, finsimp, finarr));
			
		}
		
		System.out.println(String.format("SimpleList final results:\tBest - %s\tWorst - %s",  simpb, simpw));
		System.out.println(String.format("ArrayList final results:\tBest - %s\tWorst - %s",  arrb, arrw));
		*/
		System.out.println("Th-th-th-th-That's all, folks!");
		
	}
	
}

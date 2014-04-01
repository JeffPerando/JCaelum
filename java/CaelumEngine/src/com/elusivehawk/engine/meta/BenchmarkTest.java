
package com.elusivehawk.engine.meta;

/**
 * 
 * Test log:
 * <p>
 * Comparing vs. Instanceof check<br>
 * Simple vs. Array List.<br>
 * Testing matrix stuff.<br>
 * Testing sound decoding.<br>
 * Testing file byte reading.<br>
 * Testing "++".<br>
 * Using PrintStream.<br>
 * More buffer testing.<br>
 * List iterating.<br>
 * Buffer.put(int, int) testing.<br>
 * Instanceof speed benchmarking.<br>
 * 
 * @author Elusivehawk
 */
public class BenchmarkTest
{
	public static final int TESTS = 1024;
	
	public static void main(String[] args)
	{
		System.out.println("Beginning bench testing...");
		
		/*long[] iftime = new long[TESTS],
				instoftime = new long[TESTS];
		
		long ifw = 0L;
		long ifb = 0L;
		long instofw = 0L;
		long instofb = 0L;
		
		Timer t = new Timer();
		
		Asset testobj = new TextureStatic(null, null);
		
		for (int test = 0; test < TESTS; test++)
		{
			for (int c = 0; c < TESTS; c++)
			{
				t.start();
				
				if (testobj.type == EnumAssetType.TEXTURE){}
				
				t.stop();
				
				iftime[c] = t.report();
				
			}
			
			for (int c = 0; c < TESTS; c++)
			{
				t.start();
				
				if (testobj instanceof Texture){}
				
				t.stop();
				
				instoftime[c] = t.report();
				
			}
			
			long finif = 0L;
			long fininstof = 0L;
			
			for (int c = 0; c < TESTS; c++)
			{
				finif += iftime[c];
				fininstof += instoftime[c];
				
			}
			
			finif /= TESTS;
			fininstof /= TESTS;
			
			ifw = test == 0 ? finif : Math.max(ifw, finif);
			ifb = test == 0 ? finif : Math.min(ifb, finif);
			
			instofw = test == 0 ? fininstof : Math.max(instofw, fininstof);
			instofb = test == 0 ? fininstof :  Math.min(instofb, fininstof);
			
			System.out.println(String.format("Test %s results: If - %s, Instanceof - %s", test + 1, finif, fininstof));
			
		}
		
		System.out.println(String.format("If final results:\t\tBest - %s\tWorst - %s",  ifb, ifw));
		System.out.println(String.format("Instanceof final results:\tBest - %s\tWorst - %s",  instofb, instofw));
		*/
		System.out.println("Th-th-th-th-That's all, folks!");
		
	}
	
}

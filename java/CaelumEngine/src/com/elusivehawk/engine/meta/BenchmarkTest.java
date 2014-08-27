
package com.elusivehawk.engine.meta;

import com.elusivehawk.util.Logger;

/**
 * 
 * Test log:
 * <p>
 * Timer testing.<br>
 * File filterer.<br>
 * String index testing.<br>
 * Refactor helper.<br>
 * Tokenizer testing.<br>
 * RHINO benchmarking.<br>
 * Comparing vs. Instanceof check.<br>
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
	public static final int TESTS = 10;
	public static final double DIV = 1000000000D;
	
	public static void main(String[] args)
	{
		System.out.println("Beginning bench testing...");
		
		long time;
		
		for (int c = 0; c < TESTS; c++)
		{
			time = System.nanoTime();
			
			try
			{
				Thread.sleep(1000L);
				
			}
			catch (InterruptedException e)
			{
				Logger.log().err(e);
				
			}
			
			time = System.nanoTime() - time;
			
			System.out.println(String.format("Test #%s: %s", c + 1, time / DIV));
			
		}
		
		//System.out.println(String.format("Final result: %s", MathHelper.avg(longs)));
		
		System.out.println("Th-th-th-th-That's all, folks!");
		
	}
	
}

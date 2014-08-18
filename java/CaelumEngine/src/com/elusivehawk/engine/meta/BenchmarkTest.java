
package com.elusivehawk.engine.meta;

import java.util.List;
import com.elusivehawk.util.math.MathHelper;
import com.google.common.collect.Lists;

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
	public static final int TESTS = 256;
	
	public static void main(String[] args)
	{
		System.out.println("Beginning bench testing...");
		
		List<Long> longs = Lists.newArrayListWithCapacity(TESTS);
		
		for (int c = 0; c < TESTS; c++)
		{
			long time = 0L;
			
			time = System.nanoTime();
			
			try
			{
				Thread.sleep(1L);
				
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
				
			}
			
			time = System.nanoTime() - time;
			
			System.out.println(String.format("Test #%s: %s", c + 1, time));
			
			longs.add(time);
			
		}
		
		//System.out.println(String.format("Final result: %s", MathHelper.avg(longs)));
		
		System.out.println("Th-th-th-th-That's all, folks!");
		
	}
	
}


package com.elusivehawk.engine.meta;

import java.util.List;
import com.elusivehawk.engine.util.Tokenizer;

/**
 * 
 * Test log:
 * <p>
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
	public static final int TESTS = 128;
	
	public static void main(String[] args)
	{
		System.out.println("Beginning bench testing...");
		
		try
		{
			Tokenizer t = new Tokenizer();
			
			t.addTokens("ha", "lo");
			
			List<String> split = t.tokenize("Trololololololololololo, hahahahaha");
			
			for (String str : split)
			{
				System.out.println(String.format("Test: \"%s\"", str));
				
			}
			
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			
		}
		
		System.out.println("Th-th-th-th-That's all, folks!");
		
	}
	
}

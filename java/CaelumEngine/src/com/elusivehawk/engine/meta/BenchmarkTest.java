
package com.elusivehawk.engine.meta;

import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.math.MathHelper;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * Test log:
 * <p>
 * MathHelper testing.<br>
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
	
	public static void main(String[] args)
	{
		Logger.log().log(EnumLogType.VERBOSE, "Beginning bench testing...");
		
		Vector one = new Vector(150, 150, 150), two = new Vector(10, 10, 10);
		
		Logger.log().log(EnumLogType.INFO, "Test: %s", MathHelper.distSquared(one, two));
		
		Logger.log().log(EnumLogType.VERBOSE, "Th-th-th-th-That's all, folks!");
		
	}
	
}

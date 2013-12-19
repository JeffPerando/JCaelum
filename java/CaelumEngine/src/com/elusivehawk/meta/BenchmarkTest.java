
package com.elusivehawk.meta;

import com.elusivehawk.engine.core.GameLog;
import com.elusivehawk.engine.math.Matrix;
import com.elusivehawk.engine.math.MatrixHelper;

/**
 * 
 * Test log:
 * 
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
		GameLog.info("Beginning bench testing...");
		
		Matrix trs = MatrixHelper.createRotationMatrix(90, 90, 90).mul(MatrixHelper.createScalingMatrix(90, 90, 90));
		
		GameLog.info(trs.toString());
		
		GameLog.info(MatrixHelper.createRotationMatrix(180, 180, 180).toString());
		
		GameLog.info("Th-th-th-th-That's all, folks!");
		
	}
	
}

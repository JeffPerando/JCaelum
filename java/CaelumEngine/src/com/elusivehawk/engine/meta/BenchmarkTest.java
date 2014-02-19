
package com.elusivehawk.engine.meta;

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
		System.out.println("Beginning bench testing...");
		
		Matrix one = MatrixHelper.createRotationMatrix(90, 90, 90);
		System.out.println(one.toString());
		
		Matrix two = MatrixHelper.createRotationMatrix(90, 90, 90);
		System.out.println(two.toString());
		
		Matrix trs = one.mul(two);
		
		System.out.println(trs.toString());
		
		System.out.println(MatrixHelper.createRotationMatrix(180, 180, 180).toString());
		
		System.out.println("Th-th-th-th-That's all, folks!");
		
	}
	
}

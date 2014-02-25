
package com.elusivehawk.engine.meta;

import com.elusivehawk.engine.math.Matrix;
import com.elusivehawk.engine.math.MatrixHelper;
import com.elusivehawk.engine.math.VectorF;

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
		
		Matrix one = MatrixHelper.createRotationMatrix(new VectorF(90f, 90f, 90f));
		
		Matrix two = MatrixHelper.createRotationMatrix(new VectorF(90f, 90f, 90f));
		
		System.out.println(one.mul(two).toString());
		
		System.out.println(MatrixHelper.createRotationMatrix(new VectorF(180f, 180f, 180f)).toString());
		
		System.out.println("Th-th-th-th-That's all, folks!");
		
	}
	
}

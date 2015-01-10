
package com.elusivehawk.caelum.meta;

import com.elusivehawk.caelum.render.Icon;
import com.elusivehawk.caelum.render.IconGrid;
import com.elusivehawk.util.Logger;

/**
 * 
 * Test log:
 * <p>
 * IconGrid testing.<br>
 * LWJGLDisplayImpl testing.<br>
 * Tokenizer testing again.<br>
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
		Logger.verbose("Beginning bench testing...");
		
		IconGrid grid = new IconGrid(64, 64);
		
		for (int x = 0; x < grid.getWidth(); x++)
		{
			for (int y = 0; y < grid.getHeight(); y++)
			{
				Icon icon = grid.getIcon(x, y);
				
				Logger.info("Icon %sx%s: [%s, %s, %s, %s]", x + 1, y + 1, icon.getX(0), icon.getY(0), icon.getX(3), icon.getY(3));
				
			}
			
		}
		
		Logger.verbose("Th-th-th-th-That's all, folks!");
		
	}
	
}

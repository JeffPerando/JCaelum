
package com.elusivehawk.caelum.meta;

import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JTextField;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.util.Logger;

/**
 * 
 * Test log:
 * <p>
 * Trying to figure out where the natives are located.<br>
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
		
		try
		{
			JFrame win = new JFrame();
			
			win.setName("Native Locator Helper Thingy");
			win.setSize(400, 400);
			win.setLayout(null);
			
			JTextField field = new JTextField();
			
			field.setBounds(50, 50, 200, 20);
			
			field.addActionListener(((event) ->
			{
				String str = field.getText();
				URL url = CaelumEngine.class.getResource(str);
				Logger.info("%s (%s)", url == null ? "null" : url.toString(), str);
				
			}));
			
			win.add(field);
			
			win.setVisible(true);
			
		}
		catch (Throwable e)
		{
			Logger.err(e);
			
		}
		
		Logger.verbose("Th-th-th-th-That's all, folks!");
		
	}
	
}


package com.elusivehawk.engine.meta;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import com.elusivehawk.engine.util.Timer;

/**
 * 
 * Test log:
 * <p>
 * RHINO benchmarking<br>
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
	public static final int TESTS = 128;
	
	public static void main(String[] args)
	{
		System.out.println("Beginning bench testing...");
		
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		
		CompiledScript script = null;
		
		try
		{
			script = ((Compilable)engine).compile("println('Hello World!')");
			
		}
		catch (ScriptException e)
		{
			e.printStackTrace();
			
		}
		
		long[] ctrltime = new long[TESTS],
				jstime = new long[TESTS];
		
		long ctrlw = 0L;
		long ctrlb = 0L;
		long jsw = 0L;
		long jsb = 0L;
		
		Timer t = new Timer();
		
		for (int test = 0; test < TESTS; test++)
		{
			t.start();
			
			System.out.println("Hello World!");
			
			t.stop();
			
			ctrltime[test] = t.report();
			
			t.start();
			
			try
			{
				script.eval();
				
			}
			catch (ScriptException e){}
			
			t.stop();
			
			jstime[test] = t.report();
			
			ctrlw = test == 0 ? ctrltime[test] : Math.max(ctrlw, ctrltime[test]);
			ctrlb = test == 0 ? ctrltime[test] : Math.min(ctrlb, ctrltime[test]);
			
			jsw = test == 0 ? jstime[test] : Math.max(jsw, jstime[test]);
			jsb = test == 0 ? jstime[test] :  Math.min(jsb, jstime[test]);
			
			System.out.println(String.format("Test #%s results: Control - %s, JavaScript - %s", test + 1, ctrltime[test], jstime[test]));
			
		}
		
		long ctrlavg = 0L;
		long jsavg = 0L;
		
		for (int c = 0; c < TESTS; c++)
		{
			ctrlavg += ctrltime[c];
			jsavg += jstime[c];
			
		}
		
		ctrlavg /= TESTS;
		jsavg /= TESTS;
		
		System.out.println(String.format("Control final results:\t\tBest - \t%s\t\tWorst - %s\t\tAvg - %s",  ctrlb, ctrlw, ctrlavg));
		System.out.println(String.format("JavaScript final results:\tBest - \t%s\t\tWorst - %s\tAvg - %s",  jsb, jsw, jsavg));
		
		System.out.println("Th-th-th-th-That's all, folks!");
		
	}
	
}

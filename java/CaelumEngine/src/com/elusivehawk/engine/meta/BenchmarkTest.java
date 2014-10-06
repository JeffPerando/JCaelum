
package com.elusivehawk.engine.meta;

import java.util.List;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.Token;
import com.elusivehawk.util.Tokenizer;
import com.google.common.collect.Lists;

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
		
		Tokenizer t = new Tokenizer();
		
		t.addTokens("if", " ", "\t", "(", ")", "{", "}", ",", ";");
		
		List<String> test = Lists.newArrayList();
		
		test.add("if (condition)");
		test.add("{");
		test.add("\tmethodName(arg0, arg1);");
		test.add("}");
		
		List<Token> tkns = t.tokenize(test);
		
		for (Token tkn : tkns)
		{
			Logger.log().log(EnumLogType.INFO, "Token found: \"%s\"", tkn);
			
		}
		
		Logger.log().log(EnumLogType.VERBOSE, "Th-th-th-th-That's all, folks!");
		
	}
	
}

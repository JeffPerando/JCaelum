
package com.elusivehawk.engine.meta;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import com.elusivehawk.engine.util.FileHelper;
import com.elusivehawk.engine.util.StringHelper;
import com.elusivehawk.engine.util.json.EnumJsonType;
import com.elusivehawk.engine.util.json.JsonKeypair;
import com.elusivehawk.engine.util.json.JsonObject;
import com.elusivehawk.engine.util.json.JsonParseException;
import com.elusivehawk.engine.util.json.JsonParser;
import com.google.common.collect.Lists;

/**
 * 
 * Test log:
 * <p>
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
	public static final int TESTS = 128;
	
	public static void main(String[] args)
	{
		System.out.println("Beginning bench testing...");
		
		if (args.length >= 2)
		{
			File file = FileHelper.createFile(".", args[0]),
					jsonFile = FileHelper.createFile(".", args[1]),
					log = FileHelper.createFile(".", "/log.txt");
			
			if (!FileHelper.isFileReal(file))
			{
				System.out.println(String.format("Not a real file: %s", file));
				
				return;
			}
			
			List<File> files = Lists.newArrayList();
			JsonObject json = null;
			
			FileOutputStream fos = FileHelper.createOutStream(log, true);
			PrintStream logger = new PrintStream(fos);
			
			try
			{
				json = JsonParser.parse(jsonFile);
				
			}
			catch (JsonParseException e)
			{
				e.printStackTrace();
				
				return;
			}
			
			scanForFiles(file, files);
			
			if (files.isEmpty() && file.isFile())
			{
				files.add(file);
				
			}
			
			List<String> strs;
			
			for (File f : files)
			{
				strs = StringHelper.read(f);
				
				if (strs.isEmpty())
				{
					continue;
				}
				
				logger.println(String.format("Editing src file: %s", f));
				
				for (int c = 0; c < strs.size(); c++)
				{
					String str = strs.get(c);
					String old = new String(str);
					
					for (JsonKeypair kp : json)
					{
						if (kp.type != EnumJsonType.STRING)
						{
							continue;
						}
						
						str = str.replace(kp.key, kp.value);
						
					}
					
					if (!str.equals(old))
					{
						logger.println(String.format("===Replacing line %s=== \n%s\n%s\n", c, old.trim(), str.trim()));
						
					}
					
					strs.set(c, str);
					
				}
				
				if (!StringHelper.write(f, strs, false, false))
				{
					System.out.println(String.format("Unable to write to file: %s", f));
					
				}
				
			}
			
			try
			{
				fos.close();
				
			}
			catch (IOException e){}
			
			System.out.println(String.format("Output sent to: %s", log.getAbsolutePath()));
			
		}
		
		System.out.println("Th-th-th-th-That's all, folks!");
		
	}
	
	private static void scanForFiles(File f, List<File> out)
	{
		if (f.isFile())
		{
			return;
		}
		
		File[] fs = f.listFiles();
		
		if (fs == null || fs.length == 0)
		{
			return;
		}
		
		for (File file : fs)
		{
			if (file.isFile())
			{
				out.add(file);
				
			}
			else
			{
				scanForFiles(file, out);
				
			}
			
		}
		
	}
	
}

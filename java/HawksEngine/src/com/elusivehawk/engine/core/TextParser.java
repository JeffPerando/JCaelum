
package com.elusivehawk.engine.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class TextParser
{
	public static final String FILE_SEP = System.getProperty("file.separator");
	
	private TextParser(){}
	
	public static List<String> read(String path)
	{
		return read(createFile(path));
	}
	
	public static List<String> read(File file)
	{
		List<String> text = new ArrayList<String>();
		
		if (!file.exists())
		{
			GameLog.warn("File with path " + file.getAbsolutePath() + " does not exist. It may have been tampered with.");
			
			return text;
		}
		
		if (!file.isFile())
		{
			GameLog.warn("File with path " + file.getAbsolutePath() + " is in fact a directory. It may have been tampered with.");
			
			return text;
		}
		
		if (!file.canRead())
		{
			GameLog.warn("File with path " + file.getAbsolutePath() + " cannot be read. It may have been tampered with.");
			
			return text;
		}
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			for (String line = reader.readLine(); line != null; line = reader.readLine())
			{
				text.add(line);
				
			}
			
			reader.close();
			
		}
		catch (Exception e)
		{
			GameLog.error("Error caught while reading text file: ", e);
			
		}
		
		return text;
	}
	
	public static boolean write(String path, String encoding, boolean append, boolean makeFileIfNotFound, String... text)
	{
		return write(createFile(path), encoding, append, makeFileIfNotFound, text);
	}
	
	public static boolean write(String path, List<String> text, String encoding, boolean append, boolean makeFileIfNotFound)
	{
		return write(createFile(path), text, encoding, append, makeFileIfNotFound);
	}
	
	public static boolean write(File file, String encoding, boolean append, boolean makeFileIfNotFound, String... text)
	{
		return write(file, Arrays.asList(text), encoding, append, makeFileIfNotFound);
	}
	
	public static boolean write(File file, List<String> text, String encoding, boolean append, boolean makeFileIfNotFound)
	{
		if (!file.exists() && makeFileIfNotFound)
		{
			try
			{
				file.createNewFile();
				
			}
			catch (Exception e)
			{
				GameLog.error(e);
				
				return false;
			}
			
		}
		
		if (!file.canWrite())
		{
			GameLog.warn("File with path " + file.getPath() + " cannot be written to! This is a bug!");
			
			return false;
		}
		
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
			
			for (String line : text)
			{
				writer.write(line);
				writer.newLine();
				
			}
			
			writer.close();
			
			return true;
		}
		catch (Exception e)
		{
			GameLog.error(e);
			
		}
		
		return false;
	}
	
	public static String removeLast(String str, String textToRemove, boolean trim)
	{
		if (!str.contains(textToRemove))
		{
			GameLog.warn("Failed to remove last instance of " + textToRemove + " from " + str + "; Trimming is set to " + trim + ".");
			
			return str;
		}
		
		StringBuilder b = new StringBuilder(str);
		b.replace(str.lastIndexOf(textToRemove), str.lastIndexOf(textToRemove) + textToRemove.length(), "");
		
		return trim ? b.toString().trim() : b.toString();
	}
	
	public static String concat(String separator, String endWith, String d, String... strs)
	{
		return concat(Arrays.asList(strs), separator, endWith, d);
	}
	
	public static String concat(List<String> strs, String separator, String endWith, String d)
	{
		if (strs == null || strs.size() == 0)
		{
			return d;
		}
		
		StringBuilder b = new StringBuilder();
		
		for (int c = 0; c < strs.size(); ++c)
		{
			String str = strs.get(c);
			
			if (c + 1 == strs.size())
			{
				b.append(str + endWith);
				
			}
			else
			{
				b.append(str + separator);
				
			}
			
		}
		
		return b.toString();
	}
	
	public static String parseArguments(Method m, boolean includeClassNames, boolean bracket)
	{
		StringBuilder ret = new StringBuilder();
		
		try
		{
			Class<?>[] argsC = m.getParameterTypes();
			
			if (bracket) ret.append("(");
			
			if (argsC.length > 0)
			{
				for (int c = 0; c < argsC.length; c++)
				{
					if (includeClassNames)
					{
						ret.append(argsC[c].getSimpleName());
						ret.append(" ");
						
					}
					
					ret.append("arg" + c);
					
					if (c != (argsC.length - 1))
					{
						ret.append(", ");
						
					}
					
				}
				
			}
			
			if (bracket) ret.append(")");
			
		}
		catch (Exception e)
		{
			GameLog.error(e);
			
		}
		
		return ret.toString();
	}
	
	public static File createFile(String path)
	{
		return new File(path.replace("/", FILE_SEP));
	}
	
	public static File createFile(String src, String path)
	{
		return new File(src.replace("/", FILE_SEP), path.replace("/", FILE_SEP));
	}
	
}

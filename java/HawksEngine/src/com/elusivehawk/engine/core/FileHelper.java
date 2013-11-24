
package com.elusivehawk.engine.core;

import java.io.File;
import java.io.FileInputStream;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class FileHelper
{
	public static final String FILE_SEP = System.getProperty("file.separator");
	
	public static File createFile(String path)
	{
		return new File(path.replace("/", FILE_SEP));
	}
	
	public static File createFile(String src, String path)
	{
		return new File(src.replace("/", FILE_SEP), path.replace("/", FILE_SEP));
	}
	
	public static FileInputStream createStream(File file)
	{
		if (file == null)
		{
			return null;
		}
		
		if (!file.exists())
		{
			return null;
		}
		
		if (file.isDirectory())
		{
			return null;
		}
		
		if (!file.canRead())
		{
			return null;
		}
		
		FileInputStream ret = null;
		
		try
		{
			ret = new FileInputStream(file);
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
}

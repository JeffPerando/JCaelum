
package com.elusivehawk.engine.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 
 * Helper class for handling {@link File}s.
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
	
	public static FileInputStream createInStream(File file)
	{
		if (file == null)
		{
			return null;
		}
		
		if (file.isDirectory())
		{
			return null;
		}
		
		if (!file.exists())
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
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		return ret;
	}
	
	public static FileOutputStream createOutStream(File file, boolean create)
	{
		if (file == null)
		{
			return null;
		}
		
		if (file.isDirectory())
		{
			return null;
		}
		
		if (!file.exists() && create)
		{
			try
			{
				if (!file.createNewFile())
				{
					return null;
				}
				
			}
			catch (Exception e)
			{
				return null;
			}
			
		}
		
		if (!file.canWrite())
		{
			return null;
		}
		
		FileOutputStream ret = null;
		
		try
		{
			ret = new FileOutputStream(file);
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
}

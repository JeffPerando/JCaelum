
package com.elusivehawk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import com.google.common.collect.Lists;

/**
 * 
 * Helper class for handling {@link File}s.
 * 
 * @author Elusivehawk
 */
public final class FileHelper
{
	public static final String FILE_SEP = System.getProperty("file.separator");
	
	private FileHelper(){}
	
	public static File createFile(String path)
	{
		return new File(fixPath(path));
	}
	
	public static File createFile(String src, String path)
	{
		return new File(fixPath(src), fixPath(path));
	}
	
	public static FileInputStream createInStream(File file)
	{
		if (!canReadFile(file))
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
	
	public static FileReader createReader(File file)
	{
		if (!canReadFile(file))
		{
			return null;
		}
		
		FileReader ret = null;
		
		try
		{
			ret = new FileReader(file);
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
	public static FileOutputStream createOutStream(File file, boolean create)
	{
		if (file == null)
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
		
		if (!file.isFile())
		{
			return null;
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
	
	public static FileWriter createWriter(File file, boolean create)
	{
		return createWriter(file, create, false);
	}
	
	public static FileWriter createWriter(File file, boolean create, boolean append)
	{
		if (file == null)
		{
			return null;
		}
		
		if (!file.isFile())
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
		
		FileWriter ret = null;
		
		try
		{
			ret = new FileWriter(file, append);
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
	public static boolean isFileReal(File file)
	{
		return file != null && file.exists();
	}
	
	public static boolean canReadFile(File file)
	{
		return isFileReal(file) && file.isFile() && file.canRead();
	}
	
	public static String fixPath(String path)
	{
		return path.replace("/", FILE_SEP);
	}
	
	public static List<File> getFiles(File file)
	{
		List<File> ret = Lists.newArrayList();
		
		if (!isFileReal(file))
		{
			return ret;
		}
		
		if (!file.isDirectory())
		{
			return ret;
		}
		
		getFiles0(file, ret);
		
		return ret;
	}
	
	private static void getFiles0(File file, List<File> filelist)
	{
		File[] files = file.listFiles();
		
		if (files == null || files.length == 0)
		{
			return;
		}
		
		for (File f : files)
		{
			if (f.isDirectory())
			{
				getFiles0(f, filelist);
				
			}
			else
			{
				filelist.add(f);
				
			}
			
		}
		
	}
	
	public static String getExtensionlessName(File file)
	{
		String name = file.getName();
		int ind = name.indexOf(".");
		
		if (ind == -1)
		{
			return name;
		}
		
		return StringHelper.splitLast(name, ".").one;
	}
	
	public static File getChild(String name, File folder)
	{
		return getChild(name, getFiles(folder));
	}
	
	public static File getChild(String name, List<File> files)
	{
		if (files == null || files.isEmpty())
		{
			return null;
		}
		
		for (File file : files)
		{
			if (file.getPath().endsWith(name))
			{
				return file;
			}
			
		}
		
		return null;
	}
	
}


package com.elusivehawk.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
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
	public static final FileFilter NATIVE_FILTER = ((file) ->
	{
		String ext = getExtension(file).toLowerCase();
		
		switch (ext)
		{
			case "dll":
			case "so":
			case "jnilib":
			case "dylib": return true;
			default: return file.isDirectory();
		}
		
	});
	
	private FileHelper(){}
	
	public static File createFile(String path)
	{
		return new File(fixPath(path));
	}
	
	public static File createFile(String src, String path)
	{
		return new File(fixPath(src), fixPath(path));
	}
	
	public static File getRootResDir()
	{
		try
		{
			String urlpath = FileHelper.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			
			if (urlpath != null)
			{
				return new File(urlpath);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		
		return null;
	}
	
	public static File getResource(String path)
	{
		File root = getRootResDir();
		
		if (root != null)
		{
			return new File(root, path);
		}
		
		return null;
	}
	
	public static InputStream getResourceStream(String path)
	{
		return FileHelper.class.getResourceAsStream(path);
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
	
	public static String makePathGeneric(String path)
	{
		return path.replace(FILE_SEP, "/");
	}
	
	public static List<File> getFiles(File file)
	{
		return getFiles(file, null);
	}
	
	public static List<File> getFiles(File file, FileFilter f)
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
		
		getFiles0(file, f, ret);
		
		return ret;
	}
	
	private static void getFiles0(File file, FileFilter filter, List<File> filelist)
	{
		File[] files = file.listFiles(filter);
		
		if (files == null || files.length == 0)
		{
			return;
		}
		
		for (File f : files)
		{
			if (f.isDirectory())
			{
				getFiles0(f, filter, filelist);
				
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
	
	public static String getExtension(File file)
	{
		if (file.isDirectory())
		{
			return "";
		}
		
		return StringHelper.splitLast(file.getName(), ".").two;
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
	
	public static File asTemp(String path)
	{
		return asTemp(path, null);
	}
	
	public static File asTemp(String path, FileFilter filter)
	{
		File file = null;
		
		if (path == null || path.length() == 0)
		{
			file = getRootResDir();
			
		}
		else
		{
			file = getResource(path);
			
		}
		
		if (file.isDirectory())
		{
			List<File> files = getFiles(file, filter);
			
			for (File f : files)
			{
				asTemp0(f);
				
			}
			
			return CompInfo.TMP_DIR;
		}
		
		return asTemp0(file);
	}
	
	private static File asTemp0(File file)
	{
		assert file.isFile();
		
		try
		{
			FileInputStream in = createInStream(file);
			File tmp = File.createTempFile(file.getName(), null);
			FileOutputStream out = createOutStream(tmp, true);
			
			byte[] buf = new byte[1024];
			int read = -1;
			
			while ((read = in.read(buf)) != -1)
			{
				out.write(buf, 0, read);
				
			}
			
			in.close();
			out.close();
			
			return tmp;
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			
		}
		
		return null;
	}
	
	public static void loadNatives(String path)
	{
		System.loadLibrary(asTemp(path, NATIVE_FILTER).getAbsolutePath());
		
	}
	
}

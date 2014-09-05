
package com.elusivehawk.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.elusivehawk.util.storage.SemiFinalStorage;
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
		String ext = getExtension(file);
		
		if (ext == null)
		{
			return false;
		}
		
		ext = ext.toLowerCase();
		
		switch (ext)
		{
			case "dll":
			case "so":
			case "jnilib":
			case "dylib": return true;
			default: return false;
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
	
	public static File createFile(File src, String path)
	{
		return new File(src, fixPath(path));
	}
	
	public static List<File> createFiles(List<URL> urls)
	{
		List<File> ret = Lists.newArrayListWithExpectedSize(urls.size());
		
		if (urls.isEmpty())
		{
			return ret;
		}
		
		for (URL url : urls)
		{
			URI uri = null;
			
			try
			{
				uri = url.toURI();
				
			}
			catch (Exception e)
			{
				Logger.log().err(e);
				
			}
			
			if (uri == null)
			{
				continue;
			}
			
			ret.add(new File(uri));
			
		}
		
		return ret;
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
		if (!canRead(file))
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
		if (!canRead(file))
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
	
	public static boolean isReal(File file)
	{
		return file != null && file.exists();
	}
	
	public static boolean canRead(File file)
	{
		return isReal(file) && file.isFile() && file.canRead();
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
	
	public static List<File> getFiles(File file, FileFilter filter)
	{
		List<File> ret = Lists.newArrayList();
		
		if (!isReal(file))
		{
			return ret;
		}
		
		if (!file.isDirectory())
		{
			return ret;
		}
		
		scanForFiles(file, ((f) ->
		{
			if (filter.accept(f))
			{
				ret.add(f);
				
			}
			
			return true;
		}));
		
		return ret;
	}
	
	public static String getExtensionlessName(File file)
	{
		return StringHelper.getPrefix(file.getName(), ".");
	}
	
	public static String getExtension(File file)
	{
		String ret = StringHelper.getSuffix(file.getName(), ".");
		
		if (ret == null)
		{
			return null;
		}
		
		return ret.toLowerCase();
	}
	
	public static File getChild(String name, File folder)
	{
		return getChild(name, folder, false);
	}
	
	public static File getChild(String name, File folder, boolean goDeep)
	{
		SemiFinalStorage<File> ret = SemiFinalStorage.create(null);
		
		scanForFiles(folder, goDeep, ((file) ->
		{
			if (file.getName().equals(name))
			{
				ret.set(file);
				return false;
			}
			
			return true;
		}));
		
		return ret.get();
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
	
	public static boolean copy(File file, File dest)
	{
		assert canRead(file);
		
		try
		{
			FileInputStream in = createInStream(file);
			
			File dst;
			
			if (!dest.exists())
			{
				if (dest.isDirectory())
				{
					if (!dest.mkdirs())
					{
						return false;
					}
					
				}
				else
				{
					if (!dest.createNewFile())
					{
						return false;
					}
					
				}
				
			}
			
			if (dest.isDirectory())
			{
				dst = new File(dest, file.getName());
				
			}
			else
			{
				dst = dest;
				
			}
			
			FileOutputStream out = createOutStream(dst, true);
			
			byte[] buf = new byte[1024];
			int read = -1;
			
			while ((read = in.read(buf)) > 0)
			{
				out.write(buf, 0, read);
				
			}
			
			in.close();
			out.close();
			
			return true;
		}
		catch (Throwable e)
		{
			Logger.log().err(e);
			
		}
		
		return false;
	}
	
	public static void scanForFiles(File folder, IFileScanner sc)
	{
		scanForFiles(folder, true, sc);
		
	}
	
	public static void scanForFiles(File file, boolean recursive, IFileScanner sc)
	{
		assert isReal(file);
		
		File[] files = null;
		
		if (file.isDirectory())
		{
			files = file.listFiles();
			
		}
		else
		{
			List<URL> zipFiles = readZip(file);
			
			if (!zipFiles.isEmpty())
			{
				files = new File[zipFiles.size()];
				
				for (int c = 0; c < zipFiles.size(); c++)
				{
					try
					{
						files[c] = new File(zipFiles.get(c).toURI());
						
					}
					catch (Exception e)
					{
						Logger.log().err(e);
						
					}
					
				}
				
			}
			
		}
		
		if (files == null || files.length == 0)
		{
			return;
		}
		
		for (File f : files)
		{
			if (f.isDirectory() && recursive)
			{
				scanForFiles(f, true, sc);
				
			}
			
			if (!sc.scan(f))
			{
				return;
			}
			
		}
		
	}
	
	public static List<File> readZipFiles(File file)
	{
		return createFiles(readZip(file));
	}
	
	public static List<URL> readZip(File file)
	{
		List<URL> ret = Lists.newArrayList();
		
		if (!isReal(file))
		{
			return ret;
		}
		
		if (file.isDirectory())
		{
			return ret;
		}
		
		String ext = getExtension(file);
		
		ZipFile zip = null;
		
		try
		{
			if ("zip".equals(ext))
			{
				zip = new ZipFile(file);
				
			}
			else if ("jar".equals(ext))
			{
				zip = new JarFile(file);
				
			}
			
		}
		catch (Exception e)
		{
			Logger.log().err(e);
			
		}
		
		if (zip != null)
		{
			Enumeration<? extends ZipEntry> entries = zip.entries();
			ZipEntry entry;
			
			while (entries.hasMoreElements())
			{
				entry = entries.nextElement();
				
				try
				{
					ret.add(new URL(String.format("jar:file:%s!/%s", zip.getName(), entry.getName())));
					
				}
				catch (Exception e){}//Ignore this, because this will never throw an error.
				
			}
			
			try
			{
				zip.close();
				
			}
			catch (IOException e)
			{
				Logger.log().err(e);
				
			}
			
		}
		
		return ret;
	}
	
	@FunctionalInterface
	public static interface IFileScanner
	{
		public boolean scan(File file);
		
	}
	
}

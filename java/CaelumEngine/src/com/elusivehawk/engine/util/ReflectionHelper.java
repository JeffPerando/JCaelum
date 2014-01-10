
package com.elusivehawk.engine.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 
 * Class for simplifying reflection usage.
 * 
 * @author Elusivehawk
 */
public final class ReflectionHelper
{
	private ReflectionHelper(){}
	
	public static Object getField(Class<?> c, String field, Object target)
	{
		Object ret = null;
		
		try
		{
			ret = c.getDeclaredField(field).get(target);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		
		return ret;
	}
	
	public static Object invokeMethod(Class<?> c, String method, Object target, Object... args)
	{
		try
		{
			for (Method m : c.getMethods())
			{
				if (m.getName() == method)
				{
					Class<?>[] argsC = m.getParameterTypes();
					
					if (argsC != null && args != null)
					{
						if (argsC.length != args.length)
						{
							continue;
						}
						
						boolean invoke = true;
						
						for (int count = 0; count < args.length; count++)
						{
							if (!argsC[count].isInstance(args[count]))
							{
								invoke = false;
								break;
							}
							
						}
						
						if (invoke)
						{
							if (!m.isAccessible())
							{
								m.setAccessible(true);
								
							}
							
							return m.invoke(target, args);
						}
						
					}
					else if (args == null)
					{
						continue;
					}
					
				}
				
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		
		return null;
	}
	
	public static Object invokeConstructor(Class<?> c, Object... args)
	{
		for (Constructor<?> con : c.getDeclaredConstructors())
		{
			try
			{
				Class<?>[] req = con.getParameterTypes();
				
				if (args != null && req != null)
				{
					boolean flag = true;
					
					for (int count = 0; count < args.length; count++)
					{
						if (!req[count].isInstance(args[count]))
						{
							flag = false;
							break;
						}
						
					}
					
					if (flag)
					{
						return con.newInstance(args);
					}
					
				}
				else if (args == null && req == null)
				{
					return con.newInstance((Object[])null);
				}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				
			}
			
		}
		
		return null;
	}
	
	public static Object newInstance(String clazz)
	{
		return newInstance(clazz, (Class<?>[])null, null);
	}
	
	public static Object newInstance(String clazz, Class<?>[] assign, Class<? extends Annotation>[] annot)
	{
		Class<?> cl = null;
		
		try
		{
			cl = Class.forName(clazz);
			
		}
		catch (Exception e){}
		
		if (cl == null)
		{
			return null;
		}
		
		return newInstance(cl, assign, annot);
	}
	
	public static Object newInstance(Class<?> clazz, Class<?>[] assign, Class<? extends Annotation>[] annot)
	{
		if (clazz != null)
		{
			if (assign != null)
			{
				for (Class<?> a : assign)
				{
					if (!clazz.isAssignableFrom(a))
					{
						return null;
					}
					
				}
				
			}
			
			if (annot != null)
			{
				for (Class<? extends Annotation> anno : annot)
				{
					if (!clazz.isAnnotationPresent(anno))
					{
						return null;
					}
					
				}
				
			}
			
			Object ret = null;
			
			try
			{
				ret = clazz.newInstance();
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				
			}
			
			return ret;
		}
		
		return null;
	}
	
	public static Tuple<ClassLoader, Set<Class<?>>> loadLibrary(File file)
	{
		Tuple<ClassLoader, Set<Class<?>>> ret = new Tuple<ClassLoader, Set<Class<?>>>(null, null);
		
		try
		{
			ZipFile zip = file.getName().endsWith(".jar") ? new JarFile(file) : new ZipFile(file);
			
			if (zip.size() == 0)
			{
				System.out.println("No entries found in: " + file.getAbsolutePath());
				
				zip.close();
				
				return ret;
			}
			
			Enumeration<? extends ZipEntry> entries = zip.entries();
			List<URL> urls = new ArrayList<URL>(); 
			List<String> resources = new ArrayList<String>();
			
			while (entries.hasMoreElements())
			{
				ZipEntry entry = entries.nextElement();
				
				if (entry.isDirectory())
				{
					continue;
				}
				
				URL url = new URL("jar:file:" + zip.getName() + "!/" + entry.getName());
				
				if (entry.getName().endsWith(".class"))
				{
					urls.add(url);
					resources.add(entry.getName().replaceAll("/", ".").replaceAll(".class", ""));
					
				}
				
			}
			
			zip.close();
			
			if (urls.isEmpty())
			{
				return ret;
			}
			
			URLClassLoader loader = URLClassLoader.newInstance(urls.toArray(new URL[urls.size()]));
			Set<Class<?>> set = new TreeSet<Class<?>>();
			
			for (String res : resources)
			{
				Class<?> cl = loader.loadClass(res);
				
				if (cl != null)
				{
					set.add(cl);
					
				}
				
			}
			
			ret.one = loader;
			ret.two = set;
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		
		return ret;
	}
	
}

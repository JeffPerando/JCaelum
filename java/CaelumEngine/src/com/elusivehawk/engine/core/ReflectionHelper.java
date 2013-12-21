
package com.elusivehawk.engine.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

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
		if (clazz == null)
		{
			return null;
		}
		
		Class<?> c = null;
		
		try
		{
			c = Class.forName(clazz);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		
		if (c != null)
		{
			if (assign != null)
			{
				for (Class<?> a : assign)
				{
					if (!c.isAssignableFrom(a))
					{
						return null;
					}
					
				}
				
			}
			
			if (annot != null)
			{
				for (Class<? extends Annotation> anno : annot)
				{
					if (!c.isAnnotationPresent(anno))
					{
						return null;
					}
					
				}
				
			}
			
			Object ret = null;
			
			try
			{
				ret = c.newInstance();
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				
			}
			
			return ret;
		}
		
		return null;
	}
	
}

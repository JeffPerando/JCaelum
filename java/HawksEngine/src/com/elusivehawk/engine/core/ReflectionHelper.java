
package com.elusivehawk.engine.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 
 * 
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
			GameLog.error(e);
			
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
			GameLog.error(e);
			
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
				GameLog.error(e);
				
			}
			
		}
		
		return null;
	}
	
}

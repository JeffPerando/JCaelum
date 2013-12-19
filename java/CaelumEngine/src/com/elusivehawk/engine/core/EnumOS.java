
package com.elusivehawk.engine.core;

/**
 * 
 * Convnience enum for determining the current system OS.
 * 
 * @author Elusivehawk
 */
public enum EnumOS
{
	WINDOWS, MACOSX, LINUX, SOLARIS, OTHER;
	
	public static final EnumOS OS = getCurrentOS();
	
	private static EnumOS getCurrentOS()
	{
		String os = System.getProperty("os.name").toLowerCase();
		
		for (EnumOS potenOS : values())
		{
			if (os.contains(potenOS.toString()))
			{
				return potenOS;
			}
			
		}
		
		return OTHER;
	}
	
	@Override
	public String toString()
	{
		return name().toLowerCase();
	}
	
}

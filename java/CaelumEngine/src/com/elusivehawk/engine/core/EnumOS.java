
package com.elusivehawk.engine.core;

/**
 * 
 * Convenience enum for determining the current system OS.
 * 
 * @author Elusivehawk
 */
public enum EnumOS
{
	WINDOWS, MACOSX, LINUX, SOLARIS, ANDROID, OTHER;
	
	private static final EnumOS CURR_OS = determineOS();
	
	private static EnumOS determineOS()
	{
		if (System.getProperty("java.vendor") == "The Android Project")
		{
			return ANDROID;//I feel terrible for this...
		}
		
		String os = System.getProperty("os.name").toLowerCase();
		
		for (EnumOS potenOS : values())
		{
			if (os.startsWith(potenOS.toString()))
			{
				return potenOS;
			}
			
		}
		
		return OTHER;
	}
	
	public static EnumOS getCurrentOS()
	{
		return CURR_OS;
	}
	
	@Override
	public String toString()
	{
		return name().toLowerCase();
	}
	
}

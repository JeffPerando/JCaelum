
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
	
	public static final EnumOS OS = getCurrentOS();
	
	private static EnumOS getCurrentOS()
	{
		if (System.getProperty("java.vendor") == "The Android Project")
		{
			return ANDROID;//I feel terrible for this...
		}
		
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


package com.elusivehawk.engine.core;

/**
 * 
 * Convenience enum for determining the current system OS.
 * 
 * @author Elusivehawk
 */
public enum EnumOS
{
	WINDOWS, MAC, LINUX, SOLARIS, ANDROID, OTHER;
	
	private static final EnumOS CURRENT_OS = determineOS();
	
	private static EnumOS determineOS()
	{
		if ("The Android Project".equalsIgnoreCase(System.getProperty("java.vendor")))
		{
			return ANDROID;//Still feel rather crummy for this...
		}
		
		String os = System.getProperty("os.name").toLowerCase();
		
		for (EnumOS potenOS : values())
		{
			if (os.startsWith(potenOS.toString()))
			{
				return potenOS;
			}
			
		}
		
		System.err.println(String.format("Unknown OS: %s", os));
		
		return OTHER;
	}
	
	public static EnumOS getCurrentOS()
	{
		return CURRENT_OS;
	}
	
	@Override
	public String toString()
	{
		return this.name().toLowerCase();
	}
	
}

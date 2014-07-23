
package com.elusivehawk.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Version
{
	public static final String ALPHA = "Alpha",
			BETA = "Beta",
			RC = "RC",
			RELEASE = "Release";
	
	public final String release;
	public final int major, minor, patch, build;
	
	public final String formatted;
	
	public Version(int mj, int mn, int p)
	{
		this(RELEASE, mj, mn, p);
		
	}
	
	public Version(String type, int mj, int mn, int p)
	{
		this(type, mj, mn, p, 0);
		
	}
	
	public Version(int mj, int mn, int p, int b)
	{
		this(RELEASE, mj, mn, p, b);
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Version(String type, int mj, int mn, int p, int b)
	{
		release = type;
		major = mj;
		minor = mn;
		patch = p;
		build = b;
		
		formatted = String.format("%s v%s.%s.%s.%s", this.release, this.major, this.minor, this.patch, this.build);
		
	}
	
	@Override
	public String toString()
	{
		return this.formatted;
	}
	
}

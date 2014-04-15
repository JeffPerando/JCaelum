
package com.elusivehawk.engine.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Version
{
	public final int major, minor, patch, build;
	
	public Version(int mj, int mn)
	{
		this(mj, mn, 0);
		
	}
	
	public Version(int mj, int mn, int p)
	{
		this(mj, mn, p, 0);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Version(int mj, int mn, int p, int b)
	{
		major = mj;
		minor = mn;
		patch = p;
		build = b;
		
	}
	
	@Override
	public String toString()
	{
		return String.format("%s.%s.%s.%s", this.major, this.minor, this.patch, this.build);
	}
	
}


package com.elusivehawk.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class Logger
{
	private static final Logger INSTANCE = new Logger();
	
	private ILog log = new DefaultLog();
	
	private Logger(){}
	
	public static ILog log()
	{
		return INSTANCE.log;
	}
	
	public static synchronized void setLog(ILog l)
	{
		assert l != null;
		
		INSTANCE.log = l;
		
	}
	
}

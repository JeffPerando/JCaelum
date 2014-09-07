
package com.elusivehawk.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ILog
{
	public void log(EnumLogType type, String msg);
	
	public boolean enableVerbosity();
	
	public void setEnableVerbosity(boolean v);
	
	//XXX Default methods
	
	default void debug(String msg, Object... info)
	{
		this.log(EnumLogType.DEBUG, msg, info);
		
	}
	
	default void err(Throwable e)
	{
		this.err(null, e);
		
	}
	
	default void err(String msg, Throwable e, Object... info)
	{
		this.log(EnumLogType.ERROR, msg, info);
		e.printStackTrace();
		
	}
	
	default void info(String msg, Object... info)
	{
		this.log(EnumLogType.INFO, msg, info);
		
	}
	
	default void verbose(String msg, Object... info)
	{
		this.log(EnumLogType.VERBOSE, msg, info);
		
	}
	
	default void warn(String msg, Object... info)
	{
		this.log(EnumLogType.WARN, msg, info);
		
	}
	
	default void wtf(String msg, Object... info)
	{
		this.log(EnumLogType.WTF, msg, info);
		
	}
	
	default void log(EnumLogType type, String msg, Object... info)
	{
		this.log(type, msg == null || info == null ? msg : String.format(msg, info));
		
	}
	
}

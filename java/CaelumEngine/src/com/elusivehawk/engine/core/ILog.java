
package com.elusivehawk.engine.core;

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
	
	default void log(EnumLogType type, String msg, Object... info)
	{
		this.log(type, String.format(msg, info));
		
	}
	
	default void log(EnumLogType type, String msg, Throwable e)
	{
		this.log(type, msg);
		e.printStackTrace();
		
	}
	
}

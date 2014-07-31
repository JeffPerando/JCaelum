
package com.elusivehawk.engine;

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
	
	default void err(String msg, Throwable e, Object... info)
	{
		this.log(EnumLogType.ERROR, msg, info);
		e.printStackTrace();
		
	}
	
}

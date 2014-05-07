
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
	
	public void log(EnumLogType type, String msg, Throwable e);
	
	default void log(EnumLogType type, Iterable<String> str)
	{
		str.forEach((msg) ->
		{
			this.log(type, msg);
			
		});
		
	}
	
	public boolean enableVerbosity();
	
	public void setEnableVerbosity(boolean v);
	
}

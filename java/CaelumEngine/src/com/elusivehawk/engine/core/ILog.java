
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
	
}

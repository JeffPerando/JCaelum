
package com.elusivehawk.engine.util.json;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class JsonParseException extends RuntimeException
{
	private static final long serialVersionUID = -4595551040088007378L;
	
	public JsonParseException()
	{
		super();
		
	}
	
	public JsonParseException(String msg)
	{
		super(msg);
		
	}
	
	public JsonParseException(String msg, Object... objs)
	{
		this(String.format(msg, objs));
		
	}
	
	public JsonParseException(Throwable e)
	{
		super(e);
		
	}
	
	public JsonParseException(String msg, Throwable e)
	{
		super(msg, e);
		
	}
	
	public JsonParseException(String msg, Throwable e, boolean arg2, boolean arg3)
	{
		super(msg, e, arg2, arg3);
		
	}
	
}

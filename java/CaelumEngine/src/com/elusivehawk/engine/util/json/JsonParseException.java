
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
	
	public JsonParseException(String err)
	{
		super(err);
		
	}
	
	public JsonParseException(String err, Object... objs)
	{
		this(String.format(err, objs));
		
	}
	
	public JsonParseException(Throwable e)
	{
		super(e);
		
	}
	
	public JsonParseException(String err, Throwable e)
	{
		super(err, e);
		
	}
	
	public JsonParseException(String err, Throwable e, boolean arg2, boolean arg3)
	{
		super(err, e, arg2, arg3);
		
	}
	
}

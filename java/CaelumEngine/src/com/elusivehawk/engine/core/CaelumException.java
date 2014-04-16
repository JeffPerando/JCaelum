
package com.elusivehawk.engine.core;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class CaelumException extends RuntimeException
{
	private static final long serialVersionUID = 2203750387405676174L;
	
	public CaelumException()
	{
		super();
		
	}
	
	public CaelumException(String err)
	{
		super(err);
		
	}
	
	public CaelumException(String err, Object... args)
	{
		this(String.format(err, args));
		
	}
	
	public CaelumException(Throwable e)
	{
		super(e);
		
	}
	
	public CaelumException(String err, Throwable e)
	{
		super(err, e);
		
	}
	
	public CaelumException(String err, Throwable e, boolean arg2, boolean arg3)
	{
		super(err, e, arg2, arg3);
	}
	
}

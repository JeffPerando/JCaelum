
package com.elusivehawk.engine;

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
	
	public CaelumException(Throwable e)
	{
		super(e);
		
	}
	
	public CaelumException(String err, Object... args)
	{
		this(String.format(err, args));
		
	}
	
}
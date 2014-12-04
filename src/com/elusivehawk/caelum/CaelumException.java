
package com.elusivehawk.caelum;

/**
 * 
 * Primary class for exceptions created by Caelum, because... reasons?
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
	
	public CaelumException(String err, Throwable e, Object... args)
	{
		super(String.format(err, args), e);
		
	}
	
	public CaelumException(String err, Object... args)
	{
		this(String.format(err, args));
		
	}
	
}

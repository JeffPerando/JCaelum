
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.CaelumException;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class RenderException extends CaelumException
{
	private static final long serialVersionUID = -75004262541484661L;
	
	public RenderException()
	{
		super();
		
	}
	
	public RenderException(String err)
	{
		super(err);
		
	}
	
	public RenderException(String err, Object... args)
	{
		super(err, args);
		
	}
	
	public RenderException(Throwable e)
	{
		super(e);
		
	}
	
	public RenderException(String err, Throwable e)
	{
		super(err, e);
		
	}
	
	public RenderException(String err, Throwable e, boolean arg2, boolean arg3)
	{
		super(err, e, arg2, arg3);
		
	}
	
}

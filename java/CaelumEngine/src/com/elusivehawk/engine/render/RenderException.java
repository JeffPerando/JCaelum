
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class RenderException extends RuntimeException
{
	private static final long serialVersionUID = 215154067784145194L;
	
	public RenderException()
	{
		super();
		
	}
	
	public RenderException(String err)
	{
		super(err);
		
	}
	
	public RenderException(Throwable e)
	{
		super(e);
		
	}
	
	public RenderException(String err, Throwable e)
	{
		super(err, e);
		
	}
	
}

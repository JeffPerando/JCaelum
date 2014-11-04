
package com.elusivehawk.caelum.render.gl;

import com.elusivehawk.caelum.render.RenderException;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GLException extends RenderException
{
	private static final long serialVersionUID = 5649271805648956485L;
	
	public GLException()
	{
		super();
		
	}
	
	public GLException(GLEnumError err)
	{
		super(err.name());
		
	}
	
	public GLException(String err, Object... args)
	{
		super(err, args);
		
	}
	
	public GLException(Throwable e)
	{
		super(e);
		
	}
	
	public GLException(String err, Throwable e)
	{
		super(err, e);
		
	}
	
	public GLException(String err, Throwable e, boolean arg2, boolean arg3)
	{
		super(err, e, arg2, arg3);
		
	}
	
}

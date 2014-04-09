
package com.elusivehawk.engine.render.opengl;

import com.elusivehawk.engine.render.RenderException;

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
	
	public GLException(int err)
	{
		this(((Integer)err).toString());
		
	}
	
	public GLException(String err)
	{
		super(err);
		
	}
	
	public GLException(Throwable e)
	{
		super(e);
		
	}
	
	public GLException(String err, Throwable e)
	{
		super(err, e);
		
	}
	
}

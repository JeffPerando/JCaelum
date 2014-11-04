
package com.elusivehawk.caelum;

import com.elusivehawk.util.Internal;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Internal
public class GameTickException extends CaelumException
{
	private static final long serialVersionUID = 8634027849112642888L;
	
	public GameTickException()
	{
		super();
		
	}
	
	public GameTickException(String err)
	{
		super(err);
		
	}
	
	public GameTickException(String err, Object... args)
	{
		super(err, args);
		
	}
	
	public GameTickException(Throwable e)
	{
		super(e);
		
	}
	
	public GameTickException(String err, Throwable e)
	{
		super(err, e);
		
	}
	
	public GameTickException(String err, Throwable e, boolean arg2, boolean arg3)
	{
		super(err, e, arg2, arg3);
		
	}
	
}

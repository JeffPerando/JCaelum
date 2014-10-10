
package com.elusivehawk.engine.network;

import com.elusivehawk.engine.CaelumException;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class NetworkException extends CaelumException
{
	private static final long serialVersionUID = 464992111315007812L;
	
	public NetworkException()
	{
		super();
		
	}
	
	public NetworkException(String err)
	{
		super(err);
		
	}
	
	public NetworkException(String err, Object... args)
	{
		super(err, args);
		
	}
	
	public NetworkException(Throwable e)
	{
		super(e);
		
	}
	
	public NetworkException(String err, Throwable e)
	{
		super(err, e);
		
	}
	
	public NetworkException(String err, Throwable e, boolean arg2, boolean arg3)
	{
		super(err, e, arg2, arg3);
		
	}
	
}
